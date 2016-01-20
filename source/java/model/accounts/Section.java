package model.accounts;

import model.accounts.User;
import model.test.Test;
import model.util.DBAccess;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
/**
 * A section represents a professor's class. A section has a name, a collection of students, and a collection of proctors.
 *
 * @author Jacob Garcia
 */
public class Section {

	/**
	 * for access to the database
	 */
	private DBAccess database;
   
	/**
	 * this section's id.
	 */
   private long sectionId;
   
   /**
    * This section's owner's id.
    */
   private long ownerId;
   
   /**
    * This section's name
    */
   private String name;
   
   /**
    * The students enrolled in the section
    */
   private ArrayList<User> students;
   
   /**
    * The proctors for this section
    */
   private ArrayList<User> proctors;
   
   /**
    * The tests for this section
    */
   private ArrayList<Test> tests;

   /**
    * basic Section constructor
    */
   public Section() {
	   students = new ArrayList<User>();
	   proctors = new ArrayList<User>();
	   tests = new ArrayList<Test>();
   }
   
   /**
    * Create a section object with data populated from the db
    * 
    * @param sectionId the Id of the section to create
    */
   public Section(long sectionId) {
	  ResultSet results = null;
	  
	  students = new ArrayList<User>();
	  proctors = new ArrayList<User>();
	  tests = new ArrayList<Test>();
	  try {
	     database = new DBAccess();
	     results = database.select("Sections", "*", "sectionId = " + sectionId);
				
	     while (results.next()) {
		    setId(results.getLong("sectionId"));
		    setOwnerId(results.getLong("ownerId"));
			setName(results.getString("sectionName"));
			//System.out.println("We got this s**t username: " + curUser.getUsername());
	     }
	     database.closeConnection();
	     
	     
	     /*Get users and tests associated with this Section*/
	     ArrayList<Long> userIds = new ArrayList<Long>();
	     ArrayList<Long> testIds = new ArrayList<Long>();
	     
	     
	     results = database.select("UsersSections", "*", "sectionId = " + getId());
	     while (results.next()) {
	    	 long userId = results.getLong("userId");
	    	 userIds.add(userId);
	     }
	     database.closeConnection();
	     
	     results = database.select("TestSections", "*", "sectionId = " + getId());
	     while (results.next()) {
	    	 long testId = results.getLong("testId");
	    	 testIds.add(testId);
	     }
	     database.closeConnection();
	     
	     for (int i=0; i<userIds.size(); i++) {
	    	User theUser = new User(userIds.get(i));
	    	addStudent(theUser);
	     }
	     for (int i=0; i<testIds.size(); i++) {
		    Test theTest = new Test(testIds.get(i), ownerId);
		    System.out.println(theTest.getName());
		    addTest(theTest);
		 }
				
      } catch (Exception e) {
         // TODO Auto-generated catch block
	     e.printStackTrace();
      }
   }
   
   /**
    * The sectionId for the current Section.
    *
    * @return the sectionId for the current Section
    */
   public long getId() {
	   return sectionId;
   }

   /** 
    * Sets the sectionId to use for the current Section
    *
    * @param id the sectionId to use for the current Section
    * 
    * pre:
    * post: sectionId.equals(id);
    */
   public void setId(long id) {
	   sectionId = id;
   }
   
   
   /**
    * The ownerId for the current Section.
    *
    * @return the ownerId for the current Section
    */
   public long getOwnerId() {
	   return ownerId;
   }

   /** 
    * Sets the ownerId to use for the current Section
    *
    * @param id the ownerId to use for the current Section
    * 
    * pre:
    * post: ownerId.equals(id);
    */
   public void setOwnerId(long id) {
	   ownerId = id;
   }

   
  /** The name for the current Section.
   *
   * @return the name for the current Section
   */
  public String getName() {
	   return name;
  }

  /** 
   * Sets the name to use for the current Section
   *
   * @param sectionName the name to use for the current Section
   * 
   * pre:
   * post: name.equals(sectionName);
   */
  public void setName(String sectionName) {
	   name = sectionName;
  }

   
   /**
    * The addStudent operation adds the given User to this Section.
    *
    * @param target the Student to be added
    */
   public void addStudent(User target) {
	   students.add(target);
	   if (target.getCanProctor()) {
		   proctors.add(target);
	   }
   }

   /** 
    * The deleteStudent operation removes the given User from this Section.
    *
    * @param target the Student to be removed
    */
   public void deleteStudent(User target) {
	   if (students.contains(target)) {
		   students.remove(target);
	   }
	   if (proctors.contains(target)) {
		   proctors.remove(target);
	   }
   }

   /** 
    * The addTest operation adds the given Test to this Section.
    *
    * @param target the Test to be added
    */
   public void addTest(Test target) {
	   tests.add(target);
   }
   
   /** 
    * The deleteStudent operation removes the given User from this Section.
    *
    * @param target the Student to be removed
    */
   public void deleteTest(Test target) {
	   tests.remove(target);
   }
   
   
   /**
    * Gets the list of students in this section.
    */
   public ArrayList<User> getStudents() {
	   return students;
   }
   
   /**
    * Gets the list of proctors in this section.
    */
   public ArrayList<User> getProctors() {
	   return proctors;
   }
   
   /**
    * Gets the list of tests in this section.
    */
   public ArrayList<Test> getTests() {
	   return tests;
   }
   
   /**
    * Gets a student at a given index in this section.
    *
    * @param index the index of the student to get.
    * @return the student at the given index.
    */
   public User getStudent(int index) {
	   return students.get(index);
   }
   
   /**
    * Sets the student at a given index in this section.
    *
    * @param index the index of the student to set.
    * @param user the student to set
    */
   public void setStudent(int index, User user) {
	   students.set(index, user);
   }
   
   /**
    * Gets a proctor at a given index in this section.
    *
    * @param index the index of the proctor to get.
    * @return the proctor at the given index.
    */
   public User getProctor(int index) {
	   return proctors.get(index);
   }
   
   /**
    * Sets the proctor at a given index in this section.
    *
    * @param index the index of the proctor to set.
    * @param user the proctor to set
    */
   public void setProctor(int index, User user) {
	   proctors.set(index, user);
   }
   
   /**
    * Gets a test at a given index in this section.
    *
    * @param index the index of the test to get.
    * @return the test at the given index.
    */
   public Test getTest(int index) {
	   return tests.get(index);
   }
   
   /**
    * Sets the test at a given index in this section.
    *
    * @param index the index of the test to set.
    * @param test the test to set
    */
   public void setTests(int index, Test test) {
	   tests.set(index, test);
   }
   
   /**
    * Convert this section object to a string by getting the name
    * 
    * @return name of section
    */
   @Override
   public String toString() {
      return this.getName();
   }
   
   /**
    * Get a list of all sections owned by a particular user
    * 
    * @param ownerId id of the user whose sections we want
    * @return list of Sections owned by the given user
    */
   public static List<Section> getSectionsOwnedByUser(long ownerId) {
      LinkedList<Section> ret = new LinkedList<Section>();
      
      try {
         DBAccess database = new DBAccess();
         
         ResultSet sectionRecords = database.select("Sections",
               "sectionId", "ownerId = " + ownerId);
         
         while (sectionRecords.next()) {
            ret.add(new Section(sectionRecords.getLong("sectionId")));
         }
         
         database.closeConnection();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         System.out.println("Error getting sections owned by user " + ownerId);
         e.printStackTrace();
      }
      
      return ret;
   }
   
   /**
    * Adds this to the database.
    */
   public void addToDatabase() {
	   try {
   	    DBAccess database = new DBAccess();
		   
		database.insert("Sections(sectionId, ownerId, sectionName)", 
				   "VALUES(" + getId() + ", " + getOwnerId() + ", '" + getName() + "')");
		//add professor's student alias?
		//database.insert("UsersSections(sectionId, userId, isEnrolled)", 
		//		   "VALUES(" + getId() + ", " + getOwnerId() + ", 1)");
		
	    System.out.println("Got here in Section.");
	   /*String[] tagsList = tags.toArray(new String[0]);
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
		   
			database.insert("QuestionNodes(questionId, correctAnswerId, testId, ownerId)", 
			      "VALUES("+ questions.get(i).getId() + ", " + questions.get(i).getCorrectAnswer().getId() + ", " + getId() + ", " + getOwnerId() + ")");
		   
	   }*/
	   } catch (Exception e1) {
			  // TODO Auto-generated catch block
			  e1.printStackTrace();
	   }
   }
}
