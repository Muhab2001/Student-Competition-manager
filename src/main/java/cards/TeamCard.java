package cards;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Competition;
import models.Team;
import pages.CompetitionController;
import utils.CompetitionsMemory;
import utils.Navigator;

import java.io.IOException;
import java.util.ArrayList;

/**
 * controller for team cards
 */
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

    /**
     * attaching mouse hovering to fade transition effect
     */
    @FXML
    public void initialize(){
       editIn = new FadeTransition(Duration.millis(200), editTeamBtn);//used to fetch data when the element is displayed
         deleteIn = new FadeTransition(Duration.millis(200), deleteTeamBtn);//used to fetch data when the element is displayed
          editOut = new FadeTransition(Duration.millis(200), editTeamBtn);//used to fetch data when the element is displayed
          deleteOut = new FadeTransition(Duration.millis(200), deleteTeamBtn);//used to fetch data when the element is displayed
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
        deleteTeamBtn.setOpacity(0);
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
    private Button deleteTeamBtn;

    @FXML
    private Label rankLabel;

    @FXML
    private VBox studentsContainer;

    /**
     * listener to fire team editing dialog
     * @param event
     * @throws IOException fxml file corruption
     */
    @FXML
    void editTeam(ActionEvent event) throws IOException {
        TeamDialog controller =
                Navigator.<TeamDialog>nextDialog("team", "Edit a Team");
        controller.fillContent(currentTeam, currentController, competitionIndex, controller);
        controller.setHeader("Edit a Team");
        controller.addTopBar((Stage)((Node)event.getSource()).getScene().getWindow());
    }

    /**
     * listener to fire team deletion dialog
     * @param event
     * @throws IOException fxml file corruption
     */
    @FXML
    void deleteTeam(ActionEvent event) throws IOException {
        Competition competition = CompetitionsMemory.getCompetition(competitionIndex);
        TeamDeleteConfirm controller = Navigator.nextDialog("team-delete", "Confirm Team Deletion");
        controller.fillContent(currentTeam.index, competition, currentController);

    }

    /**
     * populating the card with team data
     * @param team target team
     * @param controller running competition controller
     * @param competitionIndex current competition index
     * @throws IOException fxml file corruption
     */
    public void fillContent(Team team, CompetitionController controller, int competitionIndex) throws IOException {
        // set content to labels
        rankLabel.setId("teamrank-" + team.index);
        deleteTeamBtn.setId("delete-" + rankLabel.getId());
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
