package view.student;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import view.logins.ProfessorLoginUI;
import model.accounts.User;

import java.io.IOException;
import java.lang.Integer;
import java.lang.Override;
import java.lang.System;
import java.sql.ResultSet;
import java.util.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.accounts.User;
import model.grading.administration.GradeManager;
import view.navigation.ProfessorMainViewUI;
import javafx.scene.Scene;

import model.answer.*;
import model.test.*;
import model.question.*;
import model.util.DBAccess;
import model.test.*;

/**
 * The main view for students when they log in.
 *
 * @author Mark McKinney
 */
public class StudentMainView extends Pane implements Initializable {
    @FXML // fx:id="logout"
    private Button logout;

    @FXML // fx:id="pagination"
    private Pagination pagination;

    @FXML  // fx:id="progressBar"
    private ProgressBar progressBar;

    @FXML // fx:id="numCompleted"
    private Label numCompleted;

    @FXML // fx:id="numSkipped"
    private Label numSkipped;

    @FXML // fx:id="timer"
    private Label timer;

    /**
     * The pane on which the current question is shown.
     */
    private TitledPane questionPane;

    /**
     * The id of the current test.
     */
    private Long testId;
    /**
     * The current logged in user.
     */
    private User curUser;
    /**
     * DBAccess for access to the database.
     */
    private DBAccess database;
    /**
     * The current test submission being worked on.
     */
    private TestSubmission testSubmission;
    /**
     * The current test.
     */
    private Test test;
    /**
     * The list of questions for the current test.
     */
    private List<Question> questions;

    /**
     * A dummy true false question.
     */
    private TrueFalseQuestion tfq;
    /**
     * A dummy multiple choice question.
     */
    private MultipleChoiceQuestion mcq;

    /**
     * Instantiate a student main view.
     * @param userId The user id that is logged in.
     */
    public StudentMainView(long userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentMainView.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        curUser = new User();
        curUser.setId(userId);

        initTest();
    }

    /**
     * Prompt the user with what tests are currently available and set up the view for it.
     */
    public void initTest() {
        /*
        so I'll search for current TestSubmissions for a given takerId, then use its testSessionId to get the testId.
        Then I can get all questionIds for a test with the testId. Then I populate the QuestionNodes table with the
        questionId and answerId but add in the current testSessionId? Then all I have to do is update answers by id
        till the student is done?
         */
        // get all TestSubmissions for the curUser from db, ask which to take, get all answers, populate
        ResultSet results = null;
        try {
            database = new DBAccess();
            results = database.select("TestSubmissions", "takerId", "takerId = " + curUser.getId());

            ArrayList<String> list = new ArrayList<>();
            String chosenTest = null;
            HashMap<String, long[]> testMap = new HashMap<>();
            while (results.next()) {
                long testSubmissionId = results.getLong("testSubmissionId");
                long testSessionId = results.getLong("testSessionId");

                ResultSet resultSet = database.select("TestSessions", "testSessionId", "testSessionId = " + testSessionId);
                if (resultSet.next()) {
                    long testId = resultSet.getLong("testId");

                    ResultSet testResultSet = database.select("Tests", "testId", "testId = " + testId);
                    if (testResultSet.next()) {
                        String name = testResultSet.getString("name");
                        String description = testResultSet.getString("description");

                        String testString = testSubmissionId + " " + testSessionId + " " + testId + " " +
                                name + ": " + description;
                        list.add(testString);
                        chosenTest = testString;
                        long ids[] = {testId, testSubmissionId};
                        testMap.put(testString, ids);
                    }
                }
            }

            if (list.size() > 0) {
                System.out.println(list.size() + " current tests");
                ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(chosenTest, list);
                choiceDialog.setTitle("Choose which test to take:");
                choiceDialog.setContentText("Choose which test to take:");

                Optional<String> result = choiceDialog.showAndWait();
                if (result.isPresent()) {
                    chosenTest = result.get();
                    System.out.println("you picked " + chosenTest);
                    testSubmission = new TestSubmission(testMap.get(chosenTest)[0], testMap.get(chosenTest)[1]);
                }
            } else {
                System.out.println("No current tests");
                final Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You do not have any current tests.");
                alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                    @Override
                    public void handle(DialogEvent event) {
                        alert.close();
                        //logout();
                    }
                });
                alert.showAndWait();
            }

            initPane();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Sets the number of completed questions.
     * @param num The number of completed questions.
     */
    private void setNumCompleted(int num) {
        numCompleted.setText(num + "/" + questions.size());
    }

    /**
     * Initiates the main view and sets up question pagination.
     */
    private void initPane() {
        if (testSubmission != null) {
            test = testSubmission.getTest();
            questions = test.getAllQuestions();
            pagination.setPageCount(questions.size());
            setNumCompleted(0);
        }

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                return changeQuestion(param);
            }
        });

        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logout();
            }
        });
    }

    /**
     * Changes questions to whichever page the user clicks.
     * @param param The page to go to.
     * @return The Pane that will be displayed by the pagination.
     */
    private TitledPane changeQuestion(int param) {
        if (questions != null) {
            System.out.println("Switching to question " + (param + 1));
            Question question = questions.get(param);

            Label description = new Label(question.getQuestion());

            if (question instanceof CodeQuestion){
                CodeQuestion q = (CodeQuestion) question;
                CodeAnswer answer = q.getStudentAnswer();

                final TextArea textArea = new TextArea();
                ScrollPane scrollPane = new ScrollPane(textArea);

                textArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        answer.setAnswerText(textArea.getText());
                    }
                });

                VBox vbox = new VBox(description, scrollPane);
                questionPane = new TitledPane(question.getQuestionName(), vbox);

            } else if (question instanceof FreeResponseQuestion){
                FreeResponseQuestion q = (FreeResponseQuestion) question;
                FreeResponseAnswer answer = q.getStudentAnswer();

                TextArea textArea = new TextArea();
                ScrollPane scrollPane = new ScrollPane(textArea);

                textArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        answer.setAnswer(textArea.getText());
                    }
                });

                VBox vbox = new VBox(description, scrollPane);
                questionPane = new TitledPane(question.getQuestionName(), vbox);

            } else if (question instanceof MatchingQuestion){
                MatchingQuestion q = (MatchingQuestion) question;

            } else if (question instanceof MultipleChoiceQuestion){
                MultipleChoiceQuestion q = (MultipleChoiceQuestion) question;
                MultipleChoiceAnswer answer = q.getStudentAnswer();

                ToggleGroup group = new ToggleGroup();

                List<AnswerOption> answers = answer.getOptions();
                ArrayList<RadioButton> buttons = new ArrayList<>(answers.size());

                for (AnswerOption option : answers) {
                    RadioButton button = new RadioButton(option.getOptionText());
                    button.setToggleGroup(group);
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            answer.setAnswer(option.getOptionId());
                        }
                    });
                }

            } else if (question instanceof TrueFalseQuestion){
                TrueFalseQuestion q = (TrueFalseQuestion) question;
                TrueFalseAnswer answer = q.getStudentAnswer();

                ToggleGroup group = new ToggleGroup();
                RadioButton trueButton = new RadioButton("True");
                trueButton.setToggleGroup(group);
                trueButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        answer.setAnswer(true);
                    }
                });

                RadioButton falseButton = new RadioButton("False");
                falseButton.setToggleGroup(group);
                falseButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        answer.setAnswer(false);
                    }
                });

                if (answer.getAnswer()) {
                    trueButton.setSelected(true);
                } else {
                    falseButton.setSelected(true);
                }

                VBox vbox = new VBox(description, trueButton, falseButton);
                questionPane = new TitledPane(question.getQuestionName(), vbox);
            }
        } else {
            TrueFalseQuestion tfq = new TrueFalseQuestion();
            tfq.setQuestion("MIPS processors have barrel shifters.");
            MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
            mcq.setQuestion("Which of the following sorts can run indefinitely?");
            if (param == 0) {
                Label label = new Label(tfq.getQuestion());

                ToggleGroup group = new ToggleGroup();
                RadioButton trueButton = new RadioButton("True");
                trueButton.setToggleGroup(group);
                RadioButton falseButton = new RadioButton("False");
                falseButton.setToggleGroup(group);

                VBox vbox = new VBox(label, trueButton, falseButton);
                questionPane = new TitledPane("Question " + (param + 1), vbox);
            } else if (param == 1) {
                Label label = new Label(mcq.getQuestion());

                ToggleGroup group = new ToggleGroup();
                RadioButton button1 = new RadioButton("Quicksort");
                button1.setToggleGroup(group);
                RadioButton button2 = new RadioButton("MergeSort");
                button2.setToggleGroup(group);
                RadioButton button3 = new RadioButton("Bogosort");
                button3.setToggleGroup(group);
                RadioButton button4 = new RadioButton("Bucketsort");
                button4.setToggleGroup(group);

                VBox vbox = new VBox(label, button1, button2, button3, button4);
                questionPane = new TitledPane("Question " + (param + 1), vbox);
            } else {
                Label label = new Label("Question " + (param + 1));
                questionPane = new TitledPane("Question " + (param + 1), label);
            }
        }
        questionPane.setAlignment(Pos.TOP_CENTER);
        questionPane.setCollapsible(false);
        return questionPane;
    }

    /**
     * Initialize with FXMLLoader.
     * @param fxmlFileLocation Location of fxml file.
     * @param resources The resources to use.
     */
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //assert myButton != null : "fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected
    }

    /**
     * Log the user out.
     */
    private void logout() {
        System.out.println("Logout?");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to log out?");
        alert.setResultConverter(new Callback<ButtonType, ButtonType>() {
            @Override
            public ButtonType call(ButtonType param) {
                if (param.getButtonData().isCancelButton()){
                    System.out.println("Close logout dialog");
                } else {
                    System.out.println("Log the student out.");
                    database.closeConnection();
                    Stage testStage = (Stage)(getScene().getWindow());
                    ProfessorLoginUI loginPane = new ProfessorLoginUI("Student");
                    Scene loginScene = new Scene(loginPane, 600, 600);
                    testStage.setScene(loginScene);
                }
                return param;
            }
        });
        alert.showAndWait();
    }
}
