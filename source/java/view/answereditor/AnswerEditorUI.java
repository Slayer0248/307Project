package view.answereditor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.accounts.User;
import model.answer.Answer;
import model.question.Question;

/**
 * User interface to edit an answer
 * 
 * @author Jeet Shah
 */
public class AnswerEditorUI extends Pane implements Initializable {

    @FXML
    private Pane answerPane;

    @FXML
    private Button answerSaveChanges;

    @FXML
    private Button answerCancel;

    @FXML
    private Button answerMainMenu;

    @FXML
    private TextArea answerFRQ;

    @FXML
    private ImageView answerToQuestion;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * The user curently logged in
	 */
	private User curUser;
	
	/**
	 * Initialize an answer editor
	 * @param user current user
	 * @param answer the answer
	 * @param temp the question
	 */
	public AnswerEditorUI(User user, Answer answer, Question temp) {

		this.curUser = user;
		
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("AnswerEditorUI.fxml"));
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
	 * Initialize their profile
	 */
	private void initProfile(){
		
		
	}

}

