package view.testCreation;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * AdvancedGeneratorPage2UI view class
 * 
 * @author Clay
 */

public class AdvancedGeneratorPage2UI extends Pane implements Initializable {
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //@FXML // fx:id="advancedGeneratorPage2Pane"
    //private Pane advancedGeneratorPage2Pane; // Value injected by FXMLLoader

    @FXML // fx:id="requiredFieldsLabel"
    private Label requiredFieldsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nameFieldLabel"
    private Label nameFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nameField"
    private TextField nameField; // Value injected by FXMLLoader

    @FXML // fx:id="classFieldLabel"
    private Label classFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="classField"
    private TextField classField; // Value injected by FXMLLoader

    @FXML // fx:id="tagsFieldLabel"
    private Label tagsFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="tagsField"
    private TextArea tagsField; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionFieldLabel"
    private Label descriptionFieldLabel; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionField"
    private TextArea descriptionField; // Value injected by FXMLLoader

    /**
     * Constructor for AdvancedGeneratorPage2UI
     */
    public AdvancedGeneratorPage2UI() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdvancedGeneratorPage2.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            // handle exception
        	e.printStackTrace();
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
        }
    }
    
    /**
     * Initialize from fxml. 
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //assert advancedGeneratorPage2Pane != null : "fx:id=\"advancedGeneratorPage2Pane\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert requiredFieldsLabel != null : "fx:id=\"requiredFieldsLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert nameFieldLabel != null : "fx:id=\"nameFieldLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert classFieldLabel != null : "fx:id=\"classFieldLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert classField != null : "fx:id=\"classField\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert tagsFieldLabel != null : "fx:id=\"tagsFieldLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert tagsField != null : "fx:id=\"tagsField\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert descriptionFieldLabel != null : "fx:id=\"descriptionFieldLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";

    }

    /**
     * Initialize from fxml. 
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//assert advancedGeneratorPage2Pane != null : "fx:id=\"advancedGeneratorPage2Pane\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert requiredFieldsLabel != null : "fx:id=\"requiredFieldsLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert nameFieldLabel != null : "fx:id=\"nameFieldLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert classFieldLabel != null : "fx:id=\"classFieldLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert classField != null : "fx:id=\"classField\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert tagsFieldLabel != null : "fx:id=\"tagsFieldLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert tagsField != null : "fx:id=\"tagsField\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert descriptionFieldLabel != null : "fx:id=\"descriptionFieldLabel\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'AdvancedGeneratorPage2.fxml'.";
        
        
	}

}
