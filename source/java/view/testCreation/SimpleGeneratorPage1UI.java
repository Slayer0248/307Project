package view.testCreation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.test.Test;
import model.util.DBAccess;

/**
 * SimpleGeneratorPage1UI view class
 * 
 * @author Clay
 *
 */
public class SimpleGeneratorPage1UI extends Pane implements Initializable {
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //@FXML // fx:id="simpleGeneratorPane"
    //private Pane simpleGeneratorPane; // Value injected by FXMLLoader

    @FXML // fx:id="requiredFieldsLabel"
    private Label requiredFieldsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="instructionsLabel"
    private Label instructionsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="generationRulesSectionPane"
    private Pane generationRulesSectionPane; // Value injected by FXMLLoader

    @FXML // fx:id="generationRulesTitle"
    private Label generationRulesTitle; // Value injected by FXMLLoader

    @FXML // fx:id="olderLastUsedCheckbox"
    private CheckBox olderLastUsedCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="varyDifficultyCheckbox"
    private CheckBox varyDifficultyCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="moreDifficultCheckbox"
    private CheckBox moreDifficultCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="asDifficultCheckbox"
    private CheckBox asDifficultCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="lessDifficultCheckbox"
    private CheckBox lessDifficultCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="multipleTestsCheckbox"
    private CheckBox multipleTestsCheckbox; // Value injected by FXMLLoader
    
    @FXML
    private Button moreAssessmentsButton;
    
    @FXML
    private Button lessAssessmentsButton;
    
    @FXML
    private Label numAssessmentsLabel;

    @FXML // fx:id="ascDifficultyOrderCheckbox"
    private CheckBox ascDifficultyOrderCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="ascTimeOrderCheckbox"
    private CheckBox ascTimeOrderCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="questionTypeOrderCheckbox"
    private CheckBox questionTypeOrderCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="ascPointsOrderCheckbox"
    private CheckBox ascPointsOrderCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="testStatsSectionPane"
    private Pane testStatsSectionPane; // Value injected by FXMLLoader

    @FXML // fx:id="testStatsSectionTitle"
    private Label testStatsSectionTitle; // Value injected by FXMLLoader

    @FXML // fx:id="testTotalsSectionPane"
    private Pane testTotalsSectionPane; // Value injected by FXMLLoader

    @FXML // fx:id="testTotalsSectionTitle"
    private Label testTotalsSectionTitle; // Value injected by FXMLLoader

    @FXML // fx:id="testTotalsFormPane"
    private Pane testTotalsFormPane; // Value injected by FXMLLoader

    @FXML // fx:id="pointsFieldLabel"
    private Label pointsFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="pointsField"
    private TextField pointsField; // Value injected by FXMLLoader

    @FXML // fx:id="lengthFieldLabel"
    private Label lengthFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="lengthHoursField"
    private TextField lengthHoursField; // Value injected by FXMLLoader

    @FXML // fx:id="lengthHoursLabel"
    private Label lengthHoursLabel; // Value injected by FXMLLoader

    @FXML // fx:id="lengthMinutesField"
    private TextField lengthMinutesField; // Value injected by FXMLLoader

    @FXML // fx:id="lengthMinutesLabel"
    private Label lengthMinutesLabel; // Value injected by FXMLLoader

    @FXML // fx:id="difficultyFieldLabel"
    private Label difficultyFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="difficultyField"
    private TextField difficultyField; // Value injected by FXMLLoader

    @FXML // fx:id="classFieldLabel"
    private Label classFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="classField"
    private TextField classField; // Value injected by FXMLLoader

    @FXML // fx:id="questionCategoriesLabel"
    private Label questionCategoriesLabel; // Value injected by FXMLLoader

    @FXML // fx:id="questionCategoriesField"
    private TextArea questionCategoriesField; // Value injected by FXMLLoader

    @FXML // fx:id="testSectionBreakdownPane"
    private Pane testSectionBreakdownPane; // Value injected by FXMLLoader

    @FXML // fx:id="testSectionBreakdownTitle"
    private Label testSectionBreakdownTitle; // Value injected by FXMLLoader

    @FXML // fx:id="testSectionBreakdownForm"
    private Pane testSectionBreakdownForm; // Value injected by FXMLLoader

    @FXML // fx:id="sectionTimeHeader"
    private Label sectionTimeHeader; // Value injected by FXMLLoader

    @FXML // fx:id="sectionPointsHeader"
    private Label sectionPointsHeader; // Value injected by FXMLLoader

    @FXML // fx:id="sectionDifficultyHeader"
    private Label sectionDifficultyHeader; // Value injected by FXMLLoader

    @FXML // fx:id="tfSectionLabel"
    private Label tfSectionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="tfSectionTimeField"
    private TextField tfSectionTimeField; // Value injected by FXMLLoader

    @FXML // fx:id="tfSectionPointsField"
    private TextField tfSectionPointsField; // Value injected by FXMLLoader

    @FXML // fx:id="tfSectionDifficultyField"
    private TextField tfSectionDifficultyField; // Value injected by FXMLLoader

    @FXML // fx:id="mcSectionLabel"
    private Label mcSectionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="mcSectionTimeField"
    private TextField mcSectionTimeField; // Value injected by FXMLLoader

    @FXML // fx:id="mcSectionPointsField"
    private TextField mcSectionPointsField; // Value injected by FXMLLoader

    @FXML // fx:id="mcSectionDifficultyField"
    private TextField mcSectionDifficultyField; // Value injected by FXMLLoader

    @FXML // fx:id="blanksSectionLabel"
    private Label blanksSectionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="blanksSectionTimeField"
    private TextField blanksSectionTimeField; // Value injected by FXMLLoader

    @FXML // fx:id="blanksSectionPointsField"
    private TextField blanksSectionPointsField; // Value injected by FXMLLoader

    @FXML // fx:id="blanksSectionDifficultyField"
    private TextField blanksSectionDifficultyField; // Value injected by FXMLLoader

    @FXML // fx:id="shortAnswerSectionLabel"
    private Label shortAnswerSectionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="shortAnswerSectionTimeField"
    private TextField shortAnswerSectionTimeField; // Value injected by FXMLLoader

    @FXML // fx:id="shortAnswerSectionPointsField"
    private TextField shortAnswerSectionPointsField; // Value injected by FXMLLoader

    @FXML // fx:id="shortAnswerSectionDifficultyField"
    private TextField shortAnswerSectionDifficultyField; // Value injected by FXMLLoader

    @FXML // fx:id="longAnswerSectionLabel"
    private Label longAnswerSectionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="longAnswerSectionTimeField"
    private TextField longAnswerSectionTimeField; // Value injected by FXMLLoader

    @FXML // fx:id="longAnswerSectionPointsField"
    private TextField longAnswerSectionPointsField; // Value injected by FXMLLoader

    @FXML // fx:id="longAnswerSectionDifficultyField"
    private TextField longAnswerSectionDifficultyField; // Value injected by FXMLLoader

    @FXML // fx:id="codingSectionLabel"
    private Label codingSectionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="codingSectionTimeField"
    private TextField codingSectionTimeField; // Value injected by FXMLLoader

    @FXML // fx:id="codingSectionPointsField"
    private TextField codingSectionPointsField; // Value injected by FXMLLoader

    @FXML // fx:id="codingSectionDifficultyField"
    private TextField codingSectionDifficultyField; // Value injected by FXMLLoader

    @FXML // fx:id="createSectionPane"
    private Pane createSectionPane; // Value injected by FXMLLoader

    @FXML // fx:id="createSectionLabel"
    private Label createSectionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="createBlankCheckbox"
    private CheckBox createBlankCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="createCopyCheckbox"
    private CheckBox createCopyCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="testCopySelect"
    private ComboBox<String> testCopySelect; // Value injected by FXMLLoader

    private DBAccess database;
    private ArrayList<Test> existingTests;
    private TestCreatorUI parentView;
    
    private ArrayList<Test> partialTests;
    
    /**
     * constructor for SimpleGeneratorPage1UI
     * 
     * @param testCreator the parent view for SimpleGeneratorPage1UI
     */
    public SimpleGeneratorPage1UI(TestCreatorUI testCreator) {
    	database = new DBAccess();
    	existingTests = new ArrayList<Test>();
    	parentView = testCreator;
    	partialTests = new ArrayList<Test>();
    	partialTests.add(new Test());
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SimpleGeneratorPage1.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
        	e.printStackTrace();
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
        }
    	
    	initUI();
    }
    
    /**
     * Returns whether tests can be partially generated based on form input here.
     * 
     * @return message for whether tests should be partially generated from this point forward.
     */
    public String shouldCreatePartialTests() {
    	String msg = "";
    	int showError = 0;
    	
    	try {
    		//if (database.countAll("questions", whereClause))
    		if (showError== 0 && !classField.isDisabled()) {
        		
        		if (classField.getText().equals("")) {
        			showError = 1;
        			msg = msg.concat("- Section name is a required field\n");
        		}
        		else if (showError== 0 && database.countAll("Sections", "sectionName = " + classField.getText()) == 0) {
        			showError = 1;
     			    msg = msg.concat("- A section named '"+ classField.getText() +"' was not found. Please enter a valid section name.\n");
     					
        		} 
        		else if (showError== 0 && database.countAll("Sections", "sectionName = " + classField.getText()) > 1) {
    			   showError = 1;
    			   msg = msg.concat("- Test must be associated with a single section.\n");
    					
        	    }
        		
    		}
    		if (showError == 0 && !questionCategoriesField.isDisabled()) {
     		   if (questionCategoriesField.getText().length() == 0) {
     		      showError = 1;
  			      msg = msg.concat("- At least one question category must be entered.\n");
  			   
     		   }
     		   else if (showError == 0 && questionCategoriesField.getText().length() > 0) {
     		      String[] allCategories = questionCategoriesField.getText().split(",[ ]*");
     		      for (int i=0; i<allCategories.length && showError == 0; i++) {
     		    	 if (database.countAll("Categories", "category like '%" + allCategories[i] + "%'") == 0) {
     		    		showError = 1;
     	   			    msg = msg.concat("- No category with\"" +allCategories[i]+"\" was found.\n");
     		    	 }
     		      }
     		      
      		      
   			   
      		   }
     		}
    		if (showError == 0 && !createBlankCheckbox.isSelected() && !createCopyCheckbox.isSelected()) {
    		   showError = 1;
 			   msg = msg.concat("- No test creation method was selected.\n");
    		}
    		else if (showError == 0 && !createBlankCheckbox.isSelected() && createCopyCheckbox.isSelected()) {
    		   
    		   String copyTestName = testCopySelect.getValue();
    		   if (testCopySelect.getItems().indexOf(copyTestName) == 0) {
    			  showError = 1;
      			  msg = msg.concat("- Test must be associated with a single section.\n");
    		   }
     		   
     		}
    		
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
    	
    	if (msg.equals("")) {
    		msg = msg.concat("No Errors");
    	}
    	return msg;
    }
    
    /**
     * Whether the points, time, and difficulty limits can be met
     * 
     * @return whether the points, time, and difficulty limits can be met
     */
    public boolean canMeetLimits() {
    	boolean meetsLimits = false;
    	
    	return meetsLimits;
    }
    
    /**
     * Returns partially generated tests.
     * 
     * @return partially generated tests.
     */
    public ArrayList<Test> getPartialTests() {
    	
    	
    	return partialTests;
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
     * Setup SimpleGeneratorPage1UI.
     * 
     * @return section text field
     */
    public void initUI() {
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
    	
    	try {
			if (database.countAll("questions", "ownerId = " + parentView.getCurrentUser().getId()) == 0) {
			   questionCategoriesField.setDisable(true);
			   disableQuestionFormElements();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	moreAssessmentsButton.setDisable(true);
    	lessAssessmentsButton.setDisable(true);
    	
    	multipleTestsCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val) {
                      if (new_val != old_val) {
                    	  int numTests = Integer.parseInt(numAssessmentsLabel.getText());
                    	  if (new_val) {
                    		  moreAssessmentsButton.setDisable(false);
                    		  
                    		  if (numTests > 2) {
                    			 lessAssessmentsButton.setDisable(false);
                    		  }
                    		  while (partialTests.size() < numTests) {
                    			  partialTests.add(new Test());
                    		  }
                    	  }
                    	  else {
                    		  moreAssessmentsButton.setDisable(true);
                    	      lessAssessmentsButton.setDisable(true);
                    	      while (partialTests.size() > 1) {
                    			  partialTests.remove(0);
                    		  }
                    	  }
                      }
                }
            });
    	
    	moreAssessmentsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				int numTests = Integer.parseInt(numAssessmentsLabel.getText());
				numAssessmentsLabel.setText(""+ (numTests+1));
				partialTests.add(new Test());
				if (partialTests.size() == 3) {
					lessAssessmentsButton.setDisable(true);
				}
			}
		});
    	
    	lessAssessmentsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				int numTests = Integer.parseInt(numAssessmentsLabel.getText());
				numAssessmentsLabel.setText(""+ (numTests-1));
				partialTests.remove(0);
				if (partialTests.size() <= 2) {
					lessAssessmentsButton.setDisable(true);
				}
			}
		});
    }
    
    /**
     * Disable form elements. Will be run if no questions exist in the database.
     */
    public void disableQuestionFormElements() {
    	olderLastUsedCheckbox.setDisable(true);
    	varyDifficultyCheckbox.setDisable(true);
    	moreDifficultCheckbox.setDisable(true);
    	asDifficultCheckbox.setDisable(true);
    	lessDifficultCheckbox.setDisable(true);
    	ascDifficultyOrderCheckbox.setDisable(true);
    	ascTimeOrderCheckbox.setDisable(true);
    	questionTypeOrderCheckbox.setDisable(true);
    	ascPointsOrderCheckbox.setDisable(true);
    	
    	//Test totals
    	pointsField.setDisable(true);
    	lengthHoursField.setDisable(true);
    	lengthMinutesField.setDisable(true);
    	difficultyField.setDisable(true);
    	
    	//Section totals
    	tfSectionTimeField.setDisable(true);
    	tfSectionPointsField.setDisable(true);
    	tfSectionDifficultyField.setDisable(true);
    	mcSectionTimeField.setDisable(true);
    	mcSectionPointsField.setDisable(true);
    	mcSectionDifficultyField.setDisable(true);
    	blanksSectionTimeField.setDisable(true);
    	blanksSectionPointsField.setDisable(true);
    	blanksSectionDifficultyField.setDisable(true);
    	shortAnswerSectionTimeField.setDisable(true);
    	shortAnswerSectionPointsField.setDisable(true);
    	shortAnswerSectionDifficultyField.setDisable(true);
    	longAnswerSectionTimeField.setDisable(true);
    	longAnswerSectionPointsField.setDisable(true);
    	longAnswerSectionDifficultyField.setDisable(true);
    	codingSectionTimeField.setDisable(true);
    	codingSectionPointsField.setDisable(true);
    	codingSectionDifficultyField.setDisable(true);
    }
    
    /*public String formatNumber(int num) {
    	String formattedString = "";
    	int origNum = num;
    	while (num > 10) {
    		num = num/10;
    	}
    	
    	formattedString = formattedString + Integer.toString(origNum);
    	if (num == 1) {
    	   formattedString = formattedString + "st";
    	}
    	else if (num == 2) {
    	   formattedString = formattedString + "nd";
    	}
    	else if (num == 3) {
    	   formattedString = formattedString + "rd";
    	}
    	else {
    	   formattedString = formattedString + "th";
    	}
    	
    	return formattedString;
    }*/
    
    /**
     * Initialize from fxml. 
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //assert simpleGeneratorPane != null : "fx:id=\"simpleGeneratorPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert requiredFieldsLabel != null : "fx:id=\"requiredFieldsLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert instructionsLabel != null : "fx:id=\"instructionsLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert generationRulesSectionPane != null : "fx:id=\"generationRulesSectionPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert generationRulesTitle != null : "fx:id=\"generationRulesTitle\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert olderLastUsedCheckbox != null : "fx:id=\"olderLastUsedCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert varyDifficultyCheckbox != null : "fx:id=\"varyDifficultyCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert moreDifficultCheckbox != null : "fx:id=\"moreDifficultCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert asDifficultCheckbox != null : "fx:id=\"asDifficultCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert lessDifficultCheckbox != null : "fx:id=\"lessDifficultCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert multipleTestsCheckbox != null : "fx:id=\"multipleTestsCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert ascDifficultyOrderCheckbox != null : "fx:id=\"ascDifficultyOrderCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert ascTimeOrderCheckbox != null : "fx:id=\"ascTimeOrderCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert questionTypeOrderCheckbox != null : "fx:id=\"questionTypeOrderCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert ascPointsOrderCheckbox != null : "fx:id=\"ascPointsOrderCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert testStatsSectionPane != null : "fx:id=\"testStatsSectionPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert testStatsSectionTitle != null : "fx:id=\"testStatsSectionTitle\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert testTotalsSectionPane != null : "fx:id=\"testTotalsSectionPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert testTotalsSectionTitle != null : "fx:id=\"testTotalsSectionTitle\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert testTotalsFormPane != null : "fx:id=\"testTotalsFormPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert pointsFieldLabel != null : "fx:id=\"pointsFieldLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert pointsField != null : "fx:id=\"pointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert lengthFieldLabel != null : "fx:id=\"lengthFieldLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert lengthHoursField != null : "fx:id=\"lengthHoursField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert lengthHoursLabel != null : "fx:id=\"lengthHoursLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert lengthMinutesField != null : "fx:id=\"lengthMinutesField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert lengthMinutesLabel != null : "fx:id=\"lengthMinutesLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert difficultyFieldLabel != null : "fx:id=\"difficultyFieldLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert difficultyField != null : "fx:id=\"difficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert classFieldLabel != null : "fx:id=\"classFieldLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert classField != null : "fx:id=\"classField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert questionCategoriesLabel != null : "fx:id=\"questionCategoriesLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert questionCategoriesField != null : "fx:id=\"questionCategoriesField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert testSectionBreakdownPane != null : "fx:id=\"testSectionBreakdownPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert testSectionBreakdownTitle != null : "fx:id=\"testSectionBreakdownTitle\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert testSectionBreakdownForm != null : "fx:id=\"testSectionBreakdownForm\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert sectionTimeHeader != null : "fx:id=\"sectionTimeHeader\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert sectionPointsHeader != null : "fx:id=\"sectionPointsHeader\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert sectionDifficultyHeader != null : "fx:id=\"sectionDifficultyHeader\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert tfSectionLabel != null : "fx:id=\"tfSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert tfSectionTimeField != null : "fx:id=\"tfSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert tfSectionPointsField != null : "fx:id=\"tfSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert tfSectionDifficultyField != null : "fx:id=\"tfSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert mcSectionLabel != null : "fx:id=\"mcSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert mcSectionTimeField != null : "fx:id=\"mcSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert mcSectionPointsField != null : "fx:id=\"mcSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert mcSectionDifficultyField != null : "fx:id=\"mcSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert blanksSectionLabel != null : "fx:id=\"blanksSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert blanksSectionTimeField != null : "fx:id=\"blanksSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert blanksSectionPointsField != null : "fx:id=\"blanksSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert blanksSectionDifficultyField != null : "fx:id=\"blanksSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert shortAnswerSectionLabel != null : "fx:id=\"shortAnswerSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert shortAnswerSectionTimeField != null : "fx:id=\"shortAnswerSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert shortAnswerSectionPointsField != null : "fx:id=\"shortAnswerSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert shortAnswerSectionDifficultyField != null : "fx:id=\"shortAnswerSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert longAnswerSectionLabel != null : "fx:id=\"longAnswerSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert longAnswerSectionTimeField != null : "fx:id=\"longAnswerSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert longAnswerSectionPointsField != null : "fx:id=\"longAnswerSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert longAnswerSectionDifficultyField != null : "fx:id=\"longAnswerSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert codingSectionLabel != null : "fx:id=\"codingSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert codingSectionTimeField != null : "fx:id=\"codingSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert codingSectionPointsField != null : "fx:id=\"codingSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert codingSectionDifficultyField != null : "fx:id=\"codingSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert createSectionPane != null : "fx:id=\"createSectionPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert createSectionLabel != null : "fx:id=\"createSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert createBlankCheckbox != null : "fx:id=\"createBlankCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert createCopyCheckbox != null : "fx:id=\"createCopyCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
        assert testCopySelect != null : "fx:id=\"testCopySelect\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";

    }

    /**
     * Initialize from fxml. 
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 //assert simpleGeneratorPane != null : "fx:id=\"simpleGeneratorPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert requiredFieldsLabel != null : "fx:id=\"requiredFieldsLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert instructionsLabel != null : "fx:id=\"instructionsLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert generationRulesSectionPane != null : "fx:id=\"generationRulesSectionPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert generationRulesTitle != null : "fx:id=\"generationRulesTitle\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert olderLastUsedCheckbox != null : "fx:id=\"olderLastUsedCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert varyDifficultyCheckbox != null : "fx:id=\"varyDifficultyCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert moreDifficultCheckbox != null : "fx:id=\"moreDifficultCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert asDifficultCheckbox != null : "fx:id=\"asDifficultCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert lessDifficultCheckbox != null : "fx:id=\"lessDifficultCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert multipleTestsCheckbox != null : "fx:id=\"multipleTestsCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert ascDifficultyOrderCheckbox != null : "fx:id=\"ascDifficultyOrderCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert ascTimeOrderCheckbox != null : "fx:id=\"ascTimeOrderCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert questionTypeOrderCheckbox != null : "fx:id=\"questionTypeOrderCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert ascPointsOrderCheckbox != null : "fx:id=\"ascPointsOrderCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert testStatsSectionPane != null : "fx:id=\"testStatsSectionPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert testStatsSectionTitle != null : "fx:id=\"testStatsSectionTitle\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert testTotalsSectionPane != null : "fx:id=\"testTotalsSectionPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert testTotalsSectionTitle != null : "fx:id=\"testTotalsSectionTitle\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert testTotalsFormPane != null : "fx:id=\"testTotalsFormPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert pointsFieldLabel != null : "fx:id=\"pointsFieldLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert pointsField != null : "fx:id=\"pointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert lengthFieldLabel != null : "fx:id=\"lengthFieldLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert lengthHoursField != null : "fx:id=\"lengthHoursField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert lengthHoursLabel != null : "fx:id=\"lengthHoursLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert lengthMinutesField != null : "fx:id=\"lengthMinutesField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert lengthMinutesLabel != null : "fx:id=\"lengthMinutesLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert difficultyFieldLabel != null : "fx:id=\"difficultyFieldLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert difficultyField != null : "fx:id=\"difficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert classFieldLabel != null : "fx:id=\"classFieldLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert classField != null : "fx:id=\"classField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert questionCategoriesLabel != null : "fx:id=\"questionCategoriesLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert questionCategoriesField != null : "fx:id=\"questionCategoriesField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert testSectionBreakdownPane != null : "fx:id=\"testSectionBreakdownPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert testSectionBreakdownTitle != null : "fx:id=\"testSectionBreakdownTitle\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert testSectionBreakdownForm != null : "fx:id=\"testSectionBreakdownForm\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert sectionTimeHeader != null : "fx:id=\"sectionTimeHeader\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert sectionPointsHeader != null : "fx:id=\"sectionPointsHeader\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert sectionDifficultyHeader != null : "fx:id=\"sectionDifficultyHeader\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert tfSectionLabel != null : "fx:id=\"tfSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert tfSectionTimeField != null : "fx:id=\"tfSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert tfSectionPointsField != null : "fx:id=\"tfSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert tfSectionDifficultyField != null : "fx:id=\"tfSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert mcSectionLabel != null : "fx:id=\"mcSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert mcSectionTimeField != null : "fx:id=\"mcSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert mcSectionPointsField != null : "fx:id=\"mcSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert mcSectionDifficultyField != null : "fx:id=\"mcSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert blanksSectionLabel != null : "fx:id=\"blanksSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert blanksSectionTimeField != null : "fx:id=\"blanksSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert blanksSectionPointsField != null : "fx:id=\"blanksSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert blanksSectionDifficultyField != null : "fx:id=\"blanksSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert shortAnswerSectionLabel != null : "fx:id=\"shortAnswerSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert shortAnswerSectionTimeField != null : "fx:id=\"shortAnswerSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert shortAnswerSectionPointsField != null : "fx:id=\"shortAnswerSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert shortAnswerSectionDifficultyField != null : "fx:id=\"shortAnswerSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert longAnswerSectionLabel != null : "fx:id=\"longAnswerSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert longAnswerSectionTimeField != null : "fx:id=\"longAnswerSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert longAnswerSectionPointsField != null : "fx:id=\"longAnswerSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert longAnswerSectionDifficultyField != null : "fx:id=\"longAnswerSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert codingSectionLabel != null : "fx:id=\"codingSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert codingSectionTimeField != null : "fx:id=\"codingSectionTimeField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert codingSectionPointsField != null : "fx:id=\"codingSectionPointsField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert codingSectionDifficultyField != null : "fx:id=\"codingSectionDifficultyField\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert createSectionPane != null : "fx:id=\"createSectionPane\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert createSectionLabel != null : "fx:id=\"createSectionLabel\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert createBlankCheckbox != null : "fx:id=\"createBlankCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert createCopyCheckbox != null : "fx:id=\"createCopyCheckbox\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        assert testCopySelect != null : "fx:id=\"testCopySelect\" was not injected: check your FXML file 'SimpleGeneratorPage1.fxml'.";
	        
	        
	}
}
