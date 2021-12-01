package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import models.Student;
import utils.Navigator;

import java.io.IOException;
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
    void editTeam(ActionEvent event) throws IOException {
        TeamDialog controller =
                Navigator.<TeamDialog>nextDialog("team", "Edit a Team");
        controller.fillContent();
    }
    // passing the information for the card
    public void setContent() throws IOException {
        // set content to labels
        // TODO: replace with a dynamic population according to team number
        for(int i = 0; i < 3; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../student-card-view.fxml"));
            studentsContainer.getChildren().add((Node) fxmlLoader.load());

        }
    }





}
