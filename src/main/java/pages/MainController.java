package pages;

import cards.CompetitionCard;
import cards.CompetitionDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Competition;
import utils.CompetitionsMemory;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;

public class MainController implements TopBarable {

    @FXML
    private VBox mainRoot;


    // used to fetch data when the element is displayed
    @FXML
    public void initialize() throws IOException {


        vBox1.setPadding(new Insets(14));
        vBox2.setPadding(new Insets(14));
        for(int i = 0; i < CompetitionsMemory.INSTANCE.competitions.size(); i++){

            Competition competition = CompetitionsMemory.INSTANCE.getCompetition(i);

            if(i % 2 == 0){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition-card.fxml"));
                vBox1.getChildren().add((Node) fxmlLoader.load());
                ((CompetitionCard) fxmlLoader.getController()).fillContent(competition);
            }
            else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition-card.fxml"));
                vBox2.getChildren().add((Node) fxmlLoader.load());
                ((CompetitionCard) fxmlLoader.getController()).fillContent(competition);


            }


        }

    }

    @FXML
    private ScrollPane CompetitionsContainer;

    @FXML
    private Label email;

    @FXML
    private Button trackBtn;

    @FXML
    private Label username;

    @FXML
    private VBox vBox1;

    @FXML
    private VBox vBox2;

    @FXML
    void trackCompetition(ActionEvent event) throws IOException {
        CompetitionDialog dialogController =
                Navigator.<CompetitionDialog>nextDialog("competition", "Track a New Competition");
        dialogController.setIsTrack(true);
        dialogController.addTopBar((Stage)((Node)event.getSource()).getScene().getWindow());

    }

    //TODO : remove after testing
    @FXML
    void profileClicked(MouseEvent event) {
        Node node = (Node) event.getSource();
        String view = (String) node.getId();
        System.out.println(view);
    }

    // DONE
    public void fillContent(String name, String email){
        username.setText(name);
        this.email.setText(email);
    }


    @Override
    public void addTopBar(Stage stage) {
        TopBarPane topBar = new TopBarPane(stage,"Competitions");
        mainRoot.getChildren().add(0,topBar);
    }
}

