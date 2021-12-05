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
    void confirmRanking(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // TODO: get the ranks inputted by the user in the RankingDialog
    public void fillContent(Competition currentCompetition) throws IOException {
        ArrayList<Team> teams = currentCompetition.getTeams(); // Get the teams of the current competition
        VBox vbox = new VBox(5); // A new VBox for the rankings
        for (Team team : teams) {
            System.out.println(team.toString());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../email-slot.fxml"));
            vbox.getChildren().add((Node) fxmlLoader.load()); // Add empty ranking cards to the VBox
            EmailSlot slot = fxmlLoader.getController();

            for (Student student : team.students) { // Add each student card to the student container VBox
                System.out.println(student.toString());
                FXMLLoader studentNode = new FXMLLoader(getClass().getResource("../student-card-view.fxml"));
                slot.getStudentContainer().getChildren().add(studentNode.load()); // Add the student card to the student container
            }
        }
        ranksContainer.setContent(vbox);
    }

}
