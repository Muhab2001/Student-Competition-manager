package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.Student;

import java.util.ArrayList;

public class TeamCard {


    // used to fetch data when the element is displayed
    @FXML
    public void initialize(){

    }

    @FXML
    private Button editTeamBtn;

    @FXML
    private Label rankLabel;

    @FXML
    private VBox studentsContainer;

    @FXML
    void editTeam(ActionEvent event) {

    }
    // passing the information for the card
    public void setContent(String name, ArrayList<Student> students, String rank){
        // set content to labels
    }





}
