package view.testCreation;

import model.test.generation.AutomationGroup;
import model.test.generation.AutomationRule;
import model.test.generation.CompareRule;
import view.question.QuestionBankUI;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


/**
 * AdvancedGeneratorPage1UI view class
 * 
 * @author Clay
 */
public class AdvancedGeneratorPage1UI extends Pane implements Initializable {
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    
    
    //@FXML // fx:id="advancedGeneratorPage1Pane"
    //private Pane advancedGeneratorPage1Pane; // Value injected by FXMLLoader

    @FXML // fx:id="advancedGeneratorInstructionsLabel"
    private Label advancedGeneratorInstructionsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="advancedGeneratorSplitPane"
    private SplitPane advancedGeneratorSplitPane; // Value injected by FXMLLoader

    @FXML // fx:id="groupOrderingPane"
    private AnchorPane groupOrderingPane; // Value injected by FXMLLoader

    @FXML // fx:id="addGroupButton"
    private Rectangle addGroupButton; // Value injected by FXMLLoader

    @FXML // fx:id="addGroupButton"
    private Rectangle backgroundRect; // Value injected by FXMLLoader

    
    @FXML // fx:id="addGroupLabel"
    private Label addGroupLabel; // Value injected by FXMLLoader

    @FXML // fx:id="groupOrderingScroll"
    private ScrollBar groupOrderingScroll; // Value injected by FXMLLoader

    @FXML // fx:id="groupOrderingDivider"
    private Separator groupOrderingDivider; // Value injected by FXMLLoader

    
    
    @FXML // fx:id="multipleTestsCheckbox"
    private CheckBox multipleTestsCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="groupEditorPane"
    private AnchorPane groupEditorPane; // Value injected by FXMLLoader

    @FXML
    private Pane groupsPane;
    
    @FXML
    private Pane allGroupsSubPane;
    
    @FXML
    private Pane editorPane;
    
    @FXML
    private Pane controlsPane;
    
    @FXML
    private Label selectGroupLabel;
    
    @FXML // fx:id="groupEditorScroll"
    private ScrollBar groupEditorScroll; // Value injected by FXMLLoader

    @FXML // fx:id="groupTitleLabel"
    private Label groupTitleLabel; // Value injected by FXMLLoader

    @FXML // fx:id="numQuestionsFieldLabel"
    private Label numQuestionsFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="numQuestionsField"
    private TextField numQuestionsField; // Value injected by FXMLLoader

    @FXML // fx:id="rulesHeaderLabel"
    private Label rulesHeaderLabel; // Value injected by FXMLLoader

    @FXML // fx:id="addRuleButton"
    private Rectangle addRuleButton; // Value injected by FXMLLoader

    @FXML // fx:id="addRuleLabel"
    private Label addRuleLabel; // Value injected by FXMLLoader
    
    
    private ArrayList<AutomationGroupUI> automationGroups;
    private ArrayList<AutomationRuleUI> automationRules;
    
    private TestCreatorUI parent; 
   // private Pane groupsPane;

    /**
     * Constructor for AdvancedGeneratorPage1UI
     * 
     * @param creatorView parent view ui
     */
    public AdvancedGeneratorPage1UI(TestCreatorUI creatorView) {
    	
    	parent = creatorView;
    	automationGroups = new ArrayList<AutomationGroupUI>();
    	automationRules = new ArrayList<AutomationRuleUI>();
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdvancedGeneratorPage1.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
        	e.printStackTrace();
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
        }
    	groupOrderingScroll.setMax(28);
    	controlsPane.setVisible(false);
    	
    	initUI();
    }
    
    
    /**
     * Setup AdvancedGeneratorPage1UI ui elements.
     */
    public void initUI() {
    	
    	
    	addGroupButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
					//setActivePane(new QuestionBankUI());
				 //AutomationGroup test = new AutomationGroup();
				 AutomationGroupUI testUI = new AutomationGroupUI(AdvancedGeneratorPage1UI.this);
				 automationGroups.add(testUI);
				 
				 allGroupsSubPane.getChildren().add(testUI);
				 double groupOffset = 10;
				 double curYCoord = groupOffset;
				 double groupHeight = -1;
				 for (int i=0; i<automationGroups.size(); i++) {
					 automationGroups.get(i).relocate(6.0, curYCoord);
					 curYCoord = curYCoord + automationGroups.get(i).getPrefHeight() + groupOffset;
					 groupHeight = automationGroups.get(i).getPrefHeight();
				 }
				 if (curYCoord > groupsPane.getPrefHeight() && allGroupsSubPane.getPrefHeight() > groupsPane.getPrefHeight()) {
				    groupOrderingScroll.setMax(groupOrderingScroll.getMax() + groupHeight + groupOffset);
				 }
				 addGroupButton.relocate(6.0, curYCoord);
				 addGroupLabel.relocate(addGroupLabel.getLayoutX(), curYCoord+6.0);
				//System.out.println("Show Question Bank UI.");
				 System.out.println("Example: " + groupOrderingScroll.getMax());
				 refreshGroups();
			}
		});
    	
    	addGroupLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
					//setActivePane(new QuestionBankUI());
				 //AutomationGroup test = new AutomationGroup();
				 AutomationGroupUI testUI = new AutomationGroupUI(AdvancedGeneratorPage1UI.this);
				 automationGroups.add(testUI);
				
				 allGroupsSubPane.getChildren().add(testUI);
				 double groupOffset = 10;
				 double curYCoord = groupOffset;
				 double groupHeight = -1;
				 for (int i=0; i<automationGroups.size(); i++) {
					 automationGroups.get(i).relocate(6.0, curYCoord);
					 allGroupsSubPane.setPrefHeight(allGroupsSubPane.getPrefHeight() + groupOffset + automationGroups.get(i).getPrefHeight());
					 groupHeight = automationGroups.get(i).getPrefHeight();
					 curYCoord = curYCoord + automationGroups.get(i).getPrefHeight() + groupOffset;
				 }
				 addGroupButton.relocate(6.0, curYCoord);
				 addGroupLabel.relocate(addGroupLabel.getLayoutX(), curYCoord+6.0);
				 //S
				 if (curYCoord > groupsPane.getPrefHeight() && allGroupsSubPane.getPrefHeight() > groupsPane.getPrefHeight()) {
				    groupOrderingScroll.setMax(groupOrderingScroll.getMax() + groupHeight + groupOffset);
				 }
				 System.out.println("Example: " + groupOrderingScroll.getMax());
				 refreshGroups();
			}
		});
    	
    	groupOrderingScroll.valueProperty().addListener(new ChangeListener<Number>() {
    		public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
    			if (allGroupsSubPane.getPrefHeight() > groupsPane.getPrefHeight()) {
    				allGroupsSubPane.setLayoutY(-new_val.doubleValue());
    				if (allGroupsSubPane.getLayoutY() + allGroupsSubPane.getHeight() < groupsPane.getHeight()) {
        				allGroupsSubPane.setLayoutY(groupsPane.getHeight() - allGroupsSubPane.getHeight());
        			}
    			}		
    		}
    	});
    	
    	groupEditorScroll.valueProperty().addListener(new ChangeListener<Number>() {
    		public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
    			if (controlsPane.getPrefHeight() > editorPane.getPrefHeight()) {
    			   controlsPane.setLayoutY(-new_val.doubleValue());
    			   if (controlsPane.getLayoutY() + controlsPane.getHeight() < editorPane.getHeight()) {
    				  controlsPane.setLayoutY(editorPane.getHeight() - controlsPane.getHeight());
    			   }
    			}
    		}
    	});
    	
    	addRuleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				AutomationRuleUI ruleUI = new AutomationRuleUI(AdvancedGeneratorPage1UI.this, getSelected(), new AutomationRule());
				ruleUI.fillInAutomationRule();
				ruleUI.addAutomationRuleToDb();
				controlsPane.getChildren().add(ruleUI);
				
				double ruleOffset = 10;
				double curYCoord = addRuleButton.getLayoutY();
				if (automationRules.size() > 0) {
					curYCoord = automationRules.get(0).getLayoutY();
				}
				double ruleHeight = -1;

				automationRules.add(ruleUI);
				
				System.out.println("Example: " + curYCoord);
				for (int i=0; i<automationRules.size(); i++) {
				   automationRules.get(i).relocate(8.0, curYCoord);
				   controlsPane.setPrefHeight(controlsPane.getPrefHeight() + ruleOffset + automationRules.get(i).getPrefHeight());
				   ruleHeight = automationRules.get(i).getPrefHeight();
				   curYCoord = curYCoord + automationRules.get(i).getPrefHeight() + ruleOffset;
				}
				addRuleButton.relocate(8.0, curYCoord);
				addRuleLabel.relocate(addRuleLabel.getLayoutX(), curYCoord+13.0);
				//S
				if (curYCoord > editorPane.getPrefHeight() && controlsPane.getPrefHeight() > editorPane.getPrefHeight()) {
					groupEditorScroll.setMax(groupEditorScroll.getMax() + ruleHeight + ruleOffset);
				}
			}
    	});
    	
    	addRuleLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				AutomationRuleUI ruleUI = new AutomationRuleUI(AdvancedGeneratorPage1UI.this, getSelected(), new AutomationRule());
				ruleUI.fillInAutomationRule();
				ruleUI.addAutomationRuleToDb();
				
				controlsPane.getChildren().add(ruleUI);
				
				double ruleOffset = 10;
				double curYCoord = addRuleButton.getLayoutY();
				if (automationRules.size() > 0) {
					curYCoord = automationRules.get(0).getLayoutY();
				}
				double ruleHeight = -1;
				
				automationRules.add(ruleUI);
				System.out.println("Example: " + curYCoord);
				for (int i=0; i<automationRules.size(); i++) {
				   automationRules.get(i).relocate(6.0, curYCoord);
				   controlsPane.setPrefHeight(controlsPane.getPrefHeight() + ruleOffset + automationRules.get(i).getPrefHeight());
				   ruleHeight = automationRules.get(i).getPrefHeight();
				   curYCoord = curYCoord + automationRules.get(i).getPrefHeight() + ruleOffset;
				}
				addRuleButton.relocate(6.0, curYCoord);
				addRuleLabel.relocate(addRuleLabel.getLayoutX(), curYCoord+6.0);
				//S
				if (curYCoord > editorPane.getPrefHeight() && controlsPane.getPrefHeight() > editorPane.getPrefHeight()) {
				   groupEditorScroll.setMax(groupEditorScroll.getMax() + ruleHeight + ruleOffset);
				}
			}
    	});
    	
    	controlsPane.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				
				int ruleIndex = -1;
				
				for (int i=0; i<automationRules.size() && ruleIndex == -1; i++) {
				   if (automationRules.get(i).getLayoutY() <= e.getY() && 
				      automationRules.get(i).getLayoutY() + automationRules.get(i).getPrefHeight() >= e.getY() &&
				      (automationRules.get(i).getLayoutX() > e.getX() || 
				      automationRules.get(i).getLayoutX() + automationRules.get(i).getPrefWidth() < e.getX())) {
					   ruleIndex = i;
				   }
	
				}
				if (ruleIndex != -1) {
				   System.out.println("Rule to remove is " + ruleIndex);
				   controlsPane.getChildren().remove(automationRules.get(ruleIndex));
				   double shiftAmount = automationRules.get(ruleIndex).getHeight() + 10;
				   automationRules.get(ruleIndex).getRule().deleteInDatabase();
				   getSelected().getAutomationRules().remove(automationRules.get(ruleIndex).getRule());
				   automationRules.remove(ruleIndex);
				   for (int i=ruleIndex; i< automationRules.size(); i++) {
					   automationRules.get(i).relocate(automationRules.get(i).getLayoutX(), automationRules.get(i).getLayoutY()-shiftAmount);
				   }
				   shiftAddRuleButton(-shiftAmount);
				   
				}
			}
    	});
    	//prefHeight="370" prefWidth="192.0"
    	//groupOrderingScroll.
    	
    }
    
    /**
     * Get all AutomationGroupUIs in AdvancedGeneratorPage1UI
     * 
     * @return all AutomationGroupUIs in AdvancedGeneratorPage1UI
     */
    public ArrayList<AutomationGroupUI> getAllGroups() {
    	return automationGroups;
    }
    
    /**
     * Get all AutomationRuleUIs in AdvancedGeneratorPage1UI
     * 
     * @return all AutomationRuleUIs in AdvancedGeneratorPage1UI
     */
    public ArrayList<AutomationRuleUI> getAllRules() {
    	return automationRules;
    }
    
    /**
     * Get the selected AutomationGroupUI in AdvancedGeneratorPage1UI
     * 
     * @return the selected AutomationGroupUI in AdvancedGeneratorPage1UI
     */
    public AutomationGroup getSelected() {
    	AutomationGroupUI groupUI = null;
    	for (int i=0; i<automationGroups.size() && groupUI == null; i++) {
    		if (automationGroups.get(i).uiIsSelected()) {
    		   groupUI = automationGroups.get(i);
    		}
    	}
    	
    	if (groupUI != null) {
    		return groupUI.getGroup();
    	}
    	return null;
    }
    
    /**
     * Get the editor pane
     * 
     * @return the editor pane
     */
    public Pane getEditorPane() {
    	return editorPane;
    }
    
    /**
     * Get the controls pane
     * 
     * @return the controls pane
     */
    public Pane getControlsPane() {
    	return controlsPane;
    }
    
    /**
     * Get the groups pane
     * 
     * @return the groups pane
     */
    public Pane getGroupsPane() {
    	return allGroupsSubPane;
    }
    
    /**
     * Get the group editor scroll bar.
     * 
     * @return the group editor scroll bar.
     */
    public ScrollBar getGroupEditorScroll() {
    	return groupEditorScroll;
    }
    
    /**
     * Shifts the add automation rule button in the ui
     */
    public void shiftAddRuleButton(double shiftAmount) {
    	addRuleButton.relocate(6.0, addRuleButton.getLayoutY() + shiftAmount);
		addRuleLabel.relocate(addRuleLabel.getLayoutX(), addRuleLabel.getLayoutY()+shiftAmount);
    }
    
    /**
     * Shifts the add automation group button in the ui
     */
    public void shiftAddGroupButton(double shiftAmount) {
    	addGroupButton.relocate(6.0, addGroupButton.getLayoutY() + shiftAmount);
    	addGroupLabel.relocate(addGroupLabel.getLayoutX(), addGroupLabel.getLayoutY()+shiftAmount);
    }
    
    /**
     * Returns the parent view ui
     * 
     * @return the parent view ui
     */
    public TestCreatorUI getTestCreatorUI() {
    	return parent;
    }
    
    /**
     * Shows the selected AutomationGroupUI's associated data.
     */
    public void showAutomationGroup(AutomationGroup group) {
    	while (automationRules.size() > 0) {
    		controlsPane.getChildren().remove(automationRules.get(0));
    		automationRules.remove(0);	
    	}
    	addRuleButton.relocate(8.0, 81.0);
        addRuleLabel.relocate(100.0, 94.0);
        
        double ruleOffset = 10;
        double curYAutoRule = 81.0;
        double ruleHeight = -1;
    	for (int i=0; i<group.getAutomationRules().size(); i++) {
    		System.out.println("Num rules: " + group.getAutomationRules().size());
    		AutomationRuleUI ruleUI = new AutomationRuleUI(AdvancedGeneratorPage1UI.this, group, new AutomationRule());
    		ruleUI.setRule(group.getAutomationRule(i));
    		getSelected().setAutomationRule(group.getAutomationRule(i), i);
    		getSelected().updateInDatabase();
			controlsPane.getChildren().add(ruleUI);
			
			automationRules.add(ruleUI);
			ruleUI.relocate(8.0, curYAutoRule);
			ruleHeight = ruleUI.getPrefHeight();
			//controlsPane.setPrefHeight(controlsPane.getPrefHeight() + ruleOffset + automationRules.get(j).getPrefHeight());
			curYAutoRule = curYAutoRule + ruleUI.getPrefHeight() + ruleOffset;
			
			//S
			if (curYAutoRule > editorPane.getPrefHeight() && controlsPane.getPrefHeight() > editorPane.getPrefHeight()) {
				groupEditorScroll.setMax(groupEditorScroll.getMax() + ruleHeight + ruleOffset);
			}
			
			double ruleCompHeight = -1;
    		if (group.getAutomationRule(i).getCompareRules().size() > 0) {
    		   double curYCompRule = 68.0;
    		   for (int j=0; j<group.getAutomationRule(i).getCompareRules().size(); j++) {
    			   CompareRuleUI compareRuleUI = new CompareRuleUI(AdvancedGeneratorPage1UI.this, ruleUI, group, group.getAutomationRule(i), new CompareRule());
    			   compareRuleUI.relocate(10, curYCompRule);
    			   compareRuleUI.setRule(group.getAutomationRule(i).getCompareRule(j));
    			   //ruleUI.getAllCompareRules().add(compareRuleUI);
    			   ruleUI.getChildren().add(compareRuleUI);
    			   
    			   
    			   double shiftAmount = ruleOffset +compareRuleUI.getPrefHeight();
    			   getControlsPane().setPrefHeight(getControlsPane().getPrefHeight() + shiftAmount);
    			   ruleUI.shiftAutomationRuleUI(shiftAmount);
    			   curYCompRule = curYCompRule + shiftAmount;
    			   curYAutoRule = curYAutoRule + shiftAmount;
    			   
    		   }
    		   //ruleUI.get
    		}
    	}
    	addRuleButton.relocate(8.0, curYAutoRule);
		addRuleLabel.relocate(addRuleLabel.getLayoutX(), curYAutoRule+13.0);
		
    	selectGroupLabel.setVisible(false);
    	controlsPane.setVisible(true);
    }
    
    /**
     * Update the AutomationGroupUI displayed.
     */
    public void refreshGroups() {
    	for (int i=0; i<automationGroups.size(); i++) {
    		automationGroups.get(i).setLabelNum(i+1);
    	}
    }
    
    /**
     * Initialize from fxml. 
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //assert advancedGeneratorPage1Pane != null : "fx:id=\"advancedGeneratorPage1Pane\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert advancedGeneratorInstructionsLabel != null : "fx:id=\"advancedGeneratorInstructionsLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert advancedGeneratorSplitPane != null : "fx:id=\"advancedGeneratorSplitPane\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupOrderingPane != null : "fx:id=\"groupOrderingPane\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert addGroupButton != null : "fx:id=\"addGroupButton\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert addGroupLabel != null : "fx:id=\"addGroupLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupOrderingScroll != null : "fx:id=\"groupOrderingScroll\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupOrderingDivider != null : "fx:id=\"groupOrderingDivider\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert multipleTestsCheckbox != null : "fx:id=\"multipleTestsCheckbox\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupEditorPane != null : "fx:id=\"groupEditorPane\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupEditorScroll != null : "fx:id=\"groupEditorScroll\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupTitleLabel != null : "fx:id=\"groupTitleLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert numQuestionsFieldLabel != null : "fx:id=\"numQuestionsFieldLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert numQuestionsField != null : "fx:id=\"numQuestionsField\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert rulesHeaderLabel != null : "fx:id=\"rulesHeaderLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert addRuleButton != null : "fx:id=\"addRuleButton\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert addRuleLabel != null : "fx:id=\"addRuleLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";

    }

    /**
     * Initialize from fxml. 
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//assert advancedGeneratorPage1Pane != null : "fx:id=\"advancedGeneratorPage1Pane\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert advancedGeneratorInstructionsLabel != null : "fx:id=\"advancedGeneratorInstructionsLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert advancedGeneratorSplitPane != null : "fx:id=\"advancedGeneratorSplitPane\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupOrderingPane != null : "fx:id=\"groupOrderingPane\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert addGroupButton != null : "fx:id=\"addGroupButton\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert addGroupLabel != null : "fx:id=\"addGroupLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupOrderingScroll != null : "fx:id=\"groupOrderingScroll\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupOrderingDivider != null : "fx:id=\"groupOrderingDivider\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert multipleTestsCheckbox != null : "fx:id=\"multipleTestsCheckbox\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupEditorPane != null : "fx:id=\"groupEditorPane\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupEditorScroll != null : "fx:id=\"groupEditorScroll\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert groupTitleLabel != null : "fx:id=\"groupTitleLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert numQuestionsFieldLabel != null : "fx:id=\"numQuestionsFieldLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert numQuestionsField != null : "fx:id=\"numQuestionsField\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert rulesHeaderLabel != null : "fx:id=\"rulesHeaderLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert addRuleButton != null : "fx:id=\"addRuleButton\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
        assert addRuleLabel != null : "fx:id=\"addRuleLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage1.fxml'.";
	}
}
