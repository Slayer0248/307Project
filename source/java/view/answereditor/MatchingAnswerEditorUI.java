package view.answereditor;

import java.io.IOException;

/**
 * Sample Skeleton for 'MatchingAnswerEditor.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.accounts.User;
import model.answer.Answer;
import model.answer.MatchingAnswer;
import model.answer.MultipleChoiceAnswer;
import model.question.Question;
import view.navigation.ProfessorMainViewUI;
import view.questioneditor.QuestionEditorUI;

/**
 * User interface for the user to edit matching answers.
 */
public class MatchingAnswerEditorUI extends Pane implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="matchingAP"
    private AnchorPane matchingAP; // Value injected by FXMLLoader

    @FXML // fx:id="matchingPane"
    private Pane matchingPane; // Value injected by FXMLLoader

    @FXML // fx:id="matchingTable"
    private TableView<?> matchingTable; // Value injected by FXMLLoader

    @FXML // fx:id="matchingRow1"
    private TableColumn<?, ?> matchingRow1; // Value injected by FXMLLoader

    @FXML // fx:id="row1Text"
    private TextField row1Text; // Value injected by FXMLLoader

    @FXML // fx:id="row2Text"
    private TextField row2Text; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="saveChangesButton"
    private Button saveChangesButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteRowButton"
    private Button deleteRowButton; // Value injected by FXMLLoader

    @FXML // fx:id="returntoQBButton"
    private Button returntoQBButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="returntoQuestionEditorButton"
    private ImageView returntoQuestionEditorButton; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert matchingAP != null : "fx:id=\"matchingAP\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert matchingPane != null : "fx:id=\"matchingPane\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert matchingTable != null : "fx:id=\"matchingTable\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert matchingRow1 != null : "fx:id=\"matchingRow1\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert row1Text != null : "fx:id=\"row1Text\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert row2Text != null : "fx:id=\"row2Text\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert saveChangesButton != null : "fx:id=\"saveChangesButton\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert deleteRowButton != null : "fx:id=\"deleteRowButton\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert returntoQBButton != null : "fx:id=\"returntoQBButton\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";
        assert returntoQuestionEditorButton != null : "fx:id=\"returntoQuestionEditorButton\" was not injected: check your FXML file 'MatchingAnswerEditor.fxml'.";

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * The user that is currently logged in.
	 */
	private User curUser;
	/**
	 * The question that is being answered.
	 */
	private Question question;
	/**
	 * The current answer to the questoin.
	 */
	private MatchingAnswer answer ;
	
	/**
	 * Initialize a MathcingAnswerEditorUI
	 * 
	 * @param user current user
	 * @param answer the answer being edited
	 * @param temp the questoin being answered
	 */
	public MatchingAnswerEditorUI(User user, Answer answer, Question temp) {
		this.question = temp;
		this.curUser = user;
		this.answer = (MatchingAnswer) answer;
		
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("MatchingAnswerEditor.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			// handle exception
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		initProfile();
	
	}
	
	/**
	 * Set up the user interface and register callbacks.
	 */
	private void initProfile(){
		
		returntoQuestionEditorButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				Stage testStage = (Stage) (getScene().getWindow());
				QuestionEditorUI questioneditor = new QuestionEditorUI(curUser,question);

				Scene createTestScene = new Scene(questioneditor, 600, 600);
				testStage.setScene(createTestScene);

				
			}

		});
		
		
		cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// System.out.println("Edit Question.");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("No changes saved, returning to Question Bank");

				alert.showAndWait();
				Stage testStage = (Stage) (getScene().getWindow());
				ProfessorMainViewUI mainView = new ProfessorMainViewUI(600, 600);
				try {
					mainView.performFinalSetup("questionEditor", curUser.getId());// curUser.getId());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				testStage.setScene(mainView);
			}
		});
		
		
	}
	
}
