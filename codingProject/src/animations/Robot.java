package animations;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Robot extends ImageView {
	
	private int currPosition;			// current position on screen in x axis
	private int yPos;
	public Image image;
	public Robot(){
		image= new Image("/assets/PandaRobot.png");
	    currPosition = 100;
	    yPos = 300;
		this.setImage(image);
		this.setFitHeight(100);
		this.setFitWidth(100);
		this.setPreserveRatio(true);
		// set start location of zombie
		this.setLayoutX(currPosition);
		this.setLayoutY(yPos);
	}



}

