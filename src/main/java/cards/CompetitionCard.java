package cards;

import pages.CompetitionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import utils.Navigator;

import java.io.IOException;

public class CompetitionCard {

    @FXML
    private VBox competiitionId;

    @FXML
    private Label name;

    @FXML
    private Label status;

    @FXML
    private Label teamNum;

    @FXML
    private Label teamSize;

    @FXML
    void openDetails(MouseEvent event) throws IOException {
        CompetitionController controller = Navigator.<CompetitionController>next("competition", event);
        controller.fillContent();
    }

    public void fillContent(String name, String status, int teamNum, int teamSize ){
        // populating the card with the content
        this.name.setText(name);
        this.status.setText(status);
        this.teamNum.setText(Integer.toString(teamNum));
        this.teamSize.setText(Integer.toString(teamNum));
    }

}
