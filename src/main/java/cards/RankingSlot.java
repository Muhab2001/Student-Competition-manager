package cards;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.Team;

import java.io.IOException;

public class RankingSlot {

    public Team cardTeam;

    @FXML
    private TextField rankingInput;

    @FXML
    private VBox studentContainer;

    public void fillContent(Team team) throws IOException {
        // TODO: fill the students for that team

    }

}
