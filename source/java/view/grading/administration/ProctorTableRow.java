package view.grading.administration;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import model.test.TestSession;

/**
 * Adapter class thata allows tests to be displayed in the proctoring
 * interface table.
 * 
 * Uses the adapter class design pattern wink wink nudge nudge
 * 
 * @author Andrew Nelson
 */
public class ProctorTableRow {
   /**
    * The test session that this row represents.
    */
	private final TestSession test;
	
	/**
	 * The value that goes in the name field.
	 */
	private final SimpleStringProperty testName;
	/**
	 * The number that shows up in the test completedness field.
	 */
	private final SimpleDoubleProperty testCompletedness;
	/**
	 * The thing that goes in the manage button spot.
	 */
	private final SimpleStringProperty manageButton;
	
	/**
	 * Create a new row that goes in the proctor table
	 * 
	 * @param test the test session that this row represents
	 */
	public ProctorTableRow(TestSession test) {
		this.test = test;
		
		testName = new SimpleStringProperty(test.getName());
		testCompletedness = new SimpleDoubleProperty(test.getAverage());
		manageButton = new SimpleStringProperty("Manage");
	}
	
	/**
	 * Get the session that this row represents.
	 * 
	 * @return the session this row represents
	 */
	public TestSession getSession() {
	   return test;
	}
	
	/**
	 * Get the name of this test
	 * 
	 * @return the name of this test
	 */
	public String getTestName() {
		return testName.get();
	}
	
	/**
	 * Set the name for this test
	 * 
	 * @param name the new name for this test
	 */
	public void setTestName(String name) {
		testName.set(name);
	}
	
	/**
	 * Get the completedness (from 0 to 1) of this test
	 * 
	 * @return test completedness
	 */
	public Double getTestCompletedness() {
		return testCompletedness.get();
	}
	
	/**
	 * Set the completedness (from 0 to 1) of this test
	 * 
	 * @param completedness new completedness
	 */
	public void setTestCompletedness(double completedness) {
		testCompletedness.set(completedness);
	}
	
	/**
	 * Get the manage button
	 * 
	 * @return the manage button
	 */
	public String getManageButton() {
		return manageButton.get();
	}
	
	/**
	 * Set the manage button... you shouldn't need this logically but javaFX requires a setter for introspection purposes
	 * 
	 * @param button the new button
	 */
	public void setManageButton(String button) {
		manageButton.set(button);
	}
}
