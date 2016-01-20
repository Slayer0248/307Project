package view.testCreation;

import java.io.IOException;
import java.net.URL;
import java.text.RuleBasedCollator;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.test.generation.AutomationGroup;
import model.test.generation.AutomationRule;
import model.test.generation.CompareRule;
import model.util.DBAccess;

/**
 * 
 * ui for a rule to compare to
 *
 */
public class CompareRuleUI extends Pane implements Initializable {

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //@FXML // fx:id="compareRulePane"
    //private Pane compareRulePane; // Value injected by FXMLLoader

    @FXML // fx:id="compareRuleRect"
    private Rectangle compareRuleRect; // Value injected by FXMLLoader

    @FXML // fx:id="removeCompareRuleButton"
    private Circle removeCompareRuleButton; // Value injected by FXMLLoader

    @FXML // fx:id="removeCompareRuleLabel"
    private Label removeCompareRuleLabel; // Value injected by FXMLLoader

    @FXML // fx:id="booleanTypeSelect"
    private ComboBox<String> booleanTypeSelect; // Value injected by FXMLLoader

    @FXML // fx:id="compareToField"
    private TextField compareToField; // Value injected by FXMLLoader
    
    private AdvancedGeneratorPage1UI parentView;
    private AutomationRuleUI parentRuleUI;
    private AutomationGroup parentGroup;
    private AutomationRule parentRule;
    private CompareRule myRule;
    private DBAccess database;
    
    /**
     * ui constructor
     * 
     * @param view
     * @param ruleUI
     * @param group
     * @param rule
     * @param theRule
     */
    public CompareRuleUI(AdvancedGeneratorPage1UI view, AutomationRuleUI ruleUI, AutomationGroup group, 
    		AutomationRule rule, CompareRule theRule) {
    	database = new DBAccess();
    	this.parentView = view;
    	this.parentRuleUI = ruleUI;
    	parentGroup = group;
    	parentRule = rule;
    	myRule = theRule;
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CompareRule.fxml"));
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
     * adds the rule to the database
     */
    public void addCompareRuleToDb() {
     	int index = parentView.getSelected().getAutomationRules().indexOf(parentRule);
     	//parentRule.addCompareRule(myRule);
     	parentView.getSelected().getAutomationRules().get(index).addCompareRule(myRule);
     	parentView.getSelected().updateInDatabase();
    }
    
    /**
     * fils in the rule
     */
    public void fillInCompareRule() {
    	if (database.isOpenIdFor("CompareRules", "compareRuleId")) {
      	   myRule.setId(database.getOpenIdFor("CompareRules", "compareRuleId"));
      	}
      	myRule.setGroupId(parentGroup.getId());
      	myRule.setRuleId(parentRule.getId());
      	myRule.setJoinType("");
      	myRule.setCompareTo("");
      	myRule.addToDatabase();
    }
    /**
     * initializes the ui
     */
    public void initUI() {
    	booleanTypeSelect.setDisable(false);
    	booleanTypeSelect.getItems().clear();
    	booleanTypeSelect.getItems().addAll("None", "and", "or");
    	
    	removeCompareRuleButton.toFront();
    	removeCompareRuleLabel.toFront();
    	booleanTypeSelect.toFront();
    	removeCompareRuleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//setup for this automation group in here
				System.out.println("IIIIIIIIIIIn remove compare rule\n\n");
				int compIndex = parentRuleUI.getAllCompareRules().indexOf(CompareRuleUI.this);
				int autoIndex = parentView.getAllRules().indexOf(parentRuleUI); 
				System.out.println("Test this " + compIndex+ "\n");
				//parentView.getAllGroups().remove(index);
				parentRuleUI.getAllCompareRules().remove(compIndex);
				//parentView.getGroupsPane().getChildren().remove(AutomationGroupUI.this);
				Pane group = (Pane)CompareRuleUI.this.getParent();
				group.getChildren().remove(CompareRuleUI.this);
				myRule.deleteInDatabase();
				int index = parentView.getSelected().getAutomationRules().indexOf(parentRule);
				//parentRule.removeCompareTo(index);
				//parentView.getSelected().getAutomationRules().get(index).removeCompareTo(index);
				
				double shiftAmount = CompareRuleUI.this.getPrefHeight() + 10.0;
                for (int i=compIndex; i<parentRuleUI.getAllCompareRules().size(); i++) {
                	parentRuleUI.getAllCompareRules().get(i).relocate(parentRuleUI.getAllCompareRules().get(i).getLayoutX(),
                			parentRuleUI.getAllCompareRules().get(i).getLayoutY() -shiftAmount);	
				}
                parentRuleUI.shiftAutomationRuleUI(-shiftAmount);
                
                for (int i=autoIndex+1; i<parentView.getAllRules().size(); i++) {
                	parentView.getAllRules().get(i).relocate(parentView.getAllRules().get(i).getLayoutX(),
                			parentView.getAllRules().get(i).getLayoutY() -shiftAmount);	
				}
                parentView.shiftAddGroupButton(-shiftAmount);
			}
    	});
    	
    	
    	removeCompareRuleLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//setup for this automation group in here
				System.out.println("IIIIIIIIIIIn remove compare rule\n\n");
				int compIndex = parentRuleUI.getAllCompareRules().indexOf(CompareRuleUI.this);
				int autoIndex = parentView.getAllRules().indexOf(parentRuleUI); 
				System.out.println("Test this " + compIndex+ "\n");
				//parentView.getAllGroups().remove(index);
				parentRuleUI.getAllCompareRules().remove(compIndex);
				//parentView.getGroupsPane().getChildren().remove(AutomationGroupUI.this);
				Pane group = (Pane)CompareRuleUI.this.getParent();
				group.getChildren().remove(CompareRuleUI.this);
				myRule.deleteInDatabase();
				
				double shiftAmount = CompareRuleUI.this.getPrefHeight() + 10.0;
                for (int i=compIndex; i<parentRuleUI.getAllCompareRules().size(); i++) {
                	parentRuleUI.getAllCompareRules().get(i).relocate(parentRuleUI.getAllCompareRules().get(i).getLayoutX(),
                			parentRuleUI.getAllCompareRules().get(i).getLayoutY() -shiftAmount);	
				}
                parentRuleUI.shiftAutomationRuleUI(-shiftAmount);
                
                for (int i=autoIndex+1; i<parentView.getAllRules().size(); i++) {
                	parentView.getAllRules().get(i).relocate(parentView.getAllRules().get(i).getLayoutX(),
                			parentView.getAllRules().get(i).getLayoutY() -shiftAmount);	
				}
                parentView.shiftAddRuleButton(-shiftAmount);
			}
    	});
    }
    
    /**
     * gets myRule
     * @return
     */
    public CompareRule getRule() {
    	return myRule;
    }
    
    /**
     * sets myRule
     * @param rule
     */
    public void setRule(CompareRule rule) {
    	myRule =rule;
    }

    /**
     * checks initialization
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //assert compareRulePane != null : "fx:id=\"compareRulePane\" was not injected: check your FXML file 'CopareRule.fxml'.";
        assert compareRuleRect != null : "fx:id=\"compareRuleRect\" was not injected: check your FXML file 'CopareRule.fxml'.";
        assert removeCompareRuleButton != null : "fx:id=\"removeCompareRuleButton\" was not injected: check your FXML file 'CopareRule.fxml'.";
        assert removeCompareRuleLabel != null : "fx:id=\"removeCompareRuleLabel\" was not injected: check your FXML file 'CopareRule.fxml'.";
        assert booleanTypeSelect != null : "fx:id=\"booleanTypeSelect\" was not injected: check your FXML file 'CopareRule.fxml'.";
        assert compareToField != null : "fx:id=\"compareToField\" was not injected: check your FXML file 'CopareRule.fxml'.";

    }

    /**
     * checks initialization
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//assert compareRulePane != null : "fx:id=\"compareRulePane\" was not injected: check your FXML file 'CopareRule.fxml'.";
        assert compareRuleRect != null : "fx:id=\"compareRuleRect\" was not injected: check your FXML file 'CopareRule.fxml'.";
        assert removeCompareRuleButton != null : "fx:id=\"removeCompareRuleButton\" was not injected: check your FXML file 'CopareRule.fxml'.";
        assert removeCompareRuleLabel != null : "fx:id=\"removeCompareRuleLabel\" was not injected: check your FXML file 'CopareRule.fxml'.";
        assert booleanTypeSelect != null : "fx:id=\"booleanTypeSelect\" was not injected: check your FXML file 'CopareRule.fxml'.";
        assert compareToField != null : "fx:id=\"compareToField\" was not injected: check your FXML file 'CopareRule.fxml'.";
	}
    
    
}
