package utils;

import javafx.event.Event;

import java.io.IOException;

/**
 * an interface used for warning dialogs with on confirm action listener
 */
public interface Confirmable {

    void onConfirm(Event event) throws IOException;
}
