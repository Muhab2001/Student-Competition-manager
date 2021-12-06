package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Competition;
import pages.CompetitionController;
import utils.CompetitionsMemory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CompetitionDialog {

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
        dateInput.setConverter(new StringConverter<LocalDate>() {
            String pattern = "M/d/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                dateInput.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    private Stage stage;

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

    @FXML // TODO: tracking a competition
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
}
