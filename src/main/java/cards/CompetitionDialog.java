package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pages.CompetitionController;
import utils.CompetitionsMemory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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

    //
    public void fillContent(CompetitionController controller, String name, String websiteLink, int teamSize, String dueDate) throws IOException {
        controller.fillContent(controller);
        dateInput.setValue(LocalDate.parse(dueDate));
        linkInput.setText(websiteLink);
        nameInput.setText(name);
        sizeInput.setText(String.valueOf(teamSize));
    }

    // Edits a competition's details in the CompetitionsMemory instance
    public void editCompetition(ActionEvent actionEvent) {
        String name = nameInput.getText();
        String websiteLink = linkInput.getText();
        int teamSize = Integer.parseInt(sizeInput.getText());
        String dueDate = String.valueOf(LocalDate.parse(String.valueOf(dateInput.getValue())));

        CompetitionsMemory.INSTANCE.editCompetition(0, name, websiteLink, teamSize, dueDate);

        // Closing the stage
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
