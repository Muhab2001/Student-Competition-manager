package pages;

import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Team;
import models.User;
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
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class CompetitionControllerTest {

    private MainController mainController;
    private ScrollPane scrollPane;


    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        CompetitionsMemory.competitions = ExcelStorage.getAllCompetitions();
        FXMLLoader fxmlLoader = new FXMLLoader(MainControllerTest.class.getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        mainController = fxmlLoader.getController();
        mainController.fillContent("username", "s201945570@kfpupm.edu.sa", mainController);
        stage.setScene(scene);
        stage.show();
        FxRobot robot = new FxRobot();
        robot.clickOn("#comp-0");


    }

    @AfterEach
    void tearDown() throws TimeoutException {
        FxToolkit.hideStage(); // closing the app after testing all use cases
        FxToolkit.cleanupStages();
    }

    @Test
    @DisplayName("test for successful ranking and emailing participants")
    void validAnnounceRanks(FxRobot robot) {
        // opening the dialog

        robot.clickOn("#announceRanking");
        VBox vbox = robot.lookup("#studentContainer").query();
        TextField field1 = robot.lookup("#teamslot-0").query();
        robot.clickOn(field1).write("1");

        robot.press(KeyCode.TAB).write("2");
//        robot.moveTo((TextField)robot.lookup("#teamslot-1").query()).clickOn().write("2");

        robot.clickOn("#rankRoot").clickOn("#confirmRankings");
        vbox = robot.lookup("#studentContainer").query();
        //testing opening email dialog;
        VBox finalVbox = robot.lookup("#studentContainer").query();;
        robot.clickOn((Button)robot.lookup("#teammail-0").query());
        robot.press(KeyCode.TAB).clickOn();
        assertAll(()-> {
            assertEquals("emailRoot", robot.lookup("#emailRoot").query().getScene().getRoot().getId());
        },
                // first team ranking
                ()-> {

            FxAssert.verifyThat("#teamrank-0", LabeledMatchers.hasText("1"));

                }, ()-> {


            FxAssert.verifyThat("#teamrank-1", LabeledMatchers.hasText("2"));
        });



    }

    @Test

    @DisplayName(" test for unsuccessful ranking and emailing participants")
    void invalidAnnounceRanks(FxRobot robot) {
        robot.clickOn("#announceRanking").clickOn("#confirmRankings");
        // empty input
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please provide a rank for all teams"));
        TextField field1 = robot.lookup("#teamslot-0").query();
        robot.clickOn(field1).write("1");
        robot.press(KeyCode.TAB).write("-2").clickOn("#confirmRankings");
        // negative numbers
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please provide positive values"));
        //non-numeric
        robot.clickOn("#teamslot-1").write("aasasa").clickOn("#confirmRankings");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please provide Numeric values for ranks"));



    }
    @Test
    @DisplayName("A successful mutation for competition details with no student overflow")
    void validEditDetailsNoOverflow(FxRobot robot) {
        robot.clickOn("#editDetailsBtn");
        TextField name = robot.lookup("#nameInput").query();
        TextField link = robot.lookup("#linkInput").query();
        TextField size = robot.lookup("#sizeInput").query();
        // checking pre-population for editing dialog
        assertAll(
                () -> assertEquals("editRoot", robot.lookup("#editRoot").query().getId()),
                () -> assertEquals("testing competition #1",(name).getText()),
                () -> assertEquals("https://ultrahack.org/aiot-hackathon-stc",(link).getText()),
                () -> assertEquals("6",(size).getText()));

        robot.write("edited").push(KeyCode.TAB).write("www.twitter.com")
                .push(KeyCode.TAB).write("7");
        robot.clickOn("#submitBtn");

        // checking visible changes
        Label sizeLabel =  robot.lookup("#sizeLabel").query();

        assertEquals("7", sizeLabel.getText());
        FxAssert.verifyThat("#competitionName", LabeledMatchers.hasText("edited"));
        // asserting the new website
        robot.clickOn("#visitWebsiteBtn");
        FxAssert.verifyThat("#websiteLink", LabeledMatchers.hasText("https://www.twitter.com"));
        assertEquals(7, CompetitionsMemory.getCompetition(0).teamSize); // competition info changed
        for(Team team: CompetitionsMemory.getCompetition(0).teams) {
            assertEquals(7, team.teamSize); // team info changed
            assertTrue(team.students.size() <= 7); // team size does not violate the upper limit
        }
    }

    @Test
    @DisplayName("A successful mutation for competition details with no student overflow")
    void validEditDetailsOverflow(FxRobot robot) {
        robot.clickOn("#editDetailsBtn");
        TextField name = robot.lookup("#nameInput").query();
        TextField link = robot.lookup("#linkInput").query();
        TextField size = robot.lookup("#sizeInput").query();
        // checking pre-population for editing dialog
        assertAll(
                () -> assertEquals("editRoot", robot.lookup("#editRoot").query().getId()),
                () -> assertEquals("testing competition #1", (name).getText()),
                () -> assertEquals("https://ultrahack.org/aiot-hackathon-stc", (link).getText()),
                () -> assertEquals("6", (size).getText()));

        robot.write("edited").push(KeyCode.TAB).write("www.twitter.com")
                .push(KeyCode.TAB).write("5"); // less than current number
        // confirming the overflow dialog
        robot.clickOn("#submitBtn").clickOn("#overflow-confirm");

        // checking visible changes
        Label sizeLabel = robot.lookup("#sizeLabel").query();

        assertEquals("5", sizeLabel.getText());
        FxAssert.verifyThat("#competitionName", LabeledMatchers.hasText("edited"));
        // asserting the new website
        robot.clickOn("#visitWebsiteBtn");
        FxAssert.verifyThat("#websiteLink", LabeledMatchers.hasText("https://www.twitter.com"));
        assertEquals(5, CompetitionsMemory.getCompetition(0).teamSize); // competition info changed
        for (Team team : CompetitionsMemory.getCompetition(0).teams) {
            assertEquals(5, team.teamSize); // team info changed
            assertTrue(team.students.size() <= 5); // team size does not violate the upper limit

        }
    }

    @Test
    void invalidEditDetails(FxRobot robot) {
        //empty input
        robot.clickOn("#editDetailsBtn").push(KeyCode.BACK_SPACE).clickOn("#submitBtn");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please fill all input fields before proceeding"));
        // negative size input
        robot.write("edited").doubleClickOn("#sizeInput").write("-1").clickOn("#submitBtn");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please enter positive numeric values in size field"));
        // non-numeric input
        robot.write("edited").doubleClickOn("#sizeInput").write("abvbvb").clickOn("#submitBtn");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please enter positive numeric values in size field"));
        //duplicate competition name
        robot.clickOn("#sizeInput").doubleClickOn().push(KeyCode.BACK_SPACE).write("2"); //providing valid team size to raise comp name error first
        robot.doubleClickOn("#nameInput").write("testing competition #2").clickOn("#submitBtn");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please avoid entering duplicate competition names"));

    }


    @Test
    @DisplayName("A test for successful new team creation")
    void validAddTeam(FxRobot robot) {
        VBox container = robot.lookup("#teamsContainer").query();
        robot.clickOn("#addTeamBtn");
        robot.push(KeyCode.TAB);
        robot.write("muhab c").push(KeyCode.TAB); // non-duplicate name, alphabetic only
        robot.write("201945580"); // valid KFUPM id
        robot.push(KeyCode.TAB);
        robot.write("SWE");
        robot.push(KeyCode.TAB);
        robot.write("muhab d").push(KeyCode.TAB); // non-duplicate name, alphabetic only
        robot.write("201945590"); // valid KFUPM id
        robot.push(KeyCode.TAB);
        robot.write("SWE");
        robot.clickOn("#confirm");
        // team num has changed
        FxAssert.verifyThat("#teamNumLAbel", LabeledMatchers.hasText("3"));
        assertEquals(3, container.getChildren().size());
        // student id is matching the entered name
        FxAssert.verifyThat("#stname-muhabc", LabeledMatchers.hasText("muhab c"));

    }

    @Test
    @DisplayName("A test for a successful team deletion")
    void validDeleteTeam(FxRobot robot) {
        VBox container = robot.lookup("#teamsContainer").query();
        robot.clickOn("#delete-teamrank-0").clickOn("#delete-confirm");
        assertEquals(1, container.getChildren().size());
    }

    @Test
    @DisplayName("A test for an unsuccessful new team creation")
    void invalidAddTeam(FxRobot robot) {
        VBox container = robot.lookup("#teamsContainer").query();
        robot.clickOn("#addTeamBtn");
        //empty teams case
        robot.clickOn("#confirm");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Cannot add an empty team"));
        // non-complete student card info
        robot.push(KeyCode.TAB).write("Muhab").clickOn("#confirm");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("To add a student you must provide full details"));
        // duplicate student from other teams case
        robot.push(KeyCode.TAB).write("Muhab").push(KeyCode.TAB).write("201945570").push(KeyCode.TAB).write("SWE").clickOn("#confirm");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Cannot add duplicate students' info from other teams"));
        // duplicate names and ids in same team
        robot.push(KeyCode.TAB).write("Muhab b").push(KeyCode.TAB).write("201945580").push(KeyCode.TAB).write("SWE");
        robot.push(KeyCode.TAB).write("Muhab b").push(KeyCode.TAB).write("201945580").push(KeyCode.TAB).write("CS");
        robot.clickOn("#confirm");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Cannot add duplicate students' info in one team"));
        // duplicate names only in same team
        robot.push(KeyCode.TAB).write("Muhab b").push(KeyCode.TAB).write("201945580").push(KeyCode.TAB).write("SWE");
        robot.push(KeyCode.TAB).write("Muhab b").push(KeyCode.TAB).write("201945590").push(KeyCode.TAB).write("CS");
        robot.clickOn("#confirm");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Cannot add duplicate students' info in one team"));
        // duplicate ids only in same team
        robot.push(KeyCode.TAB).write("Muhab b").push(KeyCode.TAB).write("201945580").push(KeyCode.TAB).write("SWE");
        robot.push(KeyCode.TAB).write("Muhab a").push(KeyCode.TAB).write("201945580").push(KeyCode.TAB).write("CS");
        robot.clickOn("#confirm");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Cannot add duplicate students' info in one team"));
        // invalid id
        robot.push(KeyCode.TAB).write("Muhab c").push(KeyCode.TAB).write("aaa").push(KeyCode.TAB).write("SWE").clickOn("#confirm");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please provide a valid KFUPM id number"));
        // invalid name
        robot.push(KeyCode.TAB).write("Muhab 4").push(KeyCode.TAB).write("201945590").push(KeyCode.TAB).write("SWE").clickOn("#confirm");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please provide alphabetic input only for name"));
        // invalid major
        robot.push(KeyCode.TAB).write("Muhaby").push(KeyCode.TAB).write("201945590").push(KeyCode.TAB).write("SWE4").clickOn("#confirm");
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Please provide a valid KFUPM major code"));

    }

    @Test
    @DisplayName("Testing the deletion of a competition")
    void delete(FxRobot robot) {
        CompetitionsMemory.CURRENT_USER = new User("muhab", "password", "s201945570@kfupm.edu.sa");
        robot.clickOn("#deleteBtn").clickOn("#delete-confirm");
        VBox box1 = robot.lookup("#vBox1").query();
        VBox box2 = robot.lookup("#vBox2").query();
        // there were 4 competitions previously
        assertEquals(3, box1.getChildren().size() + box2.getChildren().size());

    }

    @Test
    @DisplayName("Testing the population of a competition page")
    void fillContent(FxRobot robot) {
//        robot.clickOn("#compRoot").clickOn("#compBody");
        assertAll(
                () -> {robot.clickOn("#compRoot").clickOn("#compBody");
                    FxAssert.verifyThat("#competitionName", LabeledMatchers.hasText("testing competition #1"));
                },
                () -> {
                    robot.clickOn("#compRoot").clickOn("#compBody");
                    FxAssert.verifyThat("#teamNumLAbel", LabeledMatchers.hasText("2"));
                },
                () -> {robot.clickOn("#compRoot").clickOn("#compBody");
                    FxAssert.verifyThat("#sizeLabel", (LabeledMatchers.hasText("6")));
                },
                () -> {
                    robot.clickOn("#compRoot").clickOn("#compBody");
                    FxAssert.verifyThat("#statusLabel", LabeledMatchers.hasText("Open"));
                },
                () -> {
                    robot.clickOn("#compRoot").clickOn("#compBody");
                    FxAssert.verifyThat("#dateLabel", LabeledMatchers.hasText("12/23/2021"));
                },
                () -> {
                    robot.clickOn("#compRoot").clickOn("#compBody");
                    assertEquals(2, ((VBox) robot.lookup("#teamsContainer").query()).getChildren().size());
                }


        );
    }
}