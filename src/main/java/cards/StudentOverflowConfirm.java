package cards;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Competition;
import models.Team;
import pages.CompetitionController;
import utils.Confirmable;
import java.io.IOException;

/**
 * contorller for student overflow warning dialog
 */
public class StudentOverflowConfirm implements Confirmable {

    private Competition competition;
    private CompetitionController compController;
    private CompetitionDialog dialogController;
    private int deletedStudents;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label errContent;

    @FXML
    private Label errHeader;

    @FXML
    private Button proceedBtn;

    /**
     * aborting extra student deletion process
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) {
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * proceeding with deleting extra students
     * @param event
     * @throws IOException fxml file corruption
     */
    @FXML
    void proceed(ActionEvent event) throws IOException {
        onConfirm(event);
        dialogController.submitEdited(competition.teamSize);
        compController.fillContent(competition, compController, true);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    /**
     * providing the dialog with number of students to be dleetedm and needed controller to perform deletion
     * @param stDeleted number of studnets to be deleted
     * @param competition current competition
     * @param controller running competition controller
     * @param dialog instance of competition dialog to close when needed
     */
    public void fillContent(int stDeleted, Competition competition, CompetitionController controller, CompetitionDialog dialog){
        proceedBtn.setId("overflow-confirm");
    this.competition = competition;
    this.dialogController = dialog;
    this.compController = controller;
    deletedStudents = stDeleted;

    }

    @Override
    public void onConfirm(Event event) {
        for(Team team: competition.teams){
            for(int i = 0; i < deletedStudents; i++){
                team.students.remove(team.students.size() - 1);
            }
        }
    }
}
