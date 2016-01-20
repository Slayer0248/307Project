package model.answer;


/**
 * A MatchingAnswer represents an answer to a MatchingQuestion.
 *
 * @author Mark McKinney
 */
public class MatchingAnswer extends Answer {
	/**
	 * Create a MatchingAnswer object.
	 *
	 * @param numAnswers The number of items/answers to match.
	 */
	public MatchingAnswer(long answerId) {
	   super(answerId);
	}
   /**
    * Create a new MatchingAnswer... add it to db and return it
    * 
    * @return the newly created MatchingAnswer
    */
   public static MatchingAnswer newMatchingAnswer() {
      return new MatchingAnswer(newAnswer("Code", ""));
   }
   
   /**
    * Create a new CodeAnswer and associate it with the db
    * 
    * @param questionId the id of the question this is an answer to
    * @param testSubmissionId the id of the test submission this is on
    * @return the newly created codeAnswer
    */
   public static MatchingAnswer newStudentMatchingAnswer(long questionID, long testSubmissionId) {
      return new MatchingAnswer(newStudentAnswer("Matching", questionID, testSubmissionId));
   }

   /**
    * Grab a MatchingAnswer from the database given the the
    * question it's an answer to and the test submission it's an answer
    * on
    * 
    *  @param questionId id of question this is an answer to
    *  @param testSubmissionId id of the test submission this is for
    *  @return the student's answer 
    */
   public static MatchingAnswer getStudentMatchingAnswer(long questionId, long testSubmissionId) {
      return new MatchingAnswer(getStudentAnswerId(questionId, testSubmissionId));
   }

	/**
	 * Figure out whether this answer is the same as some other answer
	 * @param that The Answer to check against.
	 * @return Whether the Answer matches this one.
	 */
   public boolean matches(Answer that) {
      return true;
   }

	/**
	 * Gets the answer to the Question.
	 *
	 * @return The answer for the question.
	 */
	public int[] getAnswers() {
		String[] orig = getRawAnswer().split(",");
		int[] ret = new int[orig.length];
		for (int i = 0; i < orig.length; i++) {
		   ret[i] = Integer.parseInt(orig[i]);
		}
		return ret;
	}

	/**
	 * Sets part of the answer to the Question.
     *
	 * @param source The source that is being matched to.
	 * @param target The target selected to match the source.
	 *
	 */
	public void setAnswer(int source, int target) {
	   int[] newAnswers = getAnswers();
	   newAnswers[source] = target;
	   
	   setAnswers(newAnswers);
	}
	
	/**
	 * Set all the answers to the question
	 * 
	 * @param answers the new answers array to serialize and store
	 */
	public void setAnswers(int[] answers) {
      String[] tmp = new String[answers.length];
      for (int i = 0; i < tmp.length; i++) {
         tmp[i] = Integer.toString(answers[i]);
      }
      
      setRawAnswer(String.join(",", tmp));
	}
	
	/**
	 * Adds a new match to the end of the array.
	 */
	public void addMatch() {
	   int[] answers = getAnswers();
	   
		int[] resizedAnswers = new int[answers.length +1];
		for(int i=0; i< answers.length; i++) {
			resizedAnswers[i] = answers[i];
		}
		resizedAnswers[answers.length] = answers.length;
		
		setAnswers(resizedAnswers);
	}
	
	/**
	 * Removes match at the specified index
	 * 
	 * @param index the index of the answer to remove
	 *
	 * post: getAnswers().length == (getAnswers().length - 1);
	 */
	public void removeMatch(int index) {
	   int[] answers = getAnswers();
	   
		int[] resizedAnswers = new int[answers.length -1];
		for(int i=0; i< answers.length; i++) {
			if (i<index) {
			   if (answers[i] > answers[index]) {
				   resizedAnswers[i] = answers[i]-1; 
			   }
			   else {
				   resizedAnswers[i] = answers[i];
			   }
			}
			else if (i>index) {
				if (answers[i] > answers[index]) {
					resizedAnswers[i-1] = answers[i]-1;
				}
				else {
					resizedAnswers[i-1] = answers[i];	
				}
			}
		}
		//resizedAnswers[answers.length] = answers.length;
		setAnswers(resizedAnswers);
	}
	
	/**
	 * Creates a copy of an existing MatchingAnswer.
	 *
	 * @return a copy of the current MatchingAnswer.
	 */ 
	public MatchingAnswer copy() {
		MatchingAnswer copiedMatchingAnswer = (MatchingAnswer)super.copy();
		return copiedMatchingAnswer;
	}
}
