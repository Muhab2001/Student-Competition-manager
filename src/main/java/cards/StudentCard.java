package cards;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import models.Student;

import java.io.IOException;
import java.util.HashMap;

/**
 * controller class for student cards for view/editing
 */
public class StudentCard {

    // used to fetch data when the element is displayed

    private int index;
    private TeamDialog currentController;

    @FXML
    public void initialize(){

    }

    @FXML
    private HBox stCard;

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


    /**
     * event listener for enter key to confirm team mutation
     * @param event
     * @throws IOException
     */
    @FXML
    void enterMutate(KeyEvent event) throws IOException {

        if (event.getCode().equals(KeyCode.ENTER)){
            currentController.enterMutateTeams(event);
//            ((TextField) event.getSource()).setFocusTraversable(false);
        }
    }

    /**
     * populating content for student card (editable fxml card)
     * @param student target student
     * @param dialog running team dialog
     */
    public void fillEditableContent(Student student, TeamDialog dialog){
        stCard.setId("stcard-" + student.index);

        currentController = dialog;
        index = student.index;
        majorInput.setText(student.major);
        idInput.setText(student.id);
        nameInput.setText(student.name);
    }

    /**
     * providing target index, and dialog controller (editable empty fxml card)
     * @param dialog running team dialog
     * @param index target student  index
     */
    public void fillEditableEmptyContent(TeamDialog dialog, int index){
        stCard.setId("stcard-" + index);
        currentController = dialog;
        this.index = index;
    }

    /**
     * populating student card (view-only fxml file)
     * @param student
     */
    public void fillContent(Student student){

        stCard.setId("stcard-" + student.index);
        name.setId("stname-" + student.name.replaceAll("\\s+", ""));
        System.out.println(student.name.replace("\\s+", "")); // TODO: delete test log
        index = student.index;
        major.setText(student.major);
        idNum.setText(student.id);
        name.setText(student.name);
    }

    /**
     * getting information from editable student card
     * @return student object formed from input data
     */
    public Student retreive(){
        Student student = new Student(index);
        student.name = nameInput.getText().strip();
        student.id = idInput.getText().strip();
        student.major = majorInput.getText().strip();
        return student;
    }

    /**
     * utility method for erroneous fields
     */
    public void flagError(){
    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    majorInput.pseudoClassStateChanged(errorClass, true);
    nameInput.pseudoClassStateChanged(errorClass, true);
    idInput.pseudoClassStateChanged(errorClass, true);
    }
    /**
     * utility method for erroneous fields
     */
    public void clearError(){
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        majorInput.pseudoClassStateChanged(errorClass, false);
        nameInput.pseudoClassStateChanged(errorClass, false);
        idInput.pseudoClassStateChanged(errorClass, false);
    }

}



