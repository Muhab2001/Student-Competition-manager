package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.TopBarPane;
import utils.TopBarable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CompetitionDialog implements TopBarable {

    // used to fetch data when the element is displayed
    @FXML
    public void initialize(){
        //this.stage = (Stage)root.getScene().getWindow();
        dateInput.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
            }
        });

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

    @FXML
    void trackCompetition(ActionEvent event) {
        // tracking a competition and validating
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void fillContent(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyy");
        dateInput.setValue(LocalDate.parse("11/19/2021", formatter));
        linkInput.setText("http://kaggle.com");
        nameInput.setText("Kaggle data competition");
        sizeInput.setText("3");

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
