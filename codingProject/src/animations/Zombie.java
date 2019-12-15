package animations;

import java.util.Timer;
import java.util.TimerTask;
import java.util.LinkedList;
import java.util.Collections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Zombie extends ImageView{
	
	private int speed = 5;				// walkspeed of zombie
	private int currPosition=0;		// current position on screen in x axis
	private int yPos=0;		// current position on screen in y axis
	private int health = 10;			// health of zombie
	private Image image;				// image used for zombie
	
	private Timer walkTimer;			// timer to control how often zombie walks
	
	public Zombie(int xPosition, int yPosition) {
		
		currPosition = xPosition;
		yPos = yPosition;
		
		// set the image of the zombie
		
		String imagePath = "/assets/zombie.png";
		String imagePath2 = "/assets/zombie3.png";
		String imagePath3 = "/assets/zombie4.png";
		
		
		LinkedList<String> paths = new LinkedList<String>();
		paths.add(imagePath);
		paths.add(imagePath2);
		paths.add(imagePath3);
		Collections.shuffle(paths);
		
		image = new Image(paths.get(0));
		this.setImage(image);     
		this.setFitHeight(100);
		this.setFitWidth(100);
		this.setPreserveRatio(true);
		
		// set start location of zombie   
		this.setLayoutX(currPosition);
		this.setLayoutY(yPos);
		
		// start walking
		walkTimer = new Timer();
		walkTimer.schedule(task, 1000, 1000);
	}
	public int xCordinate() {
		return this.currPosition;
	}
	public int yCordinate() {
		return this.yPos;
	}
	
	// makes zombie walk
	public void zombieWalk() {
		
		currPosition -= speed;
		this.setLayoutX(currPosition);
	}
	
	// zombie performs the task
	TimerTask task = new TimerTask() {
    	
    	public void run() {
    		
    		// zombie walks forward
    		zombieWalk();
    	}
    };
}
