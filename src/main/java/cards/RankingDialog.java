package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmRanking(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private TextField rankingInput;

    @FXML
    private VBox studentContainer;

    public void fillContent() throws IOException {
        VBox vbox = new VBox(5);

            for(int i = 0; i < 3; i++){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ranking-slot.fxml"));
                vbox.getChildren().add((Node) fxmlLoader.load());

            }
        ranksContainer.setContent(vbox);

    }

}
