package pages;

import cards.CompetitionCard;
import cards.CompetitionDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Competition;
import utils.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * controller class to handle the main page
 */
public class MainController implements TopBarable {
    private MainController mainController;
    // holds competitions within loaded fxml cards
    public final ArrayList<CompetitionCard> competitions = new ArrayList<>();
    @FXML
    private VBox mainRoot;
    /**
     * loads all competitions from `CompetitionsMemory`
     */
    @FXML
    public void initialize() {
        vBox1.setPadding(new Insets(14));
        vBox2.setPadding(new Insets(14));
        for(int i = 0; i < CompetitionsMemory.competitions.size(); i++){
            Competition competition = CompetitionsMemory.getCompetition(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("competition-card.fxml"));
            if(i % 2 == 0){
                try {
                    vBox1.getChildren().add(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            else{
                try {
                    vBox2.getChildren().add(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            CompetitionCard controller2=  fxmlLoader.getController();
            controller2.fillContent(competition);
            competitions.add(controller2);
        }


        // Assigning hover transitions for cards
        for (CompetitionCard competitionCard : competitions) {

            VBox card = competitionCard.competitionId;

            Hover.raising(card);
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
    /**
     * event listener to open the dialog for tracking new competitions
     * @param event
     * @throws IOException when fxml file is corrupted
     */
    @FXML
    void trackCompetition(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        CompetitionDialog dialogController =
                Navigator.nextDialog("competition", "Track a New Competition");
        dialogController.setIsTrack(true);
        dialogController.addTopBar(stage);
        dialogController.fillEmptyContent(mainController, stage);

    }
    /**
     * method to populate needed crednetials for website elements
     * @param name username
     * @param email user email
     * @param controller an instance of the main controller for later usage
     * @return
     */
    public ArrayList<CompetitionCard> fillContent(String name, String email, MainController controller){
        mainController = controller;
        username.setText(name);
        this.email.setText(email);
    return competitions;
    }
    @Override
    public void addTopBar(Stage stage) {
        TopBarPane topBar = new TopBarPane(stage,"Competitions");
        mainRoot.getChildren().add(0,topBar);
    }


}

