package pages;

import cards.CompetitionCard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
import org.testfx.service.query.NodeQuery;
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
        stage.setScene(scene);
        stage.show();
    this.stage = stage;

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
    void invalidtrackCompetition(FxRobot robot) {

        robot.clickOn("#trackBtn");
        // asserting the root is visible
        assertEquals("trackRoot", robot.lookup("#trackRoot").query().getScene().getRoot().getId());
        robot.clickOn("#submitBtn"); // empty input
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please fill all input fields before proceeding"));
        robot.clickOn("#nameInput").write("aaaaa");
        robot.press(KeyCode.TAB).write("asasas");
        robot.clickOn("#sizeInput").write("-1"); // invalid team size
        robot.clickOn("#submitBtn");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please enter positive numeric values in size field"));
        //duplicate competition name
        robot.write("2"); //providing valid team size to raise comp name error first
        robot.doubleClickOn("#nameInput").write("testing competition #1").clickOn("#submitBtn");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please avoid entering duplicate competition names"));


    }

    @Test
    @DisplayName("Test for tracking competition")
    void validtrackCompetition(FxRobot robot) {
        robot.clickOn("#trackBtn");
        // asserting the root is visible
        assertEquals("trackRoot", robot.lookup("#trackRoot").query().getScene().getRoot().getId());
        robot.clickOn("#nameInput").write("aaaaa");
        robot.push(KeyCode.TAB).write("asasas");
        robot.clickOn("#sizeInput").write("1");
        robot.clickOn("#submitBtn");
        robot.clickOn("#returnBtn");
        FxAssert.verifyThat("#comp-aaaaa", LabeledMatchers.hasText("aaaaa")); // successful creation

    }


}