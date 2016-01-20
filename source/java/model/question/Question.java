package model.question;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.util.DBAccess;
import model.answer.Answer;
import model.answer.*;
import java.util.*;

/**
 * Creates a Question data structure
 * 
 * @author Jeet Shah
 */
public class Question {
	/**
	 * Test id.
	 */
	private long testId;
	/**
	 * Question id.
	 */
	private long questionId;
	/**
	 * The owner id.
	 */
	private long ownerId;

	/**
	 * The question name.
	 */
	private String QuestionName;
	/**
	 * The question.
	 */
	private String question;
	/**
	 * The question categories.
	 */
	private HashSet<String> QuestionCategories;
	/**
	 * The timed question.
	 */
	private boolean TimedQuestion;
	/**
	 * The time.
	 */
	private int Time;
	/**
	 * The question type.
	 */
	private String QuestionType;

	/**
	 * The correct answer.
	 */
	private Answer correctAnswer;
	/**
	 * The student answer.
	 */
	private Answer studentAnswer;

	/**
	 * The points.
	 */
	private double Points;
	/**
	 * The difficulty.
	 */
	private double Difficulty;
	/**
	 * The date created.
	 */
	private Date DateCreated;
	/**
	 * Last used.
	 */
	private Date LastUsedOn;
	/**
	 * The question image.
	 */
	private File questionImage = null;

	/**
	 * The database.
	 */
	private DBAccess database;

	/**
	 * Any special grading instructions associated with a Question. Primarily
	 * intended for FreeResponseQuestion and CodeQuestion, which are graded
	 * manually.
	 */
	private String gradingInstructions = "";

	/**
	 * Creates a question.
	 */
	public Question() {
		QuestionCategories = new HashSet<String>();
		try {
			database = new DBAccess();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates a question.
	 * @param questionId
	 */
	public Question(long questionId) {
		this.questionId = questionId;
		QuestionCategories = new HashSet<String>();
		DateCreated = new Date();

	}

	/**
	 * Creates a question.
	 * @param questionID
	 * @param ownerID
	 */
	public Question(long questionID, long ownerID) {
		this.questionId = questionID;
		this.ownerId = ownerID;
		QuestionCategories = new HashSet<String>();

		try {
			database = new DBAccess();
			ResultSet results = database.select("Questions", "*",
					"questionId = " + questionID + " AND ownerId = " + ownerId);
			while (results.next()) {
				setQuestionName(results.getString("name"));
				setcorrectAnswer(results.getLong("correctanswerId"));
				setQuestion(results.getString("question"));
				setQuestionType(results.getString("type"));
				setGradingInstructions(results.getString("gradingInstructions"));
				setTimedQuestion(results.getBoolean("isTimed"));
				if (TimedQuestion) {
					setTime(results.getInt("estimatedTime"));
				}
				setPoints(results.getInt("totalPoints"));
				setDifficulty(results.getInt("difficulty"));
				setCreationDate(results.getDate("creationDate"));
				setLastUsedDate(results.getDate("lastUsedDate"));
			}

			database.closeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Sets the correct answer.
	 * @param ID
	 */
	private void setcorrectAnswer(long ID) {
		Answer temp = new Answer(ID);
		if (temp.getType().contains("True")) {

			correctAnswer = new TrueFalseAnswer(temp.getId());
		}
		if (temp.getType().contains("Matching")) {

			correctAnswer = new MatchingAnswer(temp.getId());
		}
		if (temp.getType().contains("Multiple")) {

			correctAnswer = new MultipleChoiceAnswer(temp.getId(), ownerId);
		}
		if (temp.getType().contains("Free")) {

			correctAnswer = new FreeResponseAnswer(temp.getId());
		}

	}

	/**
	 * Sets the question from the database.
	 * @param questionId
	 * @param testId
	 * @param ownerId
	 * @return
	 * @throws Exception
	 */
	public static Question getQuestionFromDb(long questionId, long testId, long ownerId) throws Exception {
		DBAccess database = new DBAccess();

		ResultSet record = database.select("Questions", "type", "questionId = " + questionId);
		record.next();

		String qType = record.getString("type");
		database.closeConnection();

		if (qType.equals("Code")) {
			return new CodeQuestion(questionId, testId, ownerId);
		} else if (qType.equals("FreeResponse")) {
			return new FreeResponseQuestion(questionId, testId, ownerId);
		} else if (qType.equals("Matching")) {
			return new MatchingQuestion(questionId, testId, ownerId);
		} else if (qType.equals("MultipleChoice")) {
			return new MultipleChoiceQuestion(questionId, testId, ownerId);
		} else if (qType.equals("TrueFalse")) {
			return new TrueFalseQuestion(questionId, testId, ownerId);
		}
		throw new Exception("Unimplemented Question Type");
	}

	/**
	 * Gets the image for the question.
	 * 
	 * @return The image for the question.
	 */
	public File getQuestionImage() {
		return questionImage;
	}

	/**
	 * Sets the questionImage to use for the question
	 * 
	 * @param image
	 *            the questionImage to use for the question
	 */
	public void setQuestionImage(File image) {

	}

	/**
	 * Gets the correct Answer for the question.
	 * 
	 * @return The correct Answer for the question.
	 */
	public Answer getCorrectAnswer() {
		return correctAnswer;
	}

	/**
	 * Sets the correct Answer for the question
	 * 
	 * @param answer
	 *            the correct Answer to use for the question
	 */
	public void setCorrectAnswer(Answer answer) {
		correctAnswer = answer;
	}

	/**
	 * Gets the student's Answer for the question.
	 * 
	 * @return The student's Answer for the question.
	 */
	public Answer getStudentAnswer() {
		return studentAnswer;
	}

	/**
	 * Sets the student's Answer for the question
	 * 
	 * @param answer
	 *            the student's Answer to use for the question
	 */
	public void setStudentAnswer(Answer answer) {
		studentAnswer = answer;
	}

	/**
	 * Returns the Test id associated with this question.
	 *
	 * @return The Test id associated with this question.
	 */
	public long getTestId() {
		return testId;
	}

	/**
	 * Sets the Test id associated with this question.
	 *
	 * @param id
	 *            the Test id to associate with this question.
	 */
	public void setTestId(long id) {
		testId = id;
	}

	/**
	 * Returns the id associated with this question.
	 * 
	 * @return The id associated with this question.
	 */
	public long getId() {
		return questionId;
	}

	/**
	 * Sets the id associated with this question.
	 *
	 * @param id
	 *            the id to associate with this question. 
	post: questionId == id;
	 */
	public void setId(long id) {
		questionId = id;
	}

	/**
	 * Returns the user id associated with this question.
	 * 
	 * @return The user id associated with this question.
	 */
	public long getOwnertId() {
		return ownerId;
	}

	/**
	 * Sets the user id associated with this question.
	 *
	 * @param id
	 *            the user id to associate with this question.
	 */
	public void setOwnerId(long id) {
		ownerId = id;
	}

	/**
	 * Gets the QuestionName for the user
	 * 
	 * @return the string QuestionName
	 */
	public String getQuestionName() {
		return QuestionName;
	}

	/**
	 * Updates the QuestionName.
	 * 
	 * @param name
	 *            holds the QuestionName to update variable QuestionName 
	 post: QuestionName == name
	 */
	public void setQuestionName(String name) {
		QuestionName = name;
	}

	/**
	 * Gets the question type.
	 * @return
	 */
	public String getQuestionType() {
		return QuestionType;

	}

	/**
	 * Sets the question type.
	 * @param type
	 */
	public void setQuestionType(String type) {
		this.QuestionType = type;
	}

	/**
	 * Gets the Question for the user.
	 * 
	 * @return the Question String
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Updates the Question.
	 * 
	 * @param questionText
	 *            holds the question to update variable Question 
	pre: questionText != null; 
   post: question.equals(questionText)
	 */
	public void setQuestion(String questionText) {
		question = questionText;
	}

	/**
	 * Adds a set of categories to a question.
	 *
	 * @param newCategories
	 *            the categories to add to the current question. pre: post:
	 */
	public void addCategories(Set<String> newCategories) {
		newCategories.removeAll(QuestionCategories);
		QuestionCategories.addAll(newCategories);
	}

	/**
	 * Removes all categories in oldCategories from a question.
	 *
	 * @param oldCategories
	 *            the set of categories to remove from the current Question's
	 *            tags. pre: post:
	 */
	public void removeCategories(Set<String> oldCategories) {
		QuestionCategories.removeAll(oldCategories);
	}

	/**
	 * Returns whether this question is a result given a search query
	 *
	 * @param search
	 *            the search text entered in the question bank.
	 * @return true if this question will show up in this search. False
	 *         otherwise. pre: post:
	 */
	public boolean isSearchResult(String search) {
		boolean isSearchResult = false;
		String[] words = search.split("\\W+");
		for (int i = 0; i < words.length || !isSearchResult; i++) {
			if (QuestionCategories.contains(words[i]) || QuestionName.contains(words[i])
					|| question.contains(words[i])) {
				isSearchResult = true;
			}
		}

		return isSearchResult;
	}

	/**
	 * Returns all the categories for the current question
	 *
	 * @return All the categories for the current question pre: post:
	 */
	public Set<String> getCategories() {
		return QuestionCategories;
	}

	/**
	 * Returns if Question is timed or not
	 * 
	 * @return the boolean logic whether question is timed or not
	 */
	public boolean getTimedQuestion() {
		return TimedQuestion;
	}

	/**
	 * Updates the boolean if the Question is timed or not.
	 *
	 * @param timedquestion
	 *            is used to update the variable TimedQuestion 
	post: TimedQuestion == timedquestion
	 */
	public void setTimedQuestion(boolean timedquestion) {
		TimedQuestion = timedquestion;
	}

	/**
	 * Returns Time if and only if TimedQuestion is true.
	 * 
	 * @return the time for that question
	 */
	public int getTime() {
		return Time;
	}

	/**
	 * Updates the Time for the Question
	 * 
	 * @param time
	 *            is used to update the previous Time for the question.
	 */
	public void setTime(int time) {
		Time = time;
	}

	/**
	 * Gets the Points for the Question
	 * 
	 * @return the points for the Question
	 */
	public double getQuestionPoints() {
		return Points;
	}

	/**
	 * Updates the points for the Question
	 * 
	 * @param points
	 *            is used to update the previous Points for the question.
	 */
	public void setPoints(double points) {
		Points = points;
	}

	/**
	 * Gets the Difficulty for the Question
	 * 
	 * @return the difficulty for the Question
	 */
	public double getQuestionDifficulty() {
		return Difficulty;
	}

	/**
	 * Updates the difficulty for the Question
	 * 
	 * @param difficulty
	 *            is used to update the previous Difficulty for the Question.
	 */
	public void setDifficulty(double difficulty) {
		Difficulty = difficulty;
	}

	/**
	 * Gets the date the Question was created on
	 * 
	 * @return the DateTime the Question was created on
	 */
	public Date getCreationDate() {
		return DateCreated;
	}

	/**
	 * Updates the date the Question was created on
	 * 
	 * @param dateCreated
	 *            is used to update the previous DateCreated for the Question.
	 */
	public void setCreationDate(Date dateCreated) {
		DateCreated = dateCreated;
	}

	/**
	 * Gets the date the Question was last used on
	 * 
	 * @return the DateTime the Question was last used on
	 */
	public Date getLastUsedDate() {
		return LastUsedOn;
	}

	/**
	 * Updates the date the Question was last used on
	 * 
	 * @param lastUsed
	 *            is used to update the previous LastUsedOn for the Question.
	 pre: lastUsed != null;
	 post: LastUsedOn == lastUsed;
	 */

	public void setLastUsedDate(Date lastUsed) {
		LastUsedOn = lastUsed;
	}

	/**
	 * Returns the gradingInstructions associated with this Question.
	 *
	 * @return The gradingInstructions associated with this Question.
	 */
	public String getGradingInstructions() {
		return gradingInstructions;
	}

	/**
	 * Updates the gradingInstructions associated with this Question.
	 *
	 * @param instructions
	 *            the string to be associated with this Question's
	 *            gradingInstructions.
	 *            
	 pre: instructions != null;
	 post: gradingInstructions.equals(instructions);
	 */
	public void setGradingInstructions(String instructions) {
		gradingInstructions = instructions;
	}

	/**
	 * Creates a copy of an existing Question.
	 *
	 * @return a copy of the current Question.
	 */
	public Question copy() {
		Question copiedQuestion = new Question();
		return copiedQuestion;
	}

	/**
	 * Adds the question to the database.
	 */
	public void addToDatabase() {
		int booltoint = getTimedQuestion() ? 1 : 0;
		if (getQuestionType().contains("True")) {
			Answer temp = new Answer();
			temp.addToDatabase(getQuestionType(), "NULL");
			correctAnswer = new TrueFalseAnswer(temp.getId());
		}
		if (getQuestionType().contains("Matching")) {
			Answer temp = new Answer();
			temp.addToDatabase(getQuestionType(), "NULL");
			correctAnswer = new MatchingAnswer(temp.getId());
		}
		if (getQuestionType().contains("Multiple")) {
			Answer temp = new Answer();
			temp.addToDatabase(getQuestionType(), "NULL");
			correctAnswer = new MultipleChoiceAnswer(temp.getId(), ownerId);
		}
		if (getQuestionType().contains("Free")) {
			Answer temp = new Answer();
			temp.addToDatabase(getQuestionType(), "NULL");
			correctAnswer = new FreeResponseAnswer(temp.getId());
		}

		try {
			database.insert(
					"Questions(questionId,correctAnswerId,ownerId,type,name,question,gradinginstructions,image,isTimed,estimatedTime,totalPoints,difficulty,lastUsedDate,creationDate)",
					"VALUES(" + "NULL" + "," + correctAnswer.getId() + "," + getOwnertId() + ",'" + getQuestionType()
							+ "','" + getQuestionName() + "','" + getQuestion() + "','" + getGradingInstructions()
							+ "'," + "NULL" + "," + booltoint + "," + getTime() + "," + getQuestionPoints() + ","
							+ getQuestionDifficulty() + "," + "strftime('%s', CURRENT_TIMESTAMP)" + ","
							+ "strftime('%s', CURRENT_TIMESTAMP))");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Returns if in the database.
	 * @return
	 */
	public boolean isInDataBase() {
		boolean isInDatabase = false;
		try {
			if (database.countAll("Questions", "questionId = " + getId()) > 0) {
				isInDatabase = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isInDatabase;
	}

	/**
	 * Gets the answer id.
	 * @return
	 */
	public long getAnswerId() {
		return correctAnswer.getId();
	}

	/**
	 * Updates the database.
	 */
	public void updateInDatabase() {
		int isTime = getTimedQuestion() ? 1 : 0;
		database.update("Questions",
				"type = '" + getQuestionType() + "'," + "name = '" + getQuestionName() + "'" + ",question = '"
						+ getQuestion() + "' ,gradingInstructions = '" + getGradingInstructions() + "',isTimed ="
						+ isTime + ",estimatedTime =" + getTime() + ",totalPoints=" + getQuestionPoints()
						+ ",difficulty=" + getQuestionDifficulty(),
				"questionId = " + getId());
	}

	/**
	 * Delets the question from the database.
	 */
	public void deleteQuestion() {
		try {
			database.delete("Questions", "questionId = " + getId());
			database.delete("Answers", "answerId = " + correctAnswer.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
