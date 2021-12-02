package pages;

import cards.ErrorMessage;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import models.User;
import utils.Navigator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.codec.digest.Crypt; // encryption package

public class LoginController {


    @FXML
    private VBox container;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField usernameInput;

    /**
     *
     * @param event the enter key trigger
     */
    @FXML
    void enterLogin(KeyEvent event) {
    if(event.getCode().equals(KeyCode.ENTER))
        logger(event);
    }

    @FXML
    void login(ActionEvent event) {
        logger(event);
    }

    private void logger(Event event){
        try{
            // authenticate(usernameInput.getText(), passwordInput.getText())
            if (true) {
                MainController controller = Navigator.<MainController>next("main", event);
                controller.fillContent(usernameInput.getText(), "s201945570@kfpupm.edu.sa");
            }else {
                ErrorMessage errorMessage = Navigator.<ErrorMessage>error("error-msg");
                errorMessage.fillContent("Wrong Credentials! Please Enter Correct Credentials to continue");
                container.getChildren().remove(1);
                container.getChildren().add(1, errorMessage.getLabel());
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private boolean authenticate(String username, String password){
        // we will use a txt file to store credentials for our users
        try {

            // encrypting input password to grant access via SHA-512 and Random salt
             String hashedPass = Crypt.crypt(password.strip(), "$6$" + new Random(password.length()).nextInt(100000)  + (int) password.charAt(3));
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("PasswordContainer.json"));
            reader.beginArray();
            while (reader.hasNext()) {
                User user = gson.fromJson(reader, User.class);
                System.out.println(hashedPass);
                if(user.username.equals(username) && user.password.equals(hashedPass)){
                    return true;
                }
               }
            reader.endArray();
            reader.close();
            System.out.println("INcorrect password!");

            return false;
        }catch (Exception e){
            System.out.println(e.getMessage() + " Duh!");
            return false;
        }
    }

}
