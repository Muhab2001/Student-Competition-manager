package pages;

import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MainControllerTest {

    private Stage stage;


    @Start
    public void start(Stage stage) throws IOException {

    }

    @AfterEach
    void tearDown() throws TimeoutException {
        FxToolkit.hideStage(); // closing the app after testing all use cases
    }


    @Test
    void fillContent() {
    }

    @Test
    @DisplayName("Test for tracking competition")
    void trackCompetition() {
    }

    @Test
    @DisplayName("Test for filling the main page with existing competitions")
    void testFillContent() {
    }
}