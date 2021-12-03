package pages;

import cards.CompetitionDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Navigator;

import java.io.IOException;

public class MainController {



    // used to fetch data when the element is displayed
    @FXML
    public void initialize() throws IOException {
    // TODO: get content from the competitions ArrayList from CompetitionsMemory

        for(int i = 0; i < 10; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition-card.fxml"));
            vbox1.getChildren().add((Node) fxmlLoader.load());
             fxmlLoader = new FXMLLoader(getClass().getResource("../competition-card.fxml"));
            vbox2.getChildren().add((Node) fxmlLoader.load());
        }

    }

    @FXML
    private VBox vbox1;

    @FXML
    private VBox vbox2;

    @FXML
    private ScrollPane CompetitionsContainer;

    @FXML
    private Label email;

    @FXML
    private Button trackBtn;

    @FXML
    private Label username;

    @FXML
    void trackCompetition(ActionEvent event) throws IOException {
        CompetitionDialog dialogController =
                Navigator.<CompetitionDialog>nextDialog("competition", "Track a New Competition");

    }

    //TODO : remove after testing
    @FXML
    void profileClicked(MouseEvent event) {
        Node node = (Node) event.getSource();
        String view = (String) node.getId();
        System.out.println(view);
    }

    public void fillContent(String name, String email){
        username.setText(name);
        this.email.setText(email);
    }


}

