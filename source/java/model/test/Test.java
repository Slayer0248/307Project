package model.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.answer.Answer;
import model.question.Question;
import model.util.DBAccess;

/**
 * A Test object.
 *
 * @author Clay Jacobs
 */
public class Test {
  
   /**
    * The database for the test tool.
    */  
   private DBAccess database;
  
   /**
    * The id of this test.
    */  
   private long testId;
   
   /**
    * The id of the User who owns this test.
    */  
   private long ownerId;
  
   /**
    * Name of a test.
    */  
   private String name;
   
   /**
    * Section in which the test is given.
    */
   private long sectionId;
   
   /**
    * Description of a test.
    */
   private String description;
   
   /**
    * All the questions on a test in order.
    */
   private ArrayList<Question> questions;

   
   /**
    * Tags to search for a test.
    */
   private HashSet<String> tags;
   
   /**
    * Calling Test with no parameters creates a new test to add to db
    */
   public Test() {
      tags = new HashSet<String>();
      questions = new ArrayList<Question>();
      try {
		 database = new DBAccess();
	  } catch (Exception e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
	  }
   }
   
   
   /**
    * Calling Test with a passed testID pulls an existing test from the database
    * 
    * @param testId
    */
   public Test(long testId) {
	  this.testId = testId;
      tags = new HashSet<String>();
      questions = new ArrayList<Question>();
   }
   
   
   /**
    * Calling Test with a passed testID pulls an existing test from the database
    * 
    * @param testId
    */
   public Test(long testId, long ownerId) {
	  this.testId = testId;
	  this.ownerId = ownerId;
      tags = new HashSet<String>();
      questions = new ArrayList<Question>();
      try {
		database = new DBAccess();
		ResultSet results = database.select("Tests", "*", "testId = " + testId + " AND ownerId = " + ownerId);
		while (results.next()) {
			setName(results.getString("name"));
			setDescription(results.getString("description"));
		}
		database.closeConnection();
		
		results = database.select("TestSections", "*", "testId = " + testId);
		while (results.next()) {
			setSectionId(results.getLong("sectionId"));
		}
		database.closeConnection();
		
		results = database.select("TestQuestions", "*", "testId = " + testId);
		while (results.next()) {
		   questions.add(Question.getQuestionFromDb(
		         results.getLong("questionId"), this.getId(), this.getOwnerId()));
		}
		database.closeConnection();
	  } catch (Exception e) {
	      // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
   }
   
   /**
	* Returns the id associated with this answer.
	*
	* @return The id associated with this answer.
	pre:
    post:
	*/
   public long getId() {
      return testId;
   }
	
   /**
	* Sets the id associated with this answer.
	*
	* @param id the id to associate with this answer.
	pre: !exists(Test other; 
             database.contains(other); 
               other.getId() == id);
    post: testId == id;
	*/
   public void setId(long id) {
	   testId = id;
   
   }

   /**
	* Returns the owner id associated with this answer.
	*
	* @return The owner id associated with this answer.
	pre:
    post:
	*/
   public long getOwnerId() {
      return ownerId;
   }
	
   /**
	* Sets the owner id associated with this answer.
	*
	* @param id the owner id to associate with this answer.
	*/
   public void setOwnerId(long id) {
	   ownerId = id;
   
   }
   
   /**
    * Adds question at the end of a test.
    *
    * @param question the question to add to the current Test.
    pre:
    post:
    */
   public void addQuestion(Question question) {
   
	   System.out.println("Added the question!");
	   questions.add(question);
   }
   
   /**
    * Removes the question at index.
    *
    * @param index the index of the question to remove from the current Test.
    * 
    pre: index >= 0 && index < questions.size();
    post: 
    */
   public void removeQuestion(int index) {
	   System.out.println("Removed question!");
	   questions.remove(index);
   
   }
   

   /**
    * Sort the list of questions on a test by sortCondition in order order.
    * Valid sort conditions are "Name", "Difficulty", "Time", and "Question type".
    * Valid orderings are "Ascending" or "Descending".
    *
    * @param sortCondition The Question property by which the current Test's questions should be sorted
    * @param order The order in which to sort the Questions.
    pre:
    post:
    */
   public void sortBy(String sortCondition, String order) {
   
   
   }

   /**
    * Returns the question at index.
    *
    * @param index the index of a question in the current Test
    * @return the Question at the specified index 
    pre:
    post:
    */
   public Question getQuestion(int index) {
       Question result = questions.get(index);
       return result;
   }
   
   /**
    * Sets the question at index to question.
    *
    * @param question the question to update at the specified index of a test.
    * @param index the specified index of a question in the current Test
    pre:
    post:
    */
   public void setQuestion(Question question, int index) {
       questions.set(index, question);
   }
   
   /**
    * Returns the correct answer at index.
    *
    * @param index the index of a question in the current Test
    * @return the correct Answer to the Question at the specified index
    pre:
    post: 
    */
   public Answer getCorrectAnswer(int index) {
      Answer correctAnswer = questions.get(index).getCorrectAnswer();
      return correctAnswer;
   }
   
   /**
    * Sets the correct answer at index to answer.
    *
    * @param answer the Answer to use as the correct answer.
    * @param index the specified index of a question in the current Test
    pre:
    post:
    */
   public void setCorrectAnswer(Answer answer, int index) {
      Question curQuestion = questions.get(index);
      curQuestion.setCorrectAnswer(answer);
   }
   
   /**
    * Returns the student answer at index.
    *
    * @param index the index of a question in the current Test
    * @return the student Answer to the Question at the specified index
    pre:
    post: 
    */
   public Answer getStudentAnswer(int index) {
	   Answer studentAnswer = questions.get(index).getStudentAnswer();
	   return studentAnswer;
   }
   
   /**
    * Sets the student answer at index to answer.
    *
    * @param answer the Answer to use as the student answer.
    * @param index the specified index of a question in the current Test
    pre:
    post:
    */
   public void setStudentAnswer(Answer answer, int index) {
	   Question curQuestion = questions.get(index);
	   curQuestion.setStudentAnswer(answer);
   }
   
   /**
    * Adds a set of tags to a test.
    *
    * @param newTags the tags to add to the current Test.
    pre:
    	newTags != null
    post:
    */
   public void addTags(Set<String> newTags) {
	   newTags.removeAll(tags);
	   tags.addAll(newTags);
   
   }
   
   /**
    * Removes all tags in oldTags from a test.
    *
    * @param oldTags the set of tags to remove from the current Test's tags.
    pre:
    post:
    */
   public void removeTags(Set<String> oldTags) {
	   tags.removeAll(oldTags);
   }
   
   /**
    * Returns whether this test is a result given a search query
    *
    * @param search the search text entered in the test bank.
    * @return true if this test will show up in this search. False otherwise.
    pre:
    post:
    */
   public boolean isSearchResult(String search) {
     
	   boolean isSearchResult = false;
	   String[] words = search.split("\\W+");
	   for (int i=0; i<words.length || !isSearchResult; i++) {
		   if (tags.contains(words[i]) || name.contains(words[i]) || description.contains(words[i])) {
			   isSearchResult = true;
		   }
	   }
	   //while (scanner.h)
       return isSearchResult;
   }
   
   /**
    * Returns all the tags for the current test
    *
    * @return All the tags for the current test
    pre:
    post:
    */
   public Set<String> getTags() {
      return tags;
   }
   
   
   /**
    * Creates a copy of an existing test.
    *
    * @return a copy of the current Test.
    pre:
    post:
    */ 
   public Test copy() {
	  Test copiedTest = new Test(this.testId, this.ownerId);
      return copiedTest;
   }
   
   /**
    * Sets the name of a test to testName.
    *
    * @param testName
    pre:
    	testName != null
    post:
    */  
   public void setName(String testName) {
	   name = testName;
	   System.out.println("Set Test Name!");
   }
   
   /**
    * Sets the sectionId in which the test is given to sectionId.
    *
    * @param id
    pre:
    	id >= 0
    post:
    */
   public void setSectionId(long id) {
	   sectionId = id;
   }
   
   /**
    * Sets the description of a test to testDescription.
    *
    * @param testDescription
    pre:
    	testDescription != null
    post:
    */
   public void setDescription(String testDescription) {
	   description = testDescription;
       System.out.println("in model.test.Test.setDescription()");
   
   }
   
   
   
   /**
    * @return The name of the current test.
    pre:
    post:
    */  
   public String getName() {
      return name;
   }
   
   /**
    * @return The sectionId in which the current test is given.
    pre:
    post:
    */
   public long getSectionId() {
      return sectionId;
   }
   
   /**
    * @return The description of the current test.
    pre:
    post:
    */
   public String getDescription() {
      return description;
   }
   
   /**
    * @return The description of the current test.
    pre:
    post:
    */
   public List<Question> getAllQuestions() {
      return questions;
   }
   
   /**
    *  Resets the Test to have no questions.
 	*/
   public void deleteQuestions() {
	   this.questions = new ArrayList<>();
   }
   
   /**
    * @return The list of all correct answers on a given test
    pre:
    post:
    */
   public List<Answer> getAllCorrectAnswers() {
	  ArrayList<Answer> correctAnswers = new ArrayList<Answer>();
	  for (int i=0; i<questions.size(); i++) {
		  correctAnswers.add(questions.get(i).getCorrectAnswer());
	  }
      return correctAnswers;
   }
   
   
   /**
    * @return The list of all student answers on a test
    pre:
    post:
    */
   public List<Answer> getAllStudentAnswers() {
	   ArrayList<Answer> studentAnswers = new ArrayList<Answer>();
	   for (int i=0; i<questions.size(); i++) {
		   studentAnswers.add(questions.get(i).getStudentAnswer());
	   }
	   return studentAnswers;
   }
   
   /**
    * Adds the Test to the database.
 	*/
   public void addToDatabase() {
	   try {
		
		  database.insert("Tests(testId, ownerId, name, description)", 
				   "VALUES(" + getId() + ", " + getOwnerId() + ", '" + getName() + "', '" + getDescription() + "')");

	   System.out.println("Got here in Test.");
	   String[] tagsList = tags.toArray(new String[0]);
	   for(int i=0; i<tagsList.length; i++) {
		   long tagId = -1; //= database.getOpenIdFor("Tags", "tagId");
		   
			  if (database.countAll("Tags", "tag = '" + tagsList[i] + "'") == 0) {
				 tagId = database.getOpenIdFor("Tags", "tagId");
				 database.insert("Tags(tagId, tag)", "VALUES(" + tagId + ", '" + tagsList[i] +"')"); 
			  }
			  else {
				 ResultSet results = database.select("Tags", "tagId", "tag = '" + tagsList[i] + "'");
				 while (results.next()) {
					 tagId = results.getLong("tagId");
				 }
				 database.closeConnection();
			  }
			  database.insert("TestTags(tagId, testId)", "VALUES(" + tagId + ", " + getId() + ")");
		  
	   }
       for(int i=0; i<questions.size(); i++) {
		   
			/*database.insert("QuestionNodes(questionId, correctAnswerId, testId, ownerId)", 
			      "VALUES("+ questions.get(i).getId() + ", " + questions.get(i).getCorrectAnswer().getId() + ", " + getId() + ", " + getOwnerId() + ")");*/
    	   if (database.countAll("TestQuestions", "testId = " + getId() + " AND questionId = " + questions.get(i).getId()) == 0) {
    		   database.insert("TestQuestions(testId, questionId)", "VALUES("+getId()+ ", "+ questions.get(i).getId() + ")");  
    	   }
    	   
		   
	   }
	   } catch (Exception e1) {
			  // TODO Auto-generated catch block
			  e1.printStackTrace();
		   }
   }
   
   /**
    * Adds the test to the test section table.
    */
   public void addToTestSectionsTable() {
	   try {
		 database.insert("TestSections(sectionId, testId)", "VALUES(" + getSectionId() + ", " + getId() + ")");
	   } catch (SQLException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	   }
   }
   
   /**
    * Convert the test to a string (return the name)
    * 
    * @return name of the test
    */
   public String toString() {
      return this.getName();
   }
}

