package model.question;

import model.answer.CodeAnswer;

/**
 * A CodeQuestion that extends Question
 *
 * @author Jacob Garcia
 */
public class CodeQuestion extends Question {
   /*private CodeAnswer correctAnswer;
   private CodeAnswer studentAnswer;*/

	
   /**
    * The code question constructor.
    */
	public CodeQuestion() {
	   
   }
 
   /**
    * Code Question constructor
    * @param questionId
 	* @param testId
 	* @param ownerId
 	*/
	public CodeQuestion(long questionId, long testId, long ownerId) {
	   // TODO Auto-generated constructor stub
   }

	/**
    * Gets the correct CodeAnswer for the question
    *
    * @return The correct CodeAnswer for the question
    */
   public CodeAnswer getCorrectAnswer() {
	   return (CodeAnswer)super.getCorrectAnswer();
   }
   
   /**
   * Sets the correct CodeAnswer for the question
   * @param answer the correct CodeAnswer to use for the question
   */
   public void setCorrectAnswer(CodeAnswer answer) {
	   super.setCorrectAnswer(answer);
   }
   
    /**
    * Gets the student's CodeAnswer for the question
    *
    * @return The student's CodeAnswer for the question
    */
   public CodeAnswer getStudentAnswer() {
	   return (CodeAnswer)super.getCorrectAnswer();
   }
   
   /**
   * Sets the student's CodeAnswer for the question
   * @param answer the student's CodeAnswer to use for the question
   */
   public void setStudentAnswer(CodeAnswer answer) {
	   super.setStudentAnswer(answer);
   }
   
   /**
	* Creates a copy of an existing CodeQuestion.
	*
	* @return a copy of the current CodeQuestion.
	*/ 
   public CodeQuestion copy() {
	   CodeQuestion copiedCodeQuestion = (CodeQuestion)super.copy();
	   return copiedCodeQuestion;
   }
}
