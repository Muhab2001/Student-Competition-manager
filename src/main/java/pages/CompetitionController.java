package pages;

import cards.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Competition;
import utils.*;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * a class to handle competition details page
 */
public class CompetitionController implements TopBarable {
    private CompetitionController currentController; // to manipulate the controller by other controller
    private boolean OPENED = false; // to determine due date dialog firing
    private Competition currentCompetition;
    /**
     * event listener for fire the ranking dialog
     * @param event
     * @throws IOException when fxml file is corrupted
     */
    @FXML
    void announceRanks(ActionEvent event) throws IOException {
        RankingDialog controller = Navigator.<RankingDialog>nextDialog("ranking", "Add a New Team");
        controller.fillContent(currentCompetition, currentController, controller);
        controller.addTopBar((Stage)((Node)event.getSource()).getScene().getWindow());
    }
    /**
     * event listener to fire the detail editing dialog
     * @param event
     * @throws IOException when fxml file is corrupted
     */
    @FXML
    void editDetails(ActionEvent event) throws IOException {
        CompetitionDialog dialogController = Navigator.<CompetitionDialog>nextDialog("competition-edit",
                "Edit a Competition");

        dialogController.fillEditContent(currentCompetition, currentController, dialogController);

        dialogController.addTopBar((Stage)((Node)event.getSource()).getScene().getWindow());
}
    /**
     * event listener to navigate back to the main page
     * @param event
     * @throws IOException when fxml file is corrupted
     */
    @FXML
    void navigateBack(ActionEvent event) throws IOException {
       Navigator.<MainController>next("main", event);
    }
    /**
     * event listener to fire the team addition dialog
     * @param event
     * @throws IOException when fxml file is corrupted
     */
    @FXML
    void addTeam(ActionEvent event) throws IOException {
        TeamDialog controller = Navigator.<TeamDialog>nextDialog("team", "Add a Team");
        controller.setHeader("Add a Team");
        controller.fillEmptyContent(currentCompetition.teamSize, currentController, currentCompetition.index, controller);
        controller.addTopBar((Stage)((Node)event.getSource()).getScene().getWindow());

    }
    /**
     * event listener to navigate the competition website
     * @param event
     * @throws IOException when fxml file is corrupted
     */
    @FXML
    void visitWebsite(ActionEvent event) throws IOException {

        WebsiteController controller = Navigator.next("website", event);

        controller.showWebsite(currentCompetition); // visit the link
    }
    /**
     * event listener to delete the current competition
     * @param event
     * @throws IOException when fxml file is corrupted
     */
    @FXML
    void delete(ActionEvent event) throws IOException {
        // implement the deletion process before navigating
        CompDeleteConfirm controller = Navigator.nextDialog("comp-delete", "Confirmation");
        controller.fillContent(currentCompetition, currentController, (Stage)((Node) event.getSource()).getScene().getWindow());


    }
    /**
     * method to populate the competition info after initialization
     * @param competition target competition
     * @param controller competition controller to be sotred and passed for other controllers
     * @param opened to enforce no duplicate pop-ups foe due date dialog
     * @throws IOException for fxml file corruption
     */
    public void fillContent(Competition competition, CompetitionController controller, boolean opened) throws IOException {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            LocalDate date = LocalDate.parse(competition.dueDate, formatter);
            OPENED = opened;
        try{
            final URL url = new URL(competition.websiteLink);
            final URLConnection conn = url.openConnection();
            conn.connect();
            connectionToolTip.setText("Connection is ready");
            connectionStatus.setImage(new Image("img/icons8-check-circle-24.png"));
        }catch (Exception e){
            if (!competition.isOpen) {
                if(!OPENED) {
                    ErrorDialog dialog = Navigator.<ErrorDialog>nextDialog("error", "Warning");
                    dialog.fillContent("Competition is Due!",
                            "This Competition can no more accept any participants. Announce The rankings or change the due date");
                }
                addTeamBtn.setDisable(true);
                statusIndicator.setFill(Color.RED);
                statusLabel.setText("Closed");
            } else {
                statusIndicator.setFill(Color.rgb(31, 255, 145));
                statusLabel.setText("Open");
                addTeamBtn.setDisable(false);
            }

        currentCompetition = competition;
        currentController = controller;
        competitionName.setText(competition.name);
        dateLabel.setText(competition.dueDate);
        sizeLabel.setText(String.valueOf(competition.teamSize));
        teamNumLAbel.setText(String.valueOf(competition.teams.size()));
            teamsContainer.getChildren().clear();
        for (int i = 0; i < competition.teams.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../team-card.fxml"));
            Node teamCard = fxmlLoader.load();

            // make transition for team card
//            Hover.raising(teamCard);

            teamsContainer.getChildren().add(teamCard);
            TeamCard controller2 = fxmlLoader.getController();
            controller2.fillContent(competition.teams.get(i), currentController, competition.index);
        }

    }}
    @Override
    public void addTopBar(Stage stage) {
        TopBarPane topBar = new TopBarPane(stage,competitionName.getText());
        compRoot.getChildren().add(0,topBar);
    }

    @FXML
    private VBox compRoot;
    @FXML
    private AnchorPane compBody;
    @FXML
    private ImageView connectionStatus;
    @FXML
    private Tooltip connectionToolTip;
    @FXML
    private Circle statusIndicator;
    @FXML
    private Button addTeamBtn;
    @FXML
    private Label competitionName;
    @FXML
    private Label dateLabel;
    @FXML
    private Button editDetailsBtn;
    @FXML
    private Button returnBtn;
    @FXML
    private Label sizeLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label teamNumLAbel;
    @FXML
    private Button visitWebsiteBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private VBox teamsContainer;
    @FXML
    private Button announceRanking;
}
