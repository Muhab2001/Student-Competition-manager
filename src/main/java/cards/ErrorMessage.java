package cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorMessage {

    @FXML
    private Label msgText;

    public void fillContent(String msg){
        msgText.setText(msg);
    }

    public Label getLabel(){
        return msgText;
    }

}
