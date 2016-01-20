package view.testbank;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.accounts.User;
import model.question.Question;
import model.question.QuestionBank;
import model.test.Test;
import view.navigation.ProfessorMainViewUI;

/**
 * UI for the TestEditor.
 * 
 * @author colton.stapper
 */
public class TestEditorUI extends Pane implements Initializable {

	/**
	 * The parent.
	 */
	private ProfessorMainViewUI parent;
	/**
	 * The question bank.
	 */
	private QuestionBank questionBank;
	/**
	 * The chosen questions.
	 */
	private List<Question> chosenQuestions;
	/**
	 * The potential questions.
	 */
	private List<Question> potentialQuestions;
	/**
	 * The chosen question cells.
	 */
	private List<TestQuestionCellUI> chosenCells;
	/**
	 * The potential question cells.
	 */
	private List<TestQuestionCellUI> potentialCells;
	/**
	 * The current user.
	 */
	private User curUser;
	/**
	 * The test.
	 */
	private Test chosenTest;
	
	/**
	 * The test name label.
	 */
	@FXML
	private TextField testName;
	
	/**
	 * The test description label.
	 */
	@FXML
	private TextArea description;
	
	/**
	 * The tag label.
	 */
	@FXML
	private TextArea tags;
	
	/**
	 * The total points label.
	 */
	@FXML
	private Label totalPoints;
	
	/**
	 * The difficulty label.
	 */
	@FXML
	private Label averageDifficulty;
	
    /**
     * The cancel button.
     */
    @FXML
    private Button cancelButton;
    
    /**
     * Chosen question pane.
     */
    @FXML
    private Pane chosenQuestionPane;
    
    /**
     * Potential question pane.
     */
    @FXML
    private Pane potentialQuestionPane;
    
    /**
     * Potential scroll bar.
     */
    @FXML
    private ScrollBar potentialScrollBar;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
    /**
     * @param parent
     * @param chosenTest
     */
    public TestEditorUI(ProfessorMainViewUI parent, Test chosenTest, User user) {
    	this.parent = parent;
    	this.chosenTest = chosenTest;
    	this.curUser = user;
    	this.questionBank = new QuestionBank(curUser.getId());
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TestEditor.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
        	e.printStackTrace();
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
        }
    	
    	setupView();
    }
    
    /**
     * Sets up the view for the UI.
     */
    public void setupView() {
    	testName.setText(chosenTest.getName());
    	description.setText(chosenTest.getDescription());
    	tags.setText(chosenTest.getTags().toString());
    	
    	setupQuestionPanes();
    }
    
    /**
     * Sets up the UI for the Question Panes.
     */
    public void setupQuestionPanes() {    	
    	potentialQuestions = new ArrayList<>();
    	chosenQuestions = new ArrayList<>();
    	potentialCells = new ArrayList<>();
    	chosenCells = new ArrayList<>();
    	
    	List<Question> allQuestions = questionBank.getQuestions();
    	for (Question question : allQuestions) {
    		boolean found = false;
    		for (Question chosenQuestion : chosenTest.getAllQuestions()) {
    			if (chosenQuestion.getId() == question.getId()) {
    				found = true;
    				chosenQuestions.add(chosenQuestion);
    			}
    		}
    		if (!found) {
    			potentialQuestions.add(question);
    		}
    	}
    	
    	layoutQuestionCells();
    	
//    	potentialScrollBar.valueProperty().addListener(new ChangeListener<Number>() {
//	        public void changed(ObservableValue<? extends Number> ov,
//	            Number old_val, Number new_val) {
//	        	potentialQuestionPane.setLayoutY(-new_val.doubleValue());
//	            }
//	    });
    	
    }
    
    /**
     * Refreshes summary labels.
     */
    private void refreshSummaryLabels() {
    	double totalPnts = 0.0;
    	double averageDiff = 0.0;
    	int numQuestions = 0;
    	for (Question question : chosenQuestions) {
    		totalPnts += question.getQuestionPoints();
    		averageDiff += question.getQuestionDifficulty();
    		numQuestions++;
    	}
    	totalPoints.setText(totalPnts + " Points");
    	if (numQuestions > 0) {
    		averageDifficulty.setText(averageDiff / numQuestions + "/5 Difficulty");
    	}
    	else {
    		averageDifficulty.setText("N/A");
    	}
    }
    
    /**
     * Layouts the cells.
     */
    private void layoutQuestionCells() {
    	layoutPotentialCells();
    	layoutChosenCells();
    	
    	refreshSummaryLabels();
    }
    
    /**
     * Layouts potential cells.
     */
    private void layoutPotentialCells() {
    	for (int i = 0; i < potentialQuestions.size(); i++) {
    		
    		Question question = potentialQuestions.get(i);
        	TestQuestionCellUI questionCell = new TestQuestionCellUI((ProfessorMainViewUI)getScene(), question, false);
        	assert(questionCell != null);
        	potentialCells.add(questionCell);
        	
        	Button addQuestionButton = questionCell.getAddOrRemoveButton();
        	addQuestionButton.setId(String.valueOf(i));
        	addQuestionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createAddQuestionHandler());
        	
        	potentialQuestionPane.getChildren().add(questionCell);
        	
        	double groupOffset = 10;
        	double curYCoord = 15;

        	for (TestQuestionCellUI cell : potentialCells) {
        		cell.relocate(0.0, curYCoord);
        		curYCoord += cell.getPrefHeight() + groupOffset;
        	}
    	}
    }
    
    /**
     * Layouts chosen cells.
     */
    private void layoutChosenCells() {
    	for (int i = 0; i < chosenQuestions.size(); i++) {
    		
    		Question question = chosenQuestions.get(i);
        	TestQuestionCellUI questionCell = new TestQuestionCellUI((ProfessorMainViewUI)getScene(), question, true);
        	assert(questionCell != null);
        	chosenCells.add(questionCell);
        	
        	Button removeQuestionButton = questionCell.getAddOrRemoveButton();
        	removeQuestionButton.setId(String.valueOf(i));
        	removeQuestionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createRemoveQuestionHandler());
        	
        	chosenQuestionPane.getChildren().add(questionCell);
        	
        	double groupOffset = 10;
        	double curYCoord = 15;

        	for (TestQuestionCellUI cell : chosenCells) {
        		cell.relocate(0.0, curYCoord);
        		curYCoord += cell.getPrefHeight() + groupOffset;
        	}
    	}
    }
    
    /**
     * Creates an Add question handler.
     * @return
     */
    private final EventHandler<MouseEvent> createAddQuestionHandler() {
        
	    return new EventHandler<MouseEvent> () {
		    @Override
		    public void handle(MouseEvent event) {
//		        System.out.println("Add question button clicked");;
		        final Button addButton = (Button) event.getSource();

	        	TestQuestionCellUI questionCell = potentialCells.get(Integer.parseInt(addButton.getId()));
	        	Question chosenQuestion = questionCell.getQuestion();
		        chosenQuestions.add(chosenQuestion);
		        potentialQuestions.remove(chosenQuestion);
	        	
		        for (TestQuestionCellUI cell : chosenCells) {
		        	chosenQuestionPane.getChildren().remove(cell);
		        }
		        
		        for (TestQuestionCellUI cell : potentialCells) {
		        	potentialQuestionPane.getChildren().remove(cell);
		        }
		        
		        chosenCells = new ArrayList<>();
		        potentialCells = new ArrayList<>();
		        layoutQuestionCells();
		    }
		};
    }

    /**
     * Creates a Remove Question handler.
     * @return
     */
    private final EventHandler<MouseEvent> createRemoveQuestionHandler() {
        
	    return new EventHandler<MouseEvent> () {
		    @Override
		    public void handle(MouseEvent event) {
//		        System.out.println("Remove question button clicked");;
		        final Button removeButton = (Button) event.getSource();

	        	TestQuestionCellUI questionCell = chosenCells.get(Integer.parseInt(removeButton.getId()));
	        	Question chosenQuestion = questionCell.getQuestion();
	        	potentialQuestions.add(chosenQuestion);
		        chosenQuestions.remove(chosenQuestion);
	        	
		        for (TestQuestionCellUI cell : chosenCells) {
		        	chosenQuestionPane.getChildren().remove(cell);
		        }
		        
		        for (TestQuestionCellUI cell : potentialCells) {
		        	potentialQuestionPane.getChildren().remove(cell);
		        }
		        
		        chosenCells = new ArrayList<>();
		        potentialCells = new ArrayList<>();
		        layoutQuestionCells();
		    }
		};
    }
    
    /**
     * Saves the changes to Test.
     */
    @FXML
    public void saveChanges() {
    	System.out.println("SAVE CHANGES");
    	
    	System.out.println("Potential Questions: " + potentialQuestions);
    	System.out.println("Chosen Questions: " + chosenQuestions);
    	
    	chosenTest.setDescription(description.getText());
    	chosenTest.setName(testName.getText());
    	chosenTest.deleteQuestions();
    	for (Question chosenQ : chosenQuestions) {
    		chosenTest.addQuestion(chosenQ);
    	}
    	
    	
    }
    
    /**
     * Cancel editing and return to TestBank.
     */
    @FXML
    public void cancelEdit() {
		System.out.println("Go to Test Bank View here");
		Stage testStage = (Stage)(getScene().getWindow());
    	ProfessorMainViewUI mainView = new ProfessorMainViewUI(600, 600);
    	try {
			mainView.performFinalSetup("testCreator", curUser.getId());//curUser.getId());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	testStage.setScene(mainView);
    }
}
