package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Competition;
import models.Student;
import models.Team;

import java.io.IOException;
import java.util.ArrayList;

public class EmailDialog {

    private int emailCounter = 0;
    private EmailDialog currentController;
    private Competition currentCompetition;

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

    @FXML // TODO: changing the method name
    void confirmRanking(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private VBox studentContainer;

    public void fillContent(Competition competition, EmailDialog controller) throws IOException {
        ArrayList<Team> teams = competition.teams; // Get the teams of the current competition
        currentController = controller;
        currentCompetition = competition;
        for (Team team : teams) {
            System.out.println(team.toString());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../email-slott.fxml"));
            studentContainer.getChildren().add((Node) fxmlLoader.load()); // Add empty ranking cards to the VBox
            EmailSlot slot = fxmlLoader.getController();
            slot.fillContent(team, competition.name, currentController);

        }

    }

    public void incrementCounter(){
        emailCounter++;
        if(emailCounter == currentCompetition.teams.size())
            confirmRankings.setDisable(false);
    }

}
