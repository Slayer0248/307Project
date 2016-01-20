import javafx.application.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.logins.ProfessorLoginUI;
import view.student.StudentMainView;

/**
 * The log in for the professor interface
 *
 */
public class JeetTestTaker extends Application {
   /**
    * The window on which everything will be drawn
    */
	private Stage primaryStage;
	/**
	 * The main scene to set up after the user logs in
	 */
	private Scene mainView;
	/**
	 * The pane we wil render oupon login.
	 */
	private ProfessorLoginUI mainPane;
	
	/**
	 * Start the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
        launch(args);
    }

	/**
	 * Start the profram
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage = stage;
		
		primaryStage.setResizable(true);
		primaryStage.setHeight(600);
		primaryStage.setWidth(600);
		primaryStage.setTitle("Jeet Test tool");
		
        mainPane = new ProfessorLoginUI("Student");
		
		mainView = new Scene(mainPane, 600, 600);
		
		primaryStage.setScene(mainView);
		primaryStage.show();
	}
	
	/**
	 * Log the user in 
	 * 
	 * @param userid the id of the user that is logged in.
	 */
	public void login(long userid) {
		//mainView = new StudentMainView(600.0, 600.0);
		
		primaryStage.setScene(mainView);
		primaryStage.show();
	}
	
	
}