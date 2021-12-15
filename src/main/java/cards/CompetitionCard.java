package cards;

import javafx.scene.layout.VBox;
import models.Competition;
import pages.CompetitionController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import utils.Navigator;
import java.io.IOException;

/**
 * controller class for competition cards
 */
public class CompetitionCard {

    private int id = -1;

    private Competition currentCompetition;

    @FXML
    public VBox competitionId;

    @FXML
    private Label name;

    @FXML
    private Label status;

    @FXML
    private Label teamNum;

    @FXML
    private Label teamSize;

    /**
     * event listener to navigate to competition details page
     * @param event
     * @throws IOException
     */
    @FXML
    void openDetails(MouseEvent event) throws IOException {

        CompetitionController controller = Navigator.<CompetitionController>next("competition", event);
        controller.fillContent(currentCompetition, controller, false);

    }

    /**
     * populating the card with competition info
     * @param competition target competition
     */
    public void fillContent(Competition competition){
        // populating the card with the content
        competitionId.setId("comp-" + competition.index);
        name.setId("comp-" + competition.name);
        String status = competition.isOpen ? "Open" : "Closed";
        String style = competition.isOpen ? "-fx-font: 14px 'Segoe UI bold'; -fx-text-fill: #1fff91" : "-fx-font: 14px 'Segoe UI bold'; -fx-text-fill: red";
        currentCompetition = competition;
        id = competition.index;
        this.name.setText(competition.name);
        this.status.setText(status);
        this.status.setStyle(style);
        this.teamNum.setText(Integer.toString(competition.teams.size()));
        this.teamSize.setText(Integer.toString(competition.teamSize));

    }

    @Override
    public String toString() {
        return "CompetitionCard{" +
                "\nid=" + id +
                "\n, competitionId=" + competitionId.getId() +
                "\n, name=" + name.getText() +
                "\n, status=" + status.getText() +
                "\n, teamNum=" + teamNum.getText() +
                "\n, teamSize=" + teamSize.getText() +
                '}';
    }
}
