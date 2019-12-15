package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import database.DatabaseController;
import database.User;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;


public class LessonController {
	
	@FXML
    private AnchorPane anchorPane;
	
    @FXML
    private TextArea lessonTxt;
    
    @FXML
    private Label lessonTitle;
    
    @FXML
    private Button btnNext;
    
    private User user;
    private int lessonCleared;
    private DatabaseController database;
    
    // list which will hold all the parts of the lesson
    ArrayList<String> lessonParts = new ArrayList<String>();
    
    // will be used to iterate through list of lesson parts
    private int lesson = 0;
    
    private File myFile;
    
    @FXML
    void initialize() {
    	
    	// keep text area read only
    	lessonTxt.setFocusTraversable(false);
    	
    	// when user presses the next button
    	btnNext.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		// go to the next part of the lesson
    		nextLesson();
    		lesson++;
    		
    		// start text area at the top
    		lessonTxt.positionCaret(0);
        });
    }
    
    // function that transitions the next part of the lesson
    public void nextLesson() {
    	
    	// only display if there are parts left
    	if(lesson < lessonParts.size()) {
    		
    		// check if there is a quiz at the end of lesson
    		if(lessonParts.get(lesson).compareTo("Quiz Time!\n") == 0) {
    			
    			// set title
    			lessonTitle.setText(lessonParts.get(lesson));
    			lesson++;
    		}
    		
    		// clear the screen and display next block of text
	    	lessonTxt.clear();
	    	lessonTxt.appendText(lessonParts.get(lesson));
    	}
    	else {
    		
    		// get fxml file as a url
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/lessonPick.fxml"));
            
            // update lesson as complete
            if(user.checkLessonComplete(lessonCleared).equals("0"))
            	user.completeLesson(lessonCleared, "100");
            
            database = new DatabaseController();

            // go to grade view
            try {

                // load in a different fxml file into the window
                AnchorPane formPane = loader.load();
                anchorPane.getChildren().setAll(formPane);
                
                database.updateLesson(user);

            } catch(IOException e) {
                e.printStackTrace();
            }
            
            // call a method in another controller
            LessonPickController display = loader.getController();
            display.passUser(user);
    	}
    }
    
    // get list of parts ready
    public void setUpParts() throws IOException{
    	
    	// get lesson content from file
		Scanner input = new Scanner(myFile);
		String parag = "";
		
		// go through file
		while(input.hasNextLine()) {
			
			String s = input.nextLine();
			
			// if encountering special symbol, add empty line to paragraph
			if(s.compareTo("**") == 0)
				parag += "\n";
			
			// if it is a sentence, add to string
			else if(s.trim().length() != 0)
		        parag += s + "\n";
		    
		    // else it is a paragraph, add to list
		    else {
		        lessonParts.add(parag);
		        parag = "";
		    }
		}
		
		input.close();
    }
    
    // should be called from another screen, user picked a lesson
    public void setLesson(String lessonName, User user, int lessonCleared) {
    	
    	this.user = user;
    	this.lessonCleared = lessonCleared;
    	
    	// set the file name for the lesson the user picked
    	myFile = new File("Lessons/" + lessonName);
    	
    	// initial lesson setup
    	try {
			setUpParts();
			
			// set title of lesson
			lessonTitle.setText(lessonParts.get(lesson));
			
			lesson++;
			
			// display first part of lesson
			lessonTxt.appendText(lessonParts.get(lesson));
			lesson++;
			
			// start text area at the top
			lessonTxt.positionCaret(0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
