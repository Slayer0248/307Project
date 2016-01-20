package model.test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.answer.*;
import model.question.*;
import model.test.*;
import model.grading.Grade;
import model.util.DBAccess;

/**
 * Keeps track of the user's progress in answering test questions
 * during and after a proctoring.
 *
 * @author Andrew Nelson
 */
public class TestSubmission {
   /**
    * Id of the test submission that this is
    */
   private long testSubmissionId;
   /**
    * Id of the user whos submission this is
    */
   private long takerId;
   /**
    * Id of the user who will eventually grade this
    */
   private long graderId;
   /**
    * Grades the user got on this 
    */
   private List<Grade> grades;
   /**
    * Answers the user gave to this test
    */
   private List<Answer> answers;
   
   /**
    * The test that this is a submission for
    */
   private Test test;
   /**
    * The time the test is supposed to start
    */
   private Date startTime;
   /**
    * The time the test is uspposed to end
    */
   private Date endTime;
   
   /**
    * Our connection ot the database
    */
   private DBAccess database;
   
   /**
    * Create a TestSubmission object populating data from the db
    * 
    * Technically testSubmissionId is all this constructor logically
    * needs, but passing testId along with it means we don't have
    * to do an extra query.  If this is a problem, talk to Andrew and
    * he'll write you another constructor.
    * 
    * @param testId id of the test that is being given
    * @param testSubmissionId 
    pre: testId > 0 && testSubmissionId > 0
    */
   public TestSubmission(long testId, long testSubmissionId) {
      this.testSubmissionId = testSubmissionId;
      this.test = new Test(testId);
      this.answers = new ArrayList<Answer>();
      
      if (testId < 0 || testSubmissionId < 0) {
         throw new RuntimeException("Bad Input!");
      }
      
      try {
         database = new DBAccess();
         
         ResultSet submissionRecord = database.select("TestSubmissions", 
               "*", "testSubmissionId = " + testSubmissionId);
         
         while (submissionRecord.next()) {
            this.startTime = submissionRecord.getDate("startTime");
            this.endTime = submissionRecord.getDate("endTime");
            this.takerId = submissionRecord.getLong("takerId");
            this.graderId = submissionRecord.getLong("graderId");
            
            this.grades = new ArrayList<Grade>();
         }

         List<Question> questions = test.getAllQuestions();

         for (Question question : questions) {
            Answer answer;
            switch (question.getQuestionType()) {
               case "Code":
                  answer = CodeAnswer
                     .getStudentCodeAnswer(question.getId(), testSubmissionId);
                  break;
               case "FreeResponse":
                  answer = FreeResponseAnswer
                     .getStudentFreeResponseAnswer(question.getId(), testSubmissionId);
                  break;
               case "Matching":
                  answer = MatchingAnswer
                     .getStudentMatchingAnswer(question.getId(), testSubmissionId);
                  break;
               case "MultipleChoice":
                  answer = MultipleChoiceAnswer
                     .getStudentMultipleChoiceAnswer(question.getId(), testSubmissionId);
                  break;
               case "TrueFalse":
                  answer = TrueFalseAnswer
                     .getStudentTrueFalseAnswer(question.getId(), testSubmissionId);
                  break;
               default:
                  System.out.println("unsupported question type in TestSubmission");
                  throw new UnsupportedOperationException();
            }
            this.answers.add(answer);
         }
         
         database.closeConnection();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         System.out.println("Error occured while creating submission record with ID " +
               testSubmissionId);
         e.printStackTrace();
      }
   }
   
   /**
    * Create a TestSubmission and insert it into the db
    * 
    * @param testId id of the test that is being given
    * @param userID the id of the student the submission is being created for
    * @return the TestSubmission that we just made
    
    pre: testId > 0 && userId > 0 && startTime != null && endTime != null && graderId > 0
    
    */
   public static TestSubmission createNewTestSubmission(long testId, long userId,
         Date startTime, Date endTime, long graderId) {
      long testSubmissionId = -1;
      
      if (testId < 0 || userId < 0 || graderId < 0 || startTime == null || endTime == null) {
         throw new RuntimeException("Bad input");
      }
      
      try {
         DBAccess database = new DBAccess();
         Test test = new Test(testId);
         
         database.insert("TestSubmissions", 
               "(testSessionId, takerId, graderId, startTime, endTime) " +
               "VALUES ({testSessionId}, {takerId}, {graderId}, {startTime}, {endTime})"
                     .replace("{testSessionId}", Long.toString(testId))
                     .replace("{takerId}", Long.toString(userId))
                     .replace("{graderId}", Long.toString(graderId))
                     .replace("{startTime}", Long.toString(startTime.getTime() * 1000))
                     .replace("{endTime}", Long.toString(endTime.getTime() * 1000)));
         
         ResultSet submissionRecord = database.select("TestSubmissions", 
               "MAX(testSubmissionId)", "");
         
         while (submissionRecord.next()) {
            testSubmissionId = submissionRecord.getLong("MAX(testSubmissionId)");
         }

         List<Question> questions = test.getAllQuestions();

         for (Question question : questions) {
            Answer answer;
            switch (question.getQuestionType()) {
               case "Code":
                  answer = CodeAnswer
                     .newStudentCodeAnswer(question.getId(), testSubmissionId);
                  break;
               case "FreeResponse":
                  answer = FreeResponseAnswer
                     .newStudentFreeResponseAnswer(question.getId(), testSubmissionId);
                  break;
               case "Matching":
                  answer = MatchingAnswer
                     .newStudentMatchingAnswer(question.getId(), testSubmissionId);
                  break;
               case "MultipleChoice":
                  answer = MultipleChoiceAnswer
                     .newStudentMultipleChoiceAnswer(question.getId(), testSubmissionId);
                  break;
               case "TrueFalse":
                  answer = TrueFalseAnswer
                     .newStudentTrueFalseAnswer(question.getId(), testSubmissionId);
                  break;
               default:
                  System.out.println("unsupported question type in TestSubmission");
                  throw new UnsupportedOperationException();
            }
         }
         
         database.closeConnection();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         System.out.println("Error occured while creating submission record with ID " +
               testSubmissionId);
         e.printStackTrace();
      }
      
      return new TestSubmission(testId, testSubmissionId);
   }

   /**
    * Get the test
    * 
    * @return the test
    */
   public Test getTest() {
      return test;
   }

   /**
	* Returns the id of the test submission.
	*
	* @return The id of the test submission.
	
	post: 
	
	*/
   public long getTestSubmissionId() {
      return this.testSubmissionId;
   }
	
   /**
	* Sets the id to use for the test submission.
	*
	* @param id the id to use for the test submission
	*/
   public void setTestSubmissionId(long id) {
      
   }

   /**
	* Returns whether the Test has been fully graded yet.
	*
	* @return True if all test questions have been graded. False otherwise.
	*/
   public boolean isFullyGraded() {
      return false;
   }

   /**
	* Returns the grade on the question at the specified index.
	*
	* @param index the index of the question grade to update.
	* @return The grade on the question at the specified index.
	*/
   public Grade getGrade(int index) {
      return null;
   }
	
   /**
	* Sets the grade on the question at the specified index.
	*
	* @param grade the grade to use for the question
	* @param index the index of the question grade to update.
	post: grades.get(index) == grade;
	*/
   public void setGrade(Grade grade, int index) {
      this.grades.set(index, grade);
   }


   /**
	* Returns the user id of the student.
	*
	* @return The user id of the student.
	*/
   public long getStudentId() {
      return this.takerId;
   }
	
   /**
	* Sets the user id to use for the student.
	*
	* @param id the user id to use for the student
	*/
   public void setStudentId(long id) {
      this.takerId = id;
   }


   /**
	* Returns the user id associated with the grader of this answer.
	*
	* @return The user id associated with the grader of this answer.
	*/
   public long getGraderId() {
      return this.graderId;
   }
	
   /**
	* Sets the user id associated with this answer.
	*
	* @param id user id of the grader to associate with this answer.
	*/
   public void setGraderId(long id) {
      this.graderId = id;
   }

   /**
    * Change the start date/time of this user's test.  If the test has already
    * started (the startTime is in the past), this method will throw an
    * exception.
    *
    * @param newStartTime the new start time for this user's test
    */
   public void setStartTime(Date newStartTime) {
      this.startTime = newStartTime;
   }

   /**
    * Change the end date/time of this test.  If the test has already
    * ended, this method will throw an exception.
    * 
    * @param newEndTime the new end time for this user's test
    post: endTime == newEndTime;
    */
   public void setEndTime(Date newEndTime) {
      this.endTime = newEndTime;
   }

   /**
    * Get the number of points that were possible on the test
    *
    * @return maximum score that could be earned on the test 
    */
   public int getMaxScore() {
      return 0;
   }

   /**
    * Get the user's score on the test... the unit is just points
    * (how many points did the user get? See the test to see how many
    * points the test was out of) 
    *
    * @return number of points the user got on the test
    */
   public double getUserScore() {
      return 0;
   }

   /**
    * Get the number of questions the user answered.
    *
    * @return Number of questions answered by the user
    */
   public int getQuestionsAnswered() {
      return 0;
   }

   /**
    * Get all the answers the user gave
    *
    * @return collection of answers the user gave to a particular test
    */
   public List<Answer> getAnswers() {
      return new ArrayList<Answer>();
   }
   
}
