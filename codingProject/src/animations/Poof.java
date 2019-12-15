package animations;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Poof extends ImageView {
	
	private int currPosition;			// current position on screen in x axis
	private int yPos;
	public Image image;
	
	public void setXandY(int x, int y) {
		this.currPosition =x;
		this.yPos =y+50;
		this.setImage(image);
		this.setFitHeight(60);
		this.setFitWidth(50);
		this.setPreserveRatio(true);
		this.setLayoutX(currPosition);
		this.setLayoutY(yPos);
	}
	
	public Poof(int x , int y){
		currPosition = x;
		yPos = y;
		image= new Image("/assets/poof.png");
	}



}

