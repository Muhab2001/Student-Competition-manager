package cards;

import pages.CompetitionController;
import pages.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition.fxml")); // get the fxml file
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // get the current stage
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        CompetitionController controller = fxmlLoader.getController();
        controller.fillContent();
        stage.setScene(scene);
        stage.show();
    }

    public void fillContent(String name, String status, int teamNum, int teamSize ){
        // populating the card with the content
        this.name.setText(name);
        this.status.setText(status);
        this.teamNum.setText(Integer.toString(teamNum));
        this.teamSize.setText(Integer.toString(teamNum));
    }

}
