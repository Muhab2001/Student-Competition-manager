package pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import utils.CompetitionsMemory;
import utils.ExcelStorage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class WebsiteControllerTest {

    private Stage stage;
    private MainController mainController;

    @Start
    public void start(Stage stage) throws IOException {
        CompetitionsMemory.competitions = ExcelStorage.getAllCompetitions();
        FXMLLoader fxmlLoader = new FXMLLoader(MainControllerTest.class.getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        mainController = fxmlLoader.getController();
        mainController.fillContent("username", "s201945570@kfpupm.edu.sa", mainController);
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    @AfterEach
    void tearDown(FxRobot robot) throws TimeoutException {
        FxToolkit.hideStage(); // closing the app after testing all use cases
    }

    @Test
    @DisplayName("Testing back navigation with connection")
    void validnavigateBack(FxRobot robot) {
        robot.clickOn("#comp-0");
        robot.clickOn("#visitWebsiteBtn");
        robot.clickOn("#returnBtn");
        assertEquals("compRoot", robot.lookup("#compRoot").query().getScene().getRoot().getId());
    }

    @Test
    @DisplayName("Testing back navigation with no connection")
    void invalidnavigateBack(FxRobot robot) {
        robot.clickOn("#comp-2");
        robot.clickOn("#visitWebsiteBtn");
        robot.clickOn("#proceedBtn");
        robot.clickOn("#returnBtn");
        assertEquals("compRoot", robot.lookup("#compRoot").query().getScene().getRoot().getId());
    }

    @Test
    @DisplayName("Testing the successful population of a website with connection")
    void showWebsiteValid(FxRobot robot) {
        robot.clickOn("#comp-0");
        robot.clickOn("#visitWebsiteBtn");
        FxAssert.verifyThat("#websiteLink", LabeledMatchers.hasText("https://ultrahack.org/aiot-hackathon-stc"));
    }

    @Test
    @DisplayName("Testing the unsuccessful population of a website with no connection")
    void showWebsiteInvalid(FxRobot robot) {

        robot.clickOn("#comp-2");
        robot.clickOn("#visitWebsiteBtn");
                FxAssert.verifyThat("#errHeader", LabeledMatchers.hasText("No Internet Connection!"));
    }
}