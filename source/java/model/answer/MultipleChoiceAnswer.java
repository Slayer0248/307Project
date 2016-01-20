package model.answer;

import java.lang.RuntimeException;
import java.sql.ResultSet;
import java.util.*;

import model.util.DBAccess;

/**
 * A MultipleChoiceAnswer represents an answer to a MultipleChoiceQuestion.
 *
 * @author Mark McKinney
 */
public class MultipleChoiceAnswer extends Answer {
   /**
    * A list of AnswerOption objects for this Answer.
    */
   private List<AnswerOption> options;
   /**
    * DBAccess for accessing the database.
    */
   private DBAccess database;
   /**
    * The question id this Answer is assigned to.
    */
   private long questionId;

   /**
    * Create a MultipleChoiceAnswer from the database
    *
    * pre: answerId >= 0 && questionId >= 0;
    */
   public MultipleChoiceAnswer(long answerId, long questionId) {
      super(answerId);
      if (answerId >= 0 && questionId >= 0) {

         this.questionId = questionId;

         this.database = new DBAccess();
         this.questionId = questionId;

         try {
            populateOptionsFromDatabase();
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      } else {
         throw new RuntimeException("answerId and questionId must be valid longs >= 0: " + answerId + " " + questionId);
      }
   }

   /**
    * Create a new AnswerOption associated with this test
    * 
    * @param option Answer options.
    * @param position Position to be added at.
    * @return
    */
   public AnswerOption addAnswerOption(String option, int position) {
      return AnswerOption.newAnswerOption(questionId, option, position);
   }

   /**
    * Create a new MultipleChoiceAnswer... add it to db and return it
    * 
    * @param questionId
    *           ID of the question this is an answer to
    * @param answer
    *           the number of the answer option
    * @return the newly created MultipleChoiceAnswer
    */
   public static MultipleChoiceAnswer newMultipleChoiceAnswer(long questionId, long answer) {
      return new MultipleChoiceAnswer(newAnswer("MultipleChoice", Long.toString(answer)), questionId);
   }
   
   /**
    * Create a new Answer and associate it with the db
    * 
    * @param questionId the id of the question this is an answer to
    * @param testSubmissionId the id of the test submission this is on
    * @return the newly created Answer
    */
   public static MultipleChoiceAnswer newStudentMultipleChoiceAnswer(long questionID, long testSubmissionId) {
      return new MultipleChoiceAnswer(newStudentAnswer("MultipleChoice", questionID, testSubmissionId),
            questionID);
   }
   
   /**
    * Grab a MultipleChoiceAnswer from the database given the the
    * question it's an answer to and the test submission it's an answer
    * on
    * 
    *  @param questionId id of question this is an answer to
    *  @param testSubmissionId id of the test submission this is for
    *  @return the student's answer 
    */
   public static MultipleChoiceAnswer getStudentMultipleChoiceAnswer(long questionId, long testSubmissionId) {
      return new MultipleChoiceAnswer(getStudentAnswerId(questionId, testSubmissionId),
            questionId);
   }

   /**
    * Grab the AnswerOptions from the database for the given answer
    * 
    * @throws Exception
    */
   private void populateOptionsFromDatabase() throws Exception {
      ResultSet results = database.select("AnswerOptions", "optionId", "questionId = " + this.questionId);
      while (results.next()) {
         options.add(new AnswerOption(results.getLong("optionId")));
      }
      database.closeConnection();
   }

   /**
    * Get a list of all the options for the test
    * 
    * @return list of all options
    */
   public List<AnswerOption> getOptions() {
      return options;
   }

   /**
    * Figure out whether this answer is the same as some other answer
    * @param that The Answer to check against.
    * @return Whether the Answer matches this one.
    */
   public boolean matches(Answer that) {
      return this.compareTo(that) == 0;
   }

   /**
    * Returns the answer the student gave.
    *
    * @return The answer the student gave.
    */
   public int getAnswer() {
      return new Integer(getRawAnswer());
   }

   /**
    * Sets the answer to the Question.
    *
    * @param answer
    *           the ID of the AnswerOption the student gave
    *
    * post: this.getAnswer() == answer;
    */
   public void setAnswer(long answer) {
      setRawAnswer(Long.toString(answer));
   }

   /**
    * Creates a copy of an existing MultipleChoiceAnswer.
    *
    * @return a copy of the current MultipleChoiceAnswer.
    */
   public MultipleChoiceAnswer copy() {
      MultipleChoiceAnswer copiedMultipleChoiceAnswer = (MultipleChoiceAnswer) super.copy();
      return copiedMultipleChoiceAnswer;
   }
}
