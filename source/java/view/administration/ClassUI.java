package view.administration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import model.test.Test;
import model.question.MatchingQuestion;
import model.question.MultipleChoiceQuestion;
import model.question.Question;
import model.question.TrueFalseQuestion;
import model.accounts.Section;
import model.accounts.User;
import view.navigation.ProfessorMainViewUI;

/**
 * A Cell for a section within the ClassAdministrationUI.
 * 
 * @author Jacob Garcia
 */
public class ClassUI extends Pane  implements Initializable{
	
	private Section section;
	
    //@FXML
    //private Pane questionPane;
	private ProfessorMainViewUI parent;
	private ArrayList<Label> labels;
	
    @FXML
    private Label className;
    
    @FXML
    private AnchorPane testPane;
    
    @FXML
    private ComboBox studentListComboBox;
    
    @FXML
    private Button addStudentButton;
    
    @FXML
    private Button delStudentButton;
    
    @FXML
    private Button delClassButton;

    @FXML
    void initialize() {
        //assert questionPane != null : "fx:id=\"questionPane\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert className != null : "fx:id=\"testName\" was not injected: check your FXML file 'ClassUI.fxml'.";
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        //assert questionPane != null : "fx:id=\"questionPane\" was not injected: check your FXML file 'QuestionUI.fxml'.";
        assert className != null : "fx:id=\"testName\" was not injected: check your FXML file 'ClassUI.fxml'.";
	}
	
    /**
     * Creates a TestBankCellUI.
     * @param parent
     */
    public ClassUI(ProfessorMainViewUI parent, Section section) {
    	this.parent = parent;
    	this.section = section;
    	this.labels = new ArrayList<>();
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassUI.fxml"));
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
     * Changes the text UI of the cell based on the {@linkplain Class}.
     */
    private void setupUI() {
    	List<Test> tests = section.getTests();
    	className.setText(section.getName());;
    	
    	for(int i = 0; i < tests.size(); i++) {
    		String name = tests.get(i).getName();
    		//System.out.println(name);
    		Label testLabel = new Label(name);
    		assert(testLabel != null);
        	labels.add(testLabel);
    		
        	testPane.getChildren().add(testLabel);
        	
        	double groupOffset = 21;
        	double curYCoord = 0;

        	for (Label label : labels) {
        		label.relocate(0, curYCoord);
        		curYCoord += groupOffset;
        	}
    	}
    	List<User> proctors = section.getProctors();
    	List<User> students = section.getStudents();
    	for(int i = 0; i < proctors.size(); i++) {
    		String fullName = "(P) " + proctors.get(i).getFirstName() + " " + proctors.get(i).getLastName();
    	    studentListComboBox.getItems().add(fullName);
    	}
    	for(int i = 0; i < students.size(); i++) {
    		String fullName = students.get(i).getFirstName() + " " + students.get(i).getLastName();
    	    studentListComboBox.getItems().add(fullName);
    	}
    }
    
    /**
     * @return Add Button.
     */
    public Button getAddStudentButton() {
    	return addStudentButton;
    }
    
    /**
     * @return Delete Button.
     */
    public Button getDelStudentButton() {
    	return delStudentButton;
    }
    
    /**
     * @return X Button.
     */
    public Button getDelClassButton() {
    	return delClassButton;
    }
    
    public ComboBox getComboBox() {
    	return studentListComboBox;
    }
    
    /**
     * @return the Section that the ClassUI belongs to.
     */
    public Section getSection() {
    	return section;
    }
}
