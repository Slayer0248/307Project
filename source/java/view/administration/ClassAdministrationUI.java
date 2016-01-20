package view.administration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.test.Test;
import model.util.DBAccess;
import model.accounts.Section;
import model.accounts.User;
import view.navigation.ProfessorMainViewUI;
import view.testbank.TestBankCellUI;
import view.administration.ClassUI;

/**
 * UI for the administration page.
 * 
 * @author Jacob Garcia
 */
public class ClassAdministrationUI extends Pane implements Initializable {
   /**
    * The pane to return to when this view finishes.
    */
	private ProfessorMainViewUI parent;
	/**
	 * List fo classes.
	 */
	private ArrayList<ClassUI> classes;
	
	@FXML
    private AnchorPane classPane;
	
    @FXML
    private Button searchButton;
    
    @FXML
    private Button addNewClassButton;
    
    @FXML
    private Button addStudentButton;
	
    @FXML
    private Button delStudentButton;
    
    @FXML
    private Button delClassButton;
    
    private User curUser;
    private DBAccess database;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Create a new Class Administration User Interface so the user
	 * can administerate their classes.
	 * 
	 * @param parent pane to return to when this is done
	 * @param user the user currently logged in
	 */
    public ClassAdministrationUI(ProfessorMainViewUI parent, User user) {
    	this.parent = parent;
    	curUser = user;
    	this.classes = new ArrayList<>();
    	database = new DBAccess();
    	
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassAdministrationUI.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
        	e.printStackTrace();
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
        }
    	
    	populateScroll();
    	setupView();
    }
    
    /**
     * populate all the classes and things like that from the db.
     */
    public void populateScroll() {
    	List<Section> sections = Section.getSectionsOwnedByUser(curUser.getId());
    	
    	for(int i = 0; i < sections.size(); i++) {
    		Section section = sections.get(i);
    		ClassUI tempClass = new ClassUI(parent, section);
    		assert(tempClass != null);
        	classes.add(tempClass);
        	
        	Button addStudentButton = tempClass.getAddStudentButton();
        	addStudentButton.setId(String.valueOf(i));
        	addStudentButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createAddHandler());
        	
        	Button delStudentButton = tempClass.getDelStudentButton();
        	delStudentButton.setId(String.valueOf(i));
        	delStudentButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createRemoveHandler());
        	
        	Button delClassButton = tempClass.getDelClassButton();
        	delClassButton.setId(String.valueOf(i));
        	delClassButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createDeleteHandler());
        	
        	ComboBox studentListComboBox = tempClass.getComboBox();
        	studentListComboBox.setId(String.valueOf(i));
        	
        	classPane.getChildren().add(tempClass);
        	
        	double groupOffset = 150;
        	double curYCoord = 0;

        	for (ClassUI cell : classes) {
        		cell.relocate(27.0, curYCoord);
        		curYCoord += groupOffset;
        	}
    	}
    }
    
    /**
     * Set up button handlers.
     */
    public void setupView() {
    	searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                System.out.println("Search the query.");
            }
        });
    	
    	addNewClassButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                System.out.println("Add new Class.");
                parent.setActivePane(new AddClassUI(parent, curUser));
            }
        });
    }
    
    /**
     * Set up the handler for add
     * 
     * @return the mouse event that occured.
     */
    private final EventHandler<MouseEvent> createAddHandler() {
        
	    return new EventHandler<MouseEvent> () {
		    @Override
		    public void handle(MouseEvent event) {
		        System.out.println("Add button clicked");;
		        final Button addStudentButton = (Button) event.getSource();
		        
		        int tempClassNum = Integer.parseInt(addStudentButton.getId());
		        Section section = classes.get(tempClassNum).getSection();
		        parent.setActivePane(new AddStudentUI(parent, curUser, section));
		    }
	    };
    }
    
    /**
     * Set up the handler for remove.
     * 
     * @return the event that happened.
     */
    private final EventHandler<MouseEvent> createRemoveHandler() {
        
	    return new EventHandler<MouseEvent> () {
		    @Override
		    public void handle(MouseEvent event) {
		        System.out.println("Delete button clicked");;
		        final Button delStudentButton = (Button) event.getSource();
		        
		      //remove from db(userSections)
		        int tempClassNum = Integer.parseInt(delStudentButton.getId());
		        Long sectionId = classes.get(tempClassNum).getSection().getId();
		        
	    		ClassUI tempClass = new ClassUI(parent, classes.get(tempClassNum).getSection());
		        ComboBox tempComboBox = tempClass.getComboBox();
		        String fullName = new String((String)tempComboBox.getValue());      //null pointer exception!
		        String tempName = new String("");
		        Long userId = null;
		        try {
		        	ResultSet res1 = database.select("UsersSections", "userId", "sectionId = " + classes.get(tempClassNum).getSection().getId());
		        	database.closeConnection();
		        	System.out.println(fullName);
		        	while(!fullName.equals(tempName)){
		        		userId = res1.getLong("userId");
		    		    ResultSet res2 = database.select("Users", "*", "userID = " + userId);
		    		    
		    		    tempName = "";
		        		if(res2.getBoolean("isProctor"))
    		        		tempName = "(P) ";
		        		tempName = tempName + res2.getString("FirstName") + " " + res2.getString("LastName");
		        		database.closeConnection();
		        	}
		        	database.delete("UsersSections", "sectionId = " + sectionId + " AND userId = " + userId);
		        }
		        catch (Exception e) {
	    			System.out.println("Error occured while deleting a Student for user: "
	    		               + curUser.getId());
	    		         e.printStackTrace();
	    		}
		    }
	    };
    }

    /**
     * Set up the event handler for delete
     * 
     * @return the event that occured.
     */
    private final EventHandler<MouseEvent> createDeleteHandler() {
        
	    return new EventHandler<MouseEvent> () {
		    @Override
		    public void handle(MouseEvent event) {
		        System.out.println("X button clicked");;
		        final Button delClassButton = (Button) event.getSource();

		        Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setTitle("Confirmation Dialog");
		        alert.setContentText("Are you sure you want to delete this class?");
		        int tempClassNum = Integer.parseInt(delClassButton.getId());
		        Optional<ButtonType> result = alert.showAndWait();
		        if (result.get() == ButtonType.OK){
		            System.out.println("Delete class");
		        	ClassUI tempClass = classes.get(tempClassNum);
		        	//remove rows from db(TestSections)(UserSections)
	                //remove from db(Sections)
		        	try {
		        		database.delete("Sections", "sectionId=" + tempClass.getSection().getId());
		        	}
		        	catch (SQLException e) {
		    			System.out.println("Error occured while deleting a Class for user: "
		    		               + curUser.getId());
		    		         e.printStackTrace();
		    		}
		        	
		        	for (ClassUI cell : classes) {
		        		classPane.getChildren().remove(cell);
		        	}
		        	classes = new ArrayList<>();
		        	populateScroll();
		            alert.close();
		        } else {
		            System.out.println("Do not Remove class");
		            alert.close();
		            	// ... user chose CANCEL or closed the dialog
		        }
		        
		    }
		};
    }
}
