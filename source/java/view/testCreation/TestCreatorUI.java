package view.testCreation;

import javafx.scene.Scene;
import model.test.Test;
import model.util.DBAccess;
import model.accounts.User;
import view.navigation.ProfessorMainViewUI;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The main ui to create a test.
 * 
 * @author Clay
 */

public class TestCreatorUI extends Pane implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //@FXML // fx:id="createTestViewPane"
    //private static Pane createTestViewPane; // Value injected by FXMLLoader

    @FXML // fx:id="createTestViewRect"
    private Rectangle createTestViewRect; // Value injected by FXMLLoader

    @FXML // fx:id="createTestViewSeperatorAbove"
    private Separator createTestViewSeperatorAbove; // Value injected by FXMLLoader

    @FXML // fx:id="createTestTitleLabel"
    private Label createTestTitleLabel; // Value injected by FXMLLoader

    @FXML // fx:id="quickCreatorLink"
    private Hyperlink quickCreatorLink; // Value injected by FXMLLoader

    @FXML // fx:id="simpleGeneratorLink"
    private Hyperlink simpleGeneratorLink; // Value injected by FXMLLoader

    @FXML // fx:id="advancedGeneratorLink"
    private Hyperlink advancedGeneratorLink; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML // fx:id="creatorContainerScrollPane"
    private ScrollPane creatorContainerScrollPane; // Value injected by FXMLLoader

    //@FXML // fx:id="creatorContainerAnchor"
    //private AnchorPane creatorContainerAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="createTestViewSeperatorBelow"
    private Separator createTestViewSeperatorBelow; // Value injected by FXMLLoader
    
    private Pane testCreatorPane;
    
    private User curUser;
    private Test createdTest;
    private DBAccess database;

    //private long curUserId;
    
    /**
     * Creates the TestCreatorUI
     * @param user the logged in user
     */
    public TestCreatorUI(User user) {
    	try {
			database = new DBAccess();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	curUser = user;
    	//System.out.println("User id is " + curUser.getId());
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTestPage.fxml"));
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
    	//System.out.println("" + creatorContainerScrollPane.getHeight() + " " + creatorContainerScrollPane.getWidth());
        //System.out.println("" + creatorContainerScrollPane.getLayoutX() + " " + creatorContainerScrollPane.getLayoutY());
        //System.out.println("" + createButton.getHeight() + " " + createButton.getWidth());
        //System.out.println("" + createButton.getLayoutX() + " " + createButton.getLayoutY());
    	
    }
    
    /**
     * Gets the current user who is logged in
     * @return the user who is logged in
     */
    public User getCurrentUser() {
    	return curUser;
    }
    
    /**
     * Initialize from fxml. 
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        //assert createTestViewPane != null : "fx:id=\"createTestViewPane\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert createTestViewRect != null : "fx:id=\"createTestViewRect\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert createTestViewSeperatorAbove != null : "fx:id=\"createTestViewSeperatorAbove\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert createTestTitleLabel != null : "fx:id=\"createTestTitleLabel\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert quickCreatorLink != null : "fx:id=\"quickCreatorLink\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert simpleGeneratorLink != null : "fx:id=\"simpleGeneratorLink\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert advancedGeneratorLink != null : "fx:id=\"advancedGeneratorLink\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert creatorContainerScrollPane != null : "fx:id=\"creatorContainerScrollPane\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        //assert creatorContainerAnchor != null : "fx:id=\"creatorContainerAnchor\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert createTestViewSeperatorBelow != null : "fx:id=\"createTestViewSeperatorBelow\" was not injected: check your FXML file 'CreateTestPage.fxml'.";

        //setupView();
    }

    /**
     * Initialize from fxml. 
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
        //assert createTestViewPane != null : "fx:id=\"createTestViewPane\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert createTestViewRect != null : "fx:id=\"createTestViewRect\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert createTestViewSeperatorAbove != null : "fx:id=\"createTestViewSeperatorAbove\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert createTestTitleLabel != null : "fx:id=\"createTestTitleLabel\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert quickCreatorLink != null : "fx:id=\"quickCreatorLink\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert simpleGeneratorLink != null : "fx:id=\"simpleGeneratorLink\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert advancedGeneratorLink != null : "fx:id=\"advancedGeneratorLink\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert creatorContainerScrollPane != null : "fx:id=\"creatorContainerScrollPane\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        //assert creatorContainerAnchor != null : "fx:id=\"creatorContainerAnchor\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        assert createTestViewSeperatorBelow != null : "fx:id=\"createTestViewSeperatorBelow\" was not injected: check your FXML file 'CreateTestPage.fxml'.";
        
        
        
	}
	
	/**
     * Setup for the TestCreatorUI. 
     */
	private void setupView() {
		quickCreatorLink.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				QuickTestCreatorUI quickCreate = new QuickTestCreatorUI(TestCreatorUI.this);
				
				testCreatorPane = (Pane)quickCreate;
				testCreatorPane.relocate(0, 0);
				creatorContainerScrollPane.setVmax(testCreatorPane.getPrefHeight());
				creatorContainerScrollPane.setContent(testCreatorPane);
				
				createButton.setText("Create");
			}
		});
		
		simpleGeneratorLink.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
                SimpleGeneratorPage1UI simpleGenerate1 = new SimpleGeneratorPage1UI(TestCreatorUI.this);
				
				testCreatorPane = (Pane)simpleGenerate1;
				testCreatorPane.relocate(0, 0);
				creatorContainerScrollPane.setVmax(testCreatorPane.getPrefHeight());
				creatorContainerScrollPane.setContent(testCreatorPane);
				
				createButton.setText("Next");
			}
		});
		
		advancedGeneratorLink.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				AdvancedGeneratorPage1UI advancedGenerate1 = new AdvancedGeneratorPage1UI(TestCreatorUI.this);
				
				testCreatorPane = (Pane)advancedGenerate1;
				testCreatorPane.relocate(0, 0);
				creatorContainerScrollPane.setVmax(testCreatorPane.getPrefHeight());
				creatorContainerScrollPane.setContent(testCreatorPane);
				
				createButton.setText("Next");
			}
		});
		
		createButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//System.out.println(createButton.getText());
				if(((Button)e.getSource()).getText().equals("Create")) {
					if (testCreatorPane != null) {
						
					   //Test createdTest= new Test();
					   int showError = 0;
					   String errorMsg = "";
					   createdTest = null;
					   ArrayList<Test> createdTests = new ArrayList<Test>();
					   if (testCreatorPane instanceof QuickTestCreatorUI) {
						  QuickTestCreatorUI qctest = (QuickTestCreatorUI)testCreatorPane;
						  
						  
						  
						  
						  
                          if (qctest.shouldCopyTest() && !qctest.shouldCreateBlankTest() && !qctest.selectedTestToCopy() && qctest.getTestToCopy() == null) {
                        	  showError = 1;
							  errorMsg = errorMsg + "- Must select a valid test to copy.\n";
						  } 
                          
                          
                          if (showError == 0 && !qctest.shouldCopyTest() && !qctest.shouldCreateBlankTest()) {
                        	  showError = 1;
							  errorMsg = errorMsg + "- Must select an option to create a test.\n";
                          }
                          if (showError == 0) {
  						     createdTest = qctest.getCreatedTest();
  						     //qctest.setUseForm(toUse);
  						     //showUseFormDialog()
  						  }
                          
                          if (qctest.shouldUseForm() == 1) {
						     if (showError== 0 && createdTest.getName().length() == 0) {
							     showError = 1;
							     errorMsg = errorMsg + "- Test must have a name to be created.\n";
						     }
						     if (showError== 0 && createdTest.getTags().size() == 0) {
							     showError = 1;
							     errorMsg = errorMsg + "- Test must have at least 1 tag to be created\n";
						     }
						     if (showError== 0 && createdTest.getDescription().length() == 0) {
							     showError = 1;
							     errorMsg = errorMsg + "- Test must have a description to be created.\n";
						     }
						     try {
							    if (showError== 0 && !qctest.getClassField().isDisabled() &&
							       database.countAll("Sections", "sectionId = " + createdTest.getSectionId()) != 1) {
								   showError = 1;
								   errorMsg = errorMsg + "- Test must be associated with a single section\n";
							    }
							 
							    //if (showError== 0 && qctest.shouldCopyTest() && !qctest.shouldCreateBlankTest())
							 
						     } catch (Exception e1) {
							    // TODO Auto-generated catch block
							    e1.printStackTrace();
						     }
                          }
					   }
					   else if (testCreatorPane instanceof SimpleGeneratorPage2UI) {
						   SimpleGeneratorPage2UI simpleGen2 = (SimpleGeneratorPage2UI)testCreatorPane;
						   errorMsg = errorMsg.concat(simpleGen2.shouldFinishTests());
						   if (errorMsg.equals("No Errors")) {
							   createdTests.addAll(simpleGen2.getFinishedTests());
						   }
						   else {
							   showError = 1;
						   }
						   
					   }
					   else if (testCreatorPane instanceof AdvancedGeneratorPage2UI) {
						   
					   }
					   
					   if (showError == 0) {
						  if (testCreatorPane instanceof QuickTestCreatorUI) {
						     if (database.isOpenIdFor("Tests", "testId")) {
							     createdTest.setId(database.getOpenIdFor("Tests", "testId"));
						     }
						     System.out.println("new id = " + database.getOpenIdFor("Tests", "testId")+ "\n");
						     createdTest.addToDatabase();
                          //
                        	 QuickTestCreatorUI qctest = (QuickTestCreatorUI)testCreatorPane;
                        	 qctest.getClassField().setDisable(false);
                        	 if (!qctest.getClassField().isDisabled()) {
                        		createdTest.addToTestSectionsTable(); 
                        		System.out.println("yay\n");
                        	 }
						  }
						  if (testCreatorPane instanceof SimpleGeneratorPage2UI) {
							 for (int i=0; i<createdTests.size(); i++) {
							   createdTests.get(i).setOwnerId(curUser.getId());
							   if (database.isOpenIdFor("Tests", "testId")) {
								  createdTests.get(i).setId(database.getOpenIdFor("Tests", "testId"));
							   }
							   System.out.println("new id = " + database.getOpenIdFor("Tests", "testId")+ "\n");
							   createdTests.get(i).addToDatabase();
	                          
							   SimpleGeneratorPage2UI simpleGen2 = (SimpleGeneratorPage2UI)testCreatorPane;
	                           if (!simpleGen2.getLastPage().getClassField().isDisabled()) {
	                        	   createdTests.get(i).addToTestSectionsTable(); 
	                           }
							 }
					      }
						  
						  System.out.println("Created a Test!");
						  
					      System.out.println("Go to Test Bank View here");
					      Stage testStage = (Stage)(getScene().getWindow());
		            	  ProfessorMainViewUI mainView = new ProfessorMainViewUI(600, 600);
		            	  try {
					         mainView.performFinalSetup("testCreator", curUser.getId());//curUser.getId());
						  } catch (Exception e1) {
						     // TODO Auto-generated catch block
						     e1.printStackTrace();
						  }
		            	  testStage.setScene(mainView);
					   }
					   else {
					      //Show error.
						  showError(errorMsg);
					   }
					}
					else {
					   System.out.println("No test was created");
					}
				}
				else {
					if(creatorContainerScrollPane.getVmax() > getPrefHeight()) {
						//simple generator
						SimpleGeneratorPage1UI simpleGen1 = (SimpleGeneratorPage1UI)testCreatorPane;
						String returnMsg = simpleGen1.shouldCreatePartialTests();
						if (returnMsg.equals("No Errors")) {
						
						   ArrayList<Test> partialTests = simpleGen1.getPartialTests();
						   /*try {
							   if (database.countAll("Questions", "ownerId = " + curUser.getId()) == 0) {
								   
							   }
						   } catch (Exception e1) {
							  // TODO Auto-generated catch block
							  e1.printStackTrace();
						   }*/
							
						   System.out.println("No errors.");
						   //ArrayList<Test> partialTests;
							
						   
						   SimpleGeneratorPage2UI simpleGenerate2 = new SimpleGeneratorPage2UI(partialTests, simpleGen1);
						
						   testCreatorPane = (Pane)simpleGenerate2;
						   testCreatorPane.relocate(0, 0);
						   creatorContainerScrollPane.setVmax(testCreatorPane.getPrefHeight());
						   creatorContainerScrollPane.setContent(testCreatorPane);
						
						   createButton.setText("Create");
						}
						else {
						   showError(returnMsg);
						}
					}
					else if(creatorContainerScrollPane.getVmax() > 0) {
						//advanced generator
						AdvancedGeneratorPage2UI advancedGenerate2 = new AdvancedGeneratorPage2UI();
						
						testCreatorPane = (Pane)advancedGenerate2;
						testCreatorPane.relocate(0, 0);
						creatorContainerScrollPane.setVmax(testCreatorPane.getPrefHeight());
						creatorContainerScrollPane.setContent(testCreatorPane);
						
						createButton.setText("Create");
					}
					else {
						System.out.println("Show error message here");
					}
				}
			}
		});
		
		cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Go to Test Bank View here");
				Stage testStage = (Stage)(getScene().getWindow());
            	ProfessorMainViewUI mainView = new ProfessorMainViewUI(600, 600);
            	try {
					mainView.performFinalSetup("testCreator", curUser.getId());//curUser.getId());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	testStage.setScene(mainView);
			}
		});
	}
	
	/**
     * Setup error message alert for the TestCreatorUI. 
     */
	private void showError(String msg) {
		
		
	   Alert alert = new Alert(AlertType.ERROR);
	   alert.setTitle("Test creation errors");
	   alert.setHeaderText("Your Test can't be created due to the following errors.");
	   alert.setContentText(msg);
	   ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
	   alert.getButtonTypes().setAll(buttonTypeOk);
	   Optional<ButtonType> response = alert.showAndWait();
	   if (response.get() == buttonTypeOk){
	      alert.close();
	   }
   }
	
  /**
   * Setup use form alert for the TestCreatorUI. 
   */
   public int showUseFormDialog() {
		int result = -1;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Should Use Form");
		alert.setHeaderText("Use the form data to fill in copy or use existing test's data?");
		//alert.setContentText(msg);
		ButtonType buttonTypeYes = new ButtonType("Form", ButtonData.OK_DONE);
		ButtonType buttonTypeNo = new ButtonType("Test", ButtonData.OK_DONE);
		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
		Optional<ButtonType> response = alert.showAndWait();
	    if (response.get() == buttonTypeNo){
	       result = 0;
		}
	    if (response.get() == buttonTypeYes){
		   result = 1;
	    }
	    alert.close();
	    return result;
	    
	}
   
   /*public int showEmptyQuestionBankMessage() {
		int result = -1;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Question Bank is empty.");
		alert.setHeaderText("Any question form data will not be p");
		//alert.setContentText(msg);
		ButtonType buttonTypeYes = new ButtonType("Form", ButtonData.OK_DONE);
		ButtonType buttonTypeNo = new ButtonType("Test", ButtonData.OK_DONE);
		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
		Optional<ButtonType> response = alert.showAndWait();
	    if (response.get() == buttonTypeNo){
	       result = 0;
		}
	    if (response.get() == buttonTypeYes){
		   result = 1;
	    }
	    alert.close();
	    return result;
	    
	}*/
}
