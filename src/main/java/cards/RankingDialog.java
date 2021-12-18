package cards;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Competition;
import models.Team;
import pages.CompetitionController;
import utils.Hover;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;
import java.util.ArrayList;

/**
 * controller class for competition ranking dialog
 */
public class RankingDialog implements TopBarable {


    private Competition currentCompetition;
    private final ArrayList<RankingSlot> controllers = new ArrayList<>();
    private CompetitionController compController;
    private String errMsg;
    private RankingDialog dialog;


    @FXML
    private DialogPane rankRoot;

    @FXML
    private VBox dialogHeader;

    @FXML
    private Button cancelRankings;

    @FXML
    private Button confirmRankings;

    @FXML
    private ScrollPane ranksContainer;

    @FXML
    private TextField rankingInput;

    @FXML
    private VBox studentContainer;

    /**
     * event listener to abort ranking process
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * event listener for confirm ranking input
     * @param event
     * @throws IOException fxml file corruption
     */
    @FXML
    void confirmRanking(ActionEvent event) throws IOException {
        ranker(event);
    }

    /**
     * method to perform ranking mutation
     * @param event
     * @throws IOException fxml file corruption
     */
    public void ranker(Event event) throws IOException {
        if(validate()){
            for (RankingSlot slot : controllers) {
                slot.cardTeam.rank = Integer.parseInt(slot.retrieveRank());
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            EmailDialog controller = Navigator.<EmailDialog>nextDialog("email", "Email a team");
            controller.fillContent(currentCompetition, controller);
            controller.addTopBar((Stage)((Node)event.getSource()).getScene().getWindow());
            compController.fillContent(currentCompetition, compController, true);
        }else{
            ErrorMessage errorMessage = Navigator.<ErrorMessage>card("error-msg");
            errorMessage.fillContent(errMsg);
            dialogHeader.getChildren().remove(2);
            dialogHeader.getChildren().add(2, errorMessage.getLabel());
        }
    }

    /**
     * validating ranks' input
     * @return valdation confirmation
     */
    private boolean validate(){
        boolean valid = true;
        ArrayList<String> tmpRanks = new ArrayList<>();
        for(RankingSlot slot: controllers)
            slot.clearError();
        for(RankingSlot slot: controllers){

            String rank = slot.retrieveRank();
            if(rank.length() == 0){
                errMsg = "Please provide a rank for all teams";
                valid = false;
                slot.flagError();
            }
            else{
                try {
                    if (Integer.parseInt(rank) <= 0) {
                        errMsg = "Please provide positive values";
                        slot.flagError();
                        return false;
                    }
                } catch (NumberFormatException e) {
                    errMsg = "Please provide Numeric values for ranks";
                    slot.flagError();
                    valid = false;
                }
            }

            for(String rankStr:tmpRanks){
                if(rank.equals(rankStr) && rank.length() != 0){
                    errMsg = "Please do not provide duplicate ranks for different teams";
                    slot.flagError();
                    valid = false;
                }
            }
            tmpRanks.add(rank);
        }

        return valid;
    }


    /**
     * populating with teams data, passing required controllers
     * @param competition current competition
     * @param compController running competition controller
     * @param rankingDialog instance of ranking dialog to pass to other controllers
     * @throws IOException
     */
    public void fillContent(Competition competition, CompetitionController compController, RankingDialog rankingDialog) throws IOException {
        dialog = rankingDialog;
        currentCompetition = competition;
        this.compController = compController;
        ArrayList<Team> teams = competition.teams; // Get the teams of the current competition
        for (Team team : teams) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ranking-slot.fxml"));
            studentContainer.getChildren().add((Node) fxmlLoader.load()); // Add empty ranking cards to the VBox
            RankingSlot slot = fxmlLoader.getController();
            Hover.raising(fxmlLoader.getRoot());
            controllers.add(slot);
            slot.fillContent(team, dialog);
        }


    }

    @Override
    public void addTopBar(Stage stage) {
        String title;
        title = "Announce Rankings";
        TopBarPane topBar = new TopBarPane((Stage) rankRoot.getScene().getWindow(),title);
        dialogHeader.getChildren().add(0, topBar);
    }
}
