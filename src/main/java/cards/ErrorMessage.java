package cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * controller class for error validation messages
 */
public class ErrorMessage {

    @FXML
    private Label msgText;

    /**
     * populating the message with error info
     * @param msg
     */
    public void fillContent(String msg){
        msgText.setText(msg);
    }

    /**
     * method to retreive msg text
     * @return error text
     */
    public Label getLabel(){
        return msgText;
    }

}
