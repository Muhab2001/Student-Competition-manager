package cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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

    public void setContent(String name, String status, int teamNum, int teamSize ){
        // populating the card with the content
    }

}
