package cards;

import pages.CompetitionController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import utils.Navigator;
import java.io.IOException;

public class CompetitionCard {

    private int id = -1;


    @FXML
    private Label name;

    @FXML
    private Label status;

    @FXML
    private Label teamNum;

    @FXML
    private Label teamSize;

    // TODO: pass a competition object according to the index
    @FXML
    void openDetails(MouseEvent event) throws IOException {

        CompetitionController controller = Navigator.<CompetitionController>next("competition", event);
        controller.fillContent(controller);

    }

    public void fillContent(String name, String status, int compIndex, int teamSize ){
        // populating the card with the content
        id = compIndex;
        this.name.setText(name);
        this.status.setText(status);
        this.teamNum.setText(Integer.toString(compIndex));
        this.teamSize.setText(Integer.toString(compIndex));
    }

}
