package pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.Navigator;
import java.io.IOException;

public class LoginController {


    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField usernameInput;

    @FXML
    void login(ActionEvent event) throws IOException {
        //TODO: authenticating the credentials

        MainController controller = Navigator.<MainController>next(new FXMLLoader(getClass().getResource("../main.fxml")), event);
        controller.fillContent("realName", "s201945570@kfpupm.edu.sa");

    }

    private void authenticate(String username, String password){
        // we will use a txt file to store credentials for our users
    }

}
