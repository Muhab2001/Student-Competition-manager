package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.TopBarable;

public class ErrorDialog {

    @FXML
    private Button proceedBtn;
    @FXML
    private Label errContent;

    @FXML
    private Label errHeader;


    @FXML
    void proceed(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void fillContent(String header, String content){
        errHeader.setText(header);
        errContent.setText(content);
    }

}
