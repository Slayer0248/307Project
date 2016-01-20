package view.navigation;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClayTest extends Application {
	private Stage primaryStage;
	private ProfessorMainViewUI mainView;
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage = stage;
		
		primaryStage.setResizable(true);
		primaryStage.setHeight(600);
		primaryStage.setWidth(600);
		primaryStage.setTitle("Jeet Test tool");
		
		mainView = new ProfessorMainViewUI(600, 600);
		mainView.performFinalSetup("loginView", 0);
		
		primaryStage.setScene(mainView);
		primaryStage.show();
	}
	
	
	
}
