/**
 * Sample Skeleton for 'MCAnswerEditorUI.fxml' Controller Class
 */
package view.answereditor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.accounts.User;
import model.answer.Answer;
import model.answer.MultipleChoiceAnswer;
import model.question.Question;
import view.navigation.ProfessorMainViewUI;
import view.questioneditor.QuestionEditorUI;

/**
 * Multiple Choice Answer Editor is how the user edits multiple choice answers.
 */
public class MCAnswerEditorUI extends Pane implements Initializable {
   /**
    * The question being answered.
    */
	private Question question;

	/**
	 * The current answer to the question.
	 */
	private MultipleChoiceAnswer answer ;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="addNewStudentPane"
    private Pane addNewStudentPane; // Value injected by FXMLLoader

    @FXML // fx:id="answerText1"
    private TextField answerText1; // Value injected by FXMLLoader

    @FXML // fx:id="answerText2"
    private TextField answerText2; // Value injected by FXMLLoader

    @FXML // fx:id="saveChangesButton"
    private Button saveChangesButton; // Value injected by FXMLLoader

    @FXML // fx:id="answerText3"
    private TextField answerText3; // Value injected by FXMLLoader

    @FXML // fx:id="answerText4"
    private TextField answerText4; // Value injected by FXMLLoader

    @FXML // fx:id="answerButton1"
    private CheckBox answerButton1; // Value injected by FXMLLoader

    @FXML // fx:id="answerButton2"
    private CheckBox answerButton2; // Value injected by FXMLLoader

    @FXML // fx:id="answerButton3"
    private CheckBox answerButton3; // Value injected by FXMLLoader

    @FXML // fx:id="answerButton4"
    private CheckBox answerButton4; // Value injected by FXMLLoader

    @FXML // fx:id="answerButton5"
    private CheckBox answerButton5; // Value injected by FXMLLoader

    @FXML // fx:id="answerText5"
    private TextField answerText5; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="backToQuestionEditorButton"
    private ImageView backToQuestionEditorButton; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert addNewStudentPane != null : "fx:id=\"addNewStudentPane\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert answerText1 != null : "fx:id=\"answerText1\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert answerText2 != null : "fx:id=\"answerText2\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert saveChangesButton != null : "fx:id=\"saveChangesButton\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert answerText3 != null : "fx:id=\"answerText3\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert answerText4 != null : "fx:id=\"answerText4\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert answerButton1 != null : "fx:id=\"answerButton1\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert answerButton2 != null : "fx:id=\"answerButton2\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert answerButton3 != null : "fx:id=\"answerButton3\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert answerButton4 != null : "fx:id=\"answerButton4\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert answerButton5 != null : "fx:id=\"answerButton5\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert answerText5 != null : "fx:id=\"answerText5\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";
        assert backToQuestionEditorButton != null : "fx:id=\"backToQuestionEditorButton\" was not injected: check your FXML file 'MCAnswerEditorUI.fxml'.";

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * The current user logged in.
	 */
	private User curUser;
	
	/**
	 * Set up a muleiple choice answer editor.
	 * 
	 * @param user the current user.
	 * @param answer the answer being edited.
	 * @param temp the question being answered.
	 */
	public MCAnswerEditorUI(User user, Answer answer, Question temp) {
		this.question = temp;
		this.curUser = user;
		this.answer = (MultipleChoiceAnswer) answer;
		
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("MCAnswerEditorUI.fxml"));
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
	 * Register callbacks for UI.
	 */
	private void initProfile(){
		
		
		backToQuestionEditorButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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
