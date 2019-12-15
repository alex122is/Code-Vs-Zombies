package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.util.Duration;
import questionsToAsk.questionsAsked;
import application.Main;
import database.DatabaseController;
import database.User;
import animations.Zombie;
import animations.Poof;
import animations.Robot;

public class questionController implements Runnable {
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;    
	// have a zombie count to prevent too many on screen
    private int zombieCount = 0;
	    
	// time before new zombie appears in seconds
    private int waitTime = 6;
	    
	// time to check if zombie needs to be killed
	private int killTime = 450000000;
	
	@FXML
    private ImageView penguinBtn;
    @FXML
    private ImageView serverBtn;
	// hold all zombies
	private Queue<ImageView> q = new LinkedList<>();
	private Queue<Zombie> zomb = new LinkedList<>();

	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Label Quest;
    @FXML
    private Button ans1;
    @FXML
    private Button ans2;
    @FXML
    private Button ans3;
    @FXML
    private Button ans4;
    @FXML
    private Label points;
    @FXML
    private Label timer;
    @FXML
    private Label countDown;
    
    // how many questions player currently has right
    private int numRight = 0;

    @FXML
    private Label number;
    
    HashMap <String, String> ans = new HashMap();
    private User user;// =  new User("A","A","A",1,true);  uncomment to start to questions controller
    
    private DatabaseController database;

    questionsAsked myTrivia; 
    Main startGame = new Main();
    String question = "";
    ArrayList<String> answers;
    exampleController x;
    String answerrr = "";
    long elapsedMillis = 1;
    long secondsPassed = 25;
    
    // timer to spawn new zombies
    Timeline spawnTimer = new Timeline(
	    new KeyFrame(Duration.seconds(waitTime), e -> {
	    	
	    	int yPos = (int)(Math.random()*((250-150)+1))+150;
	    	
	    	Zombie zombie = new Zombie(550, yPos);
	    	anchorPane.getChildren().add(zombie);
	    	q.add(zombie);
	    	zomb.add(zombie);

	    })
	);
    // timer to kill zombies
	Timeline killTimer = new Timeline(
	    new KeyFrame(Duration.seconds(killTime), e -> {
	    	
	    	while(!q.isEmpty()) {
		    	ImageView zombieToKill = q.peek();
	    		anchorPane.getChildren().remove(zombieToKill);
	    		q.remove();
	    	}
	    })
	);
	Timeline countdown = new Timeline(//countdown to end game
		    new KeyFrame(Duration.seconds(1), e -> {
		    	--secondsPassed;
		    	if (secondsPassed<=0) {
		    		
		    		while(!q.isEmpty()) {  //remove all zombies
				    	ImageView zombieToKill = q.peek();
			    		anchorPane.getChildren().remove(zombieToKill);
			    		q.remove();
			    		zomb.remove();

		    		}
		    			spawnTimer.stop();  //stop all timers
		    			killTimer.stop();
		    			secondsPassed = 90000000;  // hacked, this will prevent our screen from returning to homecontrollerview.
			    	    FXMLLoader loader = new FXMLLoader();
				        loader.setLocation(getClass().getResource("/view/Homescreen.fxml"));
				
				    	try {
				    		// load in a different fxml file into the window
				    		AnchorPane formPane = loader.load();
				    		anchorPane.getChildren().setAll(formPane);
				
				    		} catch (IOException e1) {
				    			   e1.printStackTrace();
				    		}
				    	
				    	// call a method in another controller
				    	HomescreenController display = loader.getController();
				        display.passUser(user);
		    	}
		    	timer.setText(String.valueOf(secondsPassed));
		    })
		);
	
    @FXML
    void initialize() {

initializeQuestions();  //Declare and instance of the questions class and set the 
    	
    	Zombie zombie = new Zombie(550, 250); //initialize Zombies
    	anchorPane.getChildren().add(zombie);
    	q.add(zombie);
    	zomb.add(zombie);    	
    	Robot robot = new Robot();
    	ImageView imageView = new ImageView();   // the time our robot will be activated
        Timeline timeline = new Timeline(
        		 new KeyFrame(Duration.seconds(0), new KeyValue(imageView.imageProperty(), robot.image)),
                 new KeyFrame(Duration.seconds(1), new KeyValue(imageView.imageProperty(), null))
                 );
        Poof poof = new Poof(550, 250);
        ImageView poofImage = null;// = new ImageView();   // the time our robot will be activated
     
        
    	
    	spawnTimer.setCycleCount(Animation.INDEFINITE);//timers
        spawnTimer.play();
        
        killTimer.setCycleCount(Animation.INDEFINITE);
        killTimer.play();
        countdown.setCycleCount(Animation.INDEFINITE);
        countdown.play();
        
        
        
		ans1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {  //check for correct answers
					updatePointsAndLoadNew(0, imageView, timeline, poof);    		
		});
		ans2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			updatePointsAndLoadNew(1 , imageView, timeline ,  poof);    		
			
    				});
		ans3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			updatePointsAndLoadNew(2 , imageView, timeline, poof);    		
		});
		ans4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			
			updatePointsAndLoadNew(3 , imageView, timeline, poof);    		
         		
      
		});
		
		
		
		penguinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,event->{
    		Window owner = penguinBtn.getScene().getWindow();
    		String itemDescription = "Kill all zombies, and reset the timer!";
    		String str = String.format("%s You have %d penguins. Do you want to use it?", itemDescription,user.getItem01());
            Optional<ButtonType> result = AlertHelper.confirm( owner, "Confirmation", str);
            if(result.get() == ButtonType.OK) {
            	//used a penguin, reset the timer
            	if(user.getItem01() > 1) {
            		user.updateNumItem(1, false);            	
            		try {
            			database.updatePoints(user);					
            		} catch (IOException e) {		
            			System.out.println("error when trying to update points in ShopController");
            		}	    		
            		while(!q.isEmpty()) {
            			ImageView zombieToKill = q.peek();
            			anchorPane.getChildren().remove(zombieToKill);
            			q.remove();
            		}
            	}
            	else
            		AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "You do not have this item");
            }
            else{
            	// do nothing
            }
		});
		
		serverBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,event->{
    		Window owner = serverBtn.getScene().getWindow();
    		String itemDescription = "Kill all zombies, and reset the question!";
    		String str = String.format("%s You have %d servers. Do you want to use it?", itemDescription,user.getItem02());
            Optional<ButtonType> result = AlertHelper.confirm( owner, "Confirmation", str);
            if(result.get() == ButtonType.OK) {
            	// used a server, skip this question and reset the timer.
            	if(user.getItem02() > 0) {
            		user.updateNumItem(2, false);
            		try {
            			database.updatePoints(user);					
            		} catch (IOException e) {		
            			System.out.println("error when trying to update points in ShopController");
            		}
	    		
            		while(!q.isEmpty()) {
            			ImageView zombieToKill = q.peek();
            			anchorPane.getChildren().remove(zombieToKill);
            			q.remove();
            		}
            		reset();initializeQuestions();
            	}
            	else {
            		AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                    "You do not have this item");
            	}
            }
            else{
            	// do nothing
            }
		});
    }
	private void updatePointsAndLoadNew(int button, ImageView imageView, Timeline timeline,  Poof poof) {//checks for correct answers
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/questions.fxml"));

        // go to grade view  
        try {
        	String x = answers.get(button);
        	if(x == answerrr) {       //check for corrext answer
        		
        		// check if player got enough right
        		numRight++;
        		
        		if(numRight >= 5) {
        			
        			while(!q.isEmpty()) {  //remove all zombies
				    	ImageView zombieToKill = q.peek();
			    		anchorPane.getChildren().remove(zombieToKill);
			    		q.remove();
			    		zomb.remove();

		    		}
        			
	    			spawnTimer.stop();  //stop all timers
	    			killTimer.stop();
	    			secondsPassed = 90000000;  // hacked, this will prevent our screen from returning to homecontrollerview.
        			
        			// get fxml file as a url
            		FXMLLoader loader2 = new FXMLLoader();
            		loader2.setLocation(getClass().getResource("/view/homeScreen.fxml"));

            		try {

            		   // load in a different fxml file into the window
            		   AnchorPane formPane = loader2.load();
            		   anchorPane.getChildren().setAll(formPane);

            		} catch (IOException e) {
            		   e.printStackTrace();
            		}
            		
            		// call a method in another controller
			    	HomescreenController display = loader2.getController();
			        display.passUser(user);
        		}
        		else {
        		
	        		secondsPassed = secondsPassed +10;
	                callRobot(imageView, timeline, poof);
        		}
        	}
        	else
        		secondsPassed -= 5;
            // load in a different fxml file into the window
			database.updatePoints(user);
			number.setText(Integer.toString(user.getPoints()));	
			reset();
	    	initializeQuestions();

        } catch(IOException e) {
            e.printStackTrace();
            reset();
	    	initializeQuestions();
        }
		}

     void callRobot( ImageView imageView, Timeline timeline, Poof poof){  // Displays robot, deletes zombies, and give points to user
    	 user.addPoints(5);
    	 ImageView zombieToKill = q.peek(); //kill the first zombie
       	Zombie z = zomb.peek();
       	int xCor =  z.xCordinate();
       	int yCor = z.yCordinate();
       	poof.setXandY(xCor, yCor);
  		anchorPane.getChildren().add(poof);
      	
        timeline.play();
        timeline.setOnFinished(event ->anchorPane.getChildren().remove(poof) //get rid of the poof image once the panda timeline is done
);
    	anchorPane.getChildren().remove(imageView); //add robot to new 
 		anchorPane.getChildren().add(imageView);
 	
 		anchorPane.getChildren().remove(zombieToKill);
 		q.remove();
 		zomb.remove();
    }
    

	// called on when wanting to reset the questions
	void reset() {
		question= "";
		answers.clear();
	}
	void initializeQuestions() {   //resets our questions for our next wave.
		myTrivia =new questionsAsked(); 
    	myTrivia.roll_question(); 
        question = myTrivia.get_question(); //get questions
        answers = myTrivia.get_answers();  //get answers
        Collections.shuffle(answers);      // shuffle them and present to user
    	Quest.setText(question);
    	ans1.setText(answers.get(0));
    	ans2.setText(answers.get(1));
    	ans3.setText(answers.get(2));
    	ans4.setText(answers.get(3));
		database = new DatabaseController();
	    ans = myTrivia.asnwerss;
    	answerrr = ans.get(question); 
	}
    
    // called from another scene to pass user info to this scene
 	void passUser(User user) {
 		this.user = user;
		number.setText(Integer.toString(user.getPoints()));		

 	}
	@Override
	public void run() {
		try {
            Thread.sleep(5000);
            ImageView zombieToKill = q.peek();
    		anchorPane.getChildren().remove(zombieToKill);
    		q.remove();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }		
	}
    
}