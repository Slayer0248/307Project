package model.question;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.question.Question;
import model.util.DBAccess;

/**
 * Holds a list of questions.
 * 
 * @author Jeet Shah
 */
public  class QuestionBank {
	/**
	 * The questions.
	 */
	private List<Question> questions;
	/**
	 * The database.
	 */
	private DBAccess database;
	/**
	 * The owner id.
	 */
	private long ownerId;
	
	/**
	 * Creates a question bank.
	 * @param ID
	 */
	public QuestionBank(long ID){
		this.ownerId = ID;
		this.database = new DBAccess();
		
		
		populateFromDatabase();
	}
	
	/**
	 * Populates questions from the database.
	 */
	private void populateFromDatabase() {
		// TODO Auto-generated method stub
		this.questions = new ArrayList<Question>();
		try{
			ResultSet questionRecords = database.select("Questions", "questionId" , 
					"ownerId ="+this.ownerId);
			System.out.println(questionRecords.getFetchSize());
			while(questionRecords.next()){
				Question questionObject = new Question(questionRecords.getLong("questionId"), ownerId);
	            System.out.printf("Question '%s' added to questionBank\n", questionObject.getQuestionName());
				this.questions.add(questionObject);
				
			}
			database.closeConnection();
		}catch (Exception e){
	         System.out.println("Error occured while initializing test bank for user "
	                 + this.ownerId);
	           e.printStackTrace();
			
			
			
		}
		
		
	}
	
	/**
	 * Returns the questions.
	 * @return
	 */
	public List<Question> getQuestions(){
		
		return questions;
	}

	/**
	 * Adds a question to the question bank.
	 * 
	 * @param question is the object question to add to the list
	 post: exists(Question question; question != null; questions.contains(question));
	 */
	public void addQuestion(Question question){
		questions.add(question);
	}
	
	/**
	 * Removes a question from the question bank.
	 * 
	 * @param question removes the particular question from the list
	 post: exists(Question question; question != null; questions.contains(question));
	 */
	public void removeQuestion(Question question){
		questions.remove(question);
		question.deleteQuestion();
		
	}
	
	/**
	 * Get the question at the specified index in the question bank.
	 * 
	 * @param index is the index value to get the question from
	 * @return the question at that specific index in the list
	 pre: index >= 0 && index < questions.size();
	 */
	public Question getQuestion(int index){
		return questions.get(index);
		
	}
	
	/**
	 * Sets the question at the specified index in the question bank.
	 * 
	 * @param question is the question to add to the list
	 * @param index is the index to add the question in the list
	 */
	public void setQuestion(Question question, int index){
		questions.add(index, question);
	
	}
}
