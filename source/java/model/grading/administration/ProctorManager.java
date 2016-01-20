package model.grading.administration;

import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.accounts.Section;
import model.test.Test;
import model.test.TestSession;
import model.util.DBAccess;


/**
 * Interface to help the user manage test proctorings
 *
 * @author Andrew Nelson
 */
public class ProctorManager {
   private long ownerId;
   private List<TestSession> activeTests;
   private DBAccess database;
   
   /**
    * Creates a proctor manager.
    * @param ownerId
    */
   public ProctorManager(long ownerId) {
      this.ownerId = ownerId;
	   
	   populateFromDatabase();
   }
   
   /**
    * Populates from the database.
    */
   private void populateFromDatabase() {
      try {
         this.database = new DBAccess();
         
         activeTests = new LinkedList<TestSession>();
         
         ResultSet testSessionRecords = database.select(
               "TestSessions",
               "testSessionId",
               "testId IN (SELECT testId FROM Tests WHERE ownerId = "
                     + this.ownerId + ") "
                     + "AND endTime > strftime('%s', CURRENT_TIMESTAMP)");
         
         while (testSessionRecords.next()) {
            TestSession sessionObj = new TestSession(
                  testSessionRecords.getLong("testSessionId"),
                  this.ownerId);
            System.out.printf("Session for test '%s' added to proctor manager\n",
                  sessionObj.getName());
            activeTests.add(sessionObj);
         }
         
         database.closeConnection();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         System.out.println("Error while populating ProctorManager for userId "
               + this.ownerId);
         e.printStackTrace();
      }
   }

   /**
    * Start proctoring a test
    *
    * @param target test to proctor
    * @param section section to proctor it to
    * @param startTime time to schedule start of proctor
    * @param endTime time to schedule end of proctor
    pre: !exists(TestSession t; t.test == target && t.section == section &&
     				t.startTime == startTime && t.endTime == endTime && 
     				activeTests.contains(t));
    post: exists(TestSession t; t.test == target && t.section == section &&
     				t.startTime == startTime && t.endTime == endTime && 
     				activeTests.contains(t));
    */
   public void proctorTest(Test target, Section section, Date startTime, Date endTime) {
	   System.out.printf("Before proctorTest %d tests\n", activeTests.size());
	   this.activeTests.add(new TestSession(target, startTime, endTime, section));
	   System.out.println("model.grading.administration.ProctorManager.proctorTest was called so we added a test");
	   System.out.printf("After proctorTest %d tests\n", activeTests.size());
   }

   /**
    * Get a list of all the tests that are currently being proctored
    *
    * @return collection of all tests currently being proctored
    */
   public List<TestSession> getActiveTests() {
	   System.out.println("model.grading.administration.ProctorManager.getActiveTests was called");
	   return activeTests;
   }
}
