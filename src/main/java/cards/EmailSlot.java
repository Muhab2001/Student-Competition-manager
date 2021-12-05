package cards;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EmailSlot {

    @FXML
    private Button emailBtn;

    @FXML
    private Label rankValue;

    @FXML
    private VBox studentContainer;

    public VBox getStudentContainer() {return studentContainer;}


}
