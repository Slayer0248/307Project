package model.question;

import model.answer.TrueFalseAnswer;

/**
 * A TrueFalseQuestion that extends of Question
 * @author Jeet
 */
public class TrueFalseQuestion extends Question {
   /*private TrueFalseAnswer correctAnswer ;
   private TrueFalseAnswer studentAnswer ;*/

   /**
    * Creates a true false question. 
    */
	public TrueFalseQuestion() {
	   
   }
	/**
	* Creates a true false question. 
	*/	
   public TrueFalseQuestion(long questionId, long testId, long ownerId) {
	   // TODO Auto-generated constructor stub
   }

   /**
   * Gets the correct TrueFalseAnswer for the question
   * @return the correct TrueFalseAnswer for the question
   */
   public TrueFalseAnswer getCorrectAnswer() {
	   return (TrueFalseAnswer)super.getCorrectAnswer();
   }
  
  
  /**
   * Sets the correct TrueFalseAnswer for the question
   * @param answer the correct TrueFalseAnswer to use for the question
   */
   public void setCorrectAnswer(TrueFalseAnswer answer) {
	   super.setCorrectAnswer(answer);
   }
   
   /**
   * Gets the student's TrueFalseAnswer for the question
   * @return the student's TrueFalseAnswer for the question
   */
   public TrueFalseAnswer getStudentAnswer() {
	   return (TrueFalseAnswer)super.getStudentAnswer();
   }
  
  
  /**
   * Sets the student's TrueFalseAnswer for the question
   * @param answer the student's TrueFalseAnswer to use for the question
   */
   public void setStudentAnswer(TrueFalseAnswer answer) {
	   super.setStudentAnswer(answer);
   }
   
   /**
	* Creates a copy of an existing TrueFalseQuestion.
	*
	* @return a copy of the current TrueFalseQuestion.
	*/ 
   public TrueFalseQuestion copy() {
	   TrueFalseQuestion copiedTrueFalseQuestion = (TrueFalseQuestion)super.copy();
	   return copiedTrueFalseQuestion;
   }
}
