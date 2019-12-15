package database;

import java.util.ArrayList;
import java.util.Arrays;

public class User {
		
    private String userName;
    private String password;
    private String firstName;
    private int points = 0;
    private boolean loggedIn = false;
    private int item01;
    private int item02;
    private String isTeacher;
    private double completeRate = 0.0;
    
    private ArrayList<String> LessonsCompleted = new ArrayList<String>(
    		Arrays.asList("0", "0", "0", "0"));
    
    public User() {

    }

    public User(String userName, String password, String firstName, int points, boolean status,int item01,int item02, String isTeacher) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.points = points;
        this.loggedIn = status;
        this.item01 = item01;
        this.item02 = item02;
        this.isTeacher = isTeacher;
    }

    // setters and getters
    public void setUserName(String userName) {this.userName = userName;}
    public void setPassword(String password) {this.password = password;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setPoints(int points) {this.points = points;}
    public void addPoints(int points) {this.points += points;}
    public void subPoints(int points) {this.points -= points;}
    public void setStatus(boolean loggedIn) {this.loggedIn = loggedIn;}
    public void setItem01(int num) { this.item01 = num;}
    public void setItem02(int num) { this.item02 = num;}
    public String getUserName() {return userName;}
    public String getPassword() {return password;}
    public String getFirstName() {return firstName;}
    public int getPoints() {return points;}
    public boolean getStatus() {return loggedIn;}
    public int getItem01() {return this.item01;}
    public int getItem02() {return this.item02;}
    public void updateNumItem(int itemNumber , boolean isIncrement) {
    		switch(itemNumber) {
    			case 1:
    				if(isIncrement)
    					this.item01++;
    				else
    					this.item01--;
    				break;
    			case 2:
    				if(isIncrement)
    					this.item02++;
    				else
    					this.item02--;
    				break;    						
    			default:
    				System.out.println("unknown item");break;
    		}
    }
    
    // functions for checking if is teacher
    public void setIsTeacher(String isTeacher) {this.isTeacher = isTeacher;}
    public String getIsTeacher() {return isTeacher;}
    
    // functions to complete and get if a lesson is completed
    public void completeLesson(int lessonNumber, String val) {
    	LessonsCompleted.set(lessonNumber, val);
    	
    	if(completeRate < 1.0 && val.equals("100"))
    		completeRate += 0.25;
    }
    
    // get completionRate
    public double getCompletionRate() {return completeRate;}
    
    public String checkLessonComplete(int lessonNumber) {
    	return LessonsCompleted.get(lessonNumber);
    }
}
