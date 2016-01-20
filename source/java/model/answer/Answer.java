package model.answer;

import java.lang.RuntimeException;
import java.sql.ResultSet;

import model.util.DBAccess;

/**
 * An Answer either represents the correct answer for some question or the
 * answer that a student gave.
 *
 * @author Mark McKinney
 */
public class Answer {
   /**
    * The id of this answer.
    */
   private long answerId;
   /**
    * The answer type of this Answer.
    */
   private String answerType;
   /**
    * The answer for this Answer.
    */
   private String answer;

   /**
    * DBAccess for accessing the database.
    */
   private DBAccess database;

   /**
    * Creates an instance of Answer with access to the database.
    */
   public Answer() {
      this.database = new DBAccess();
   }

   /**
    * Answer has two constructors. The constructor with an answerId pulls the
    * record from the database with the given |answerId| and populates the data
    * with that record.
    * 
    * @param answerId
    *
    * pre: answerId >= 0;
    */
   public Answer(long answerId) {
      if (answerId >= 0) {
         this.answerId = answerId;
         this.database = new DBAccess();

         try {
            populateFromDatabase();
         } catch (Exception e) {
            e.printStackTrace();
         }
      } else {
         throw new RuntimeException("Answer id must be >= 0");
      }
   }

   /**
    * Get the student answer id for the associated questionId and testSubmissionId.
    *
    * @param questionId The question ID to use.
    * @param testSubmissionId The test submission ID to use.
    * @return The student answer ID.
    */
   public static long getStudentAnswerId(long questionId, long testSubmissionId) {
      DBAccess database = new DBAccess();
      long ret = -1;
      
      try {
         ResultSet record = database.select("QuestionNodes", "answerId", 
               "questionId = " + questionId + " AND testSubmissionId = " + testSubmissionId);
         
         while (record.next()) {
            ret = record.getLong("answerId");
         }
         database.closeConnection();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Couldn't get student answer ID");
         System.exit(1);
      }
      
      return ret;
   }

   /**
    * Create a new answer, add it to db, and return it
    * 
    * There are two situations where you'd want to create a new answer like this:
    * either you're editing the correct answer for a question, or you're a student
    * answering a question.  In this case, you should be editin ghte correct answer
    * 
    * @param answerType string representing what type of answer this is
    * @param answerText string representing what the answer is
    * 
    * @return answerId of new answer
    */
   public static long newAnswer(String answerType, String answerText) {
      DBAccess database = new DBAccess();
      long ret = -1;

      try {
         database.insert("Answers", "(answerType, answer)" + "VALUES ('" + answerType + "', '" + answerText + "')");

         ResultSet res = database.select("Answers", "MAX(answerId)", "");

         res.next();
         ret = res.getLong("MAX(optionId)");
         database.closeConnection();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      return ret;
   }
   
   /**
    * Create a new answer and associate it with a test submission
    * 
    * Use this constructor (or a derived constructor) when setting up the
    * answer that a student gives for a particular question.  
    * 
    * @param answerType string representing the type of answer
    * @param questionId the question that this is an answer to
    * @param testSubmissionId the test submission that this is an answer for
    * @return the id of the answer that was newly created
    */
   public static long newStudentAnswer(String answerType, long questionId, long testSubmissionId) {
      DBAccess database = new DBAccess();
      long answerId = newAnswer(answerType, "");
      
      try {
         database.insert("QuestionNodes", "(questionId, answerId, testSubmissionId) " +
               "VALUES (" + questionId + ", " + answerId + ", " + testSubmissionId + ")");
         database.closeConnection();
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return answerId;
   }

   /**
    * Add an answer to the database.
    * @param answerType The type of answer to add.
    * @param answerText The text of the answer.
    */
   public void addToDatabase(String answerType, String answerText) {
      DBAccess database = new DBAccess();
      long ret = -1;

      try {
         database.insert("Answers", "(answerType, answer)" + "VALUES ('" + answerType + "', '" + answerText + "')");

         ResultSet res = database.select("Answers", "MAX(answerId)", "");
         answerId = res.getLong("MAX(answerId)");
         res.next();
         ret = res.getLong("MAX(answerId)");
         database.closeConnection();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

   /**
    * Grab all the fields in the database row associated with me
    * 
    * @throws Exception
    */
   private void populateFromDatabase() throws Exception {
      ResultSet results = database.select("Answers", "*", "answerId = " + this.answerId);
      while (results.next()) {
         this.answerType = results.getString("answerType");
         this.answer = results.getString("answer");
      }
      database.closeConnection();
   }

   /**
    * Determine whether this answer matches another answer
    * 
    * Usually this would be done like this: if
    * (correctAnswer.matches(userAnswer)) the user got it right! else the user
    * got it wrong o:
    * 
    * @param other
    * @return Whether or not the answer matches this one.
    */
   public boolean matches(Answer other) {
      return this.answer.equals(other.getRawAnswer());
   }

   /**
    * Returns the id associated with this answer.
    *
    * @return The id associated with this answer.
    */
   public long getId() {
      return answerId;
   }

   /**
    * Gets the answer string associated with this entry
    * 
    * @return the answer string
    */
   public String getRawAnswer() {
      return this.answer;
   }

   /**
    * Sets the answer string associated with this entry and put it in the db
    * 
    * @param answer...
    *           the new raw answer text as a string
    */
   public void setRawAnswer(String answer) {
      this.answer = answer;

      this.database.update("Answers", "answer='" + this.answer + "'", "answerId = " + this.answerId);
   }

   /**
    * Creates a copy of an existing answer.
    *
    * @return a copy of the current Answer.
    */
   public Answer copy() {
      return null;
   }

   /**
    * Get the type of the question.
    * @return The type of the question.
    */
   public String getType() {
      return this.answerType;
   }

   /**
    * Set the type of the question.
    * @param type The type to set the question to.
    *
    * post: this.answerType == type;
    */
   public void setType(String type) {
      this.answerType = type;
   }

   /**
    * Returns 0 if these answers are the same (other than ID) returns 1 or -1
    * otherwise
    * 
    * @param that The answer to compare to.
    * @return 0 if these answers are the same (other than ID); returns 1 or -1 otherwise.
    */
   public int compareTo(Answer that) {
      return this.answerType.compareTo(that.answerType) * this.answer.compareTo(that.answer);
   }
}
