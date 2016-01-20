package model.answer;

/**
 * An answer for a coding question.
 * 
 * @author coltonstapper
 */
public class CodeAnswer extends Answer {
   /**
    * Grab a CodeAnswer from the database given the answerId
    * 
    * @param answerId
    */
   public CodeAnswer(long answerId) {
      super(answerId);
   }

   /**
    * Create a new CodeAnswer... add it to db and return it
    * 
    * @param the
    *           answer
    * @return the newly created CodeAnswer
    */
   public static CodeAnswer newCodeAnswer(String answer) {
      return new CodeAnswer(newAnswer("Code", answer));
   }
   
   /**
    * Create a new CodeAnswer and associate it with the db
    * 
    * @param questionId the id of the question this is an answer to
    * @param testSubmissionId the id of the test submission this is on
    * @return the newly created codeAnswer
    */
   public static CodeAnswer newStudentCodeAnswer(long questionID, long testSubmissionId) {
      return new CodeAnswer(newStudentAnswer("Code", questionID, testSubmissionId));
   }
   
   /**
    * Grab a CodeAnswer from the database given the the
    * question it's an answer to and the test submission it's an answer
    * on
    * 
    *  @param questionId id of question this is an answer to
    *  @param testSubmissionId id of the test submission this is for
    *  @return the student's answer 
    */
   public static CodeAnswer getStudentCodeAnswer(long questionId, long testSubmissionId) {
      return new CodeAnswer(getStudentAnswerId(questionId, testSubmissionId));
   }

   /**
    * Figure out whether this answer is the same as some other answer
    */
   public boolean matches(Answer that) {
      return true;
   }

   /**
    * Returns the answerText associated with this CodeAnswer.
    *
    * @return The answerText associated with this CodeAnswer.
    */
   public String getAnswerText() {
      return getRawAnswer();
   }

   /**
    * Updates the answerText associated with this CodeAnswer.
    *
    * @param text
    *           the string to be associated with this CodeAnswer's answerText.
    */
   public void setAnswerText(String text) {
      setRawAnswer(text);
   }

   /**
    * Creates a copy of an existing CodeAnswer.
    *
    * @return a copy of the current CodeAnswer.
    */
   public CodeAnswer copy() {
      CodeAnswer copiedCodeAnswer = (CodeAnswer) super.copy();
      copiedCodeAnswer.setAnswerText(getAnswerText());
      return copiedCodeAnswer;
   }
}
