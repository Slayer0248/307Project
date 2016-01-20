package view.testbank;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.accounts.User;
import model.test.Test;
import model.test.TestBank;
import model.util.DBAccess;
import view.navigation.ProfessorMainViewUI;
import view.testCreation.TestCreatorUI;

/**
 * UI for the TestBank.
 * 
 * @author colton.stapper
 */
public class TestBankUI extends Pane implements Initializable {

	/**
	 * The parent.
	 */
	private ProfessorMainViewUI parent;
	
	/**
	 * The test bank.
	 */
	private TestBank testBank;
	
	/**
	 * An array of test cells.
	 */
	private  ArrayList<TestBankCellUI> testCells;
	
	/**
	 * The test bank pane.
	 */
	@FXML
	private Pane testBankAP;
	
    /**
     * The search button.
     */
    @FXML
    private Button searchButton;
    
    /**
     * The add new test button.
     */
    @FXML
    private Button addNewTestButton;
    
    /**
     * The edit test button.
     */
    @FXML
    private Button editTestButton;
    
    /**
     * The scroll bar.
     */
    @FXML
    private ScrollBar testBankScroll;
    
    /**
     * The current user.
     */
    private User curUser;
    
    /**
     * Access to the database.
     */
    private DBAccess database;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
    /**
     * Creates a TestBankUI. 
     * @param parent
     * @param user
     */
    public TestBankUI(ProfessorMainViewUI parent, User user) {
    	this.parent = parent;
    	curUser = user;
    	this.testCells = new ArrayList<>();
    	database = new DBAccess();
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TestBank.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
        	e.printStackTrace();
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
        }
    	
    	setupView();
    }
    
    /**
     * Sets up the UI.
     */
    private void setupView() {
    	
    	layoutTestCells();
    	
    	System.out.println("THIS IS TEST: " + testBank.getTests());
    	
    	searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                System.out.println("Search the query.");
            }
        });
    	
    	addNewTestButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
            	Stage testStage = (Stage)(parent.getWindow());
            	TestCreatorUI testMaker = new TestCreatorUI(curUser);
            	
            	Scene createTestScene = new Scene(testMaker, 600, 600);
            	testStage.setScene(createTestScene);
            	
            	try {
	   	        		
	   	        		System.out.println("In close method.");
	   	        		ArrayList<Long> automationGroupIds = new ArrayList<Long>();
	   	        		ArrayList<Long> automationRuleIds = new ArrayList<Long>();
	   	        		ArrayList<Long> compareRuleIds = new ArrayList<Long>();
	  					ResultSet results = database.select("AutomationGroups", "automationGroupId", "ownerId =" + curUser.getId());
	  					while (results.next()) {
	  						automationGroupIds.add(results.getLong("automationGroupId"));
	  					}
	  				    database.closeConnection();
	  					
	  				    for (int i=0; i<automationGroupIds.size(); i++) {
	  				    	results = database.select("AutomationRules", "automationRuleId", "automationGroupId = " + automationGroupIds.get(i));
	  	 	        		while (results.next()) {
	  	 	        			automationRuleIds.add(results.getLong("automationRuleId"));
	  	 	        		}
	  	 	        		database.closeConnection();
	  				    }
	   	        		
	   	        		for (int i=0; i<automationGroupIds.size(); i++) {
	   	        			for (int j=0; j<automationRuleIds.size(); j++) {
	   	        				results = database.select("CompareRules", "compareRuleId", "automationGroupId = " + automationGroupIds.get(i) + " AND automationRuleId = " + automationRuleIds.get(j));
	   	        				while (results.next()) {
	   	    	        			compareRuleIds.add(results.getLong("compareRuleId"));
	   	    	        		}
	   	    	        		database.closeConnection();
	   	        			}
	   	        		}
	   	        		
	   	        		for (int i=0; i<compareRuleIds.size(); i++) {
	   	        			database.delete("CompareRules", "compareRuleId =" + compareRuleIds.get(i).longValue());
	   	        		}
	   	        		
	   	        		for (int i=0; i<automationRuleIds.size(); i++) {
	   	        			database.delete("AutomationRules", "automationRuleId = " + automationRuleIds.get(i).longValue());
	   	        		}
	   	        		
	   	        		for (int i=0; i<automationGroupIds.size(); i++) {
	   	        			database.delete("AutomationGroups", "automationGroupId = " + automationGroupIds.get(i));
	   	        		}
	   			  } catch (Exception ex) {
	   				 // TODO Auto-generated catch block
	   				 ex.printStackTrace();
	   			  }
            	
                System.out.println("Add new test.");
            }
        });
    	
    	
    }
    
    /**
     * Layouts the test cells.
     */
    private void layoutTestCells() {
    	
    	// reinit test bank call
    	testBank = new TestBank(curUser.getId());
    	List<Test> tests = testBank.getTests();
    	
    	addNewTestButton.relocate(40.0, 90.0);
    	
    	for (int i = 0; i < tests.size(); i++) {
    		Test test = tests.get(i);
        	TestBankCellUI testCell = new TestBankCellUI((ProfessorMainViewUI)getScene(), test);
        	assert(testCell != null);
        	testCells.add(testCell);
        	
        	Button deleteButton = testCell.getDeleteButton();
        	deleteButton.setId(String.valueOf(i));
        	deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createRemoveHandler());
        	
        	Button editButton = testCell.getEditButton();
        	editButton.setId(String.valueOf(i));
        	editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createEditHandler());
        	
        	testBankAP.getChildren().add(testCell);
        	
        	double groupOffset = 10;
        	double curYCoord = 120;

        	for (TestBankCellUI cell : testCells) {
        		cell.relocate(10.0, curYCoord);
        		curYCoord += cell.getPrefHeight() + groupOffset;
        	}
        	
        	addNewTestButton.relocate(40.0, curYCoord);
        	if ((curYCoord+ addNewTestButton.getPrefHeight()+10)-120 >= testBankScroll.getMax()) {
        		testBankScroll.setMax((curYCoord+ addNewTestButton.getPrefHeight()+10)-120);
        	}
    	}
    	
	    testBankScroll.valueProperty().addListener(new ChangeListener<Number>() {
	        public void changed(ObservableValue<? extends Number> ov,
	            Number old_val, Number new_val) {
	        	testBankAP.setLayoutY(-new_val.doubleValue());
	        	
	            }
	    });
    }
    
    /**
     * Gets the scroll bar.
     * @return
     */
    public ScrollBar getScrollbar() {
    	return testBankScroll;
    }
    
    /**
     * Creates an edit handler.
     * @return
     */
    private final EventHandler<MouseEvent> createEditHandler() {
        
	    return new EventHandler<MouseEvent> () {
		    @Override
		    public void handle(MouseEvent event) {
		        System.out.println("Edit button clicked");;
		        final Button editButton = (Button) event.getSource();

	        	TestBankCellUI testCell = testCells.get(Integer.parseInt(editButton.getId()));
	        	
	        	parent.setActivePane(new TestEditorUI(parent, testCell.getTest(), curUser));
		        
		    }
		};
    }
    
    /**
     * Creates a remove Handler.
     * @return
     */
    private final EventHandler<MouseEvent> createRemoveHandler() {
    
	    return new EventHandler<MouseEvent> () {
		    @Override
		    public void handle(MouseEvent event) {
		        System.out.println("Delete button clicked");;
		        final Button deleteButton = (Button) event.getSource();

		        Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setTitle("Confirmation Dialog");
		        alert.setContentText("Are you sure you want to delete this test?");
		        int testCellNum = Integer.parseInt(deleteButton.getId());
		        Optional<ButtonType> result = alert.showAndWait();
		        if (result.get() == ButtonType.OK){
		            System.out.println("Remove pane");
		        	TestBankCellUI testCell = testCells.get(testCellNum);
		        	testBank.removeTest(testCell.getTest());
		        	for (TestBankCellUI cell : testCells) {
		        		testBankAP.getChildren().remove(cell);
		        	}
		        	testCells = new ArrayList<>();
		        	layoutTestCells();
		            alert.close();
		        } else {
		            System.out.println("Do not Remove pane");
		            alert.close();
		            	// ... user chose CANCEL or closed the dialog
		        }
		        
		    }
		};
    }
}
