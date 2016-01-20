package view.testCreation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.test.Test;

/**
 * SimpleGeneratorPage2UI view class
 * 
 * @author Clay
 */
public class SimpleGeneratorPage2UI extends Pane implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //@FXML // fx:id="simpleGeneratorPage2Pane"
    //private Pane simpleGeneratorPage2Pane; // Value injected by FXMLLoader

    @FXML // fx:id="requiredFieldsLabel"
    private Label requiredFieldsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nameFieldLabel"
    private Label nameFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nameField"
    private TextField nameField; // Value injected by FXMLLoader

    @FXML // fx:id="tagsFieldLabel"
    private Label tagsFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="tagsField"
    private TextArea tagsField; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionFieldLabel"
    private Label descriptionFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionField"
    private TextArea descriptionField; // Value injected by FXMLLoader
    
    private ArrayList<Test> createdTests;
    
    private SimpleGeneratorPage1UI lastPage;

    /**
     * Create SimpleGeneratorPage2UI for TestCreator.
     * 
     * @param partialTests Tests created in simplegeneratorpage1
     * @param page the data from the first page of simplegeneratorpage1
     */
    public SimpleGeneratorPage2UI(ArrayList<Test> partialTests, SimpleGeneratorPage1UI page) {
    	lastPage = page;
    	createdTests = new ArrayList<Test>();
    	createdTests.addAll(partialTests);
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SimpleTestGeneratorPage2.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
        	e.printStackTrace();
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
        }
    }
    
    /**
     * Returns whether tests can be generated based on form input here.
     * 
     * @return message for whether tests should be generated from this point forward.
     */
    public String shouldFinishTests() {
    	int showError = 0;
    	String msg = "";
    	
    	if (nameField.getText().length() == 0) {
    		showError = 1;
    		msg = msg.concat("- No name was provided. A test must have a name before it can be created.\n");
    	}
    	
    	if (showError == 0 && tagsField.getText().length() == 0) {
    		showError = 1;
    		msg = msg.concat("- No tags were provided. A test must have at least one tag to be created.\n");
    	}
    	
    	if (showError == 0 && descriptionField.getText().length() == 0) {
    		showError = 1;
    		msg = msg.concat("- No description was provided. A test must have a description before it can be created.\n");
    	}
    	
    	
    	if (msg.length() == 0) {
    	   msg = msg.concat("No Errors");
    	}
    	return msg;
    }
    
    /**
     * Returns generated tests based all the form input.
     * 
     * @return Returns generated tests based all the form input.
     */
    public ArrayList<Test> getFinishedTests() {
    	for (int i=0; i<createdTests.size(); i++) {
    		if (i < 1) {
    		   createdTests.get(i).setName(nameField.getText());
    		}
    		else {
    		   createdTests.get(i).setName(nameField.getText() + (i+1));
    		}
    		
    		HashSet<String> testTags = new HashSet<String>();
    		String tagsList = tagsField.getText();
 		    if (tagsList.length() > 0) {
 			   String[] allTags = tagsList.split(",[ ]*");
 			   
 			   for (int j=0; j<allTags.length; j++) {
 			      testTags.add(allTags[j]);
 			   }
 			   createdTests.get(i).addTags(testTags);
 		    }
    		
 		    createdTests.get(i).setDescription(descriptionField.getText());
    	}
    	return createdTests;
    }
    
    /**
     * Returns the first page of simple generator 
     * 
     * @return the first page of simple generator
     */
    public SimpleGeneratorPage1UI getLastPage() {
    	return lastPage;
    }
    
    /**
     * Initialize from fxml. 
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //assert simpleGeneratorPage2Pane != null : "fx:id=\"simpleGeneratorPage2Pane\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert requiredFieldsLabel != null : "fx:id=\"requiredFieldsLabel\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert nameFieldLabel != null : "fx:id=\"nameFieldLabel\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert tagsFieldLabel != null : "fx:id=\"tagsFieldLabel\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert tagsField != null : "fx:id=\"tagsField\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert descriptionFieldLabel != null : "fx:id=\"descriptionFieldLabel\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";

    }

    
    /**
     * Initialize from fxml. 
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//assert simpleGeneratorPage2Pane != null : "fx:id=\"simpleGeneratorPage2Pane\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert requiredFieldsLabel != null : "fx:id=\"requiredFieldsLabel\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert nameFieldLabel != null : "fx:id=\"nameFieldLabel\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert tagsFieldLabel != null : "fx:id=\"tagsFieldLabel\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert tagsField != null : "fx:id=\"tagsField\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert descriptionFieldLabel != null : "fx:id=\"descriptionFieldLabel\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'SimpleTestGeneratorPage2.fxml'.";
	}
}
