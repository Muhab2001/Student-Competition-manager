package cards;

import javafx.event.ActionEvent;
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
import models.Student;
import models.Team;
import pages.CompetitionController;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;
import java.util.ArrayList;

public class RankingDialog implements TopBarable {

    // used to fetch data when the element is displayed
    private Competition currentCompetition;
    private final ArrayList<RankingSlot> controllers = new ArrayList<>();
    private CompetitionController compController;
    private String errMsg;


    @FXML
    public void initialize(){

    }

    @FXML
    private DialogPane root;

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

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmRanking(ActionEvent event) throws IOException {
        if(validate()){
            for (RankingSlot slot : controllers) {
                //TODO: validation for ranks input
                slot.cardTeam.rank = Integer.parseInt(slot.retreiveRank());
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            EmailDialog controller = Navigator.<EmailDialog>nextDialog("email", "Email a team");
            controller.fillContent(currentCompetition, controller);
            compController.fillContent(currentCompetition, compController);
        }else{
            ErrorMessage errorMessage = Navigator.<ErrorMessage>card("error-msg");
            errorMessage.fillContent(errMsg);
            dialogHeader.getChildren().remove(2);
            dialogHeader.getChildren().add(2, errorMessage.getLabel());
        }
    }

    private boolean validate(){
        boolean valid = true;
        ArrayList<String> tmpRanks = new ArrayList<>();
        for(RankingSlot slot: controllers)
            slot.clearError();
        for(RankingSlot slot: controllers){

            String rank = slot.retreiveRank();
            if(rank.length() == 0){
                errMsg = "Please provide a rank for all teams";
                valid = false;
                slot.flagError();
            }
            else{
                try {
                    if (Integer.parseInt(rank) < 0) {
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




    public void fillContent(Competition competition, CompetitionController compController) throws IOException {
        currentCompetition = competition;
        this.compController = compController;
        ArrayList<Team> teams = competition.teams; // Get the teams of the current competition
        for (Team team : teams) {
            System.out.println(team.toString()); // TODO: delete the test log
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ranking-slot.fxml"));
            studentContainer.getChildren().add((Node) fxmlLoader.load()); // Add empty ranking cards to the VBox
            RankingSlot slot = fxmlLoader.getController();
            controllers.add(slot);
            slot.fillContent(team);

        }


    }

    @Override
    public void addTopBar(Stage stage) {
        String title;
        title = "Announce Rankings";
        TopBarPane topBar = new TopBarPane((Stage)root.getScene().getWindow(),title);
        dialogHeader.getChildren().add(0, topBar);
    }
}
