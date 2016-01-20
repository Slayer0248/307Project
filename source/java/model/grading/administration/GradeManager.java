package model.grading.administration;

import model.test.TestSession;
import model.accounts.*;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Collection of graded tests as well as interface for the user to interract
 * with that data.
 * 
 * @author Andrew Nelson
 */
public class GradeManager {
   private Collection<TestSession> tests;
   
   /**
    * Creates a grade manager.
    * @param user
    */
   public GradeManager(User user) {
	   
   }

   /**
    * Get a list of all the tests that have finished being proctored
    *
    * @return collection of graded tests
    */
   public Collection<TestSession> getGradedTests() {
	   return new LinkedList<TestSession>();
   }

   /**
    * Get a list of all the tests that a particular section has taken
    *
    * @param section section to filter for
    * @return collection of tests taken by the given section
    */
   public Collection<TestSession> getGradedTestsBySection(Section section) {
	   return new LinkedList<TestSession>();
   }

   /**
    * Get the average test score (out of 1.0) for a given section
    *
    * If the section has not taken any tests, this method will throw an
    * exception.
    *
    * @param section section to find the average GPA of
    * @return average GPA normalized from 0.0 to 1.0
    */
   public double getSectionGPA(Section section) {
	   return 0.0;
   }
}
