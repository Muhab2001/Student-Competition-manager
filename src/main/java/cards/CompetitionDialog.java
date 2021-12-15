package cards;

import javafx.css.PseudoClass;
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
import models.Student;
import models.Team;
import org.apache.commons.validator.routines.UrlValidator;
import org.jetbrains.annotations.NotNull;
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
    private CompetitionDialog dialogController;
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
        dateInput.setValue(LocalDate.now());

    }

    private Stage stage;

    private boolean isTrack;

    @FXML
    private VBox dialogHeader;

    @FXML
    private DialogPane trackRoot;

    @FXML
    private DialogPane editRoot;


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
    void cancel(@NotNull ActionEvent event) {
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void enterTrack(@NotNull KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER))
            tracker(event);
    }

    @FXML
    void trackCompetition(ActionEvent event) throws IOException {
        tracker(event);
    }

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
            controller.fillContent(competition, controller, true);
            System.out.println(competition);
        }
    }

    @FXML
    public void editCompetition(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        if(invalid()){
            ErrorMessage errorMessage = Navigator.<ErrorMessage>card("error-msg");
            errorMessage.fillContent(errMsg);
            dialogHeader.getChildren().remove(2);
            dialogHeader.getChildren().add(2, errorMessage.getLabel());
            nameInput.requestFocus();
        }
        else {
            int maxOccupied = 0;
            int currentOccupied = 0;
            for(Team team: competition.teams) {
                currentOccupied = 0;
                for (Student student : team.students)
                    if (student.name.length() != 0)
                        currentOccupied++;
                if(currentOccupied > maxOccupied)
                    maxOccupied = currentOccupied;
            }
            System.out.println(maxOccupied + " " + Integer.parseInt(sizeInput.getText()));
            if(maxOccupied <= Integer.parseInt(sizeInput.getText())) {
                submitEdited();
                // Closing the stage

            }else{
                // fire a confirmation dialog
                StudentOverflowConfirm controller = Navigator.nextDialog("overflow", "Student number overflow");
                controller.fillContent(maxOccupied - Integer.parseInt(sizeInput.getText()) , competition, compController, dialogController);
            }
        }
    }

    public void submitEdited() throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        competition.name = nameInput.getText();
        competition.websiteLink = validatedLink(linkInput.getText());
        competition.teamSize = Integer.parseInt(sizeInput.getText());
        competition.dueDate = String.valueOf((dateInput.getValue()).format(dateTimeFormatter));
        competition.isOpen = true; // always a valid date
        compController.fillContent(competition, compController, true);
        CompetitionsMemory.editCompetition(competition);
        for(Team team: competition.teams){
            team.teamSize = competition.teamSize; // syncing size changes;
        }
        stage.close();
    }


    private boolean invalid(){
        boolean case1 = dateInput.getValue() == (null) || sizeInput.getText().strip().length() == 0 || linkInput.getText().strip().length() == 0 || nameInput.getText().strip().length() == 0;
        String link = validatedLink(linkInput.getText());
        System.out.println(dateInput.getValue() + " " + sizeInput.getText().length() + " " + linkInput.getText().length() + " " + nameInput.getText().strip().length() );
        clearError(sizeInput);
        clearError(nameInput);
        clearError(linkInput);

        if(case1){
            errMsg = "Please fill all input fields before proceeding";
            if(sizeInput.getText().length() == 0) flagError(sizeInput);
            if(nameInput.getText().length() == 0) flagError(nameInput);
            if(linkInput.getText().length() == 0) flagError(linkInput);
            return true;
        }

        try{
            int size = Integer.parseInt(sizeInput.getText());
            if (size < 0) throw new Exception();
        }catch (Exception e){
            errMsg = "Please enter positive numeric values in size field";
            sizeInput.requestFocus();
            flagError(sizeInput);
            return true;
        }


            for (Competition competition : CompetitionsMemory.INSTANCE.getCompetitions()) {
                if(isTrack || !this.competition.equals(competition))
                if (competition.name.equals(nameInput.getText().strip())) {
                    errMsg = "Please avoid entering duplicate competition names";
                    nameInput.requestFocus();
                    flagError(nameInput);
                    return true;
                }

            }


        UrlValidator urlValidator = new UrlValidator();
        if(!urlValidator.isValid(link)){
            errMsg = "Please provide a valid link for the website";
            linkInput.requestFocus();
            flagError(linkInput);
            return true;
        }


        return false;
    }


    public void fillEditContent(@NotNull Competition competition, CompetitionController competitionController, CompetitionDialog dialogController) throws IOException {
       compController = competitionController;
       this.dialogController = dialogController;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
       this.competition = competition;
        dateInput.setValue(LocalDate.parse(competition.dueDate, dateTimeFormatter));
        linkInput.setText(competition.websiteLink);
        nameInput.setText(competition.name);
        sizeInput.setText(String.valueOf(competition.teamSize));
    }

    public void fillEmptyContent(MainController controller, Stage stage){
        setIsTrack(true);
        mainStage = stage;
    }




    @NotNull
    private String validatedLink(String link) {
        String newLink = link;

        if (!(newLink.startsWith("https") || newLink.startsWith("http"))) // if no http protocol has been used
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
        TopBarPane topBar;
        if(isTrack)
        topBar = new TopBarPane((Stage) trackRoot.getScene().getWindow(),title);
        else
            topBar = new TopBarPane((Stage) editRoot.getScene().getWindow(),title);
        dialogHeader.getChildren().add(0, topBar);
    }

    public void flagError(TextField field){
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        field.pseudoClassStateChanged(errorClass, true);

    }

    public void clearError(TextField field){
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        field.pseudoClassStateChanged(errorClass, false);

    }
}
