package cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Student;

import java.util.HashMap;

public class StudentCard {

    // used to fetch data when the element is displayed

    private int index;
    @FXML
    public void initialize(){

    }

    // VIEW
    @FXML
    private Label major;

    @FXML
    private Label idNum;

    @FXML
    private Label name;

    // EDIT & ADD

    @FXML
    private TextField majorInput;
    @FXML
    private TextField idInput;

    @FXML
    private TextField nameInput;

    // for editing cards
    public void fillEditableContent(Student student){
        index = student.index;
        majorInput.setText(student.major);
        idInput.setText(student.id);
        nameInput.setText(student.name);
    }

    // for view cards
    public void fillContent(Student student){
        index = student.index;
        major.setText(student.major);
        idNum.setText(student.id);
        name.setText(student.name);
    }

    public Student retreive(){
        // TODO: validating students' info
        Student student = new Student(index);
        student.name = nameInput.getText();
        student.id = idInput.getText();
        student.major = majorInput.getText();
        return student;
    }

}



