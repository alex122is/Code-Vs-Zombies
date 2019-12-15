package controller;

import java.io.IOException;

import database.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LessonPick2Controller {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button funcBtn;
    
    @FXML
    private Button arrayBtn;
    
    @FXML
    private AnchorPane sidePane;

    @FXML
    private Label beginLabel;

    @FXML
    private Label interLabel;

    @FXML
    private Label advLabel;

    @FXML
    private ImageView goBackBtn;
    
    private User user;
    
    @FXML
    void initialize() {
    	
    	// if pressing the go back button
    	goBackBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		// get fxml file as a url
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/homeScreen.fxml"));

            // go to grade view
            try {

                // load in a different fxml file into the window
                AnchorPane formPane = loader.load();
                anchorPane.getChildren().setAll(formPane);

            } catch(IOException e) {
                e.printStackTrace();
            }
    		
    		// call a method in another controller
            HomescreenController display = loader.getController();
            display.passUser(user);
    	});
    	
    	// pick intermediate lessons
    	beginLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		// get fxml file as a url
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/lessonPick.fxml"));

            // go to grade view
            try {

                // load in a different fxml file into the window
                AnchorPane formPane = loader.load();
                anchorPane.getChildren().setAll(formPane);

            } catch(IOException e) {
                e.printStackTrace();
            }
    		
    		// call a method in another controller
            LessonPickController display = loader.getController();
            display.passUser(user);
    	});
    	
    	// pick intermediate lessons
    	advLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		// get fxml file as a url
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/lessonPick3.fxml"));

            // go to grade view
            try {

                // load in a different fxml file into the window
                AnchorPane formPane = loader.load();
                anchorPane.getChildren().setAll(formPane);

            } catch(IOException e) {
                e.printStackTrace();
            }
    		
    		// call a method in another controller
            LessonPick3Controller display = loader.getController();
            display.passUser(user);
    	});
    	
    	// load in lesson
    	arrayBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		// get fxml file as a url
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/lesson.fxml"));

            // go to grade view
            try {

                // load in a different fxml file into the window
                AnchorPane formPane = loader.load();
                anchorPane.getChildren().setAll(formPane);

            } catch(IOException e) {
                e.printStackTrace();
            }
    		
    		// call a method in another controller
            LessonController display = loader.getController();
            display.setLesson("arrayLesson.txt", user, 2);
    	});
    }
    
    // called from another scene to pass user info to this scene
    void passUser(User user) {
    	this.user = user;
    }
}

