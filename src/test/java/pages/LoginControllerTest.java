package pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {

    private Stage stage;// used to hold on the stage object

    // start the application
    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginControllerTest.class.getClassLoader().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        LoginController controller = fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }

    @AfterEach
    void tearDown() throws TimeoutException {
        FxToolkit.hideStage(); // closing the app after testing all use cases
         }

    @Test
    @DisplayName("Testing error message for wrong input")
    void authenticateInvalid(FxRobot robot) {
        // click on the button with the specified ID
        robot.clickOn("#loginBtn");
//         UI-based assertion using IDs
        FxAssert.verifyThat("#msgText", LabeledMatchers.hasText("Wrong Credentials! Please Enter Correct Credentials to continue"));
//         Another method to test the same

        // create a query to ge the element
        NodeQuery label= ( robot.lookup("#msgText"));
        // call the query, cast the type ans user normal assert
        assertEquals("Wrong Credentials! Please Enter Correct Credentials to continue", ((Label) label.query()).getText());
    }

    @Test
    @DisplayName("Testing error message for correct input")
    void authenticateValid(FxRobot robot){

    // click on text fields, insert text input and click login button
    robot.clickOn("#usernameInput").write("KFUPM Press1");
    robot.clickOn("#passwordInput").write("Pass12#34?");
    robot.clickOn("#loginBtn");

    // check if the stage has navigated to the main page

    assertEquals("mainRoot", stage.getScene().getRoot().getId());


    }
}