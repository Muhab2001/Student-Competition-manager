package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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

    }

    @FXML
    void confirmRanking(ActionEvent event) {

    }

    @FXML
    private TextField rankingInput;

    @FXML
    private VBox studentContainer;

}
