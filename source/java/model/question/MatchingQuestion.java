package model.question;

import model.answer.MatchingAnswer;
import java.util.*;
/**
 * A MatchingQuestion that extends of Question
 * @author Jeet
 */
public class MatchingQuestion extends Question {
   /*private MatchingAnswer correctAnswer;
   private MatchingAnswer studentAnswer;*/
   
   /**
    * the answer sources. 
    */
   private ArrayList<String> answerSources;
   /**
   * The answer targets.
   */
   private ArrayList<String> answerTargets;
   
   /**
   * The matching question constructor.
   */
   public MatchingQuestion() {
	   answerSources = new ArrayList<String>();
	   answerTargets = new ArrayList<String>();
   }
   
   /**
    * Matching question constructor.
    * @param questionId
    * @param testId
    * @param ownerId
    */
   public MatchingQuestion(long questionId, long testId, long ownerId) {
	  // TODO Auto-generated constructor stub
   }

/**
   * Gets the correct MatchingAnswer for the question
   * @return The correct MatchingAnswer for the question
   */
   public MatchingAnswer getCorrectAnswer() {
	   return (MatchingAnswer)super.getCorrectAnswer();
   }
   
   /**
   * Sets the correct MatchingAnswer for the question
   * @param answer the correct MatchingAnswer to use for the question
   */
   public void setCorrectAnswer(MatchingAnswer answer) {
	   super.setCorrectAnswer(answer);
   }
   
  /**
   * Gets the student's MatchingAnswer for the question.
   * @return The student's MatchingAnswer for the question.
   */
   public MatchingAnswer getStudentAnswer() {
	   return (MatchingAnswer)super.getStudentAnswer();
   }
   
   /**
   * Sets the student's MatchingAnswer for the question
   * @param answer the student's MatchingAnswer to use for the question
   */
   public void setStudentAnswer(MatchingAnswer answer) {
	   super.setCorrectAnswer(answer);
   }
   
   /**
	* Adds an empty matching option to the question.
	*
	* @return The index at which the new answer was added or -1
	* for failure.
	*/
   public int addAnswer() {
	   int addIndex = -1;
	   int originalSize = answerSources.size();
	   answerSources.add("");
	   answerTargets.add("");
	   this.getCorrectAnswer().addMatch();
	   if (originalSize < answerSources.size()) {
		   addIndex = originalSize;
	   }
	   
	   return addIndex;
   }
		
   /**
	* Removes the matching option at the specified index.
	*
	* @param index The index of the option to remove.
	* @return The new number of options if successful, 0 if the index wasn't found, 
	* or -1 if unsuccessful
	*/
   public int removeAnswer(int index) {
	   int finalSize = -1;
	   int origSize = answerSources.size();
	   if (!answerSources.isEmpty() && index >= 0 && index <answerSources.size()) {
		   answerSources.remove(index);
		   answerTargets.remove((this.getCorrectAnswer().getAnswers())[index]);
		   this.getCorrectAnswer().removeMatch(index);
		   if (answerSources.size() < origSize) {
			   finalSize = answerSources.size();
		   }
	   }
	   return finalSize;
   }
	
   /**
	* Swaps the position of two sources while maintaining the same source-target pairings
	*
	* @param first The index of a source option to swap.
	* @param second The index of a source option to swap with.
	*/
   public void swapSources(int first, int second) {
	    
	   String firstSource = answerSources.get(first);
	   String secondSource = answerSources.get(second);
	   answerSources.set(first, secondSource);
	   answerSources.set(second, firstSource);
	   
	   int temp = (this.getCorrectAnswer().getAnswers())[second];
	   this.getCorrectAnswer().setAnswer(second, (this.getCorrectAnswer().getAnswers())[first]);
	   this.getCorrectAnswer().setAnswer(first, temp);
   }
	
   /**
	* Swaps the position of two targets while maintaining the same source-target pairings
	*
	* @param first The index of a target option to swap.
	* @param second The index of a target option to swap with.
	*/
   public void swapTargets(int first, int second) {
	   String firstTarget = answerTargets.get(first);
	   String secondTarget = answerTargets.get(second);
	   answerTargets.set(first, secondTarget);
	   answerTargets.set(second, firstTarget);
	   
	   int temp = (this.getCorrectAnswer().getAnswers())[second];
	   this.getCorrectAnswer().setAnswer(second, (this.getCorrectAnswer().getAnswers())[first]);
	   this.getCorrectAnswer().setAnswer(first, temp);
   }
	
   /**
	* Returns the sources for the matching question.
	*
	* @return The sources for the matching question.
	*/
   public List<String> getSources() {
	   return answerSources;
   }
	
   /**
	* Returns the targets for the matching question.
	*
	* @return The targets for the matching question.
	*/
   public List<String> getTargets() {
	   return answerTargets;
   }
   
   /**
	* Gets the source text at the given index for a Question.
	*
	* @param index The position of the specified source.
	* @return The source text at the specified index. 
	*/
   public String getSource(int index) {
	   return answerSources.get(index);
   }

   /**
	* Sets the source text at the given index for a Question.
	*
	* @param source The source text to use.
	* @param index The position of the source to update.
	*/
   public void setSource(String source, int index) {
	   answerSources.set(index, source);
   }
	
   /**
    * Gets the target text at the given index for a Question.
	*
	* @param index The position of the specified target.
	* @return The target at the specified index. 
	*/
   public String getTarget(int index) {
	   return answerTargets.get(index);
   }

   /**
	* Sets the target text at the given index for a Question.
	*
	* @param target The target text to use.
	* @param index The position of the target to update.
	*/
   public void setTarget(String target, int index) {
	   answerTargets.set(index, target);
   }
   
   /**
	* Creates a copy of an existing MatchingQuestion.
	*
	* @return a copy of the current MatchingQuestion.
	*/ 
   public MatchingQuestion copy() {
	   MatchingQuestion copiedMatchingQuestion = (MatchingQuestion)super.copy();
	   return copiedMatchingQuestion;
   }
}
