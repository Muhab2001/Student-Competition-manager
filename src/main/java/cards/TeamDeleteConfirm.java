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

/**
 * controller for team dletion confirmation dialog
 */
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

    /**
     * listener to abort team deletion
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * listener to proceed with team deletion
     * @param event
     * @throws IOException fxml file corruption
     */
    @FXML
    void proceed(ActionEvent event) throws IOException {
        onConfirm(event);
        compController.fillContent(competition, compController, true);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * provide the dialog with target team index, and needed controllers to perform deletion
     * @param deletedIndex target team index
     * @param competition current competition
     * @param controller running competition controller
     */
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
