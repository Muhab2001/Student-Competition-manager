package pages;

import cards.CompetitionCard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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
import org.testfx.util.WaitForAsyncUtils;
import utils.CompetitionsMemory;
import utils.ExcelStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MainControllerTest {


    private MainController mainController;
    private Stage stage;

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
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
    void tearDown() throws TimeoutException {
        FxToolkit.hideStage(); // closing the app after testing all use cases

    }


    @Test
    void fillContent(FxRobot robot) throws TimeoutException {
      assertAll(() ->  assertEquals(3, mainController.cards.size())
              ,() -> assertEquals("""
                      CompetitionCard{
                      id=0
                      , competitionId=comp-0
                      , name=testing competition #1
                      , status=Open
                      , teamNum=2
                      , teamSize=6}""", mainController.cards.get(0).toString()),
              () -> assertEquals("""
                      CompetitionCard{
                      id=1
                      , competitionId=comp-1
                      , name=testing competition #2
                      , status=Open
                      , teamNum=2
                      , teamSize=6}""", mainController.cards.get(1).toString()),
              () -> assertEquals("""
                      CompetitionCard{
                      id=2
                      , competitionId=comp-2
                      , name=testing competition #3
                      , status=Open
                      , teamNum=2
                      , teamSize=6}""", mainController.cards.get(2).toString()),
              () -> FxAssert.verifyThat("#email", LabeledMatchers.hasText("s201945570@kfpupm.edu.sa")),
              () -> FxAssert.verifyThat("#username", LabeledMatchers.hasText("username")));


    }

    @Test
    @DisplayName("Test for tracking competition")
    void trackCompetition(FxRobot robot) {

        robot.clickOn("#trackBtn");
        assertEquals("trackRoot", robot.lookup("#trackRoot").query().getScene().getRoot().getId());
    }

}