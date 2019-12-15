package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import database.DatabaseController;
import database.User;

public class LessonPickController {

	@FXML
    private AnchorPane anchorPane;
	
	@FXML
    private AnchorPane sidePane;

    @FXML
    private Button lesson1Btn;

    @FXML
    private Button lesson2Btn;

    @FXML
    private ImageView goBackBtn;
    
    @FXML
    private Label beginLabel;
    
    @FXML
    private Label interLabel;

    @FXML
    private Label advLabel;
    
    private User user;
    
    @FXML
    void initialize() {
    	
    	// load in lesson 1
    	lesson1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
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
            display.setLesson("Lesson1.txt", user, 0);
    	});
    	
    	// load in lesson 2
    	lesson2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
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
            display.setLesson("Lesson2.txt", user, 1);
    	});
    	
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
    	interLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		// get fxml file as a url
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/lessonPick2.fxml"));

            // go to grade view
            try {

                // load in a different fxml file into the window
                AnchorPane formPane = loader.load();
                anchorPane.getChildren().setAll(formPane);

            } catch(IOException e) {
                e.printStackTrace();
            }
    		
    		// call a method in another controller
            LessonPick2Controller display = loader.getController();
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
    }
    
    // called from another scene to pass user info to this scene
    void passUser(User user) {
    	this.user = user;
    }
}