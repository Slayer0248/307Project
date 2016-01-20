package view.testCreation;

import model.util.DBAccess;
import model.accounts.Section;
import model.test.Test;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * QuickTestCreatorUI view class
 * 
 * @author Clay
 */
public class QuickTestCreatorUI extends Pane implements Initializable {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //@FXML // fx:id="quickCreatorPane"
    //private Pane quickCreatorPane; // Value injected by FXMLLoader

    @FXML // fx:id="quickCreatorInstructionsLabel"
    private Label quickCreatorInstructionsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="requiredFieldsLabel"
    private Label requiredFieldsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nameFieldLabel"
    private Label nameFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nameField"
    private TextField nameField; // Value injected by FXMLLoader

    @FXML // fx:id="classFieldLabel"
    private Label classFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="classField"
    private TextField classField; // Value injected by FXMLLoader

    @FXML // fx:id="tagsFieldLabel"
    private Label tagsFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="tagsField"
    private TextArea tagsField; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionFieldLabel"
    private Label descriptionFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionField"
    private TextArea descriptionField; // Value injected by FXMLLoader

    @FXML // fx:id="createSectionLabel"
    private Label createSectionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="createBlankCheckbox"
    private CheckBox createBlankCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="createCopyCheckbox"
    private CheckBox createCopyCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="testCopySelect"
    private ComboBox<String> testCopySelect; // Value injected by FXMLLoader

    
    private TestCreatorUI parentView;
    private DBAccess database;
    private ArrayList<Test> existingTests;
    private int useForm = 0;
    
    
    /**
     * Constructor for a QuickTestCreatorUI.
     * 
     * @param testCreator parent view for this QuickTestCreatorUI
     */
    public QuickTestCreatorUI(TestCreatorUI testCreator) {
    	existingTests = new ArrayList<Test>();
    	parentView = testCreator;
    	System.out.println("User id is " + parentView.getCurrentUser().getId());
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuickTestCreator.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
        	e.printStackTrace();
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
        }
    	
    	initQuickCreator();
    }
    
    /**
     * Setup for QuickTestCreator ui elements
     */
    public void initQuickCreator() {
    	try {
			database = new DBAccess();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	ResultSet results;
    	int numTests = 0;
    	int numSections = 0;
        try {
			results = database.select("Tests", "*", "ownerId = " + parentView.getCurrentUser().getId());
			while (results.next()) {
				Test theTest = new Test();
				theTest.setId(results.getLong("testId")); 
				theTest.setOwnerId(parentView.getCurrentUser().getId());
				existingTests.add(theTest);
				numTests++;
			}
			database.closeConnection();
			for(int i=0; i<existingTests.size(); i++) {
				Test theTest = new Test(existingTests.get(i).getId(), existingTests.get(i).getOwnerId());
				existingTests.set(i, theTest);
			}
			
			results = database.select("Sections", "*", "ownerId = " + parentView.getCurrentUser().getId());
			while (results.next()) {
				numSections++;
			}
			database.closeConnection();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
        createBlankCheckbox.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//if (createCopyCheckbox.isSelected()) {
			    createCopyCheckbox.setSelected(false);
			    testCopySelect.setDisable(true);
			    testCopySelect.getItems().clear();
			    testCopySelect.getItems().add("Select Test to Copy");
				//}
			}
		});
    	
    	createCopyCheckbox.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				createBlankCheckbox.setSelected(false);
				testCopySelect.setDisable(false);
				testCopySelect.getItems().clear();
				testCopySelect.getItems().add("Select Test to Copy");
				for (int i=0; i< existingTests.size(); i++) {
					testCopySelect.getItems().add(existingTests.get(i).getName());
				}
			}
		});
        
        if (numTests == 0) {
        	createBlankCheckbox.setSelected(true);
        	createCopyCheckbox.setSelected(false);
        	createBlankCheckbox.setDisable(true);
        	createCopyCheckbox.setDisable(true);
        }
        if (numSections == 0) {
        	classField.setText("None created");
        	classField.setDisable(true);
        }
        
    	testCopySelect.setDisable(true);
    }
    
    
    /**
     * Returns section text field
     * 
     * @return section text field
     */
    public TextField getClassField() {
    	return classField;
    }
    
    /**
     * Returns Whether form should be used for test creation
     * 
     * @return whether form should be used for test creation
     */
    public int shouldUseForm() {
    	return useForm;
    }
    
    /**
     * Set whether form should be used for test creation
     * 
     * @param toUse whether form should be used for test creation
     */
    public void setUseForm(int toUse) {
    	useForm = toUse;
    }
    
    /**
     * Returns selected test to copy
     * 
     * @return selected test to copy
     */
    public boolean selectedTestToCopy() {
    	boolean selected = false;
    	if (existingTests.get(0).equals(testCopySelect.getValue())) {
    		selected = true;
    	}
    	return selected;
    }
    
    /**
     * Returns whether checkboxes on this form are disabled.
     * 
     * @return whether checkboxes on this form are disabled.
     */
    public boolean checkBoxesDisabled() {
    	boolean isDisabled = false;
    	if (createBlankCheckbox.isDisabled() && createCopyCheckbox.isDisabled()) {
    		isDisabled = true;
    	}
    	
    	return isDisabled;
    }
   
    /**
     * Returns whether blank test should be created.
     * 
     * @return whether blank test should be created.
     */
    public boolean shouldCreateBlankTest() {
    	boolean shouldCreateBlank = false;
    	if (createBlankCheckbox.isSelected()) {
    		shouldCreateBlank = true;
    	}
    	return shouldCreateBlank;
    }
    
    /**
     * Returns whether test should be copied.
     * 
     * @return whether test should be copied.
     */
    public boolean shouldCopyTest() {
    	boolean shouldCopy = false;
    	if (createCopyCheckbox.isSelected()) {
    		shouldCopy = true;
    	}
    	return shouldCopy;
    }
    
    /**
     * Returns Test to copy data from.
     * 
     * @return Test to copy data from.
     */
    public Test getTestToCopy() {
    	String testName = testCopySelect.getValue();
    	Test copyTest = null;
    	for (int i=0; i<existingTests.size() && copyTest == null; i++) {
    		if(existingTests.get(i).getName().equals(testName)) {
    			copyTest = existingTests.get(i);
    		}
    	}
    	return copyTest;
    }
    
    /**
     * Returns Test to create.
     * 
     * @return Test to create.
     */
    public Test getCreatedTest() {
    	Test created = new Test();
    	
    	if (shouldCopyTest()) {
    		created = getTestToCopy().copy();
    		setUseForm(parentView.showUseFormDialog());
    	}
    	else {
    		created = new Test();
    		useForm = 1;
    	}

    	if (useForm == 1) {
    	   created.setName(nameField.getText());
		   created.setOwnerId(parentView.getCurrentUser().getId());
		   created.setDescription(descriptionField.getText());
		
		   String tagsList = tagsField.getText();
		   if (tagsList.length() > 0) {
			   String[] allTags = tagsList.split(",[ ]*");
			   HashSet<String> testTags = new HashSet<String>();
			   for (int i=0; i<allTags.length; i++) {
			      testTags.add(allTags[i]);
			   }
			   created.addTags(testTags);
		   }
		
		
    	   if (!classField.isDisabled() && classField.getText().length() > 0) {
    		   int numSections = -1;
    		   //classField.set
    		   try {
    			   numSections = database.countAll("Sections", "ownerId = " + parentView.getCurrentUser().getId() + " AND sectionName = '" + classField.getText() + "'");
    			   if (numSections == 1) {
				      ResultSet results = database.select("Sections","*", "ownerId = " + parentView.getCurrentUser().getId() + " AND sectionName = '" + classField.getText() + "'");
				      while (results.next()) {
				         created.setSectionId(results.getLong("sectionId"));
				      }
				      database.closeConnection();
    			   }
			   } catch (Exception e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
    	   }
    	}
    	
    	return created;
    }
    
    /**
     * Initialize from fxml. 
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //assert quickCreatorPane != null : "fx:id=\"quickCreatorPane\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert quickCreatorInstructionsLabel != null : "fx:id=\"quickCreatorInstructionsLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert requiredFieldsLabel != null : "fx:id=\"requiredFieldsLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert nameFieldLabel != null : "fx:id=\"nameFieldLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert classFieldLabel != null : "fx:id=\"classFieldLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert classField != null : "fx:id=\"classField\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert tagsFieldLabel != null : "fx:id=\"tagsFieldLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert tagsField != null : "fx:id=\"tagsField\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert descriptionFieldLabel != null : "fx:id=\"descriptionFieldLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert createSectionLabel != null : "fx:id=\"createSectionLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert createBlankCheckbox != null : "fx:id=\"createBlankCheckbox\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert createCopyCheckbox != null : "fx:id=\"createCopyCheckbox\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert testCopySelect != null : "fx:id=\"testCopySelect\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";

    }

    /**
     * Initialize from fxml. 
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//assert quickCreatorPane != null : "fx:id=\"quickCreatorPane\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert quickCreatorInstructionsLabel != null : "fx:id=\"quickCreatorInstructionsLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert requiredFieldsLabel != null : "fx:id=\"requiredFieldsLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert nameFieldLabel != null : "fx:id=\"nameFieldLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert classFieldLabel != null : "fx:id=\"classFieldLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert classField != null : "fx:id=\"classField\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert tagsFieldLabel != null : "fx:id=\"tagsFieldLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert tagsField != null : "fx:id=\"tagsField\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert descriptionFieldLabel != null : "fx:id=\"descriptionFieldLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert createSectionLabel != null : "fx:id=\"createSectionLabel\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert createBlankCheckbox != null : "fx:id=\"createBlankCheckbox\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert createCopyCheckbox != null : "fx:id=\"createCopyCheckbox\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
        assert testCopySelect != null : "fx:id=\"testCopySelect\" was not injected: check your FXML file 'QuickTestCreator.fxml'.";
		
	}
}
