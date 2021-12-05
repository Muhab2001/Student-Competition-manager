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
import utils.Navigator;

import java.io.IOException;
import java.util.ArrayList;

public class RankingDialog {

    // used to fetch data when the element is displayed

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
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
        EmailDialog controller = Navigator.<EmailDialog>nextDialog("email", "Email a team");
        controller.fillContent();
    }

    @FXML
    private TextField rankingInput;

    @FXML
    private VBox studentContainer;

    // TODO: DONE get all teams from the competition
    public void fillContent(Competition competition) throws IOException {
        ArrayList<Team> teams = competition.getTeams(); // Get the teams of the current competition
        VBox vbox = new VBox(5); // A new VBox for the rankings
        for (Team team : teams) {
            System.out.println(team.toString());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ranking-slot.fxml"));
            vbox.getChildren().add((Node) fxmlLoader.load()); // Add empty ranking cards to the VBox
            RankingSlot slot = fxmlLoader.getController();

            for (Student student : team.students) { // Add each student card to the student container VBox
                System.out.println(student.toString());
                FXMLLoader studentNode = new FXMLLoader(getClass().getResource("../student-card-view.fxml"));
                slot.getStudentContainer().getChildren().add(studentNode.load()); // Add the student card to the student container
            }
        }
        ranksContainer.setContent(vbox);
    }

}
