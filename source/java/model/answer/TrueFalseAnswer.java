package model.answer;

/**
 * A TrueFalseAnswer represents an answer to a TrueFalseQuestion.
 *
 * @author Mark McKinney
 */
public class TrueFalseAnswer extends Answer {
	/**
	 * Create a TrueFalseAnswer by looking up the record in the db for the given
	 * answerId
	 */
	public TrueFalseAnswer(long answerId) {
		super(answerId);
	}

   /**
    * Create a new TrueFalseAnswer... add it to db and return it
    * 
    * @param The answer
    * @return the newly created TrueFalseAnswer
    */
   public static TrueFalseAnswer newFreeResponseAnswer(boolean answer) {
      return new TrueFalseAnswer(newAnswer("TrueFalse", answer? "1" : "0"));
   }
   
   /**
    * Create a new Answer and associate it with the db
    * 
    * @param questionId the id of the question this is an answer to
    * @param testSubmissionId the id of the test submission this is on
    * @return the newly created Answer
    */
   public static TrueFalseAnswer newStudentTrueFalseAnswer(long questionID, long testSubmissionId) {
      return new TrueFalseAnswer(newStudentAnswer("Code", questionID, testSubmissionId));
   }
   
   /**
    * Grab a TrueFalseAnswer from the database given the the
    * question it's an answer to and the test submission it's an answer
    * on
    * 
    *  @param questionId id of question this is an answer to
    *  @param testSubmissionId id of the test submission this is for
    *  @return the student's answer 
    */
   public static TrueFalseAnswer getStudentTrueFalseAnswer(long questionId, long testSubmissionId) {
      return new TrueFalseAnswer(getStudentAnswerId(questionId, testSubmissionId));
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
	public boolean getAnswer() {
		return getRawAnswer().equals("1");
	}

	/**
	 * Sets the answer to the Question.
	 *
	 * @param answer The answer the student gave.
	 * post: this.getAnswer() == answer;
	 */
	public void setAnswer(boolean answer) {
		setRawAnswer(answer? "1" : "0");
	}
	
	/**
	 * Creates a copy of an existing TrueFalseAnswer.
	 *
	 * @return a copy of the current TrueFalseAnswer.
	 *
	 */ 
	public TrueFalseAnswer copy() {
		TrueFalseAnswer copiedTrueFalseAnswer = (TrueFalseAnswer)super.copy();
		return copiedTrueFalseAnswer;
	}
}
