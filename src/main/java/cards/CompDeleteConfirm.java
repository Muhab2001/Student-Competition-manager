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
import pages.MainController;
import utils.CompetitionsMemory;
import utils.Confirmable;
import utils.Navigator;

import java.io.IOException;

/**
 * controller for competition deletion confirmation dialog
 */
public class CompDeleteConfirm implements Confirmable {

    private Competition competition;
    private CompetitionController compController;
    private Stage stage;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label errContent;

    @FXML
    private Label errHeader;

    @FXML
    private Button proceedBtn;

    /**
     * event listener to abort competition deletion process
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * event listener to proceed in competition deletion
     * @param event
     * @throws IOException fxml file corruption
     */
    @FXML
    void proceed(ActionEvent event) throws IOException {
        onConfirm(event);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        MainController controller = Navigator.back("main", this.stage);
        controller.fillContent(CompetitionsMemory.CURRENT_USER.username, CompetitionsMemory.CURRENT_USER.email, controller);
    }

    /**
     * passing needed controllers and info
     * @param comeptition
     * @param controller
     */
    public void fillContent(Competition comeptition, CompetitionController controller, Stage stage){
        proceedBtn.setId("delete-confirm");
        this.competition = comeptition;
        this.compController = controller;
        this.stage = stage;
    }

    @Override
    public void onConfirm(Event event) throws IOException {
        CompetitionsMemory.deleteCompetition(competition.index);
        if(competition.index != CompetitionsMemory.competitions.size()){
            for(int i = competition.index; i < CompetitionsMemory.competitions.size(); i++){
                CompetitionsMemory.competitions.get(i).index -= CompetitionsMemory.competitions.get(i).index;
            }
        }

    }
}
