package cards;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Competition;
import models.Student;
import models.Team;
import pages.CompetitionController;
import utils.CompetitionsMemory;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;
import java.io.IOException;
import java.util.ArrayList;

public class TeamDialog implements TopBarable {

    // used to fetch data when the element is displayed


    @FXML
    public void initialize() throws IOException {


    }

    @FXML
    private VBox headerContainer;
    private boolean isEditing = false;
    private final ArrayList<StudentCard> stdControllers = new ArrayList<>();
    private int currentCompetitionIndex = -1;
    private int index = -1;
    private CompetitionController competitionController;
    private String errMsg;
    private TeamDialog dialogcontroller;
    private Team currentTeam;

    @FXML
    private Button confirm;

    @FXML
    private DialogPane root;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label headerText;

    @FXML
    private VBox studentsContainer;

    @FXML
    void mutateTeams(ActionEvent event) throws IOException {
        mutator(event);
    }

    void enterMutateTeams(KeyEvent event) throws IOException{
        mutator(event);
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void mutator(Event event) throws IOException {
        Competition competition = CompetitionsMemory.getCompetition(currentCompetitionIndex);
        if(validate(competition)){
            Team newTeam = new Team(index, competition.teamSize);
            int tmpIndex = 0; // an index to mutate index for existing students only
            for (int i = 0; i < competition.teamSize; i++) {

                Student std = stdControllers.get(i).retreive();
                if(std.name.length() != 0 && std.id.length() != 0 && std.major.length() != 0) {
                    std.index = tmpIndex++;
                    System.out.println(std); // TODO: remove test log
                    newTeam.students.add(std);
                }
            }
            while (newTeam.students.size() < newTeam.teamSize){
                newTeam.students.add(new Student(tmpIndex++)); // empty objects to conserve Excel spreadsheet format
            }

            if (isEditing) {
                newTeam.rank = currentTeam.rank;
                competition.teams.set(index, newTeam);
            }
            else {
                newTeam.index = competition.teams.size();
                competition.teams.add(newTeam);
            }
            competitionController.fillContent(competition, competitionController);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }else{
            ErrorMessage errorMessage = Navigator.<ErrorMessage>card("error-msg");
            errorMessage.fillContent(errMsg);
            headerContainer.getChildren().remove(2);
            headerContainer.getChildren().add(2, errorMessage.getLabel());
        }
    }

    private boolean validate(Competition competition){
        Competition compClone = competition.clone();
        if(isEditing){
            compClone.teams.remove(index);
        }
        boolean valid = false;
        ArrayList<Student> tmpArr = new ArrayList<>(); // cross validating for duplicate info between students
        for(StudentCard card: stdControllers)
            card.clearError();
        for(StudentCard card: stdControllers){

            Student student = card.retreive();
            if(student.name.length() != 0 && student.major.length() != 0 && student.id.length() != 0) {
                valid = true;
                // non-duplicates in the same team
                for(Student tmpSt: tmpArr){
                    if(tmpSt.name.equals(student.name) || tmpSt.id.equals(student.id))
                    {

                        card.flagError();
                        stdControllers.get(tmpArr.indexOf(tmpSt)).flagError();
                        valid = false;
                    }
                }

                if(!valid){
                    errMsg = "Cannot add duplicate students' info in one team";
                    return false;
                }

                for(Team team: compClone.teams){
                    for(Student tmpSt: team.students){
                        if(tmpSt.name.equals(student.name) || tmpSt.id.equals(student.id))
                        {
                            card.flagError();
                            valid = false;
                        }
                    }
                }
                if(!valid){
                    errMsg = "Cannot add duplicate students' info from other teams";
                    return false;
                }
                tmpArr.add(student);
            }
            else if (student.name.length() != 0 || student.major.length() != 0 || student.id.length() != 0){
                card.flagError();
                errMsg = "To add a student you must provide full details";
                return false;
            }
        }
        if(!valid){
            errMsg = "Cannot add an empty team";
        }
        return valid;

    }

    public void setHeader(String header){
        headerText.setText(header);
    }

    public void fillContent(Team team, CompetitionController controller, int competitionIndex, TeamDialog dialogController) throws IOException {
    currentTeam = team;
    this.dialogcontroller = dialogController;
    index = team.index;
    currentCompetitionIndex = competitionIndex;
    competitionController = controller;
    isEditing = true;

        for(int i = 0; i < team.teamSize; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../student-card-edit.fxml"));
            studentsContainer.getChildren().add((Node) fxmlLoader.load());
            StudentCard card = fxmlLoader.getController();
            card.fillEditableContent(team.students.get(i), this.dialogcontroller);
            stdControllers.add(card);
        }
        
    }

    public void fillEmptyContent(int teamSize, CompetitionController controller, int competitionIndex, TeamDialog dialogController) throws IOException {

       this.dialogcontroller = dialogController;
        currentCompetitionIndex = competitionIndex;
        competitionController = controller;
        index = CompetitionsMemory.competitions.size();
        for(int i = 0; i < teamSize; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../student-card-edit.fxml"));
            studentsContainer.getChildren().add((Node) fxmlLoader.load());
            StudentCard card = fxmlLoader.getController();
            card.fillEditableEmptyContent(dialogController, i);
            stdControllers.add(card);
        }
    }

    @Override
    public void addTopBar(Stage stage) {
        String title;

        if (this.isEditing)
            title = "Edit a Team";
        else {
            title = "Add a New Team";

        }
        TopBarPane topBar = new TopBarPane((Stage)root.getScene().getWindow(),title);
        headerContainer.getChildren().add(0, topBar);
    }
}