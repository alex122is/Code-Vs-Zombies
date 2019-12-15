package controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import database.DatabaseController;
import database.User;
import controller.LessonController;

public class HomescreenController {
		
	@FXML
	private questionController c2;
	
	private User user;
	@FXML
    private Button lessons;
	
	@FXML
    private Button shopBtn;
	
	@FXML
    private AnchorPane anchorPane;
	@FXML
	private Button other;
	
	@FXML
    private Button manageBtn;
	
	@FXML
    private ProgressBar progressBar;
	
	@FXML
    private ImageView notifyBtn;
	
	private double currentProgress = 0.0;
	
	// references the notification data
	private File notifyData = new File("src/data/notify.txt");
	
    @FXML
    void initialize() {
    	
    	// go to lessons
		manageBtn.setOnAction(event->{
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("/view/teacherScreen.fxml"));
	
	    	try {
	    		// load in a different fxml file into the window
	    		AnchorPane formPane = loader.load();
	    		anchorPane.getChildren().setAll(formPane);
	
	    		} catch (IOException e) {
	    			   e.printStackTrace();
	    		}
	    	
	    	// call a method in another controller
	        TeacherController display = loader.getController();
	        display.passUser(user);
		});
		
		// if pressing the notification button
    	notifyBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		Window owner = notifyBtn.getScene().getWindow();
    		String notificationString = "";
    		
    		try {
				notificationString = getNotification();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		// show notification to user
    		AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Your notifications!", 
                    notificationString);
            return;
    		
    	});
    	
    	// go to lessons
		lessons.setOnAction(event->{
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("/view/lessonPick.fxml"));
	
	    	try {
	    		// load in a different fxml file into the window
	    		AnchorPane formPane = loader.load();
	    		anchorPane.getChildren().setAll(formPane);
	
	    		} catch (IOException e) {
	    			   e.printStackTrace();
	    		}
	    	
	    	// call a method in another controller
	        LessonPickController display = loader.getController();
	        display.passUser(user);
		});
		

		
		//when the playGame is invoked
		other.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
	          FXMLLoader loader = new FXMLLoader();
	          loader.setLocation(getClass().getResource("/view/questions.fxml"));

	      	try {
	      		// load in a different fxml file into the window
	      		AnchorPane formPane = loader.load();
	      		anchorPane.getChildren().setAll(formPane);

	      		} catch (IOException e) {
	      			   e.printStackTrace();
	      		}
	      	
		      	// call a method in another controller
		        questionController display = loader.getController();
		        display.passUser(user);
	  		});
		
		// go to lessons
		shopBtn.setOnAction(event->{
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("/view/shop.fxml"));
	
	    	try {
	    		// load in a different fxml file into the window
	    		AnchorPane formPane = loader.load();
	    		anchorPane.getChildren().setAll(formPane);
	
	    		} catch (IOException e) {
	    			   e.printStackTrace();
	    		}
	    	
	    	// call a method in another controller
	        ShopController display = loader.getController();
	        display.passUser(user);
		});
	}

  //    

	
	//TODO: the homescreen should have more features.
    
    // called from another scene to pass user info to this scene
	void passUser(User user) {
		this.user = user;
		
		// disable manage button if not a teacher
		if(user.getIsTeacher().equals("no")) {
			
			manageBtn.setDisable(true);
			manageBtn.setVisible(false);
		}
		
		// display completion rate
		currentProgress = user.getCompletionRate();
		progressBar.setProgress(currentProgress);
		progressBar.setStyle("-fx-background-color: green;");
	}
	
	// notification data
	public String getNotification() throws IOException{
		
		String myString = "";
		
		Scanner input = new Scanner(notifyData);
		
		// while there is text in file
		while(input.hasNext()) {
			
			myString += input.nextLine();
		}
		
		// close the scanner
		input.close();
		
		return myString;
	}
}