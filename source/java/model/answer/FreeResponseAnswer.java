package model.answer;
/**
 * A FreeResponseAnswer represents an answer to a FreeResponseQuestion
 *
 * @author Jacob Garcia
 */
public class FreeResponseAnswer extends Answer {
   /**
    * Get a FreeResponseAnswer from the db
    * 
    * @param answerId
    */
   public FreeResponseAnswer(long answerId) {
      super(answerId);
   }

   /**
    * Create a new FreeResponseAnswer... add it to db and return it
    * 
    * @param The answer
    * @return the newly created CodeAnswer
    */
   public static FreeResponseAnswer newFreeResponseAnswer(String answer) {
      return new FreeResponseAnswer(newAnswer("FreeResponse", answer));
   }
   
   /**
    * Create a new FreeResponseAnswer and associate it with the db
    * 
    * @param questionId the id of the question this is an answer to
    * @param testSubmissionId the id of the test submission this is on
    * @return the newly created Answer
    */
   public static CodeAnswer newStudentFreeResponseAnswer(long questionID, long testSubmissionId) {
      return new CodeAnswer(newStudentAnswer("FreeResponse", questionID, testSubmissionId));
   }
   
   /**
    * Grab a FreeResponseAnswer from the database given the the
    * question it's an answer to and the test submission it's an answer
    * on
    * 
    *  @param questionId id of question this is an answer to
    *  @param testSubmissionId id of the test submission this is for
    *  @return the student's answer 
    */
   public static FreeResponseAnswer getStudentFreeResponseAnswer(long questionId, long testSubmissionId) {
      return new FreeResponseAnswer(getStudentAnswerId(questionId, testSubmissionId));
   }
   
   /**
    * Figure out whether this answer is the same as some other answer
    */
   public boolean matches(Answer that) {
      return true;
   }
   
   /**
    * Returns the answer to the Question
    *
    * @return the answer to the Question
    */
   public String getAnswer() {
	   return getRawAnswer();
   }

   /**
    * Sets the answer to the Question
    * 
    * @param given the answer the student gave
    * 
    pre:
    post: answer.equals(given);
    */
   public void setAnswer(String given) {
	   setRawAnswer(given);
   }
   
   /**
	* Creates a copy of an existing FreeResponseAnswer.
	*
	* @return a copy of the current FreeResponseAnswer.
	*/ 
   public FreeResponseAnswer copy() {
	   FreeResponseAnswer frAnswer = (FreeResponseAnswer)super.copy();
	   return frAnswer;
   }
}
