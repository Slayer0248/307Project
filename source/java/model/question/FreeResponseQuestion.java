package model.question;

import model.answer.FreeResponseAnswer;

/**
 * A FreeResponseQuestion that extends Question
 *
 * @author Jacob Garcia
 */
public class FreeResponseQuestion extends Question {   
   /*private FreeResponseAnswer correctAnswer;
   private FreeResponseAnswer studentAnswer;*/

   /**
    * Constructor.
    */
	public FreeResponseQuestion() {
	   
   }
	
   /**
    * Constructor.
    * @param questionId
    * @param testId
    * @param ownerId
    */
	public FreeResponseQuestion(long questionId, long testId, long ownerId) {
	// TODO Auto-generated constructor stub
   }

	/**
    * Gets the correct FreeResponseAnswer for the question
    *
    * @return The correct FreeResponseAnswer for the question
    */
   public FreeResponseAnswer getCorrectAnswer() {
	   return (FreeResponseAnswer)getCorrectAnswer();
   }
   
   /**
   * Sets the correct FreeResponseAnswer for the question
   * 
   * @param answer the correct FreeResponseAnswer to use for the question
   *
   pre:
   post: correctAnswer.equals(answer);
   */
   public void setCorrectAnswer(FreeResponseAnswer answer) {
	   this.setCorrectAnswer(answer);
   }
   
   /**
    * Gets the student's FreeResponseAnswer for the question
    *
    * @return The student's FreeResponseAnswer for the question
    */
   public FreeResponseAnswer getStudentAnswer() {
	   return (FreeResponseAnswer)getStudentAnswer();
   }
   
   /**
   * Sets the student's FreeResponseAnswer for the question
   * 
   * @param answer the student's FreeResponseAnswer to use for the question
   * 
   pre:
   post: studentAnswer.equals(answer);
   */
   public void setStudentAnswer(FreeResponseAnswer answer) {
	   this.setStudentAnswer(answer);
   }
   
   /**
	* Creates a copy of an existing FreeResponseQuestion.
	*
	* @return a copy of the current FreeResponseQuestion.
	 */ 
   public FreeResponseQuestion copy() {
	   FreeResponseQuestion copiedFreeResponseQuestion = (FreeResponseQuestion)super.copy();
	   return copiedFreeResponseQuestion;
   }
}
