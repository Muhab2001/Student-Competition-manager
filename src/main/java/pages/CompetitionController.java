package pages;

import cards.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Competition;
import utils.CompetitionsMemory;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompetitionController implements TopBarable {

    private CompetitionController currentController;
    private boolean OPENED = false;

    @FXML
    public void initialize() throws IOException {

    }

    private Competition currentCompetition;

    @FXML
    private VBox compRoot;

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

    @FXML
    void announceRanks(ActionEvent event) throws IOException {
        RankingDialog controller = Navigator.<RankingDialog>nextDialog("ranking", "Add a New Team");
        controller.fillContent(currentCompetition, currentController);
    }

    @FXML
    void editDetails(ActionEvent event) throws IOException {
        CompetitionDialog dialogController = Navigator.<CompetitionDialog>nextDialog("competition-edit",
                "Edit a Competition");

        dialogController.fillEditContent(currentCompetition, currentController);

        dialogController.addTopBar((Stage)((Node)event.getSource()).getScene().getWindow());
}

    @FXML
    void navigateBack(ActionEvent event) throws IOException {
       Navigator.<MainController>next("main", event);
    }

    @FXML
    void addTeam(ActionEvent event) throws IOException {
        TeamDialog controller = Navigator.<TeamDialog>nextDialog("team", "Add a Team");
        controller.setHeader("Add a Team");
        controller.fillEmptyContent(currentCompetition.teamSize, currentController, currentCompetition.index);

    }
    @FXML
    void visitWebsite(ActionEvent event) throws IOException {

        WebsiteController controller = Navigator.<WebsiteController>next("website", event);

        controller.showWebsite(currentCompetition); // visit the link
    }
    // Validates the link for the website of the competition



    @FXML
    void delete(ActionEvent event) throws IOException {
        // implement the deletion process before navigating
        CompetitionsMemory.competitions.remove(currentCompetition.index);
        if(currentCompetition.index != CompetitionsMemory.competitions.size()){
            for(int i = currentCompetition.index; i < CompetitionsMemory.competitions.size(); i++){
                CompetitionsMemory.competitions.get(i).index -= CompetitionsMemory.competitions.get(i).index;
            }
        }
       MainController controller = Navigator.<MainController>next("../main", event);
       controller.fillContent(CompetitionsMemory.CURRENT_USER.username, CompetitionsMemory.CURRENT_USER.email, controller);


    }

    public void fillContent(Competition competition, CompetitionController controller) throws IOException {


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            LocalDate date = LocalDate.parse(competition.dueDate, formatter);
        System.out.println((LocalDate.now()).compareTo(date));

            if ((LocalDate.now()).compareTo(date) > 0) {
                if(!OPENED) {
                    Navigator.<DueDialog>nextDialog("due", "This competition is due!");
                }
                statusIndicator.setFill(Color.RED);
                statusLabel.setText("Closed");
            } else {
                statusIndicator.setFill(Color.rgb(31, 255, 145));
                statusLabel.setText("Open");
            }

        currentController = controller;
        competitionName.setText(competition.name);
        dateLabel.setText(competition.dueDate);
        sizeLabel.setText(String.valueOf(competition.teamSize));
        teamNumLAbel.setText(String.valueOf(competition.teams.size()));
            teamsContainer.getChildren().clear();
        for (int i = 0; i < competition.teams.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../team-card.fxml"));
            teamsContainer.getChildren().add((Node) fxmlLoader.load());
            TeamCard controller2 = fxmlLoader.getController();
            controller2.setContent(competition.teams.get(i), currentController, competition.index);
        }

    OPENED = true;
    }

    @Override
    public void addTopBar(Stage stage) {
        TopBarPane topBar = new TopBarPane(stage,competitionName.getText());
        compRoot.getChildren().add(0,topBar);
    }
}
