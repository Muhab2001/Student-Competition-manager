package pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import utils.Navigator;

import java.io.IOException;

public class WebsiteController {

    @FXML
    private Button returnBtn;

    @FXML
    private Label websiteLink;

    @FXML
    private WebView websiteViewer;

    @FXML
    void navigateBack(ActionEvent event) throws IOException {
        CompetitionController controller =
                Navigator.<CompetitionController>next(new FXMLLoader(getClass().getResource("../competition.fxml")), event);
        controller.fillContent();

    }

    public void showWebsite(String link){
        websiteLink.setText(link);
        WebEngine engine = websiteViewer.getEngine();
        engine.load(link);
    }

}
