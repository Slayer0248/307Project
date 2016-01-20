package model.test;

import java.io.File;
import java.util.List;
import model.test.Test;

/**
 * The test tool can export a test to a comma-seperated value
 * spreadsheet.  
 *
 * @author coltonstapper
 */
public abstract class TestExporter {
   /**
    * the name of this test
    */
   private String testName;
   /**
    * the names of the questions
    */
   private List<String> questions;

   /**
    * Generate a comma-seperated value spreadsheet using the data in
    * this class.
    *
    * @return File containing spreadsheet
    */
   public abstract File exportToCSV();
}
