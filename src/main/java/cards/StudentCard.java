package cards;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.Student;

import java.io.IOException;
import java.util.HashMap;

public class StudentCard {

    // used to fetch data when the element is displayed

    private int index;
    private TeamDialog currentController;

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

    @FXML
    void enterMutate(KeyEvent event) throws IOException {

        if (event.getCode().equals(KeyCode.ENTER)){
            currentController.enterMutateTeams(event);
//            ((TextField) event.getSource()).setFocusTraversable(false);
        }
    }

    // for editing cards
    public void fillEditableContent(Student student, TeamDialog dialog){
        currentController = dialog;
        index = student.index;
        majorInput.setText(student.major);
        idInput.setText(student.id);
        nameInput.setText(student.name);
    }

    public void fillEditableEmptyContent(TeamDialog dialog, int index){
        currentController = dialog;
        this.index = index;
    }

    // for view cards
    public void fillContent(Student student){
        index = student.index;
        major.setText(student.major);
        idNum.setText(student.id);
        name.setText(student.name);
    }

    public Student retreive(){
        Student student = new Student(index);
        student.name = nameInput.getText();
        student.id = idInput.getText();
        student.major = majorInput.getText();
        return student;
    }

    public void flagError(){
    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    majorInput.pseudoClassStateChanged(errorClass, true);
    nameInput.pseudoClassStateChanged(errorClass, true);
    idInput.pseudoClassStateChanged(errorClass, true);
    }

    public void clearError(){
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        majorInput.pseudoClassStateChanged(errorClass, false);
        nameInput.pseudoClassStateChanged(errorClass, false);
        idInput.pseudoClassStateChanged(errorClass, false);
    }

}



