package view.administration;

import java.io.IOException;

import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import view.navigation.ProfessorMainViewUI;
import model.accounts.Section;
import model.grading.administration.ProctorManager;
import model.test.Test;
import model.util.DBAccess;
import model.accounts.User;

/**
 * User interface to add a student
 */
public class AddStudentUI extends Pane {
   /*
    * Pane to return to when this view is done.
    */
   ProfessorMainViewUI parent;

   @FXML
   private Pane addNewStudentPane;

   @FXML
   private ComboBox<String> typeSelectComboBox;

   @FXML
   private TextField firstNameText;

   @FXML
   private TextField lastNameText;

   @FXML
   private TextField calPolyIDText;
   
   @FXML
   private Button addButton;
   
   private User curUser;
   private Section section;
   private DBAccess database;

   /**
    * Initialize a new student adding interface
    * 
    * @param parent pane to return to when this is done
    * @param user the user who is currently logged in
    * @param section the section that the student gets added ot.
    */
   public AddStudentUI(ProfessorMainViewUI parent, User user, Section section) {
      this.parent = parent;
      curUser = user;
      this.section = section;
      database = new DBAccess();

      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudentUI.fxml"));
         loader.setController(this);
         loader.setRoot(this);
         loader.load();
      } catch (IOException e) {
         // handle exception
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      }

      populateTestSelectComboBox();
      setupButton();
   }

   /**
    * Set up button handlers.
    */
   private void setupButton() {
	   addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		   public void handle(MouseEvent e) {
			   //if user doesn't exist, add them to db(users)
			   try {
				   Long userID = null;
				   if(database.countAll("Users", "orgUsername = '" + calPolyIDText.getText() + "'") == 0) {
					   
					   if (database.isOpenIdFor("Users", "userId")) {
						   userID = database.getOpenIdFor("Users", "userId");
					   }
					   String type = typeSelectComboBox.getValue();
					   int isProctor = 0;
					   String password = "notAdmin";
					   if(type != "Student"){
						   type = "Professor";
						   isProctor = 1;
						   password = "rootAdmin";
					   }
					   String ppFile = null;
					   database.insert("Users(userID, type, isProctor, firstName, lastName, orgUsername, password, profilePicFile)", 
							   "VALUES(" + userID + ", " + type + ", " + isProctor + ", " + firstNameText.getText() + ", " + lastNameText.getText() + ", " + calPolyIDText.getText() + ", " + password + ", " + ppFile + ")");
				   }
				   else{
					   ResultSet results = database.select("Users", "userID", 
							   "orgUsername = '" + calPolyIDText.getText() + "' AND type = 'Student'");
					   while (results.next()) {
						   userID = results.getLong("userID");
					   }
					   database.closeConnection();
				   }
				   //then enroll them in db(userSections)
				   database.insert("UsersSections(sectionId, userId, isEnrolled)", 
						   "VALUES(" + section.getId() + ", " + userID + ", 1)");
				   System.out.println(typeSelectComboBox.getValue() + " Added.");
				   parent.setActivePane(new ClassAdministrationUI(parent, curUser));
			   }
			   catch (Exception e1) {
					  // TODO Auto-generated catch block
					  e1.printStackTrace();
			   }
		   }
	   });
   }

   /**
    * Populate the test select combo box with the section options.
    */
   private void populateTestSelectComboBox() {
      this.typeSelectComboBox.getItems().setAll(new String("Student"), new String("Proctor"));
   }
}
