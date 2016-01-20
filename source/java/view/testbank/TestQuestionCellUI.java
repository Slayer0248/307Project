package view.testbank;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.question.Question;
import view.navigation.ProfessorMainViewUI;

/**
 * A Cell for a Test within the TestBankUI.
 * 
 * @author coltonstapper
 */
public class TestQuestionCellUI extends Pane  implements Initializable{
	
	/**
	 * The question.
	 */
	private Question question;
	
	/**
	 * Whether the cell is chosen or not.
	 */
	private Boolean chosen; 
	
	/**
	 * The parent.
	 */
	private ProfessorMainViewUI parent;
	
    /**
     * The question name.
     */
    @FXML
    private Label questionName;

    /**
     * The question type.
     */
    @FXML
    private Label questionType;
    
    /**
     * The number of points label.
     */
    @FXML
    private Label numPoints;
    
    /**
     * Difficulty label.
     */
    @FXML
    private Label questionDifficulty;
    
    /**
     * Add or remove button.
     */
    @FXML
    private Button addRemoveButton;

    @FXML
    void initialize() {
        //assert questionPane != null : "fx:id=\"questionPane\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionName != null : "fx:id=\"testName\" was not injected: check your FXML file 'TestBankCellUI.fxml'.";
        //assert questionBankEdit != null : "fx:id=\"questionBankEdit\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        //assert questionBankDelete != null : "fx:id=\"questionBankDelete\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionType != null : "fx:id=\"questionSummary\" was not injected: check your FXML file 'TestBankCellUI.fxml'.";
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        //assert questionPane != null : "fx:id=\"questionPane\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionName != null : "fx:id=\"testName\" was not injected: check your FXML file 'TestBankCellUI.fxml'.";
        //assert questionBankEdit != null : "fx:id=\"questionBankEdit\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        //assert questionBankDelete != null : "fx:id=\"questionBankDelete\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionType != null : "fx:id=\"questionSummary\" was not injected: check your FXML file 'TestBankCellUI.fxml'.";
	}
	
    /**
     * Creates a TestBankCellUI.
     * @param parent
     */
    public TestQuestionCellUI(ProfessorMainViewUI parent, Question question, Boolean chosen) {
    	this.parent = parent;
    	this.question = question;
    	this.chosen = chosen;
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TestQuestionCell.fxml"));
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

    private void init(ProfessorMainViewUI parent){
    	this.parent = parent;
    }
    
    /**
     * Changes the text UI of the cell based on the {@linkplain Question}.
     */
    private void setupUI() {
    	questionName.setText(question.getQuestionName());
    	if (chosen) {
    		addRemoveButton.setText("-");
    	}
    	else {
    		addRemoveButton.setText("+");
    	}
    	
    	questionType.setText(question.getQuestionType());
    	questionDifficulty.setText(question.getQuestionDifficulty() + "/5 Difficulty");
    	numPoints.setText(question.getQuestionPoints() + " Points");
    }
    
    /**
     * @return the {@link Question} that belongs to the Cell.
     */
    public Question getQuestion() {
    	return question;
    }
    
    /**
     * @return The add or remove Button.
     */
    public Button getAddOrRemoveButton() {
    	return addRemoveButton;
    }
}
