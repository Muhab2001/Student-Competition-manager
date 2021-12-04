package pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;

public class WebsiteController implements TopBarable {

    @FXML
    private Button returnBtn;

    @FXML
    private VBox root;

    @FXML
    private Label websiteLink;

    @FXML
    private WebView websiteViewer;

    @FXML
    void navigateBack(ActionEvent event) throws IOException {
        CompetitionController controller =
                Navigator.<CompetitionController>next("competition", event);
        controller.fillContent();


    }

    public void showWebsite(String link){
        websiteLink.setText(link);
        WebEngine engine = websiteViewer.getEngine();
        engine.load(link);
    }

    @Override
    public void addTopBar(Stage stage) {
        TopBarPane topBar = new TopBarPane(stage,"Competition Website");
        root.getChildren().add(0,topBar);
    }
}
