package cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StudentCard {

    // used to fetch data when the element is displayed
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

    public void fillContent(){

    }

}



