package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Competition;
import models.Student;
import models.Team;
import pages.CompetitionController;
import utils.CompetitionsMemory;

import java.io.IOException;
import java.util.ArrayList;

public class TeamDialog {

    // used to fetch data when the element is displayed


    @FXML
    public void initialize() throws IOException {


    }

    @FXML
    private VBox headerContainer;
    private boolean isEditing = false;
    private ArrayList<StudentCard> stdControllers = new ArrayList<>();
    private int currentCompetitionIndex = -1;
    private int index = -1;
    private CompetitionController currentController;

    @FXML
    private Button confirm;


    @FXML
    private Button cancelBtn;

    @FXML
    private Label headerText;

    @FXML
    private VBox studentsContainer;

    @FXML
    void mutateTeams(ActionEvent event) throws IOException {
        // TODO: mutation validation
        Competition competition = CompetitionsMemory.INSTANCE.getCompetition(currentCompetitionIndex);
        Team newTeam = new Team(index, competition.teamSize);
        for(int i = 0; i < competition.teamSize;i++){
            newTeam.students.add(stdControllers.get(i).retreive());
        }
        if(isEditing)
        competition.teams.set(index, newTeam);
        else {
            newTeam.index = competition.teams.size();
            competition.teams.add(newTeam);
        }
        currentController.fillContent(competition, currentController);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setHeader(String header){
        headerText.setText(header);
    }

    public void fillContent(Team team, CompetitionController controller, int competitionIndex) throws IOException {

    index = team.index;
    currentCompetitionIndex = competitionIndex;
    currentController = controller;
    isEditing = true;
        // TODO: use a team ArrayList parameter to populate the view
        for(int i = 0; i < team.teamSize; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../student-card-edit.fxml"));
            studentsContainer.getChildren().add((Node) fxmlLoader.load());
            StudentCard card = fxmlLoader.getController();
            card.fillEditableContent(team.students.get(i));
            stdControllers.add(card);
        }
        
    }

    public void fillEmptyContent(int teamSize, CompetitionController controller, int competitionIndex) throws IOException {
        for(int i = 0; i < teamSize; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../student-card-edit.fxml"));
            studentsContainer.getChildren().add((Node) fxmlLoader.load());
            StudentCard card = fxmlLoader.getController();
            stdControllers.add(card);
        }
    }

}