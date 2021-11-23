package pages;

import cards.CompetitionDialog;
import cards.RankingDialog;
import cards.TeamCard;
import cards.TeamDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Competition;
import models.Team;

import java.io.IOException;

public class CompetitionController {

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ranking-dialog.fxml"));
        DialogPane dialogPane = fxmlLoader.load();
        RankingDialog controller = fxmlLoader.getController();
        controller.fillContent();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add a new Team");
        dialog.setDialogPane(dialogPane); // fxml as a dialog
        dialog.initStyle(StageStyle.UNDECORATED); // TODO: custom bar
        dialog.show();
    }

    @FXML
    void editDetails(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition-dialog.fxml"));
        DialogPane dialogPane = fxmlLoader.load();
        CompetitionDialog dialogController = fxmlLoader.getController();
        dialogController.fillContent();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit a competition");
        dialog.setDialogPane(dialogPane); // fxml as a dialog
        dialog.initStyle(StageStyle.UNDECORATED); // TODO: custom bar
        dialog.show();
    }

    @FXML
    void navigateBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../main.fxml")); // get the fxml file
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // get the current stage
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addTeam(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../team-dialog.fxml"));
        DialogPane dialogPane = fxmlLoader.load();
        TeamDialog controller = fxmlLoader.getController();
        controller.setHeader("Add a Team");
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add a new Team");
        dialog.setDialogPane(dialogPane); // fxml as a dialog
        dialog.initStyle(StageStyle.UNDECORATED); // TODO: custom bar
        dialog.show();
    }

    @FXML
    void visitWebsite(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../website.fxml")); // get the fxml file
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // get the current stage
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        WebsiteController controller = fxmlLoader.getController();
        controller.showWebsite("https://www.google.com");
        stage.setScene(scene);
        stage.show();
    }

        // TODO: Perform a proper deletion in the excel storage, this is just a test
    @FXML
    void delete(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../main.fxml")); // get the fxml file
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // get the current stage
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setScene(scene);
        stage.show();
    }
    // We should pass a Competition id index to fetch its data from the excel storage
    // and create a Competition Object from the fetched data
    // also display the due date notificaiton according to the status provided
    public void fillContent() throws IOException {
    // TODO: replace with dynamic population

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
