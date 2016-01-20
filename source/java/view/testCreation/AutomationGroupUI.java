package view.testCreation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.test.generation.AutomationGroup;
import model.util.DBAccess;

/**
 * 
 * ui for collection of automation rules
 *
 */
public class AutomationGroupUI extends Pane implements Initializable {
	
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //@FXML // fx:id="automationGroupPane"
    //private Pane automationGroupPane; // Value injected by FXMLLoader

    @FXML // fx:id="automationGroupLabel"
    private Label automationGroupLabel; // Value injected by FXMLLoader

    @FXML // fx:id="removeGroupButton"
    private Circle removeGroupButton; // Value injected by FXMLLoader

    @FXML // fx:id="removeGroupLabel"
    private Label removeGroupLabel; // Value injected by FXMLLoader

    @FXML // fx:id="automationGroupRect"
    private Rectangle automationGroupRect; // Value injected by FXMLLoader

    private AdvancedGeneratorPage1UI parentView;
    
    private AutomationGroup myGroup;
    private DBAccess database;
    private boolean isSelected;
    
    /**
     * ui constructor
     * @param parent
     */
    public AutomationGroupUI(AdvancedGeneratorPage1UI parent) {
    	isSelected = false;
    	database = new DBAccess();
    	parentView = parent;
    	myGroup = new AutomationGroup();
    	myGroup.setId(database.getOpenIdFor("AutomationGroups", "automationGroupId"));
    	myGroup.setOwnerId(parentView.getTestCreatorUI().getCurrentUser().getId());
    	myGroup.setNumberOfQuestions(0);
    	myGroup.addToDatabase();
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AutomationGroup.fxml"));
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
     * initializes ui
     */
    public void initUI() {
    	removeGroupButton.toFront();
    	removeGroupLabel.toFront();
    	removeGroupButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("IIIIIIIIIIIn remove button\n\n");
				//System.out.println("Test this " + index);
				int index = parentView.getAllGroups().indexOf(AutomationGroupUI.this);
				System.out.println("Test this " + index + "\n");
				parentView.getAllGroups().remove(index);
				//AutomationGroupUI.this.getChildren().remove(AutomationGroupUI.this);
				Pane group = (Pane)AutomationGroupUI.this.getParent();
				group.getChildren().remove(AutomationGroupUI.this);
				
				double shiftAmount = AutomationGroupUI.this.getPrefHeight() + 10.0;
				for(int i=index; i<parentView.getAllGroups().size(); i++) {
					parentView.getAllGroups().get(i).relocate(parentView.getAllGroups().get(i).getLayoutX(),
							parentView.getAllGroups().get(i).getLayoutY() -shiftAmount);
				}
				parentView.shiftAddGroupButton(shiftAmount);
				myGroup.deleteFromDatabase();
				parentView.refreshGroups();
			}
    	});
    	
    	removeGroupLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("IIIIIIIIIIIn remove label\n\n");
				int index = parentView.getAllGroups().indexOf(AutomationGroupUI.this);
				System.out.println("Test this " + index + "\n");
				parentView.getAllGroups().remove(index);
				//parentView.getGroupsPane().getChildren().remove(AutomationGroupUI.this);
				Pane group = (Pane)AutomationGroupUI.this.getParent();
				group.getChildren().remove(AutomationGroupUI.this);
				
				double shiftAmount = AutomationGroupUI.this.getPrefHeight() + 10.0;
                for (int i=index; i<parentView.getAllGroups().size(); i++) {
                	parentView.getAllGroups().get(i).relocate(parentView.getAllGroups().get(i).getLayoutX(),
							parentView.getAllGroups().get(i).getLayoutY() -shiftAmount);	
				}
                parentView.shiftAddGroupButton(-shiftAmount);
                myGroup.deleteFromDatabase();
                parentView.refreshGroups();
			}
    	});
    	
    	automationGroupRect.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//setup for this automation group in here
				System.out.println("IIIIIIIIIIIn select group\n\n");
				for (int i=0; i<parentView.getAllGroups().size(); i++) {
					parentView.getAllGroups().get(i).getRectUI().setStroke(Color.BLACK);
					parentView.getAllGroups().get(i).setSelected(false);
				}
				AutomationGroupUI.this.getRectUI().setStroke(Color.DEEPSKYBLUE);
				AutomationGroupUI.this.setSelected(true);
				parentView.showAutomationGroup(myGroup);
			}
    	});
    	
    
    }
    
    /**
     * gets automationGroupRect
     * @return
     */
    public Rectangle getRectUI() {
    	return automationGroupRect;
    }
    
    /**
     * sets automationGroupRect
     * @param number
     */
    public void setLabelNum(int number) {
    	automationGroupLabel.setText("Automation Group " + number);
    }
    
    /**
     * returns whether the ui is selected
     * @return
     */
    public boolean uiIsSelected() {
    	return isSelected;
    }
    
    /**
     * sets the ui to selected or not
     * @param selection
     */
    public void setSelected(boolean selection) {
    	isSelected = selection;
    }
    
    /**
     * gets myGroup
     * @return
     */
    public AutomationGroup getGroup() {
    	return myGroup;
    }
    
    /**
     * checks initialization
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //assert automationGroupPane != null : "fx:id=\"automationGroupPane\" was not injected: check your FXML file 'AutomationGroup.fxml'.";
        assert automationGroupLabel != null : "fx:id=\"automationGroupLabel\" was not injected: check your FXML file 'AutomationGroup.fxml'.";
        assert removeGroupButton != null : "fx:id=\"removeGroupButton\" was not injected: check your FXML file 'AutomationGroup.fxml'.";
        assert removeGroupLabel != null : "fx:id=\"removeGroupLabel\" was not injected: check your FXML file 'AutomationGroup.fxml'.";
        assert automationGroupRect != null : "fx:id=\"automationGroupRect\" was not injected: check your FXML file 'AutomationGroup.fxml'.";

    }

    /**
     * checks initialization
     * @param location, resources
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//assert automationGroupPane != null : "fx:id=\"automationGroupPane\" was not injected: check your FXML file 'AutomationGroup.fxml'.";
        assert automationGroupLabel != null : "fx:id=\"automationGroupLabel\" was not injected: check your FXML file 'AutomationGroup.fxml'.";
        assert removeGroupButton != null : "fx:id=\"removeGroupButton\" was not injected: check your FXML file 'AutomationGroup.fxml'.";
        assert removeGroupLabel != null : "fx:id=\"removeGroupLabel\" was not injected: check your FXML file 'AutomationGroup.fxml'.";
        assert automationGroupRect != null : "fx:id=\"automationGroupRect\" was not injected: check your FXML file 'AutomationGroup.fxml'.";
	}
}
