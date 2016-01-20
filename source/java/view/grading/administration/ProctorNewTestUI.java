package view.grading.administration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import view.navigation.ProfessorMainViewUI;
import model.accounts.Section;
import model.accounts.User;
import model.grading.administration.ProctorManager;
import model.test.Test;
import model.test.TestBank;

/**
 * Interface to proctor a new test. Users input the conditions of the new
 * test giving and the interface will call into the model.test.TestSession
 * model to create the test session.
 * 
 * @author Andrew Nelson
 */
public class ProctorNewTestUI extends Pane {
   private static final class ExceptionalInputException extends Exception {
      public ExceptionalInputException(String message) {
         super(message);
      }
   }

   /**
    * The pane to return to when this view is over.
    */
   ProfessorMainViewUI parent;
   /**
    * The proctor manager model to help us manage sessions.
    */
   ProctorManager proctorManager;
   /**
    * The test bank model containing all the user's tests.
    */
   TestBank testBank;
   /**
    * The user that is currently logged in.
    */
   User user;

   @FXML
   private Pane proctorNewTestPane;

   @FXML
   private ComboBox<Test> testSelectComboBox;

   @FXML
   private ComboBox<Section> sectionSelectComboBox;

   @FXML
   private DatePicker dateDatePicker;

   @FXML
   private DatePicker endDatePicker;

   @FXML
   private TextField startTimeText;

   @FXML
   private TextField endTimeText;

   @FXML
   private Button proctorButton;

   /**
    * Initialize a new ProctorNewTestUI and display it on the screen.
    * Get ready to take user input and use it to start a new session. 
    * 
    * @param parent the pane to return to when done
    * @param pm the model that will help us manage test sessions
    * @param tb the test bank of all the user's tests
    * @param user the user currently logged in
    */
   public ProctorNewTestUI(ProfessorMainViewUI parent, ProctorManager pm, TestBank tb, User user) {
      this.parent = parent;
      this.proctorManager = pm;
      this.testBank = tb;
      this.user = user;

      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("ProctorNewTestUI.fxml"));
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
      populateSectionSelectComboBox();
      registerFinishButton();
   }

   /**
    * Register the callback handler that will be called when the finish button
    * is pressed.
    * 
    * We grab all the information that the user put in (showing appropriate
    * error dialogs for bad input), and create a TestSession object for it.
    */
   private void registerFinishButton() {
      proctorButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
         public void handle(MouseEvent event) {
            try {
               Test target = throwIfNull(testSelectComboBox.getValue(), "Test dropdown");
               Section section = throwIfNull(sectionSelectComboBox.getValue(), "Section dropdown");
               Date startDate = parseDateTime(dateDatePicker, startTimeText);
               Date endDate = parseDateTime(endDatePicker, endTimeText);

               proctorManager.proctorTest(target, section, startDate, endDate);

               parent.setActivePane(new ProctorTestMainUI(parent, user));
            } catch (ExceptionalInputException exception) {
               System.out.println("You didn't set one of the date fields");
               Alert popupMsg = new Alert(AlertType.ERROR, "Check your input:\n " + exception.getMessage(),
                     ButtonType.OK);
               popupMsg.showAndWait();
            }
         }
      });
   }

   /**
    * Populate the test selection dropdown menu with a list of every test the
    * user has access to.
    */
   private void populateTestSelectComboBox() {
      this.testSelectComboBox.getItems().setAll();

      for (Test test : this.testBank.getTests()) {
         this.testSelectComboBox.getItems().add(test);
      }
   }

   /**
    * Populate the section selection dropdown menu with a list of every sectoin
    * the user has access to.
    */
   private void populateSectionSelectComboBox() {
      this.sectionSelectComboBox.getItems().setAll();

      for (Section section : Section.getSectionsOwnedByUser(this.user.getId())) {
         this.sectionSelectComboBox.getItems().add(section);
      }
   }

   /**
    * Takes a DatePicker and a TextField and returns a Date object with the date
    * from the DatePicker and the time parsed from the TextField.
    * 
    * @param dayField
    *           javaFX element representing the DatePicker
    * @param timeField
    *           javaFX element representing the TextField
    * @return date combination between dayField and timeField
    * @throws ParseException
    *            when the text in timeField is not a valid date
    * @throws DateNotSetException
    *            when the user didn't pick a valid day in DatePicker
    */
   @SuppressWarnings("deprecation")
   private static Date parseDateTime(DatePicker dayField, TextField timeField) throws ExceptionalInputException {
      throwIfNull(dayField.getValue(), "Date");

      Date ret = Date.from(dayField.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

      Date time;
      try {
         time = new SimpleDateFormat("hh:mm").parse(timeField.getText());
      } catch (ParseException e) {
         throw new ExceptionalInputException("Time not in a valid format.  Please use hh:mm.");
      }

      ret.setHours(time.getHours());
      ret.setMinutes(time.getMinutes());

      return ret;
   }

   /**
    * Check that the given value is not null. If it is, throw an exception
    * telling them that they need to set the field |name|
    * 
    * @param obj
    *           value to check if null
    * @param name
    *           name of field to say to set
    * @throws ExceptionalInputException
    *            containing message saying to set |name|
    */
   private static <T> T throwIfNull(T obj, String name) throws ExceptionalInputException {
      if (obj == null)
         throw new ExceptionalInputException("Must set following field:\n" + name);
      else
         return obj;
   }
}
