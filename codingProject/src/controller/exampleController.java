package controller;

import java.net.URL;
import java.util.ResourceBundle;

import animations.Zombie;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class exampleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;
    
    // have a zombie count to prevent too many on screen
    private int zombieCount = 0;
    
    // time before new zombie appears in seconds
    private int waitTime = 10;
    
    // time to check if zombie needs to be killed
    private int killTime = 45;
    
    // hold all zombies
    private Queue<ImageView> q = new LinkedList<>();

    @FXML
    void initialize() {
    	
    	Zombie zombie = new Zombie(550, 250);
    	anchorPane.getChildren().add(zombie);
    	q.add(zombie);
    	
    	spawnTimer.setCycleCount(Animation.INDEFINITE);
        spawnTimer.play();
        
        killTimer.setCycleCount(Animation.INDEFINITE);
        killTimer.play();
    }
    
    // timer to spawn new zombies
    Timeline spawnTimer = new Timeline(
	    new KeyFrame(Duration.seconds(waitTime), e -> {
	    	
	    	int yPos = (int)(Math.random()*((250-150)+1))+150;
	    	
	    	Zombie zombie = new Zombie(550, yPos);
	    	anchorPane.getChildren().add(zombie);
	    	q.add(zombie);
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
}