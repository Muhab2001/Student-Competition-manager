package cards;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.Student;
import utils.Navigator;

import java.io.IOException;
import java.util.ArrayList;

public class TeamCard {

     FadeTransition editIn ;
     FadeTransition deleteIn ;
     FadeTransition editOut ;
     FadeTransition deleteOut;

    @FXML
    public void initialize(){
       editIn = new FadeTransition(Duration.millis(200), editTeamBtn);//used to fetch data when the element is displayed
         deleteIn = new FadeTransition(Duration.millis(200), deleteBtn);//used to fetch data when the element is displayed
          editOut = new FadeTransition(Duration.millis(200), editTeamBtn);//used to fetch data when the element is displayed
          deleteOut = new FadeTransition(Duration.millis(200), deleteBtn);//used to fetch data when the element is displayed
        editIn.setFromValue(0);
        editIn.setToValue(1);
        deleteIn.setFromValue(0);
        deleteIn.setToValue(1);
        editOut.setFromValue(1);
        editOut.setToValue(0);
        editOut.setFromValue(1);
        deleteOut.setToValue(0);
        editOut.play();
        editTeamBtn.setOpacity(0);
        deleteBtn.setOpacity(0);
        cardContainer.setOnMouseExited(e-> {
            editOut.play();
            deleteOut.play();
        });
        cardContainer.setOnMouseEntered(e-> {
            editIn.play();
            deleteIn.play();
        });
    }
    @FXML
    private VBox cardContainer;

    @FXML
    private Button editTeamBtn;

    @FXML
    private Button deleteBtn;

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

    @FXML
    void deleteTeam(ActionEvent event) {

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
