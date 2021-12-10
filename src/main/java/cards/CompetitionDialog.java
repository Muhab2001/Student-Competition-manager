package cards;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Competition;
import org.apache.commons.validator.routines.UrlValidator;
import pages.CompetitionController;
import pages.MainController;
import utils.CompetitionsMemory;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CompetitionDialog implements TopBarable {

    // used to fetch data when the element is displayed
    private Competition competition;
    private CompetitionController compController;
    private MainController mainController;
    private Stage mainStage;
    private String errMsg;



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
    private VBox dialogHeader;

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

    @FXML
    void enterTrack(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER))
            tracker(event);
    }

    @FXML
    void trackCompetition(ActionEvent event) throws IOException {
        tracker(event);
    }
    // same listener for multiple event types
    public void tracker(Event event) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        if(invalid()){
            ErrorMessage errorMessage = Navigator.<ErrorMessage>card("error-msg");
            errorMessage.fillContent(errMsg);
            dialogHeader.getChildren().remove(2);
            dialogHeader.getChildren().add(2, errorMessage.getLabel());

        }
        else{
            Competition competition = new Competition((dateInput.getValue()).format(dateTimeFormatter), nameInput.getText(), Integer.parseInt(sizeInput.getText()), validatedLink(linkInput.getText()), CompetitionsMemory.competitions.size());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            mainStage.close();
            // open a new page for the new competition
            CompetitionsMemory.competitions.add(competition);
            CompetitionController controller = Navigator.next("competition", event);
            controller.fillContent(competition, controller);
            System.out.println(competition);
        }
    }

    private boolean invalid(){
        boolean case1 = dateInput.getValue() == (null) || sizeInput.getText().strip().length() == 0 || linkInput.getText().strip().length() == 0 || nameInput.getText().strip().length() == 0;
        String link = validatedLink(linkInput.getText());

        if(case1){
            errMsg = "Please fill all input fields before proceeding";
        }

        try{
            int size = Integer.parseInt(sizeInput.getText());
        }catch (Exception e){
            errMsg = "Please enter numeric values in size field";
            sizeInput.requestFocus();
            return true;
        }

        if(isTrack){
            for (Competition competition : CompetitionsMemory.INSTANCE.getCompetitions()) {
                if (competition.name.equals(nameInput.getText().strip())) {
                    errMsg = "Please avoid entering duplicate competition names";
                    nameInput.requestFocus();
                    return true;
                }

            }
        }

        UrlValidator urlValidator = new UrlValidator();
        if(!urlValidator.isValid(link)){
            errMsg = "Please provide a valid link for the website";
            linkInput.requestFocus();
            return true;
        }


        return false;
    }

    //
    public void fillEditContent(Competition competition, CompetitionController competitionController) throws IOException {
       compController = competitionController;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
       this.competition = competition;
        dateInput.setValue(LocalDate.parse(competition.dueDate, dateTimeFormatter));
        linkInput.setText(competition.websiteLink);
        nameInput.setText(competition.name);
        sizeInput.setText(String.valueOf(competition.teamSize));
    }

    public void fillEmptyContent(MainController controller, Stage stage){
        setIsTrack(true);
        mainController = controller;
        mainStage = stage;
    }

    // Edits a competition's details in the CompetitionsMemory instance
    @FXML
    public void editCompetition(ActionEvent actionEvent) throws IOException {
        if(invalid()){
            ErrorMessage errorMessage = Navigator.<ErrorMessage>card("error-msg");
            errorMessage.fillContent(errMsg);
            dialogHeader.getChildren().remove(2);
            dialogHeader.getChildren().add(2, errorMessage.getLabel());
            nameInput.requestFocus();
        }
        else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            competition.name = nameInput.getText();
            competition.websiteLink = validatedLink(linkInput.getText());
            competition.teamSize = Integer.parseInt(sizeInput.getText());
            competition.dueDate = String.valueOf((dateInput.getValue()).format(dateTimeFormatter));
            competition.isOpen = true; // always a valid date
            compController.fillContent(competition, compController);
            CompetitionsMemory.editCompetition(competition);

            // Closing the stage
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
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
        dialogHeader.getChildren().add(0, topBar);
    }
}
