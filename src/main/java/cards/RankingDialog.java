package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Competition;
import models.Student;
import models.Team;
import pages.CompetitionController;
import utils.Navigator;

import java.io.IOException;
import java.util.ArrayList;

public class RankingDialog {

    // used to fetch data when the element is displayed
    private Competition currentCompetition;
    private ArrayList<RankingSlot> controllers = new ArrayList<>();
    private CompetitionController compController;

    @FXML
    public void initialize(){

    }

    @FXML
    private Button cancelRankings;

    @FXML
    private Button confirmRankings;

    @FXML
    private ScrollPane ranksContainer;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmRanking(ActionEvent event) throws IOException {
        for(RankingSlot slot: controllers){
            //TODO: validation
            slot.cardTeam.rank = Integer.parseInt(slot.retreiveRank());
        }
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
        EmailDialog controller = Navigator.<EmailDialog>nextDialog("email", "Email a team");
        controller.fillContent(currentCompetition);
        compController.fillContent(currentCompetition, compController);
    }

    @FXML
    private TextField rankingInput;

    @FXML
    private VBox studentContainer;


    public void fillContent(Competition competition, CompetitionController compController) throws IOException {
        currentCompetition = competition;
        this.compController = compController;
        ArrayList<Team> teams = competition.teams; // Get the teams of the current competition
        for (Team team : teams) {
            System.out.println(team.toString());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ranking-slot.fxml"));
            studentContainer.getChildren().add((Node) fxmlLoader.load()); // Add empty ranking cards to the VBox
            RankingSlot slot = fxmlLoader.getController();
            controllers.add(slot);
            slot.fillContent(team);

        }


    }

}
