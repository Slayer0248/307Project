package view.testCreation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.test.generation.AutomationGroup;
import model.test.generation.AutomationRule;
import model.test.generation.CompareRule;
import model.util.DBAccess;

/**
 * 
 * ui for filtering out questions
 *
 */
public class AutomationRuleUI extends Pane implements Initializable {

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //@FXML // fx:id="automationRulePane"
    //private Pane automationRulePane; // Value injected by FXMLLoader

    @FXML // fx:id="automationRuleRect"
    private Rectangle automationRuleRect; // Value injected by FXMLLoader

    @FXML // fx:id="ruleQuestionPropertiesSelect"
    private ComboBox<String> ruleQuestionPropertiesSelect; // Value injected by FXMLLoader

    @FXML // fx:id="ruleComparisonsSelect"
    private ComboBox<String> ruleComparisonsSelect; // Value injected by FXMLLoader

    @FXML // fx:id="ruleCompareToField"
    private TextField ruleCompareToField; // Value injected by FXMLLoader

    @FXML // fx:id="addCompareRuleRect"
    private Rectangle addCompareRuleRect; // Value injected by FXMLLoader

    @FXML // fx:id="addCompareRuleLabel"
    private Label addCompareRuleLabel; // Value injected by FXMLLoader

    @FXML // fx:id="actsOnFieldLabel"
    private Label actsOnFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="actsOnField"
    private TextField actsOnField; // Value injected by FXMLLoader

    private ArrayList<CompareRuleUI> compareRules;
    private AutomationRule rule;
    private DBAccess database;
    private AutomationGroup parentGroup;
    private AdvancedGeneratorPage1UI parentView;
    
    /**
     * ui constructor
     * @param parent
     * @param group
     * @param aRule
     */
    public AutomationRuleUI(AdvancedGeneratorPage1UI parent, AutomationGroup group, AutomationRule aRule) {
    	database = new DBAccess();
    	parentView = parent;
    	parentGroup = group;
    	rule = aRule;
    	compareRules = new ArrayList<CompareRuleUI>();
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AutomationRule.fxml"));
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
     * gets compareRules
     * @return
     */
    public ArrayList<CompareRuleUI> getAllCompareRules() {
    	return compareRules;
    }
    
    /**
     * gets rule
     * @return
     */
    public AutomationRule getRule() {
    	return rule;
    }
    
    /**
     * sets rule
     * @param newRule
     */
    public void setRule(AutomationRule newRule) {
    	rule = newRule;
    }
    
    /**
     * shifts AutomationRuleUI
     * @param shiftAmount
     */
    public void shiftAutomationRuleUI(double shiftAmount) {
    	automationRuleRect.setHeight(automationRuleRect.getHeight() + shiftAmount);
		addCompareRuleRect.relocate(6.0, addCompareRuleRect.getLayoutY()+shiftAmount);
		addCompareRuleLabel.relocate(addCompareRuleLabel.getLayoutX(), addCompareRuleRect.getLayoutY()+19.0);
		actsOnFieldLabel.relocate(actsOnFieldLabel.getLayoutX(), actsOnFieldLabel.getLayoutY()+shiftAmount);
	    actsOnField.relocate(actsOnField.getLayoutX(), actsOnField.getLayoutY()+shiftAmount);
    }
    
    /**
     * gets addCompareRuleRect
     * @return
     */
    public Rectangle getAddCompareRuleRect() {
    	 return addCompareRuleRect;
    }
    
    /**
     * adds rule to the database
     */
    public void addAutomationRuleToDb() {
    	
    	parentView.getSelected().addAutomationRule(rule);
    	parentView.getSelected().updateInDatabase();
    }
    
    /**
     * fills in the automation rule
     */
    public void fillInAutomationRule() {
    	if (database.isOpenIdFor("AutomationRules", "automationRuleId")) {
     	   rule.setId(database.getOpenIdFor("AutomationRules", "automationRuleId"));
     	}
     	rule.setGroupId(parentGroup.getId());
     	rule.setQuestionProperty("");
     	rule.setCompareType("");
     	rule.setPrimaryCompareTo("");
     	rule.setApplyTo("");
    }
    
    
    /**
     * initializes the ui
     */
    public void initUI() {
    	ruleQuestionPropertiesSelect.getItems().clear();
    	ruleQuestionPropertiesSelect.getItems().addAll("Properties", "Question", "Answer", "Categories", "Type", "Length", "Difficulty", "Points", "Last Used", "Created");
    	ruleComparisonsSelect.setDisable(true);
    	
    	ruleQuestionPropertiesSelect.valueProperty().addListener(new ChangeListener<String>() {
    		@Override public void changed(ObservableValue ov, String t, String t1) {
    			if (t1.equals("Properties")) {
    				ruleComparisonsSelect.setDisable(true);
    			}
    			else {
    				ruleComparisonsSelect.setDisable(false);
    				ruleComparisonsSelect.getItems().clear();
    				ruleComparisonsSelect.getItems().add("Comparisons");
    			}
    			
    			if (t1.equals("Question") || t1.equals("Answer") || t1.equals("Categories") || t1.equals("Type")) {
    				ruleComparisonsSelect.getItems().addAll("same as", "not same as", "contains", "not contains", "length greater than", "length equals", "length less than");
    			}
    			else if (t1.equals("Length") || t1.equals("Difficulty") || t1.equals("Points")){
    			    ruleComparisonsSelect.getItems().addAll("equals", "not equals", "greater than", "less than", "greater that or equal", "less than or equal", "between", "not between");
    			}
    			else if (t1.equals("Last Used") || t1.equals("Created")) {
    				ruleComparisonsSelect.getItems().addAll("on", "not on", "between", "not between", "before", "after");
    			}
    		}
    	});
    	
    	addCompareRuleRect.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				CompareRuleUI compareRuleUI = new CompareRuleUI(parentView, AutomationRuleUI.this, parentGroup, rule, new CompareRule());
				compareRuleUI.fillInCompareRule();
				compareRuleUI.addCompareRuleToDb();
				getChildren().add(compareRuleUI);
				
				double ruleOffset = 10;
				double curYCoord = addCompareRuleRect.getLayoutY();
				if (compareRules.size() > 0) {
					curYCoord = compareRules.get(0).getLayoutY();
				}
				double ruleHeight = -1;

				compareRules.add(compareRuleUI);
				
				System.out.println("Example: " + curYCoord);
				for (int i=0; i<compareRules.size(); i++) {
				   compareRules.get(i).relocate(6.0, curYCoord);
				   parentView.getControlsPane().setPrefHeight(parentView.getControlsPane().getPrefHeight() + ruleOffset + compareRules.get(i).getPrefHeight());
				   setPrefHeight(getPrefHeight() + ruleOffset + compareRules.get(i).getPrefHeight());
				   ruleHeight = compareRules.get(i).getPrefHeight();
				   curYCoord = curYCoord + compareRules.get(i).getPrefHeight() + ruleOffset;
				}
				
				double shiftAmount = ruleHeight + ruleOffset;
				
				for (int i= parentView.getAllRules().indexOf(AutomationRuleUI.this) +1; i<parentView.getAllRules().size(); i++) {
					parentView.getAllRules().get(i).relocate(parentView.getAllRules().get(i).getLayoutX(), parentView.getAllRules().get(i).getLayoutY() + shiftAmount);
				}
				parentView.shiftAddRuleButton(shiftAmount);
				
				//automationRuleRect.resize(automationRuleRect.getWidth(), automationRuleRect.getHeight() + shiftAmount);
				//automationRuleRect.heightProperty().add(shiftAmount);
				automationRuleRect.setHeight(automationRuleRect.getHeight() + shiftAmount);
				addCompareRuleRect.relocate(6.0, curYCoord);
				addCompareRuleLabel.relocate(addCompareRuleLabel.getLayoutX(), curYCoord+19.0);
				actsOnFieldLabel.relocate(actsOnFieldLabel.getLayoutX(), curYCoord+60.0);
			    actsOnField.relocate(actsOnField.getLayoutX(), curYCoord+79.0);
				//S
			    
			    parentView.getGroupEditorScroll().setMax(parentView.getGroupEditorScroll().getMax() + (shiftAmount * 2));
				/*if ( > getPrefHeight() && parentView.getControlsPane().getPrefHeight() > parentView.getEditorPane().getPrefHeight()) {
					
				}*/
		    }
	    });
    	
    	addCompareRuleLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				CompareRuleUI compareRuleUI = new CompareRuleUI(parentView, AutomationRuleUI.this, parentGroup, rule, new CompareRule());
				compareRuleUI.fillInCompareRule();
				compareRuleUI.addCompareRuleToDb();
				getChildren().add(compareRuleUI);
				
				double ruleOffset = 10;
				double curYCoord = addCompareRuleRect.getLayoutY();
				if (compareRules.size() > 0) {
					curYCoord = compareRules.get(0).getLayoutY();
				}
				double ruleHeight = -1;

				compareRules.add(compareRuleUI);
				
				System.out.println("Example: " + curYCoord);
				for (int i=0; i<compareRules.size(); i++) {
				   compareRules.get(i).relocate(6.0, curYCoord);
				   parentView.getControlsPane().setPrefHeight(parentView.getControlsPane().getPrefHeight() + ruleOffset + compareRules.get(i).getPrefHeight());
				   setPrefHeight(getPrefHeight() + ruleOffset + compareRules.get(i).getPrefHeight());
				   ruleHeight = compareRules.get(i).getPrefHeight();
				   curYCoord = curYCoord + compareRules.get(i).getPrefHeight() + ruleOffset;
				}
				
				double shiftAmount = ruleHeight + ruleOffset;
				//parentView.getControlsPane().setPrefHeight(parentView.getControlsPane().getPrefHeight() + shiftAmount);
				for (int i= parentView.getAllRules().indexOf(AutomationRuleUI.this) +1; i<parentView.getAllRules().size(); i++) {
					parentView.getAllRules().get(i).relocate(parentView.getAllRules().get(i).getLayoutX(), parentView.getAllRules().get(i).getLayoutY() + shiftAmount);
				}
				parentView.shiftAddRuleButton(shiftAmount);
				
				automationRuleRect.setHeight(automationRuleRect.getHeight() + shiftAmount);
				//automationRuleRect.resize(automationRuleRect.getWidth(), automationRuleRect.getHeight() + shiftAmount);
				addCompareRuleRect.relocate(6.0, curYCoord);
				addCompareRuleLabel.relocate(addCompareRuleLabel.getLayoutX(), curYCoord+6.0);
				actsOnFieldLabel.relocate(actsOnFieldLabel.getLayoutX(), curYCoord+60.0);
			    actsOnField.relocate(actsOnField.getLayoutX(), curYCoord+79.0);
				//S
			    parentView.getGroupEditorScroll().setMax(parentView.getGroupEditorScroll().getMax() + ((ruleHeight + ruleOffset)*2));
				/*if (curYCoord > getPrefHeight() && parentView.getControlsPane().getPrefHeight() > parentView.getEditorPane().getPrefHeight()) {
					parentView.getGroupEditorScroll().setMax(parentView.getGroupEditorScroll().getMax() + ruleHeight + ruleOffset);
				}*/
		    }
	    });
    }
    
    /**
     * checks initialization
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        //assert automationRulePane != null : "fx:id=\"automationRulePane\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert automationRuleRect != null : "fx:id=\"automationRuleRect\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert ruleQuestionPropertiesSelect != null : "fx:id=\"ruleQuestionPropertiesSelect\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert ruleComparisonsSelect != null : "fx:id=\"ruleComparisonsSelect\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert ruleCompareToField != null : "fx:id=\"ruleCompareToField\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert addCompareRuleRect != null : "fx:id=\"addCompareRuleRect\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert addCompareRuleLabel != null : "fx:id=\"addCompareRuleLabel\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert actsOnFieldLabel != null : "fx:id=\"actsOnFieldLabel\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert actsOnField != null : "fx:id=\"actsOnField\" was not injected: check your FXML file 'AutomationRule.fxml'.";

    }

    /**
     * checks initialization
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//assert automationRulePane != null : "fx:id=\"automationRulePane\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert automationRuleRect != null : "fx:id=\"automationRuleRect\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert ruleQuestionPropertiesSelect != null : "fx:id=\"ruleQuestionPropertiesSelect\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert ruleComparisonsSelect != null : "fx:id=\"ruleComparisonsSelect\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert ruleCompareToField != null : "fx:id=\"ruleCompareToField\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert addCompareRuleRect != null : "fx:id=\"addCompareRuleRect\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert addCompareRuleLabel != null : "fx:id=\"addCompareRuleLabel\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert actsOnFieldLabel != null : "fx:id=\"actsOnFieldLabel\" was not injected: check your FXML file 'AutomationRule.fxml'.";
        assert actsOnField != null : "fx:id=\"actsOnField\" was not injected: check your FXML file 'AutomationRule.fxml'.";
	}
}
