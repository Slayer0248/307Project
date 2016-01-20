package model.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.util.DBAccess;

/**
 * Holds a list of tests.
 * 
 * @author coltonstapper
 */
public class TestBank {
	/**
	 * The owner id.
	 */
	private long ownerId;
	/**
	 * The list of tests.
	 */
	private List<Test> tests;
	/**
	 * The database.
	 */
	private DBAccess database;
	
	/**
	 * Create a TestBank object and populate it with all the tests
	 * owned by the user with |ownerId| id.
	 * 
	 * @param ownerId id of the owner of the test
	 */
	public TestBank(long ownerId) {
	   this.ownerId = ownerId;
	   this.database = new DBAccess();
	   
	   populateFromDatabase();
	}
	
	/**
	 * Populate test bank with data from the database
	 */
	private void populateFromDatabase() {
	   this.tests = new ArrayList<Test>();
	   
	   try {
         ResultSet testRecords = database.select("Tests", "testId",
               "ownerId = " + this.ownerId);
         
         while (testRecords.next()) {
            Test testObject = new Test(testRecords.getLong("testId"), ownerId);
            System.out.printf("Test '%s' added to testBank\n", testObject.getName());
            this.tests.add(testObject);
         }
         
         database.closeConnection();
      } catch (Exception e) {
         System.out.println("Error occured while initializing test bank for user "
               + this.ownerId);
         e.printStackTrace();
      }
	}
	
	/**
	 * Get a list of all the tests in the testBank
	 */
	public List<Test> getTests() {
	   return this.tests;
	}
	
	/**
	 * Adds a test to the test bank.
	 post: exists(Test test; test != null; tests.contains(test));
	 */
	public void addTest(Test test) {
	   
	}
	
	/**
	 * Removes a test from the test bank.
	 post: !exists(Test test; test != null; tests.contains(test));
	 */
	public void removeTest(Test test) {
		try {
			database.delete("Tests", "testId=" + test.getId());
			
			database.closeConnection();
		}
		catch (SQLException e) {
			System.out.println("Error occured while deleting a test for user: "
		               + this.ownerId);
		         e.printStackTrace();
		}
	}
	
	/**
	 * Get the test at the specified index in the test bank.
	 pre: index >= 0 && index < this.tests.size();
     
	 */
	public Test getTest(int index) {
	   return null;
	}
	
	/**
	 * Sets the test at the specified index in the test bank.
	 pre: index >= 0 && index <= this.tests.size();
	 */
	public void setTest(Test test, int index) {
	   
	}
}