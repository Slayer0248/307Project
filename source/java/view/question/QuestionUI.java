package view.question;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.question.Question;
import view.navigation.ProfessorMainViewUI;

/**
 * The question ui.
 * @author Jeet
 */
public class QuestionUI extends Pane  implements Initializable{
    //@FXML
    //private Pane questionPane;
	/**
	 * The parent.
	 */
	private ProfessorMainViewUI parent;
	/**
	 * The question.
	 */
	private Question question;
    /**
     * The question text.
     */
    @FXML
    private Text questionNumber;

    /**
     * The edit button.
     */
    @FXML
    private Button editQuestionButton;

    /**
     * The delete button.
     */
    @FXML
    private Button deleteQuestionButton;

    /**
     * The points text.
     */
    @FXML
    private Text pointsQuestion;
    
    /**
     * The question string.
     */
    @FXML
    private Text questionString;

    /**
     * The question categories.
     */
    @FXML
    private Text questionCategories;

    /**
     * The question type.
     */
    @FXML
    private Text questionType;

    @FXML
    void initialize() {
        //assert questionPane != null : "fx:id=\"questionPane\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionNumber != null : "fx:id=\"questionNumber\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        //assert questionBankEdit != null : "fx:id=\"questionBankEdit\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        //assert questionBankDelete != null : "fx:id=\"questionBankDelete\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionString != null : "fx:id=\"questionString\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionCategories != null : "fx:id=\"questionCategories\" was not injected: check your FXML file 'QuestionUI.fxml'.";

    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        //assert questionPane != null : "fx:id=\"questionPane\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionNumber != null : "fx:id=\"questionNumber\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        //assert questionBankEdit != null : "fx:id=\"questionBankEdit\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        //assert questionBankDelete != null : "fx:id=\"questionBankDelete\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionString != null : "fx:id=\"questionString\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionCategories != null : "fx:id=\"questionCategories\" was not injected: check your FXML file 'QuestionUI.fxml'.";
		
	}
	
    /**
     * Creates the question ui.
     * @param parent
     * @param question
     */
    public QuestionUI(ProfessorMainViewUI parent, Question question) {
    	this.question = question;
    	this.parent = parent;
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionUI.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
        	e.printStackTrace();
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
        }
    	
    	init(parent);
    	setupUI();

    }
 

    /**
     * Inits.
     * @param parent
     */
    private void init(ProfessorMainViewUI parent){
    	this.parent = parent;
    
    }

    /**
     * Sets up the ui.
     */
    private void setupUI() {
    	questionNumber.setText("Question ".concat(Long.toString(question.getId())));
    	questionString.setText(question.getQuestionName());
    	pointsQuestion.setText(Double.toString(question.getQuestionPoints()));
    	questionType.setText(question.getQuestionType());
    	
    }
    
    /**
     * @return Delete Button.
     */
    public Button getDeleteButton() {
    	return deleteQuestionButton;
    }
    
    /**
     * @return Edit Button.
     */
    public Button getEditButton() {
    	return editQuestionButton;
    }
    
    /**
     * @return the Test that the TestCell belongs to.
     */
    public Question getQuestion() {
    	return question;
    }
    
    /**
     * Gets the question number.
     * @return
     */
    public Text getquestionNumber(){
    	return questionNumber;
    }




}
