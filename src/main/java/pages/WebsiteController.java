package pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition.fxml")); // get the fxml file
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // get the current stage
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        CompetitionController controller = fxmlLoader.getController();
        controller.fillContent();
        stage.setScene(scene);
        stage.show();
    }

    public void showWebsite(String link){
        websiteLink.setText(link);
        WebEngine engine = websiteViewer.getEngine();
        engine.load(link);
    }

}
