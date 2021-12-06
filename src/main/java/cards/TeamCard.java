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
import models.Competition;
import models.Student;
import models.Team;
import pages.CompetitionController;
import utils.CompetitionsMemory;
import utils.Navigator;

import java.io.IOException;
import java.util.ArrayList;

public class TeamCard {

    private int index = -1;
    private CompetitionController currentController;
    private int competitionIndex = -1;
    private ArrayList<StudentCard> stdControllers = new ArrayList<>();
    private Team currentTeam;

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
        controller.fillContent(currentTeam, currentController, competitionIndex);
        controller.setHeader("Edit a Team");
    }

    // TODO: launch a confirmation dialog
    @FXML
    void deleteTeam(ActionEvent event) throws IOException {
        Competition competition = CompetitionsMemory.INSTANCE.getCompetition(competitionIndex);
       competition.teams.remove(index);
        if(index != competition.teams.size()){
            for (int i = index; i < competition.teams.size(); i++) {
                competition.teams.get(i).index -= competition.teams.get(i).index; // maintaining the indexing
            }
        }
        System.out.println(competition); // TODO: delete test logs
       currentController.fillContent(competition, currentController);
    }
    // passing the information for the card
    public void setContent(Team team, CompetitionController controller, int competitionIndex) throws IOException {
        // set content to labels
        if(team.rank == -1)
            rankLabel.setText("TBA");
        else
            rankLabel.setText(String.valueOf(team.rank));
        currentController = controller;
        this.competitionIndex = competitionIndex;
        currentTeam = team;
    index = team.index;
        for(int i = 0; i < team.students.size(); i++){
            if(team.students.get(i).name.length() != 0) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../student-card-view.fxml"));
                studentsContainer.getChildren().add((Node) fxmlLoader.load());
                StudentCard card = fxmlLoader.getController();
                stdControllers.add(card);
                card.fillContent(team.students.get(i));
            }

        }
    }





}
