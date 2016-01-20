import javafx.event.EventHandler;

import java.sql.SQLException;

import javafx.application.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.logins.ProfessorLoginUI;
import view.navigation.ProfessorMainViewUI;
import model.accounts.User;
import model.util.DBAccess;

/**
 * The log in for the professor interface
 *
 */
public class JeetTool extends Application {
   /**
    * The primary stage to load.
    */
	private Stage primaryStage;
	/**
	 * The main scene to show on the screen.
	 */
	private Scene mainView;
	/**
	 * Login interface for the professor.
	 */
	private ProfessorLoginUI mainPane;
	/**
	 * The database for accessing the database.
	 */
	private DBAccess database;
	/**
	 * The current user.
	 */
	private User curUser;
	
	/**
	 * Main program
	 * 
	 * @param args any arguments passed
	 */
	public static void main(String[] args) {
        launch(args);
    }

	/**
	 * Start the program.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		database = new DBAccess();
		curUser = null;
		primaryStage = stage;
		
		primaryStage.setResizable(true);
		primaryStage.setHeight(600);
		primaryStage.setWidth(600);
		primaryStage.setTitle("Jeet professor tool");
		
		mainPane = new ProfessorLoginUI("Professor");
		
		mainView = new Scene(mainPane, 600, 600);
		
		primaryStage.setScene(mainView);
		primaryStage.show();
	}
	
	/**
	 * Log the user in
	 * 
	 * @param userid id of the user to log in
	 */
	public void login(long userid) {
		mainView = new ProfessorMainViewUI(600, 600);
		try {
			((ProfessorMainViewUI) mainView).performFinalSetup("loginView", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		primaryStage.setScene(mainView);
		primaryStage.show();     
	}	
}