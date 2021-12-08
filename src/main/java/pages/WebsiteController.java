package pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.Competition;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;

public class WebsiteController implements TopBarable {

    Competition competition;

    @FXML
    private Button returnBtn;

    @FXML
    private VBox websiteRoot;

    @FXML
    private Label websiteLink;

    @FXML
    private WebView websiteViewer;
    //DONE
    @FXML
    void navigateBack(ActionEvent event) throws IOException {
        CompetitionController controller =
                Navigator.<CompetitionController>next("competition", event);
        controller.fillContent(competition, controller);

    }


    // TODO: check for internet connection and display an appropriate error in case no connection was found
    public void showWebsite(Competition competition){
        websiteLink.setText(competition.websiteLink);
        WebEngine engine = websiteViewer.getEngine();
        engine.load(competition.websiteLink);
    }
    // DONE
    @Override
    public void addTopBar(Stage stage) {
        TopBarPane topBar = new TopBarPane(stage,"Competition Website");
        websiteRoot.getChildren().add(0,topBar);
    }


}
