package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import database.RosterReport;
import database.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

public class TeacherController {
	
	private User user;
	
	@FXML
    private AnchorPane anchorPane;
	
	@FXML
    private AnchorPane rosterInfo;
	
	@FXML
    private Button assignBtn;
	
	@FXML
    private AnchorPane messWindow;

    @FXML
    private TextArea textArea;

    @FXML
    private Button submitBtn;
    
    @FXML
    private ImageView goBackBtn;

	@FXML
    private TableView<RosterReport> table;

    @FXML
    private TableColumn<RosterReport, String> studentCol;

    @FXML
    private TableColumn<RosterReport, Float> ifCol;

    @FXML
    private TableColumn<RosterReport, Float> loopCol;

    @FXML
    private TableColumn<RosterReport, Float> classCol;

    @FXML
    private TableColumn<RosterReport, Float> arrayCol;
    
    private ObservableList<RosterReport> list = FXCollections.observableArrayList();
	
	@FXML
    void initialize() {
		
		messWindow.setDisable(true);
		messWindow.setVisible(false);
		
		// show text box for teacher to send message to all
		assignBtn.setOnAction(event->{
	        
			messWindow.setDisable(false);
			messWindow.setVisible(true);
		});
		
		// remove textbox and submit new message
		submitBtn.setOnAction(event->{
	        
			messWindow.setDisable(true);
			messWindow.setVisible(false);
		});
		
		// if pressing the go back button
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
    	
		File studentData = new File("src/data/lessonData.txt");
		
		// fill in the table
		Scanner input;
		try {
			input = new Scanner(studentData);
			
			// while there is text in file
			while(input.hasNextLine()) {
				
				String [] br = input.nextLine().split(":");
				
				// only get student data, not teacher
				if(!br[6].equals("yes")) {
					
					String name = br[1];
					float less1 = Integer.parseInt(br[2]);
					float less2 = Integer.parseInt(br[3]);
					float less3 = Integer.parseInt(br[4]);
					float less4 = Integer.parseInt(br[5]);
					
					RosterReport report = new RosterReport(name, less1, less2, less3, less4);
					list.add(report);
				}
				
			}
			
			// close the scanner
			input.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// show the report to the teacher
        studentCol.setCellValueFactory(new PropertyValueFactory<RosterReport, String>("studentName"));
        ifCol.setCellValueFactory(new PropertyValueFactory<RosterReport, Float>("lesson1"));
        loopCol.setCellValueFactory(new PropertyValueFactory<RosterReport, Float>("lesson2"));
        arrayCol.setCellValueFactory(new PropertyValueFactory<RosterReport, Float>("lesson3"));
        classCol.setCellValueFactory(new PropertyValueFactory<RosterReport, Float>("lesson4"));
        table.setItems(list);
    }
    
	// called from another scene to pass user info to this scene
	void passUser(User user) {
		this.user = user;
	}
}
