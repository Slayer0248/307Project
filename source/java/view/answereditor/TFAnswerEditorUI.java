package view.answereditor;

import java.io.IOException;

/**
 * Sample Skeleton for 'TFAnswerEditorUI.fxml' Controller Class
 */

import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.accounts.User;
import model.answer.Answer;
import model.answer.TrueFalseAnswer;
import model.question.Question;
import view.navigation.ProfessorMainViewUI;
import view.questioneditor.QuestionEditorUI;

/**
 * User interface to edit a true/false answer.
 */
public class TFAnswerEditorUI extends Pane implements Initializable {
   /**
    * The answer being edited.
    */
	private TrueFalseAnswer tempAnswer;
	/**
	 * The question being answered.
	 */
	private Question question;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="tfAnswerPane"
	private Pane tfAnswerPane; // Value injected by FXMLLoader

	@FXML // fx:id="questionText"
	private Text questionText; // Value injected by FXMLLoader

	@FXML // fx:id="trueCbeckButton"
	private CheckBox trueCbeckButton; // Value injected by FXMLLoader

	@FXML // fx:id="falseCheckButton"
	private CheckBox falseCheckButton; // Value injected by FXMLLoader

	@FXML // fx:id="saveButton"
	private Button saveButton; // Value injected by FXMLLoader

	@FXML // fx:id="returntoQBButton"
	private Button returntoQBButton; // Value injected by FXMLLoader

	@FXML // fx:id="cancelButton"
	private Button cancelButton; // Value injected by FXMLLoader

	@FXML // fx:id="backToQuestion"
	private ImageView backToQuestion; // Value injected by FXMLLoader

	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {
		assert tfAnswerPane != null : "fx:id=\"tfAnswerPane\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert questionText != null : "fx:id=\"questionText\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert trueCbeckButton != null : "fx:id=\"trueCbeckButton\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert falseCheckButton != null : "fx:id=\"falseCheckButton\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert returntoQBButton != null : "fx:id=\"returntoQBButton\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert backToQuestion != null : "fx:id=\"backToQuestion\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		assert questionText != null : "fx:id=\"questionText\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert trueCbeckButton != null : "fx:id=\"trueCbeckButton\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert falseCheckButton != null : "fx:id=\"falseCheckButton\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert returntoQBButton != null : "fx:id=\"returntoQBButton\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";
		assert backToQuestion != null : "fx:id=\"backToQuestion\" was not injected: check your FXML file 'TFAnswerEditorUI.fxml'.";

	}

	/**
	 * The currently logged in user.
	 */
	private User curUser;

	/**
	 * Initialize an answer editor for true/false questions.
	 * @param user the user logged in
	 */
	public TFAnswerEditorUI(User user) {
		tempAnswer = new TrueFalseAnswer(user.getId());
		this.curUser = user;

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("TFAnswerEditorUI.fxml"));
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
	 * Initialize an answer editor for a true false question.
	 * 
	 * @param user the user logged in.
	 * @param answer the answer being edited.
	 * @param temp the question being answered.
	 */
	public TFAnswerEditorUI(User user,Answer answer, Question temp) {
		tempAnswer = (TrueFalseAnswer) temp.getCorrectAnswer();
		this.curUser = user;
		this.question = temp;
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("TFAnswerEditorUI.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			// handle exception
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		if(tempAnswer.getAnswer()){
			trueCbeckButton.setSelected(true);
			
		}
		else{
			falseCheckButton.setSelected(false);
		}
		initProfile();

	}

	/**
	 * Register callbacks.
	 */
	private void initProfile() {
		trueCbeckButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (trueCbeckButton.isSelected()) {
					System.out.println("Selected");
					if (falseCheckButton.isSelected()) {
						falseCheckButton.setSelected(false);
					}
					tempAnswer.setAnswer(true);
				}

			}
		});

		falseCheckButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (falseCheckButton.isSelected()) {
					System.out.println("Selected");
					if (trueCbeckButton.isSelected()) {
						trueCbeckButton.setSelected(false);
					}
					tempAnswer.setAnswer(false);
				}

			}
		});

		saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				// System.out.println("Edit Question.");



					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("Changes saved in database!");
					// database.insert("Question );
					alert.showAndWait();
				

			}
		});
		
		backToQuestion.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				Stage testStage = (Stage) (getScene().getWindow());
				QuestionEditorUI questioneditor = new QuestionEditorUI(curUser,question);

				Scene createTestScene = new Scene(questioneditor, 600, 600);
				testStage.setScene(createTestScene);

				
			}

		});
		
		returntoQBButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// System.out.println("Edit Question.");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Ensure changes are saved, click OK when ready");
				ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
				ButtonType buttonTypeOne = new ButtonType("Return to Main Menu");
				alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeOne) {
					alert.close();
					Stage testStage = (Stage) (getScene().getWindow());
					ProfessorMainViewUI mainView = new ProfessorMainViewUI(600, 600);
					try {
						mainView.performFinalSetup("questionEditor", curUser.getId());// curUser.getId());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					testStage.setScene(mainView);
				} else {
					alert.close();

				}
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
