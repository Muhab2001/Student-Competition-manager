package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Competition;
import pages.CompetitionController;
import utils.CompetitionsMemory;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CompetitionDialog implements TopBarable {

    // used to fetch data when the element is displayed
    private Competition competition;
    private CompetitionController currentController;



    @FXML
    public void initialize(){
        dateInput.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
            }
        });
        dateInput.getEditor().setDisable(true);

    }

    private Stage stage;

    private boolean isTrack;

    @FXML
    private DialogPane root;

    @FXML
    private Button cancelBtn;

    @FXML
    private DatePicker dateInput;

    @FXML
    private TextField linkInput;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField sizeInput;

    @FXML
    private Button submitBtn;

    @FXML
    void cancel(ActionEvent event) {
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML // TODO: tracking a competition with validation
    void trackCompetition(ActionEvent event) {
        // if statement
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //
    public void fillContent(Competition competition, CompetitionController competitionController) throws IOException {
       currentController = competitionController;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
       this.competition = competition;
        dateInput.setValue(LocalDate.parse(competition.dueDate, dateTimeFormatter));
        linkInput.setText(competition.websiteLink);
        nameInput.setText(competition.name);
        sizeInput.setText(String.valueOf(competition.teamSize));
    }

    // Edits a competition's details in the CompetitionsMemory instance
    @FXML
    public void editCompetition(ActionEvent actionEvent) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        competition.name = nameInput.getText();
        competition.websiteLink = validatedLink(linkInput.getText());
        competition.teamSize = Integer.parseInt(sizeInput.getText());
       competition.dueDate = String.valueOf((dateInput.getValue()).format(dateTimeFormatter));
        currentController.fillContent(competition, currentController);
        CompetitionsMemory.INSTANCE.editCompetition(competition);

        // Closing the stage
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    private String validatedLink(String link) {
        String newLink = link;

        if (!newLink.startsWith("http")) // if no http protocol has been used
            newLink = "https://" + newLink;

        if (!newLink.contains(".")) // if the link has no domain suffix
            newLink += ".com";
        return newLink;
    }
    public void setIsTrack(boolean value){
        this.isTrack = value;
    }

    @Override
    public void addTopBar(Stage stage) {
        String title;

        if (this.isTrack)
            title = "Track a new Competition";
        else {
            title = "Edit the Competition";
            this.submitBtn.setText("Update Competition");
        }
        TopBarPane topBar = new TopBarPane((Stage)root.getScene().getWindow(),title);
        root.setHeader(topBar);
    }
}
