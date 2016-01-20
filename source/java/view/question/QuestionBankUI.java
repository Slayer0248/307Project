package view.question;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.accounts.User;
import model.question.Question;
import model.question.QuestionBank;
import view.navigation.ProfessorMainViewUI;
import view.questioneditor.QuestionEditorUI;

/**
 * 
 * Question bank UI
 * @author Jeet
 *
 */
public class QuestionBankUI extends Pane implements Initializable {
	/**
	 * Parent.
	 */
	private ProfessorMainViewUI parent;
	/**
	 * The question bank.
	 */
	private QuestionBank questionbank;
	/**
	 * The current user.
	 */
	private User curUser;
	/**
	 * The question cells.
	 */
	private ArrayList<QuestionUI> questionCells;

	/**
	 * The scroll pane.
	 */
	@FXML
	private ScrollPane scrollpane;

	/**
	 * The root.
	 */
	@FXML
	private Pane rootPane;

	/**
	 * The resouces.
	 */
	@FXML
	private ResourceBundle resources;

	/**
	 * The question anchor pane.
	 */
	@FXML
	private AnchorPane questionBankAP;

	/**
	 * The location.
	 */
	@FXML
	private URL location;

	/**
	 * The search button.
	 */
	@FXML
	private Button searchButton;

	/**
	 * The search field.
	 */
	@FXML
	private TextField searchText;

	/**
	 * The scroll bar.
	 */
	@FXML
	private ScrollBar questionBankScroll;

	/**
	 * The question pane.
	 */
	@FXML
	private Pane addQuestionPane;

	/**
	 * The image view.
	 */
	@FXML
	private ImageView questionBankAdd;

	@FXML
	void initialize() {
		assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'QuestionBankUI.fxml'.";
		assert searchText != null : "fx:id=\"searchText\" was not injected: check your FXML file 'QuestionBankUI.fxml'.";
		assert addQuestionPane != null : "fx:id=\"addQuestionPane\" was not injected: check your FXML file 'QuestionBankUI.fxml'.";
		assert questionBankAdd != null : "fx:id=\"questionBankAdd\" was not injected: check your FXML file 'QuestionBankUI.fxml'.";

	}
	
	/**
	 * Initializes.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'QuestionBankUI.fxml'.";
		assert searchText != null : "fx:id=\"searchText\" was not injected: check your FXML file 'QuestionBankUI.fxml'.";
		assert addQuestionPane != null : "fx:id=\"addQuestionPane\" was not injected: check your FXML file 'QuestionBankUI.fxml'.";
		assert questionBankAdd != null : "fx:id=\"questionBankAdd\" was not injected: check your FXML file 'QuestionBankUI.fxml'.";

	}

	/**
	 * Creates a question bank ui.
	 * @param parent
	 * @param user
	 */
	public QuestionBankUI(ProfessorMainViewUI parent, User user) {
		this.parent = parent;
		this.curUser = user;
		this.questionCells = new ArrayList<>();

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionBankUI.fxml"));
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
	 * Inits the profile.
	 */
	private void initProfile() {
		layoutQuestionCells();

		searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Search the query.");
			}
		});

		addQuestionPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Stage testStage = (Stage) (parent.getWindow());
				QuestionEditorUI questioneditor = new QuestionEditorUI(curUser);

				Scene createTestScene = new Scene(questioneditor, 600, 600);
				testStage.setScene(createTestScene);

				System.out.println("Add new test.");
			}
		});

	}

	/**
	 * Layouts the question cells.
	 */
	private void layoutQuestionCells() {
		questionbank = new QuestionBank(curUser.getId());
		List<Question> questions = questionbank.getQuestions();

		addQuestionPane.relocate(10.0, 90.0);
		for (int i = 0; i < questions.size(); i++) {
			Question question = questions.get(i);
			QuestionUI testCell = new QuestionUI((ProfessorMainViewUI) getScene(), question);
			//assert (testCell != null);
			questionCells.add(testCell);

			Button deleteButton = testCell.getDeleteButton();
			deleteButton.setId(String.valueOf(i));
			deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createRemoveHandler());

			Button editButton = testCell.getEditButton();
			editButton.setId(String.valueOf(i));
			editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createEditHandler());

			questionBankAP.getChildren().add(testCell);

			double groupOffset = 10;
			double curYCoord = 100;

			for (QuestionUI cell : questionCells) {
				cell.relocate(10.0, curYCoord);
				cell.getquestionNumber().setText("Question ".concat(Integer.toString(i+1)));
				curYCoord += cell.getPrefHeight() + groupOffset;

			}

			addQuestionPane.relocate(10.0, curYCoord);
		}

		questionBankScroll.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				questionBankAP.setLayoutY(-new_val.doubleValue());
			}
		});

		
	}
	
	/**
	 * Gets the scroll bar.
	 * @return
	 */
	public ScrollBar getScrollbar() {
		return questionBankScroll;
	}

	/**
	 * Creates the edit handler.
	 * @return
	 */
	private final EventHandler<MouseEvent> createEditHandler() {

		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Edit button clicked");
				;
				final Button editButton = (Button) event.getSource();

				QuestionUI testCell = questionCells.get(Integer.parseInt(editButton.getId()));

				Stage testStage = (Stage) (parent.getWindow());
				QuestionEditorUI questioneditor = new QuestionEditorUI(curUser,testCell.getQuestion());

				Scene createTestScene = new Scene(questioneditor, 600, 600);
				testStage.setScene(createTestScene);

				System.out.println("Add new test.");

			}
		};
	}


	/**
	 * Creates the remove handler.
	 * @return
	 */
	private final EventHandler<MouseEvent> createRemoveHandler() {

		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Delete button clicked");
				
				final Button deleteButton = (Button) event.getSource();

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setContentText("Are you sure you want to delete this test?");
				int testCellNum = Integer.parseInt(deleteButton.getId());
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					System.out.println("Remove pane");
					QuestionUI testCell = questionCells.get(testCellNum);
					questionbank.removeQuestion(testCell.getQuestion());
					for (QuestionUI cell : questionCells) {
						questionBankAP.getChildren().remove(cell);
					}
					questionCells = new ArrayList<>();
					
					layoutQuestionCells();
					alert.close();
				} else {
					System.out.println("Do not Remove pane");
					alert.close();
					// ... user chose CANCEL or closed the dialog
				}

			}
		};
	}

}
