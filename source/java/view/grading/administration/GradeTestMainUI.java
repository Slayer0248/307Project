package view.grading.administration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;

import model.accounts.User;
import model.grading.administration.GradeManager;
import model.test.TestSession;
import view.navigation.ProfessorMainViewUI;

/**
 * User interface for grading tests.
 * 
 * @author Andrew Nelson
 */
public class GradeTestMainUI extends Pane implements Initializable {
   /**
    * The user that is currently logged in
    */
   private User user;
   /**
    * The Grade Manager object
    */
   private GradeManager gradeManager;

   /**
    * JavaFX pane for the current ui.
    */
   @FXML
   private Pane gradeTestMainPane;

   /**
    * Table that stores the graded tests.
    */
   @FXML
   private TableView<?> gradedTestsTable;

   /**
    * Initialize a GradeTestMainUI and display it on the screen.
    * 
    * @param parent the pane to go back to when we're done
    * @param user the user who is currently logged in
    */
   public GradeTestMainUI(ProfessorMainViewUI parent, User user) {
      this.user = user;
      gradeManager = new GradeManager(user);

      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("GradeTestMainUI.fxml"));
         loader.setController(this);
         loader.setRoot(this);
         loader.load();
      } catch (IOException e) {
         // handle exception
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      }

      initGradeMain();
   }

   /**
    * Initialize all the data fields in the current pane
    */
   private void initGradeMain() {
      for (TestSession session : gradeManager.getGradedTests()) {
         // Add the session to the table of test sessions
      }
   }

   @FXML // This method is called by the FXMLLoader when initialization is
         // complete
   public void initialize() {
      assert gradeTestMainPane != null : "fx:id=\"gradeTestMainPane\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
      assert gradedTestsTable != null : "fx:id=\"gradedTestsTable\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
      // initialize logic here
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      // TODO Auto-generated method stub

      assert gradeTestMainPane != null : "fx:id=\"gradeTestMainPane\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
      assert gradedTestsTable != null : "fx:id=\"gradedTestsTable\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
      // initialize logic here
   }
}
