package controller;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import database.DatabaseController;
import database.User;
import controller.HomescreenController;

public class LoginController {

	@FXML
    private AnchorPane anchorPane;
	@FXML
	private Stage stage;
	@FXML
	private Parent registerScene;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    @FXML
    private Button registerButton;
    
    private DatabaseController database;
    //private User user;
    @FXML
    void initialize() {
    	
    	// when user presses the button
    	submitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		Window owner = submitButton.getScene().getWindow();
    		
    		if(nameField.getText().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                        "Please enter your username");
                return;
            }

            if(passwordField.getText().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                        "Please enter a password");
                return;
            }


            database = new DatabaseController();
            
            String username= nameField.getText();
            String password= passwordField.getText();
            try {
                User user = database.findUser(username, password);

                if(user.getStatus()) {

                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Successful!", 
                            "Welcome " + user.getFirstName());
                    
                    user = database.getLessonData(user);
                    
                    FXMLLoader loader = new FXMLLoader();
            		loader.setLocation(getClass().getResource("/view/homeScreen.fxml"));
            		
            		
            		try {
            			// load in a different fxml file into the window
            			AnchorPane formPane = loader.load();
            			anchorPane.getChildren().setAll(formPane);
                       	//	c.setUser(user);

            		   } catch (IOException e) {
            			   e.printStackTrace();
            		   }
            		
            		// call a method in another controller
                    HomescreenController display = loader.getController();
                    display.passUser(user);
                }

                else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                            "Wrong password");
                   System.out.println("entered wrong username or password");
                   
                }
            } catch (IOException e) {

                // TODO Auto-generated catch block

                e.printStackTrace();

            }

            //TODO access to database to login
        });
    	
    	
    	// when user presses register button
    	registerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		// get fxml file as a url
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("/view/register.fxml"));

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