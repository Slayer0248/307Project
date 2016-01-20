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
import model.test.Test;
import view.navigation.ProfessorMainViewUI;

/**
 * A Cell for a Test within the TestBankUI.
 * 
 * @author coltonstapper
 */
public class TestBankCellUI extends Pane  implements Initializable{
	
	/**
	 * The test.
	 */
	private Test test;
	
	/**
	 * The parent.
	 */
	private ProfessorMainViewUI parent;
	
    /**
     * The name label.
     */
    @FXML
    private Label testName;

    /**
     * The summary label.
     */
    @FXML
    private Label questionSummary;

    /**
     * The total points label.
     */
    @FXML
    private Label totalPoints;
    
    /**
     * The average difficulty.
     */
    @FXML
    private Label averageDifficulty;
    
    /**
     * The delete test button.
     */
    @FXML
    private Button deleteTestButton;
    
    /**
     * The edit test button.
     */
    @FXML
    private Button editTestButton;

    @FXML
    void initialize() {
        //assert questionPane != null : "fx:id=\"questionPane\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert testName != null : "fx:id=\"testName\" was not injected: check your FXML file 'TestBankCellUI.fxml'.";
        //assert questionBankEdit != null : "fx:id=\"questionBankEdit\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        //assert questionBankDelete != null : "fx:id=\"questionBankDelete\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionSummary != null : "fx:id=\"questionSummary\" was not injected: check your FXML file 'TestBankCellUI.fxml'.";
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        //assert questionPane != null : "fx:id=\"questionPane\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert testName != null : "fx:id=\"testName\" was not injected: check your FXML file 'TestBankCellUI.fxml'.";
        //assert questionBankEdit != null : "fx:id=\"questionBankEdit\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        //assert questionBankDelete != null : "fx:id=\"questionBankDelete\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert questionSummary != null : "fx:id=\"questionSummary\" was not injected: check your FXML file 'TestBankCellUI.fxml'.";
	}
	
    /**
     * Creates a TestBankCellUI.
     * @param parent
     */
    public TestBankCellUI(ProfessorMainViewUI parent, Test test) {
    	this.parent = parent;
    	this.test = test;
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TestCellUI.fxml"));
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
     * Initializes a {@linkplain TestBankCellUI}.
     * @param parent
     */
    private void init(ProfessorMainViewUI parent){
    	this.parent = parent;
    }
    
    /**
     * Changes the text UI of the cell based on the {@linkplain Test}.
     */
    private void setupUI() {
    	testName.setText(test.getName());;
    	double totalPoints = 0.0;
    	double averageDifficulty = 0.0;
    	int numQuestions = 0;
    	for (Question question : test.getAllQuestions()) {
    		totalPoints += question.getQuestionPoints();
    		averageDifficulty += question.getQuestionDifficulty();
    		numQuestions++;
    	}
    	this.totalPoints.setText(totalPoints + " Total Points");
    	
    	if (numQuestions > 0) {
    		this.averageDifficulty.setText(averageDifficulty + "/5 Difficulty");
    		this.questionSummary.setText(numQuestions + " Questions");
    	}
    	else {
    		this.averageDifficulty.setText("");
    		this.questionSummary.setText("No Questions");
    	}
    }
    
    /**
     * @return Delete Button.
     */
    public Button getDeleteButton() {
    	return deleteTestButton;
    }
    
    /**
     * @return Edit Button.
     */
    public Button getEditButton() {
    	return editTestButton;
    }
    
    /**
     * @return the Test that the TestCell belongs to.
     */
    public Test getTest() {
    	return test;
    }
}
