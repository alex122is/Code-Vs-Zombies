package controller;

import java.io.IOException;

import animations.Shaker;
import database.DatabaseController;
import database.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ShopController {

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private ImageView goBackBtn;
    
    @FXML
    private ImageView penguinBtn;

    @FXML
    private ImageView serverBtn;
    
    @FXML
    private Label pointsLabel;
    
    private User user;
    private DatabaseController database;
    @FXML
    private Label numServer;
    @FXML
    private Label numPenguin;
    @FXML
    void initialize() {
        
    	// when user presses the penguin item
    	penguinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		// check if user has enough points
    		if(user.getPoints() >= 100) {
    			
    			// create database instance
        		database = new DatabaseController();
        		
        		// subtract points
        		user.subPoints(100);
        		user.updateNumItem(1,true);
	    		// update user points
	    		try {
					database.updatePoints(user);
					//database.updateInventory(user);
			    	numPenguin.setText("You have: "+Integer.toString(user.getItem01()));
					pointsLabel.setText(Integer.toString(user.getPoints()));					
				} catch (IOException e) {
					
					System.out.println("error when trying to update points in ShopController");
				}
    		}
    		
    		// not enough points
    		else {
    			
    			// shake button
                Shaker submitShaker = new Shaker(penguinBtn);
                submitShaker.shake();
    		}
        });
    	
    	// when user presses the server item
    	serverBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    		
    		// check if user has enough points
    		if(user.getPoints() >= 10000) {
    			
    			// create database instance
        		database = new DatabaseController();
        		// subtract points
        		user.subPoints(10000);
				user.updateNumItem(2,true);
	    		// update user points
	    		try {
					database.updatePoints(user);
					//database.updateInventory(user);
			    	numServer.setText("You have: "+Integer.toString(user.getItem02()));
					pointsLabel.setText(Integer.toString(user.getPoints()));

				} catch (IOException e) {
					
					System.out.println("error when trying to update points in ShopController");
				}
    		}
    		
    		// not enough points
    		else {
    			
    			// shake button
                Shaker submitShaker = new Shaker(serverBtn);
                submitShaker.shake();
    		}
    	});
    	
    	// when user presses the back button
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
    }
    
    // called from another scene to pass user info to this scene
    void passUser(User user) {
    	this.user = user;
    	
    	// get points the user has
    	pointsLabel.setText(Integer.toString(user.getPoints()));
    	numServer.setText("You have: "+Integer.toString(user.getItem02()));
    	numPenguin.setText("You have: "+Integer.toString(user.getItem01()));
    }
}