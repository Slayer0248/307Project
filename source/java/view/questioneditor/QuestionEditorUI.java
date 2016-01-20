package view.questioneditor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import model.accounts.User;
import model.question.Question;
import model.util.DBAccess;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.answereditor.AnswerEditorUI;
import view.answereditor.MCAnswerEditorUI;
import view.answereditor.MatchingAnswerEditorUI;
import view.answereditor.TFAnswerEditorUI;
import view.navigation.ProfessorMainViewUI;
import view.question.QuestionUI;
import javafx.scene.Scene;

/**
 * The question editor ui.
 * @author Jeet
 */
public class QuestionEditorUI extends Pane implements Initializable {
	/**
	 * The current user.
	 */
	private User curUser;
	/**
	 * The database.
	 */
	private DBAccess database;
	/**
	 * The type selected.
	 */
	private boolean typeselected = false;
	/**
	 * The resources.
	 */
	@FXML
	private ResourceBundle resources;

	/**
	 * The location.
	 */
	@FXML
	private URL location;

	/**
	 * The question answer pane.
	 */
	@FXML
	private Pane questionToAnswerPane;

	/**
	 * The question pane.
	 */
	@FXML
	private Pane questionPane;

	/**
	 * The question name.
	 */
	@FXML
	private TextField questionName;

	/**
	 * The question string.
	 */
	@FXML
	private TextField questionString;

	/**
	 * The question category.
	 */
	@FXML
	private TextField questionCategory;

	/**
	 * The question type.
	 */
	@FXML
	private ChoiceBox<String> questionType;

	/**
	 * The question timed.
	 */
	@FXML
	private CheckBox questionTimedQuestion;

	/**
	 * The save changes button.
	 */
	@FXML
	private Button questionSaveChanges;

	/**
	 * The cancel.
	 */
	@FXML
	private Button questionCancel;

	/**
	 * The time per question.
	 */
	@FXML
	private ComboBox<String> timePerQuestion;

	/**
	 * The return to menu button.
	 */
	@FXML
	private Button questionReturntoMenu;

	/**
	 * The image view.
	 */
	@FXML
	private ImageView questionToAnswer;

	/**
	 * The points.
	 */
	@FXML
	private TextField points;

	/**
	 * The difficulty.
	 */
	@FXML
	private ComboBox<String> difficulty;

	@FXML
	void initialize() {
		assert questionPane != null : "fx:id=\"questionPane\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";
		assert questionName != null : "fx:id=\"questionName\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";
		assert questionString != null : "fx:id=\"questionString\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";
		assert questionCategory != null : "fx:id=\"questionCategory\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";
		assert questionType != null : "fx:id=\"questionType\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";
		assert questionTimedQuestion != null : "fx:id=\"questionTimedQuestion\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";
		assert questionSaveChanges != null : "fx:id=\"questionSaveChanges\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";
		assert questionCancel != null : "fx:id=\"questionCancel\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";
		assert timePerQuestion != null : "fx:id=\"quesitonTimeperQuestion\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";
		assert questionReturntoMenu != null : "fx:id=\"questionReturntoMenu\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";
		assert questionToAnswer != null : "fx:id=\"questionToAnswer\" was not injected: check your FXML file 'QuestionEditorUI.fxml'.";

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Creates a question editor ui.
	 * @param cur
	 */
	public QuestionEditorUI(User cur) {
		this.curUser = cur;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionEditorUI.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			// handle exception
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		ObservableList<String> comboBoxData = FXCollections.observableArrayList();
		comboBoxData.addAll("True or False", "Multiple Choice", "Free Response Question", "Code Question",
				"Matching Question");
		questionType.setItems(comboBoxData);
		ObservableList<String> comboBoxData2 = FXCollections.observableArrayList();
		comboBoxData2.addAll("30 seconds", "1 minute", "2 minutes", "3 minutes", "5 minutes", "10 minutes", "Other");
		timePerQuestion.setItems(comboBoxData2);
		ObservableList<String> comboBoxData3 = FXCollections.observableArrayList();
		comboBoxData3.addAll("1-Easy", "2", "3", "4", "5-Hard");
		difficulty.setItems(comboBoxData3);

		setupView();

	}

	/**
	 * Creates a question editor ui.
	 * @param cur
	 * @param question
	 */
	public QuestionEditorUI(User cur, Question question) {
		this.curUser = cur;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionEditorUI.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			// handle exception
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		ObservableList<String> comboBoxData = FXCollections.observableArrayList();
		comboBoxData.addAll("True or False", "Multiple Choice", "Free Response Question", "Code Question",
				"Matching Question");
		questionType.setItems(comboBoxData);
		ObservableList<String> comboBoxData2 = FXCollections.observableArrayList();
		comboBoxData2.addAll("30 seconds", "1 minute", "2 minutes", "3 minutes", "5 minutes", "10 minutes", "Other");
		timePerQuestion.setItems(comboBoxData2);
		ObservableList<String> comboBoxData3 = FXCollections.observableArrayList();
		comboBoxData3.addAll("1-Easy", "2", "3", "4", "5-Hard");
		difficulty.setItems(comboBoxData3);
		questionName.setText(question.getQuestionName());
		questionString.setText(question.getQuestion());
		Set<String> set = question.getCategories();
		String category = "";
		for (String s : set) {
			category.concat(s).concat(",");
		}
		questionCategory.setText(category);
		questionType.setValue(question.getQuestionType());
		questionTimedQuestion.setSelected(question.getTimedQuestion());
		timePerQuestion.setValue(Integer.toString(question.getTime()));
		points.setText(Double.toString(question.getQuestionPoints()));
		difficulty.setValue(Double.toString(question.getQuestionDifficulty()));

		init(question);

	}

	/**
	 * Inits.
	 * @param question
	 */
	protected void init(Question question) {
		final Question temp = question;
		temp.setTimedQuestion(false);
		temp.setTime(0);

		questionToAnswerPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				System.out.println("Selected");
				if (questionType.getValue().contains("True")) {
					Stage testStage = (Stage) (getScene().getWindow());
					TFAnswerEditorUI mainView = new TFAnswerEditorUI(curUser,temp.getCorrectAnswer(),temp);
					Scene createTestScene = new Scene(mainView, 600, 600);

					testStage.setScene(createTestScene);
				}
				if (questionType.getValue().contains("Matching")) {
					Stage testStage = (Stage) (getScene().getWindow());
					MatchingAnswerEditorUI mainView = new MatchingAnswerEditorUI(curUser,temp.getCorrectAnswer(),temp);
					Scene createTestScene = new Scene(mainView, 600, 600);

					testStage.setScene(createTestScene);
				}
				if (questionType.getValue().contains("Free")) {
					Stage testStage = (Stage) (getScene().getWindow());
					AnswerEditorUI mainView = new AnswerEditorUI(curUser,temp.getCorrectAnswer(),temp);
					Scene createTestScene = new Scene(mainView, 600, 600);

					testStage.setScene(createTestScene);
				}
				if (questionType.getValue().contains("Multiple")) {
					Stage testStage = (Stage) (getScene().getWindow());
					MCAnswerEditorUI mainView = new MCAnswerEditorUI(curUser,temp.getCorrectAnswer(),temp);
					Scene createTestScene = new Scene(mainView, 600, 600);

					testStage.setScene(createTestScene);
				} 
			}

		});


		questionTimedQuestion.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (questionTimedQuestion.isSelected()) {
					System.out.println("Selected");
					temp.setTimedQuestion(true);
				}

			}
		});

		difficulty.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				temp.setDifficulty(Double.parseDouble(newValue.substring(0, 1)));
			}
		});

		points.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				temp.setPoints(Double.parseDouble(points.getText()));
				System.out.println("TextField Text Changed (newValue: " + newValue + ")\n");
			}
		});

		timePerQuestion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("Value is::" + newValue);
				if (temp.getTimedQuestion()) {
					int value = 0;
					if (newValue.equals("Other")) {
						System.out.println("New small pane pops up");
						final Stage newStage = new Stage();
						newStage.setTitle("Enter minutes and seconds and hit save");
						VBox comp = new VBox();
						final TextField nameField = new TextField("Minutes");
						final TextField phoneNumber = new TextField("Seconds");
						Button show = new Button("Save");
						comp.getChildren().add(nameField);
						comp.getChildren().add(phoneNumber);
						comp.getChildren().add(show);

						Scene stageScene = new Scene(comp, 300, 300);
						newStage.setScene(stageScene);
						newStage.show();

						show.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								int value = Integer.parseInt(nameField.getText());
								value = value * 60;
								value = value + Integer.parseInt(phoneNumber.getText());
								temp.setTime(value);
								newStage.close();
							}
						});

					} else if (newValue.equals("30 seconds")) {
						temp.setTime(30);
					} else if (newValue.equals("1 minute")) {
						temp.setTime(60);
					} else if (newValue.equals("2 minutes")) {
						temp.setTime(120);
					} else if (newValue.equals("3 minutes")) {
						temp.setTime(180);
					} else if (newValue.equals("5 minutes")) {
						temp.setTime(300);
					} else if (newValue.equals("10 minutes")) {
						temp.setTime(600);
					}
				}

			}
		});

		questionName.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				System.out.println(newValue);
				temp.setQuestionName(newValue);
			}
		});

		questionString.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				System.out.println(newValue);
				temp.setQuestion(newValue);
			}
		});

		questionSaveChanges.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				boolean thrown = false;
				try {
					Double.parseDouble(points.getText());

				} catch (NumberFormatException e1) {
					throwAlert();
					thrown = true;

				}
				// System.out.println("Edit Question.");
				if (!thrown) {
					temp.setQuestionName(questionName.getText());
					temp.setQuestion(questionString.getText());
					temp.setOwnerId(curUser.getId());
					String[] myarray = questionCategory.getText().split(",");
					Set<String> mySet = new HashSet<String>();
					Collections.addAll(mySet, myarray);
					temp.addCategories(mySet);
					temp.updateInDatabase();
					temp.setPoints(Double.parseDouble(points.getText()));
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("Changes saved in database!");
					// database.insert("Question );
					alert.showAndWait();
				}

			}
		});

		questionCancel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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

		questionReturntoMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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
	}

	/**
	 * Throws alert.
	 */
	protected void throwAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setContentText("Warning, please add points as digits only then hit save changes");
		// database.insert("Question );
		alert.showAndWait();

	}

	/**
	 * Sets up the view.
	 */
	protected void setupView() {
		final Question temp = new Question();
		temp.setTimedQuestion(false);
		temp.setTime(0);

		questionType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("Value is::" + newValue);
				temp.setQuestionType(newValue);
				typeselected = true;
			}
		});

		questionName.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				System.out.println(newValue);
				temp.setQuestionName(newValue);
			}
		});

		questionTimedQuestion.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (questionTimedQuestion.isSelected()) {
					System.out.println("Selected");
					temp.setTimedQuestion(true);
				}

			}
		});

		difficulty.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				temp.setDifficulty(Double.parseDouble(newValue.substring(0, 1)));
			}
		});

		timePerQuestion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("Value is::" + newValue);
				if (temp.getTimedQuestion()) {
					int value = 0;
					if (newValue.equals("Other")) {
						System.out.println("New small pane pops up");
						Stage newStage = new Stage();
						newStage.setTitle("Enter minutes and seconds and hit save");
						VBox comp = new VBox();
						final TextField nameField = new TextField("Minutes");
						final TextField phoneNumber = new TextField("Seconds");
						Button show = new Button("Save");
						comp.getChildren().add(nameField);
						comp.getChildren().add(phoneNumber);
						comp.getChildren().add(show);

						Scene stageScene = new Scene(comp, 300, 300);
						newStage.setScene(stageScene);
						newStage.show();

						show.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								int value = Integer.parseInt(nameField.getText());
								value = value * 60;
								value = value + Integer.parseInt(phoneNumber.getText());
								temp.setTime(value);
								newStage.close();
							}
						});

					} else if (newValue.equals("30 seconds")) {
						temp.setTime(30);
					} else if (newValue.equals("1 minute")) {
						temp.setTime(60);
					} else if (newValue.equals("2 minutes")) {
						temp.setTime(120);
					} else if (newValue.equals("3 minutes")) {
						temp.setTime(180);
					} else if (newValue.equals("5 minutes")) {
						temp.setTime(300);
					} else if (newValue.equals("10 minutes")) {
						temp.setTime(600);
					}

				}

			}
		});

		questionString.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				System.out.println(newValue);
				temp.setQuestion(newValue);
			}
		});

		points.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				temp.setPoints(Double.parseDouble(points.getText()));
				System.out.println("TextField Text Changed (newValue: " + newValue + ")\n");
			}

		});

		questionSaveChanges.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				boolean thrown = false;
				try {
					Double.parseDouble(points.getText());

				} catch (NumberFormatException e1) {
					throwAlert();
					thrown = true;

				}
				if (typeselected) {
					if (!thrown) {
						// System.out.println("Edit Question.");
						temp.setQuestionName(questionName.getText());
						temp.setQuestion(questionString.getText());
						temp.setOwnerId(curUser.getId());
						String[] myarray = questionCategory.getText().split(",");
						Set<String> mySet = new HashSet<String>();
						Collections.addAll(mySet, myarray);
						temp.addCategories(mySet);
						temp.addToDatabase();
						temp.setPoints(Double.parseDouble(points.getText()));
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText(null);
						alert.setContentText("Added to database!");
						// database.insert("Question );
						alert.showAndWait();
						moveToAnswerEditor(temp);
					}
				}
				else{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("WARNING,please select type!");
					// database.insert("Question );
					alert.showAndWait();
				}
			}
		});

		questionCancel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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

		questionToAnswerPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText(
							"Please first save question to database, you will be redirected to answer editor once question is saved");
					alert.show();

				
			}

		});

		questionReturntoMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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
	}

	/**
	 * Moves to the answer editor.
	 * @param temp
	 */
	protected void moveToAnswerEditor(Question temp) {
		if (questionType.getValue().contains("True")) {
			Stage testStage = (Stage) (getScene().getWindow());
			TFAnswerEditorUI mainView = new TFAnswerEditorUI(curUser,temp.getCorrectAnswer(),temp);
			Scene createTestScene = new Scene(mainView, 600, 600);

			testStage.setScene(createTestScene);
		}
		if (questionType.getValue().contains("Matching")) {
			Stage testStage = (Stage) (getScene().getWindow());
			MatchingAnswerEditorUI mainView = new MatchingAnswerEditorUI(curUser,temp.getCorrectAnswer(),temp);
			Scene createTestScene = new Scene(mainView, 600, 600);

			testStage.setScene(createTestScene);
		}
		if (questionType.getValue().contains("Free")) {
			Stage testStage = (Stage) (getScene().getWindow());
			AnswerEditorUI mainView = new AnswerEditorUI(curUser,temp.getCorrectAnswer(),temp);
			Scene createTestScene = new Scene(mainView, 600, 600);

			testStage.setScene(createTestScene);
		}
		if (questionType.getValue().contains("Multiple")) {
			Stage testStage = (Stage) (getScene().getWindow());
			MCAnswerEditorUI mainView = new MCAnswerEditorUI(curUser,temp.getCorrectAnswer(),temp);
			Scene createTestScene = new Scene(mainView, 600, 600);

			testStage.setScene(createTestScene);
		} 
		
	}

}
