package pages;

import cards.CompetitionDialog;
import cards.RankingDialog;
import cards.TeamCard;
import cards.TeamDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import models.Competition;
import utils.Navigator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompetitionController {

    @FXML
    public void initialize() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyy");
        LocalDate date = LocalDate.parse("11/27/2021", formatter);
        if(LocalDate.now().compareTo(date) == 0){
            System.out.println("Tracking!");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../due-dialog.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("This competition is due!");
            dialog.setDialogPane(dialogPane); // fxml as a dialog
            dialog.initStyle(StageStyle.TRANSPARENT); // TODO: custom bar
            dialog.show();
        }
    }

    private Competition currentCompetition;

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
    private ScrollPane teamsContainer;

    @FXML
    private Button announceRanking;

    @FXML
    void announceRanks(ActionEvent event) throws IOException {
        RankingDialog controller =
                Navigator.<RankingDialog>nextDialog(new FXMLLoader(getClass().getResource("../ranking-dialog.fxml")), "Add a New Team");
        controller.fillContent();
    }

    @FXML
    void editDetails(ActionEvent event) throws IOException {
        CompetitionDialog dialogController =
                Navigator.<CompetitionDialog>nextDialog(new FXMLLoader(getClass().getResource("../competition-dialog.fxml")), "Edit a Competition");
        dialogController.fillContent();

    }

    @FXML
    void navigateBack(ActionEvent event) throws IOException {
        Navigator.<MainController>next(new FXMLLoader(getClass().getResource("../main.fxml")), event);
    }

    @FXML
    void addTeam(ActionEvent event) throws IOException {
        TeamDialog controller =
                Navigator.<TeamDialog>nextDialog(new FXMLLoader(getClass().getResource("../team-dialog.fxml")), "Add a Team");
        controller.setHeader("Add a Team");

    }

    // TODO: Perform a proper dynamic routing using fetched websites, this is just a test
    @FXML
    void visitWebsite(ActionEvent event) throws IOException {
        WebsiteController controller =
                Navigator.<WebsiteController>next(new FXMLLoader(getClass().getResource("../website.fxml")), event);
        controller.showWebsite("https://www.google.com");
    }

    // TODO: Perform a proper deletion , this is just a test
    @FXML
    void delete(ActionEvent event) throws IOException {
        // implement the deletion process before navigating
        Navigator.<MainController>next(new FXMLLoader(getClass().getResource("../main.fxml")), event);

    }

    // TODO: replace with dynamic population
    public void fillContent() throws IOException {


        VBox vbox = new VBox(8);
        for(int i = 0; i < 10; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../team-card.fxml"));
            vbox.getChildren().add((Node) fxmlLoader.load());
            TeamCard controller = fxmlLoader.getController();
            controller.setContent();
        }

        teamsContainer.setContent(vbox);

    }



}
