package cards;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Competition;
import pages.CompetitionController;
import utils.Confirmable;

import java.io.IOException;

public class TeamDeleteConfirm implements Confirmable {

    private Competition competition;
    private CompetitionController compController;
    private CompetitionDialog dialogController;
    private int deletedIndex;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label errContent;

    @FXML
    private Label errHeader;

    @FXML
    private Button proceedBtn;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void proceed(ActionEvent event) throws IOException {
        onConfirm(event);
        compController.fillContent(competition, compController, true);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void fillContent(int deletedIndex, Competition competition, CompetitionController controller){
        proceedBtn.setId("delete-confirm");
        compController = controller;
        this.deletedIndex = deletedIndex;
        this.competition = competition;
    }



    @Override
    public void onConfirm(Event event) {
        competition.teams.remove(deletedIndex);
        if(deletedIndex != competition.teams.size()){
            for (int i = deletedIndex; i < competition.teams.size(); i++) {
                competition.teams.get(i).index -= competition.teams.get(i).index; // maintaining the indexing
            }
        }
    }
}
