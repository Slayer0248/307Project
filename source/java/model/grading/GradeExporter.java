package model.grading;

import java.io.File;
import java.util.List;
import model.test.TestSubmission;

/**
 * The test tool can export the results of a test to a comma seperated value
 * spreadsheet.  The format is described as following.
 *
 * The name is the name of the test with special characters filtered out
 * Cell 0,0 is empty
 * The first row is the name of each question
 * The secont row is the maximum number of points for each question
 * Each row after that has a username and the number of points they got on
 *       each question
 *
 * @author Andrew Nelson
 */
public abstract class GradeExporter {
   private class Row {
      String username;
      TestSubmission userTest;
      //List<Integer> pointsOnQuestion;
   }
   private String testName;
   private List<String> questions;
   private List<Row> testRows;

   /**
    * Generate a comma seperated value spreadsheet using the data in
    * this class.
    *
    * @return file that the spreadsheet was written to
    */
   public abstract File exportToCSV();
}
