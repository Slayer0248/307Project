package view.accounts;

import model.accounts.User;
import model.util.DBAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.swing.event.ChangeListener;
import javax.tools.JavaFileManager.Location;
import javax.imageio.ImageIO;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.commons.net.util.Base64;

/**
 * User Interface for editing the user's account
 */
//fx:controller="view.accounts.ProfileUI"
public class ProfileUI extends Pane implements Initializable {
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //@FXML // fx:id="profilePane"
    //private Pane profilePane; // Value injected by FXMLLoader

    @FXML // fx:id="profileVBox"
    private VBox profileVBox; // Value injected by FXMLLoader

    @FXML // fx:id="profileHeaderHbox"
    private HBox profileHeaderHbox; // Value injected by FXMLLoader

    @FXML // fx:id="profilePicturePane"
    private Pane profilePicturePane; // Value injected by FXMLLoader

    @FXML // fx:id="noProfilePictureRect"
    private Rectangle noProfilePictureRect; // Value injected by FXMLLoader

    @FXML // fx:id="profilePictureView"
    private ImageView profilePictureView; // Value injected by FXMLLoader

    @FXML // fx:id="addProfilePicLink"
    private Hyperlink addProfilePicLink; // Value injected by FXMLLoader

    @FXML // fx:id="profileUserPane"
    private Pane profileUserPane; // Value injected by FXMLLoader

    @FXML // fx:id="profileFullNameLabel"
    private Label profileFullNameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="profileUsernameLabel"
    private Label profileUsernameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="updateProfilePane"
    private Pane updateProfilePane; // Value injected by FXMLLoader

    @FXML // fx:id="usernameFieldLabel"
    private Label usernameFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="usernameField"
    private TextField usernameField; // Value injected by FXMLLoader

    @FXML // fx:id="currentPasswordFieldLabel"
    private Label currentPasswordFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="currentPasswordField"
    private PasswordField currentPasswordField; // Value injected by FXMLLoader

    @FXML // fx:id="newPasswordFieldLabel"
    private Label newPasswordFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="newPasswordField"
    private PasswordField newPasswordField; // Value injected by FXMLLoader

    @FXML // fx:id="confirmPasswordFieldLabel"
    private Label confirmPasswordFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="confirmPasswordField"
    private PasswordField confirmPasswordField; // Value injected by FXMLLoader

    @FXML // fx:id="updateProfileButton"
    private Button updateProfileButton; // Value injected by FXMLLoader
	
	 /**
	  * The database to access the database with.
	  */
    private DBAccess database;
    
    /**
     * The current logged in user.
     */
    private User curUser;
	
    /**
     * Create a profile managing User Interface.
     */
    public ProfileUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileUI.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
        	e.printStackTrace();
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
        }
        
        try {
			database = new DBAccess();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        initProfile();
    }
	
    /**
     * Get all the data associated with the given user 
     * @param userId the id of the good user
     */
	public void setupForUser(long userId) {
		curUser = new User();
		curUser.setId(userId);
		
		ResultSet results = null;
		try {
			results = database.select("Users", "*", "userID = " + curUser.getId());
			
			while (results.next()) {
				curUser.setType(results.getString("type"));
				curUser.setCanProctor(results.getBoolean("isProctor"));
				curUser.setFirstName(results.getString("firstName"));
				curUser.setLastName(results.getString("lastName"));
				curUser.setUsername(results.getString("orgUsername"));
				curUser.setPassword(results.getString("password"));
				System.out.println("got this: " + curUser.getProfilePic());
				if (results.getString("profilePicFile").length() > 0) {
					
				   curUser.setProfilePic(results.getString("profilePicFile"));
				}
				//System.out.println("We got this s**t username: " + curUser.getUsername());
				
				profileFullNameLabel.setText(curUser.getFirstName() + " "+ curUser.getLastName());
				profileFullNameLabel.setAlignment(Pos.CENTER);
				profileUsernameLabel.setText(curUser.getUsername());
				usernameField.setText(curUser.getUsername());
				currentPasswordField.setText(curUser.getPassword());
				
				
				if (results.getString("profilePicFile").length() > 0) {
					Image image = new Image(getImageFile().toURI().toString(), 100.0, 100.0, true, true);
					profilePictureView.setImage(image);
					profilePictureView.resize(100, 100);
					profilePictureView.setSmooth(true);
					profilePictureView.setCache(true);
				}
		    }
			database.closeConnection();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        //assert profilePane != null : "fx:id=\"profilePane\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profileVBox != null : "fx:id=\"profileVBox\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profileHeaderHbox != null : "fx:id=\"profileHeaderHbox\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profilePicturePane != null : "fx:id=\"profilePicturePane\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert noProfilePictureRect != null : "fx:id=\"noProfilePictureRect\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profilePictureView != null : "fx:id=\"profilePictureView\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert addProfilePicLink != null : "fx:id=\"addProfilePicLink\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profileUserPane != null : "fx:id=\"profileUserPane\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profileFullNameLabel != null : "fx:id=\"profileFullNameLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profileUsernameLabel != null : "fx:id=\"profileUsernameLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert updateProfilePane != null : "fx:id=\"updateProfilePane\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert usernameFieldLabel != null : "fx:id=\"usernameFieldLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert currentPasswordFieldLabel != null : "fx:id=\"currentPasswordFieldLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert currentPasswordField != null : "fx:id=\"currentPasswordField\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert newPasswordFieldLabel != null : "fx:id=\"newPasswordFieldLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert newPasswordField != null : "fx:id=\"newPasswordField\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert confirmPasswordFieldLabel != null : "fx:id=\"confirmPasswordFieldLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert confirmPasswordField != null : "fx:id=\"confirmPasswordField\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert updateProfileButton != null : "fx:id=\"updateProfileButton\" was not injected: check your FXML file 'ProfileUI.fxml'.";

        //initialize logic here
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
        //assert profilePane != null : "fx:id=\"profilePane\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profileVBox != null : "fx:id=\"profileVBox\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profileHeaderHbox != null : "fx:id=\"profileHeaderHbox\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profilePicturePane != null : "fx:id=\"profilePicturePane\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert noProfilePictureRect != null : "fx:id=\"noProfilePictureRect\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profilePictureView != null : "fx:id=\"profilePictureView\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert addProfilePicLink != null : "fx:id=\"addProfilePicLink\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profileUserPane != null : "fx:id=\"profileUserPane\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profileFullNameLabel != null : "fx:id=\"profileFullNameLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert profileUsernameLabel != null : "fx:id=\"profileUsernameLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert updateProfilePane != null : "fx:id=\"updateProfilePane\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert usernameFieldLabel != null : "fx:id=\"usernameFieldLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert currentPasswordFieldLabel != null : "fx:id=\"currentPasswordFieldLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert currentPasswordField != null : "fx:id=\"currentPasswordField\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert newPasswordFieldLabel != null : "fx:id=\"newPasswordFieldLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert newPasswordField != null : "fx:id=\"newPasswordField\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert confirmPasswordFieldLabel != null : "fx:id=\"confirmPasswordFieldLabel\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert confirmPasswordField != null : "fx:id=\"confirmPasswordField\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        assert updateProfileButton != null : "fx:id=\"updateProfileButton\" was not injected: check your FXML file 'ProfileUI.fxml'.";
        
        //initialize logic here
	}
	
	/**
	 * Set up this screen and set up all event handlers.  
	 */
	private void initProfile() {
		/*getScene().getWindow().widthProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		        System.out.println("Width: " + newSceneWidth);
		    }
		});*/
		
		addProfilePicLink.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Upload profile picture.");
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose profile pic file");
				fileChooser.getExtensionFilters().addAll(
						new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
						new ExtensionFilter("All Files", "*.*"));
				//Stage testStage = (Stage)(getScene().getWindow());
				File selectedFile = fileChooser.showOpenDialog((Stage)(getScene().getWindow()));
				FileInputStream imageInFile;
				String imageDataString = "";
				try {
					imageInFile = new FileInputStream(selectedFile);
					byte imageData[] = new byte[(int) selectedFile.length()];
					imageInFile.read(imageData);
					imageDataString = Base64.encodeBase64String(imageData);
					imageInFile.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//String selectedExtension = selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().length()-4);
				
				//File addPicLocation
				
				//Path profileDir = Paths.get(getAppDirPath() + "/model/accounts/profilePics"); 
				
				//Path profilePic = Paths.get(profileDir.toString()+ "/" + curUser.getUsername() + selectedExtension);
				//Path profilePic = Paths.get(profileDir.toString()+ "/" + curUser.getUsername() + ".png");
			    //String relativePicPath = "/profilePics/" + curUser.getUsername() + ".png";
			    
				/*if (!Files.exists(profilePic)) {
				    try {
				        Files.createFile(profilePic);
				    } catch (IOException ex) {
				        System.err.println(ex);
				    }
				}*/
				
				curUser.setProfilePic(imageDataString);
				database.update("Users", "profilePicFile = '" +imageDataString + "'", "userID = " + curUser.getId());
				FileChannel inputChannel = null;
				FileChannel outputChannel = null;
				try {
			       inputChannel = new FileInputStream(selectedFile).getChannel();
			       outputChannel = new FileOutputStream(getImageFile()).getChannel();
			       outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			       inputChannel.close();
			       outputChannel.close();
			    } 
				catch (IOException ex) {
			        System.err.println(ex);
			    } 
	
				Image image = new Image(getImageFile().toURI().toString(), 100.0, 100.0, true, true);
				profilePictureView.setImage(image);
				profilePictureView.resize(100, 100);
				profilePictureView.setSmooth(true);
				profilePictureView.setCache(true);
				
				//Path imgsPath = Paths.get();
				String currentDir = System.getProperty("user.dir");
				//String dbPath = this.getClass().getResource("model/accounts/profilePics").getPath();
				System.out.println("File selected at " + selectedFile.getAbsolutePath());
				System.out.println(""+currentDir + " " + getImageFile().getAbsolutePath().toString());
				//curUser.setProfilePic("Pic name");
			}
		});
		
		updateProfileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Update user profile here.");
				
				int showPasswordError = 0;
				String errorMsg = "";
				
				if (!(usernameField.getText().equals(curUser.getUsername()))) {
					if (currentPasswordField.getText().equals(curUser.getPassword())) {
					   curUser.setUsername(usernameField.getText());
					   profileUsernameLabel.setText(usernameField.getText());
					   database.update("Users", "orgUsername = '" +curUser.getUsername() + "'", "userID = " + curUser.getId());
					}
					else {
					   showPasswordError = 1;
					}
				}
				
				
				if(newPasswordField.getText().length() > 0 ) {
					if(confirmPasswordField.getText().length() == 0) {
						errorMsg = errorMsg + "- Please confirm the new password you wish to use.\n";
					}
					else if(!(confirmPasswordField.getText().equals(newPasswordField.getText()))) {
						errorMsg = errorMsg + "- Passwords do not match. New password and cofirm password fields must match.\n";
					}
					else if(!(currentPasswordField.getText().equals(curUser.getPassword()))) {
					   showPasswordError = 1;
					}
					else {
					   curUser.setPassword(newPasswordField.getText());
					   currentPasswordField.setText(curUser.getPassword());
					   newPasswordField.setText("");
					   confirmPasswordField.setText("");
					   database.update("Users", "password = '" +curUser.getPassword() + "'", "userID = " + curUser.getId());
					}
				}
				
				if (showPasswordError == 1) {
					errorMsg = errorMsg + "- The Password field is incorrect. Please enter a valid password.\n";
				}
				if (errorMsg.length() > 0) {
					showError(errorMsg);
				}
				
				
				
			}
		});
	}
	
	/**
	 * Display the given error on the screen
	 * 
	 * @param msg the message to show in the error.
	 */
	private void showError(String msg) {
		
		
		Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Update Profile errors");
    	alert.setHeaderText("Your changes can't be submited due to the following errors.");
    	alert.setContentText(msg);
    	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
    	alert.getButtonTypes().setAll(buttonTypeOk);
    	Optional<ButtonType> response = alert.showAndWait();
    	if (response.get() == buttonTypeOk){
    		alert.close();
    	}
	}
	
	/**
	 * Get the directory of this to access for relative file locations.
	 * @return the relative path of here
	 */
	private String getAppDirPath() {
		String className = getClass().getName();
		String resourceName = className.replace('.', '/') + ".class";
		ClassLoader classLoader = getClass().getClassLoader();
		if (classLoader==null) {
		   classLoader = ClassLoader.getSystemClassLoader();
		}
		URL url = classLoader.getResource(resourceName);
		File currentFile = new File(url.getPath());
		File appDirectory = currentFile.getParentFile().getParentFile().getParentFile();
		return appDirectory.getAbsolutePath();
	}
	
	/**
	 * Get the image that is the profile picture for the user
	 * @return the image
	 */
	private File getImageFile() {
		FileOutputStream imageOutFile;
		File picFile = null;
		try {
			imageOutFile = new FileOutputStream(curUser.getUsername()+".png");
			imageOutFile.write(curUser.getProfilePic());
			imageOutFile.close();
			picFile = new File(curUser.getUsername()+".png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return picFile;
	}
	
}