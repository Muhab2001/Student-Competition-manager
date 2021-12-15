package pages;

import cards.ErrorDialog;
import cards.ErrorMessage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.Competition;
import org.w3c.dom.Document;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * controller class to handle competition website page
 */
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

    /**
     *
     * @param event
     * @throws IOException for fxml file corruption
     */
    @FXML
    void navigateBack(ActionEvent event) throws IOException {
        CompetitionController controller =
                Navigator.<CompetitionController>next("competition", event);
        controller.fillContent(competition, controller, true);

    }

    /**
     * a method to check connection, validate website link and display the webpage
     * @param competition the target competition
     * @throws IOException
     */
    public void showWebsite(Competition competition) throws IOException {
        WebEngine webEngine = websiteViewer.getEngine();
        this.competition = competition;
        try{
            final URL url = new URL(competition.websiteLink);
            final URLConnection conn = url.openConnection();
            conn.connect();
            websiteLink.setText(competition.websiteLink);

            webEngine.load(competition.websiteLink);
        }catch (Exception e){
//            e.printStackTrace();
           System.out.println(e.getMessage());
            ErrorMessage msg =  Navigator.card("error-msg");
            msg.fillContent("No Internet Connection!");
            websiteLink.setStyle("-fx-text-fill: red; -fx-background-radius: 17px; -fx-background-color:  #f5f5f5");
            websiteRoot.getChildren().add(2, msg.getLabel());
            websiteRoot.setSpacing(4);
            ErrorDialog dialog =Navigator.<ErrorDialog>nextDialog("error", "No Internet Connection!");
            dialog.fillContent("No Internet Connection!", "Please check your internet connection to display the competition website");
        }


    }
    @Override
    public void addTopBar(Stage stage) {
        TopBarPane topBar = new TopBarPane(stage,"Competition Website");
        websiteRoot.getChildren().add(0,topBar);
    }


}
