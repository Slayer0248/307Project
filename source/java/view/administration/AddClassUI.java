package view.administration;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
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
 * User interface to create a section
 * 
 */
public class AddClassUI extends Pane {
   /**
    * Parent pane to return to when this view is done
    */
   ProfessorMainViewUI parent;

   @FXML
   private Pane addNewClassPane;

   @FXML
   private TextField classNameText;
   
   @FXML
   private Button addButton;
   
   private Section createdSection;
   
   private User curUser;
   private DBAccess database;

   /**
    * Initialize a new class adding ui 
    * 
    * @param parent the pane to return back to
    * @param user the user who is lgged in now
    */
   public AddClassUI(ProfessorMainViewUI parent, User user) {
      try {
         database = new DBAccess();
      } catch (Exception e1) {
			// TODO Auto-generated catch block
         e1.printStackTrace();
      }
	   
      this.parent = parent;
      curUser = user;
      createdSection = new Section();
      
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("AddClassUI.fxml"));
         loader.setController(this);
         loader.setRoot(this);
         loader.load();
      } catch (IOException e) {
         // handle exception
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      }
      
      setupButton();
   }

   /**
    * Set up all the button handlers.
    */
   private void setupButton() {
	   addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		   public void handle(MouseEvent e) {
			   String className = classNameText.getText();
			   if (className.length() <= 0) {
				   System.out.println("Invalid Class name");
				   showError("- Must enter a Class name.\n");
			   }
			   else {
				   createdSection.setName(className);
				   System.out.println("Trying to add :" + className);
				   System.out.println("id = " + curUser.getId());
				   createdSection.setOwnerId(curUser.getId());
				   //db
				   if (database.isOpenIdFor("Sections", "sectionId")) {
					   createdSection.setId(database.getOpenIdFor("Sections", "sectionId"));
				   }
				   System.out.println("new id = " + database.getOpenIdFor("Sections", "sectionId")+ "\n");
				   //createdSection.setProctor(0, curUser);
				   createdSection.addToDatabase();
				   System.out.println("Class Added.");
				   parent.setActivePane(new ClassAdministrationUI(parent, curUser));
			   }
		   }
	   });
   }
   
   /**
    * Show an error to the user.
    * 
    * @param msg the error message.
    */
   private void showError(String msg) {
		
		
	   Alert alert = new Alert(AlertType.ERROR);
	   alert.setTitle("Class creation errors");
	   alert.setHeaderText("Your Class can't created due to the following errors.");
	   alert.setContentText(msg);
	   ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
	   alert.getButtonTypes().setAll(buttonTypeOk);
	   Optional<ButtonType> response = alert.showAndWait();
	   if (response.get() == buttonTypeOk){
	      alert.close();
	   }
   }
}
