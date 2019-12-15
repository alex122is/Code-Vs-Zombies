package database;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;

public class DatabaseController {
	
	// initiate the files
	private File userData = new File("src/data/Users.txt");
	private File lessonData = new File("src/data/lessonData.txt");
	
	// create a new user and return that user
	public User createUser(String username, String password, String firstName, String isTeacher) throws IOException{
		
		// create the user
		User user = new User(username, password, firstName, 0, true,0,0, isTeacher);
		
		// create the new line to be added to the file
		String userCredentials = username + ":" + password + ":" + firstName + ":500" + ":0" + ":0" + ":" + isTeacher;
	    
		// use BufferedWriter to write line to file
	    BufferedWriter bw = new BufferedWriter(new FileWriter(userData, true)); 
	    bw.write(userCredentials);
	    bw.newLine();
	    bw.close();
	    
	    // create the data for lessons
 		String userCredentials2 = username + ":" + firstName + ":0" + ":0" + ":0" + ":0" + ":" + isTeacher;
 	    
 		// use BufferedWriter to write line to file
 	    BufferedWriter bw2 = new BufferedWriter(new FileWriter(lessonData, true)); 
 	    bw2.write(userCredentials2);
 	    bw2.newLine();
 	    bw2.close();
		
		return user;
	}
	
	// checks to see if user name is already being used
	public boolean usernameExists(String username) throws IOException{
		
		Scanner input = new Scanner(userData);
		boolean exists = false;
		
		// while there is text in file
		while(input.hasNextLine()) {
			
			String [] br = input.nextLine().split(":");
			
			// if user name is found in file
			if(username.equals(br[0])) {
				exists = true;
				break;
			}
		}
		
		// close the scanner
		input.close();
		
		return exists;
	}
	
	// check if user exists so they can login
	public User findUser(String username, String password) throws IOException{
		
		Scanner input = new Scanner(userData);
		User user = new User();
		
		// while there is text in file
		while(input.hasNextLine()) {
			
			String [] br = input.nextLine().split(":");
			
			// if user name and password match one entry in file
			if(username.equals(br[0]) && password.equals(br[1])) {
				user.setUserName(username);
				user.setPassword(password);
				user.setFirstName(br[2]);
				user.setPoints(Integer.parseInt(br[3]));
				user.setStatus(true);
				user.setItem01(Integer.parseInt(br[4]));
				user.setItem02(Integer.parseInt(br[5]));
				user.setIsTeacher(br[6]);
				break;
			}
		}
		
		// close the scanner
		input.close();
		
		return user;
	}
	
	public User getLessonData(User user) throws IOException{
		
		Scanner input = new Scanner(lessonData);
		
		// while there is text in file
		while(input.hasNextLine()) {
			
			String [] br = input.nextLine().split(":");
			
			// if user name and password match one entry in file
			if(user.getUserName().equals(br[0])) {
				user.completeLesson(0, br[2]);
				user.completeLesson(1, br[3]);
				user.completeLesson(2, br[4]);
				user.completeLesson(3, br[5]);
				break;
			}
		}
		
		// close the scanner
		input.close();
		
		return user;
	}
	
	// update the points of the user
	public void updatePoints(User user) throws IOException{
		
		File tmp = new File("src/data/tmp.txt");
		
		Scanner input = new Scanner(userData);
		
		// use BufferedWriter to write line to file
	    BufferedWriter bw = new BufferedWriter(new FileWriter(tmp, true));

		while(input.hasNextLine()) {
		    
			String [] br = input.nextLine().split(":");
			
			// if user name and password match one entry in file
			if(user.getUserName().equals(br[0])) {
				continue;
			}
			
		    bw.write(br[0] + ":" + br[1] + ":" + br[2] + ":" + br[3]+":"+br[4]+":"+br[5]+":"+br[6]);
		    bw.newLine();
		}
		
		bw.write(user.getUserName() + ":" + user.getPassword() + ":" + user.getFirstName() + ":" 
				+ Integer.toString(user.getPoints()) +":"+ Integer.toString(user.getItem01())+":"+Integer.toString(user.getItem02())
				+ ":" + user.getIsTeacher());
		
	    bw.newLine();
	    
	    File updatedFile = new File("src/data/tmp.txt");
	    File toReplace = new File("src/data/Users.txt");
	    
		bw.close();
		input.close();
		
		// replace old file with new one with updated info
		toReplace.delete();
		updatedFile.renameTo(toReplace);
	}
	
	// update lessons taken
	public void updateLesson(User user) throws IOException {
		
		File tmp = new File("src/data/tmp.txt");
		
		Scanner input = new Scanner(lessonData);
		
		// use BufferedWriter to write line to file
	    BufferedWriter bw = new BufferedWriter(new FileWriter(tmp, true));

		while(input.hasNextLine()) {
		    
			String [] br = input.nextLine().split(":");
			
			// if user name and password match one entry in file
			if(user.getUserName().equals(br[0])) {
				continue;
			}
			
		    bw.write(br[0] + ":" + br[1] + ":" + br[2] + ":" + br[3]+":"+br[4]+":"+br[5]+":"+br[6]);
		    bw.newLine();
		}
		
		bw.write(user.getUserName() + ":" + user.getFirstName() + ":" + user.checkLessonComplete(0) +":"
		+ user.checkLessonComplete(1)+ ":" + user.checkLessonComplete(2) + ":" + user.checkLessonComplete(3)
		+ ":" + user.getIsTeacher());
		
	    bw.newLine();
	    
	    File updatedFile = new File("src/data/tmp.txt");
	    File toReplace = new File("src/data/lessonData.txt");
	    
		bw.close();
		input.close();
		
		// replace old file with new one with updated info
		toReplace.delete();
		updatedFile.renameTo(toReplace);
	}
}