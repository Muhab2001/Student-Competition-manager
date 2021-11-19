package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CompetitionDialog {

    // used to fetch data when the element is displayed
    @FXML
    public void initialize(){
        dateInput.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0 );
            }
        });
    }

    private Stage stage;

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

}
