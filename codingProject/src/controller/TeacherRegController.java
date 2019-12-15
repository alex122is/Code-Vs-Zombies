package controller;

import java.io.IOException;

import database.DatabaseController;
import database.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

public class TeacherRegController {
	
	@FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField nicknameField;

    @FXML
    private TextField masterField;

    @FXML
    private Button submitButton;

    @FXML
    private Button returnButton;
    
private DatabaseController database;
    
    @FXML
    void initialize() {
    	
    	// when user presses the button
    	submitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		Window owner = submitButton.getScene().getWindow();
    		
            if(nameField.getText().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                        "Please enter your name");
                return;
            }

            if(passwordField.getText().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                        "Please enter a password");
                return;
            }
            if(nicknameField.getText().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                        "enter a nickname");
                return;
            }
            
            // check that verification code was filled
            if(masterField.getText().isEmpty()) {
            	AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                        "enter the verification code");
                return;
            }
            
            // check that verification code is correct
            if(!masterField.getText().equals("A1010") ) {
            	AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                        "incorrect verification code entered");
                return;
            }
            
            database = new DatabaseController();
            
            String username= nameField.getText();
            String nick = nicknameField.getText();
            String password= passwordField.getText();
            
            try {
                
            	// check if username is already used
            	if(database.usernameExists(username)) {
            		
            		AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                            "That username already exists, try again");
                    return;
            	}
            	
            	// create new user
            	User user = database.createUser(username, password, nick, "yes");
            	user.setStatus(true);
            	
            	// go to next screen
                if(user.getStatus()) {

                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "account creation Successful!", 
                            "Welcome " + user.getFirstName());
                    
                    FXMLLoader loader = new FXMLLoader();
            		loader.setLocation(getClass().getResource("/view/homeScreen.fxml"));
            		
            		try {
            			// load in a different fxml file into the window
            			AnchorPane formPane = loader.load();
            			anchorPane.getChildren().setAll(formPane);

            		   } catch (IOException e) {
            			   e.printStackTrace();
            		   }
            		
            		// call a method in another controller
                    HomescreenController display = loader.getController();
                    display.passUser(user);
                }

            	} catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        	}
        });
    	
    	// when user presses cancel button
    	returnButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		// get fxml file as a url
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("/view/login.fxml"));

    		try {

    		   // load in a different fxml file into the window
    		   AnchorPane formPane = loader.load();
    		   anchorPane.getChildren().setAll(formPane);

    		} catch (IOException e) {
    		   e.printStackTrace();
    		}
    	});
    }
}
