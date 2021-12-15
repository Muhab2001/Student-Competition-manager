package utils;

import javafx.event.Event;

import java.io.IOException;

/**
 * an interface used for warning dialogs with on confirm action listener
 */
public interface Confirmable {
    /**
     * listener for proceed button clicks
     * @param event
     * @throws IOException for fxml loading corruption
     */
    void onConfirm(Event event) throws IOException;
}
