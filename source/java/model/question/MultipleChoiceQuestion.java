package model.question;

import model.answer.MultipleChoiceAnswer;
import java.util.*;
/**
 * A MultipleChoiceQuestion that extends of Question
 * @author Jeet
 */
public class MultipleChoiceQuestion extends Question {
   /*private MultipleChoiceAnswer correctAnswer;
   private MultipleChoiceAnswer studentAnswer;*/
   /**
    * The answers.
    */
	private ArrayList<String> answers;


   /**
    * The mc constructor.
    */
   public MultipleChoiceQuestion() {
	   answers = new ArrayList<String>();
   }
   
   /**
    * The mc question constructor.
    * @param questionId
    * @param testId
    * @param ownerId
    */
   public MultipleChoiceQuestion(long questionId, long testId, long ownerId) {
	   // TODO Auto-generated constructor stub
   }

/**
   * Gets the correct MultipleChoiceAnswer for the question.
   * @return The correct MultipleChoiceAnswer for the question.
   */
   public MultipleChoiceAnswer getCorrectAnswer() {
	   return (MultipleChoiceAnswer)super.getCorrectAnswer();
   }
   
   /**
   * Sets the correct MultipleChoiceAnswer for the question
   * @param answer the correct MultipleChoiceAnswer to use for the question
   */
   public void setCorrectAnswer(MultipleChoiceAnswer answer) {
	   super.setCorrectAnswer(answer);
   }
   
   
   /**
   * Gets the student's MultipleChoiceAnswer for the question.
   * @return The student's MultipleChoiceAnswer for the question.
   */
   public MultipleChoiceAnswer getStudentAnswer() {
	   return (MultipleChoiceAnswer)super.getStudentAnswer();
   }
   
   /**
   * Sets the student's MultipleChoiceAnswer for the question
   * @param answer the student's MultipleChoiceAnswer to use for the question
   */
   public void setStudentAnswer(MultipleChoiceAnswer answer) {
	   super.setStudentAnswer(answer);
   }
   
   
   /**
	* Adds an empty multiple choice option to the question.
	*
	* @return The index at which the new answer was added or -1
	* for failure.
	*/
   public int addAnswer() {
	   int finalIndex = -1;
	   int origSize = answers.size();
	   answers.add(origSize, "");
	   if (origSize < answers.size()) {
		   finalIndex = origSize;
	   }
	   
	   return finalIndex;
   }
		
   /**
	* Removes the multiple choice option at the specified index.
	*
	* @param index The index of the option to remove.
	* @return The new number of options if successful, 0 if the index wasn't found, 
	* or -1 if unsuccessful
	*/
   public int removeAnswer(int index) {
	   int finalSize = -1;
	   int origSize = answers.size();
	   if (!answers.isEmpty() && index >= 0 && index < answers.size()) {
		   answers.remove(index);
		   if (answers.size() < origSize) {
			   finalSize = answers.size();
		   }
	   }
	   
	   return finalSize;
   }
   
   /**
   * Returns the answers in text form the student gave.
   *
   * @return The answers to Question.
   */
   public List<String> getAnswerOptions() {
	   return answers;
   }
	
   /**
   * Returns the answer choice at the specified index.
   *
   * @return The answer choice at the specified index.
   */
   public String getAnswerText(int index) {
	   return answers.get(index);
   }

   /**
   * Sets the answer choice at the specified index.
   *
   * @param text The answer to use.
   * @param index The index of the answer choice to update
   */
   public void setAnswerText(String text, int index) {
	   answers.set(index, text);
   }
   
   /**
	* Creates a copy of an existing MultipleChoiceQuestion.
	*
	* @return a copy of the current MultipleChoiceQuestion.
	*/ 
   public MultipleChoiceQuestion copy() {
	   MultipleChoiceQuestion copiedMultipleChoiceQuestion = (MultipleChoiceQuestion)super.copy();
	   return copiedMultipleChoiceQuestion;
   }
}
