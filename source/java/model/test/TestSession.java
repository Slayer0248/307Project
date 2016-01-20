package model.test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import model.accounts.Section;
import model.accounts.User;
import model.grading.GradeExporter;
import model.util.DBAccess;

/**
 * Represents a test session (when you give a test to a section, that is one
 * test session).  A test can be given any number of times (in sequence or in
 * parallel).  Each time a test is given, an instance of this object is
 * created.  This object keeps track of the scheduled start and end date of
 * each test, as well as the progress of each student through that test.
 *
 * @author Andrew Nelson
 */
public class TestSession {
   /**
    * Id of the user who started this test session
    */
   private long ownerId;
   /**
    * the id of this test session as stored in the db
    */
   private long testSessionId;

   /**
    * the test that this is being given
    */
   private Test test;
   /**
    * The start time for this test
    */
   private Date startTime;
   /**
    * the end time for this test
    */
   private Date endTime;
   /**
    * the section this test is being given to
    */
   private Section section;
   /**
    * All the submissiosn to this test
    */
   private Collection<TestSubmission> submissions;
   
   /**
    * our database connection
    */
   private DBAccess database;

   /**
    * When TestSession is constructed with just testSessionId provided, it 
    * means the test already exists so we just get it from the database.
    * 
    * @param testSessionId id number of the test session
    
    pre: testSessionId > 0 && ownerId > 0
    */
   public TestSession(long testSessionId, long ownerId) {
	   this.testSessionId = testSessionId;
	   
	   if (testSessionId < 0 || ownerId < 0) {
	      throw new RuntimeException("Bad Input!");
	   }
      
      try {
         database = new DBAccess();
         
         ResultSet sessionRecord = database.select("TestSessions", "*", 
               "testSessionId = " + testSessionId);
         
         while (sessionRecord.next()) {
            this.test = new Test(sessionRecord.getLong("testId"), ownerId);
            this.startTime = sessionRecord.getDate("startTime");
            this.endTime = sessionRecord.getDate("endTime");
            this.section = new Section(sessionRecord.getLong("sectionId"));
         }
         database.closeConnection();
         
         this.submissions = new ArrayList<TestSubmission>();
         
         ResultSet submissionRecords = database.select("TestSubmissions", 
               "testSubmissionId", 
               "testSessionId = " + this.testSessionId);
         
         while (submissionRecords.next()) {
            this.submissions.add(
                  new TestSubmission(
                        this.test.getId(),
                        submissionRecords.getInt("testSubmissionId")));
         }
         
         database.closeConnection();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         System.out.println("An error occured accessing the database in TestSession.java");
         e.printStackTrace();
      }
   }
   
   /**
    * When TestSession is constructed with complete info, it means this is a
    * new test so populate this based on the passed values and add it to the
    * database.
    * 
    * @param test the test to give
    * @param startTime the start time of the test
    * @param endTime the end time of the test
    * @param section the section to give the test to
    
    pre: test != null && startTime != null && endTime != null && section != null
    
    */
   public TestSession(Test test, Date startTime, Date endTime,
		   Section section) {
	   this.test = test;
	   this.startTime = startTime;
	   this.endTime = endTime;
	   this.section = section;
	   this.submissions = new ArrayList<TestSubmission>();
	   
	   if (test == null || startTime == null || endTime == null || section == null) {
	      throw new RuntimeException("Bad Input!");
	   }
	   
	   try {
	      database = new DBAccess();
	      
	      database.insert("TestSessions",
	            "VALUES ({SESSION_ID}, {TEST_ID}, {SECTION_ID}, {START_TIME}, {END_TIME})"
	            .replace("{SESSION_ID}", "NULL")
	            .replace("{TEST_ID}", Long.toString(this.test.getId()))
	            .replace("{SECTION_ID}", Long.toString(this.section.getId()))
	            .replace("{START_TIME}", Long.toString(this.startTime.getTime() / 1000))
	            .replace("{END_TIME}", Long.toString(this.endTime.getTime() / 1000)));
	      
	      database.closeConnection();
	   }
	   catch (Exception e) {
	      System.out.println("Unknown error occured while creating new test");
	      e.printStackTrace();
	   }
   }

   /**
    * Get the name of the current test session.
    * @return the name of the test being given.
    */
   public String getName() {
	   return test.getName();
   }
   
   /**
    * Check whether the test is currently active.  If it is currently
    * between the start time and the end time (students are allowed
    * to answer questions or change their answers), then the test is
    * active.
    */
   public boolean testActive() {
	   return false;
   }

   /**
    * End the test for every user and set the test end time to now
    */
   public void endTest() {
	   
   }

   /**
    * End the test for a particular user... they were probably cheating and
    * so they deserve to have their test ended.
    *
    * @param target the user to be evicted from the test
    pre: target != null
    */
   public void endTestForUser(User target) {
	   
   }

      /**
	* Returns the id of the test session.
	*
	* @return The id of the test session.
	*/
   public long getTestSessionId() {
	   return this.testSessionId;
   }
	
   /**
	* Sets the id to use for the test session.
	*
	* @param id the id to use for the test session
	pre: id > 0
	*/
   public void setTestSessionId(long id) {
	   this.testSessionId = id;
   }

   /**
    * Change the start date/time of this test.  If the test has already
    * started (the startTime is in the past), this method will throw an
    * exception.
    *
    * @param newStartTime the new start time for the test
    */
   public void setStartTime(Date newStartTime) {
	   this.startTime = newStartTime;
   }

   /**
    * Change the end date/time of this test.  If the test has already
    * ended, this method will throw an exception.
    *
    * @param newEndTime the new end time for the test
    pre: newEndTime != null
    */
   public void setEndTime(Date newEndTime) {
	   this.endTime = newEndTime;
   }

   /**
    * Get the average score users got on the test
    *
    * @return average number of points scored by takers of the test
    */
   public int getAverage() {
	   return 0;
   }

   /**
    * Export grades into spreadsheet.  GradeExporter may not end up in the
    * final version of this project, but at this stage it was useful for
    * thinking about what information is necessary to generate that
    * spreadsheet.
    *
    * @return GradeExporter with all the information (and only the information)
    * necessary to create a spreadsheet.
    */
   public GradeExporter generateSpreadsheet() {
	   return null;
   }

   /**
    * Get the grade distribution for the test.
    *
    * @return grade distribution for the test.  This is a list of points
    * scored by takers of the test sorted in increasing order.
    */
   public List<Integer> getGradeDistribution() {
	   return new ArrayList<Integer>();
   }
}
