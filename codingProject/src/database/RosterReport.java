package database;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class RosterReport {

	private final SimpleStringProperty studentName;
    private final SimpleFloatProperty lesson1;
    private final SimpleFloatProperty lesson2;
    private final SimpleFloatProperty lesson3;
    private final SimpleFloatProperty lesson4;
    

    public RosterReport(String studentName, Float lesson1, Float lesson2, Float lesson3, Float lesson4) {

        super();
        this.studentName = new SimpleStringProperty(studentName);
        this.lesson1 = new SimpleFloatProperty(lesson1);
        this.lesson2 = new SimpleFloatProperty(lesson2);
        this.lesson3 = new SimpleFloatProperty(lesson3);
        this.lesson4 = new SimpleFloatProperty(lesson4);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public Float getLesson1() {
        return lesson1.get();
    }

    public Float getLesson2() {
        return lesson2.get();
    }

    public Float getLesson3() {
        return lesson3.get();
    }
    
    public Float getLesson4() {
        return lesson4.get();
    }
}
