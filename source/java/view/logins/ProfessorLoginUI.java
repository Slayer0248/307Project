package view.logins;

import javafx.event.ActionEvent;
import model.accounts.User;
import model.util.DBAccess;
import view.navigation.ProfessorMainViewUI;
import view.student.StudentMainView;
import java.io.IOException;
import java.lang.Override;
import java.util.*;
import java.sql.*;
import java.net.URL;

import javafx.fxml.Initializable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Professor login page view class.
 *
 */

//fx:controller="view.accounts.ProfessorLoginUI"
public class ProfessorLoginUI extends Pane implements Initializable {
	
		@FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;
	
	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;
	
	    //@FXML // fx:id="profLoginPane"
	    //private Pane profLoginPane; // Value injected by FXMLLoader
	
	    @FXML // fx:id="profLoginVBox"
	    private VBox profLoginVBox; // Value injected by FXMLLoader
	
	    @FXML // fx:id="profLoginTitlePane"
	    private Pane profLoginTitlePane; // Value injected by FXMLLoader
	
	    @FXML // fx:id="profLoginTitleLabel"
	    private Label profLoginTitleLabel; // Value injected by FXMLLoader
	
	    @FXML // fx:id="profLoginFieldPane"
	    private Pane profLoginFieldPane; // Value injected by FXMLLoader
	
	    @FXML // fx:id="usernameFieldLabel"
	    private Label usernameFieldLabel; // Value injected by FXMLLoader
	
	    @FXML // fx:id="usernameField"
	    private TextField usernameField; // Value injected by FXMLLoader
	
	    @FXML // fx:id="passwordFieldLabel"
	    private Label passwordFieldLabel; // Value injected by FXMLLoader
	
	    @FXML // fx:id="passwordField"
	    private PasswordField passwordField; // Value injected by FXMLLoader
	
	    @FXML // fx:id="profLoginButton"
	    private Button profLoginButton; // Value injected by FXMLLoader

		@FXML // fx:id="switchModeButton"
		private Button switchModeButton;

		@FXML // fx:id="mode"
		private Label mode;
		
	    private DBAccess database;
	    
	    private String UIType;

	    /**
	     * UI constructor method
	     * 
	     * @param typeOfUI
	     */
	    public ProfessorLoginUI(String typeOfUI) {
	    	//observer = parent;
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfessorLoginUI.fxml"));
	            loader.setController(this);
	            loader.setRoot(this);
	            loader.load();
	        } catch (IOException e) {
	            // handle exception
	        	e.printStackTrace();
		        System.err.println(e.getClass().getName() + ": " + e.getMessage());
		        System.exit(0);
	        }
	        
	        UIType = typeOfUI;
	        try {
				database = new DBAccess();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			
	        initLogin();
	    }
	    
	    /**
	     * method to initialize the buttons
	     */
	    public void initLogin() {
	    	profLoginButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Login");
					
					String username = usernameField.getText();
					String password = passwordField.getText();
					
					if (username.length() > 0 && password.length() > 0) {
					   ResultSet results;
					   User curUser = null;
					   int numResults = 0;
					   try {
						   results = database.select("Users", "userID, type, orgUsername, password", 
								   				"password = '" + password + "' AND orgUsername = '" + username + "' AND type = '" + UIType + "'");
						   //results = database.select("Users", "*", "");
						   
						   while (results.next()) {
							   
							   if(numResults == 0) {
								   curUser = new User();
								   curUser.setId(results.getLong("userID"));
								   
							   }
							   numResults++;
							   
						   }
						   database.closeConnection();
						   System.out.println("Number of users: " + numResults);
					   } catch (Exception ex) {
						   // TODO Auto-generated catch block
						   ex.printStackTrace();
					   }
					   
					   if (curUser == null) {
						   System.out.println("Invalid Login credentials");
						   showError("- Must enter a valid username and password.\n");
					   }
					   else if(numResults > 1) {
						   System.out.println("Invalid Login credentials");
						   showError("- Must enter a valid username and password.\n");   
					   }
					   else if(numResults == 0) {
						   System.out.println("Invalid Login credentials");
						   showError("- Must enter a valid username and password.\n");
					   }
					   else {
					   
					      if (UIType.equals("Professor")) {
					  
					         Stage testStage = (Stage)(getScene().getWindow());
					         ProfessorMainViewUI mainView = new ProfessorMainViewUI(600, 600);
					         try {
						         mainView.performFinalSetup("loginView", curUser.getId());
					         } catch (Exception e1) {
						         // TODO Auto-generated catch block
						         e1.printStackTrace();
					         }
					         //Scene loginScene = new Scene(loginPane, 600, 600);
					         testStage.setScene(mainView);
					      }
					      else if (UIType.equals("Student")) {
						      Stage testStage = (Stage)(getScene().getWindow());
						      StudentMainView mainView = new StudentMainView(curUser.getId());
						      Scene testScene = new Scene(mainView, 600, 600);
						      /*try {
							      mainView.performFinalSetup("loginView", 0);
						      } catch (Exception e1) {
							      // TODO Auto-generated catch block
							      e1.printStackTrace();
						      }*/
						      //Scene loginScene = new Scene(loginPane, 600, 600);
						      testStage.setScene(testScene);
						
					      }
					   }
					}
					else {
						/*Can't log user in*/
						System.out.println("Invalid Login credentials");
						showError("- Must enter a username and password.\n");
						if (UIType.equals("Professor")) {
							
						}
						else if (UIType.equals("Student")) {
							
						}
						
					}
				}
			});

			mode.setText(UIType);

			if (UIType.equals("Professor")) {
				switchModeButton.setText("Switch to Student Login");
			}
			else if (UIType.equals("Student")) {
				switchModeButton.setText("Switch to Professor Login");
			}

			switchModeButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Stage testStage = (Stage)(getScene().getWindow());
					ProfessorLoginUI loginPane = null;
					if (UIType.equals("Professor")) {
						loginPane = new ProfessorLoginUI("Student");
					}
					else if (UIType.equals("Student")) {
						loginPane = new ProfessorLoginUI("Professor");
					}
					Scene loginScene = new Scene(loginPane, 600, 600);
					testStage.setScene(loginScene);
				}
			});
	    }

	    /**
	     * checks initialization
	     */
	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    public void initialize() {
	        //assert profLoginPane != null : "fx:id=\"profLoginPane\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert profLoginVBox != null : "fx:id=\"profLoginVBox\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert profLoginTitlePane != null : "fx:id=\"profLoginTitlePane\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert profLoginTitleLabel != null : "fx:id=\"profLoginTitleLabel\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert profLoginFieldPane != null : "fx:id=\"profLoginFieldPane\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert usernameFieldLabel != null : "fx:id=\"usernameFieldLabel\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert passwordFieldLabel != null : "fx:id=\"passwordFieldLabel\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert profLoginButton != null : "fx:id=\"profLoginButton\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";

	        //initialize logic here
	    }
		
	    /**
	     * checks initialization
	     * 
	     * @param location, resources
	     */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
	        //assert profLoginPane != null : "fx:id=\"profLoginPane\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert profLoginVBox != null : "fx:id=\"profLoginVBox\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert profLoginTitlePane != null : "fx:id=\"profLoginTitlePane\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert profLoginTitleLabel != null : "fx:id=\"profLoginTitleLabel\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert profLoginFieldPane != null : "fx:id=\"profLoginFieldPane\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert usernameFieldLabel != null : "fx:id=\"usernameFieldLabel\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert passwordFieldLabel != null : "fx:id=\"passwordFieldLabel\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
	        assert profLoginButton != null : "fx:id=\"profLoginButton\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";

		}
		
		/**
		 * displays error messages
		 * 
		 * @param msg
		 */
		private void showError(String msg) {
			
			
		   Alert alert = new Alert(AlertType.ERROR);
		   alert.setTitle("Login errors");
		   alert.setHeaderText("You can't log in due to the following errors.");
		   alert.setContentText(msg);
		   ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
		   alert.getButtonTypes().setAll(buttonTypeOk);
		   Optional<ButtonType> response = alert.showAndWait();
		   if (response.get() == buttonTypeOk){
		      alert.close();
		   }
	   }
		
	
	//static private Pane loginPane = new Pane();
	
	/*public ProfessorLoginUI(double width, double height) {
		super(loginPane, width, height);
		
		Pane p = new ProfessorLoginPane(parent);
		
		loginPane.getChildren().add(p);
	}*/

	
}