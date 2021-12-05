package pages;

import cards.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Competition;
import models.Team;
import utils.CompetitionsMemory;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompetitionController implements TopBarable {

    private CompetitionController currentController;

    @FXML
    public void initialize() throws IOException {
        currentCompetition = CompetitionsMemory.INSTANCE.getCompetition(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse("12/1/2021", formatter);

        dateLabel.setText("12/1/2021");
        if(LocalDate.now().compareTo(date) > 0){

            Navigator.<DueDialog>nextDialog("due", "This competition is due!");
            statusIndicator.setFill(Color.RED);
            statusLabel.setText("Closed");
        }else {
            statusIndicator.setFill(Color.GREENYELLOW);
            statusLabel.setText("Open");
        }
    }

    private Competition currentCompetition;

    @FXML
    private VBox root;

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
        controller.fillContent();
    }

    @FXML
    void editDetails(ActionEvent event) throws IOException {
        CompetitionDialog dialogController = Navigator.<CompetitionDialog>nextDialog("competition-edit",
                "Edit a Competition");
        dialogController.fillContent(currentController, currentCompetition.name, currentCompetition.websiteLink, currentCompetition.teamSize, currentCompetition.dueDate);
    }

    @FXML
    void navigateBack(ActionEvent event) throws IOException {
       MainController controller = Navigator.<MainController>next("main", event);
        controller.addTopBar((Stage)((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    void addTeam(ActionEvent event) throws IOException {
        TeamDialog controller = Navigator.<TeamDialog>nextDialog("team", "Add a Team");
        controller.setHeader("Add a Team");
        controller.fillContent();
    }

    // TODO: DONE - Perform a proper dynamic routing using fetched websites, this is just a test
    @FXML
    void visitWebsite(ActionEvent event) throws IOException {
        WebsiteController controller = Navigator.<WebsiteController>next("website", event);
        String link = validatedLink(currentCompetition.websiteLink); // the link for the current competition's website
        controller.showWebsite(link); // visit the link
    }
    // Validates the link for the website of the competition
    String validatedLink(String link) {
        String newLink = link;

        if (!newLink.startsWith("http")) // if no http protocol has been used
            newLink = "https://" + newLink;

        if (!newLink.contains(".")) // if the link has no domain suffix
            newLink += ".com";

        return newLink;
    }

    // TODO: Perform a proper deletion , this is just a test
    @FXML
    void delete(ActionEvent event) throws IOException {
        // implement the deletion process before navigating
       MainController controller = Navigator.<MainController>next("../main", event);

    }

    // TODO: replace with dynamic population
    public void fillContent(CompetitionController controller) throws IOException {
        currentController = controller; // sets the current CompetitionController

        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../team-card.fxml"));
            teamsContainer.getChildren().add((Node) fxmlLoader.load());
            TeamCard controller2 = fxmlLoader.getController();
            controller2.setContent();
        }


    }

    @Override
    public void addTopBar(Stage stage) {
        TopBarPane topBar = new TopBarPane(stage,competitionName.getText());
        root.getChildren().add(0,topBar);
    }
}
