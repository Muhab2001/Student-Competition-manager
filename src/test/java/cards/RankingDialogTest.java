package cards;

import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class RankingDialogTest {

    private Stage stage;


    @Start
    public void start(Stage stage) throws IOException {

    }

    @AfterEach
    void tearDown() throws TimeoutException {
        FxToolkit.hideStage(); // closing the app after testing all use cases
    }

    @Test
    void confirmRanking() {
    }

    @Test
    void fillContent() {
    }
}