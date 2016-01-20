package view.grading.administration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.grading.administration.ProctorTableRow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import model.accounts.User;
import model.grading.administration.ProctorManager;
import model.test.TestBank;
import model.test.TestSession;
import view.navigation.ProfessorMainViewUI;

/**
 * The user interface for proctoring tests
 * 
 * @author Andrew Nelson
 *
 */
public class ProctorTestMainUI extends Pane implements Initializable {
   /**
    * The pane to return to when we're done with this view
    */
   private ProfessorMainViewUI parent;
   /**
    * The proctor manager
    */
   private ProctorManager proctorManager;
   /**
    * The TestBank storing the tests belonging to the user.
    */
   private TestBank testBank;
   /**
    * The current user logged in.
    */
   private User user;

   @FXML
   private TableView<ProctorTableRow> activeTestSessionsTable;

   @FXML
   private TableColumn<ProctorTableRow, String> testName;
   @FXML
   private TableColumn<ProctorTableRow, Double> testCompletedness;
   @FXML
   private TableColumn<ProctorTableRow, Object> manageButton;

   private ObservableList<ProctorTableRow> activeTestTableContents;

   @FXML
   private Button proctorNewTestButton;

   /**
    * Main interface for managing test sessions... Shows a list of current
    * sessions and a button option to add another one.
    * 
    * @param parent the window so that we can change the view when necessary
    * @param user the user that is currently logged in
    */
   public ProctorTestMainUI(ProfessorMainViewUI parent, User user) {
      this.parent = parent;

      this.proctorManager = new ProctorManager(user.getId());
      this.testBank = new TestBank(user.getId());
      this.user = user;

      System.out.println("whoa whoa whoa the constructor for ProctorTestMainUI was called");

      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("ProctorTestMainUI.fxml"));
         loader.setController(this);
         loader.setRoot(this);
         loader.load();
      } catch (IOException e) {
         // handle exception
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      }

      initProctorNew();
      populateFields();
   }

   /**
    * Initialize table of test sessions.. that means setting up the session-table
    * adapter and registering button handlers
    */
   private void initProctorNew() {
      testName.setCellValueFactory(new PropertyValueFactory<ProctorTableRow, String>("testName"));
      testCompletedness.setCellValueFactory(new PropertyValueFactory<ProctorTableRow, Double>("testCompletedness"));
      manageButton.setCellValueFactory(new PropertyValueFactory<ProctorTableRow, Object>("manageButton"));
      manageButton
            .setCellFactory(new Callback<TableColumn<ProctorTableRow, Object>, TableCell<ProctorTableRow, Object>>() {

               @Override
               public TableCell<ProctorTableRow, Object> call(TableColumn<ProctorTableRow, Object> p) {
                  return new ButtonCell(activeTestSessionsTable);
               }

            });

      proctorNewTestButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
         public void handle(MouseEvent e) {
            System.out.println("you clicked the proctor test button yay.");
            parent.setActivePane(new ProctorNewTestUI(parent, proctorManager, testBank, user));
         }
      });
   }

   /**
    * Grab all the test sessions from the proctor manager and put them
    * into the table (using ProctorTableRow as an adapter class)
    */
   private void populateFields() {
      activeTestTableContents = FXCollections.observableArrayList();
      System.out.println("populateFields called");

      for (TestSession session : proctorManager.getActiveTests()) {
         activeTestTableContents.addAll(new ProctorTableRow(session));
         System.out.println(session.getName());
      }

      activeTestSessionsTable.setItems(activeTestTableContents);
   }

   @FXML // This method is called by the FXMLLoader when initialization is
         // complete
   public void initialize() {
      assert activeTestSessionsTable != null : "fx:id=\"activeTestSessionsTable\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
      assert testName != null : "testName was not injected!!!";
      assert testCompletedness != null : "testCompletedness was not injected!!!";
      assert manageButton != null : "manageButton was not injected!!!";
      assert proctorNewTestButton != null : "fx:id=\"proctorNewTestButton\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
      // initialize logic here
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      // TODO Auto-generated method stub

      assert activeTestSessionsTable != null : "fx:id=\"activeTestSessionsTable\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
      assert testName != null : "testName was not injected!!!";
      assert testCompletedness != null : "testCompletedness was not injected!!!";
      assert manageButton != null : "manageButton was not injected!!!";
      assert proctorNewTestButton != null : "fx:id=\"proctorNewTestButton\" was not injected: check your FXML file 'ProfessorLoginUI.fxml'.";
      // initialize logic here
   }

   /**
    * Adapter class that allows a Button to be used as a table cell
    */
   private class ButtonCell extends TableCell<ProctorTableRow, Object> {
      final Button cellButton = new Button("Manage");

      ButtonCell(final TableView<ProctorTableRow> table) {

         cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
               int selectedIndex = getTableRow().getIndex();

               ProctorTableRow selectedRow = (ProctorTableRow) table.getItems().get(selectedIndex);

               //parent.setActivePane(new ProctorManageUI(parent, selectedRow.getSession()));
               System.out.println(selectedRow.getTestName());
            }
         });
      }

      // Display button if the row is not empty
      @Override
      protected void updateItem(Object t, boolean empty) {
         super.updateItem(t, empty);
         if (!empty) {
            setGraphic(cellButton);
         }
      }
   }
}
