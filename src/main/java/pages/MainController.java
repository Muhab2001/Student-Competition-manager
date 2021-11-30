package pages;

import cards.CompetitionDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Navigator;

import java.io.IOException;

public class MainController {

    public MainController(){

    }

    // used to fetch data when the element is displayed
    @FXML
    public void initialize() throws IOException {
    // TODO: remove this snippet after testing menu population
        VBox vBox = new VBox(16);
        VBox vBox1 = new VBox(16);
        vBox1.setPadding(new Insets(14));
        vBox.setPadding(new Insets(14));
        for(int i = 0; i < 10; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition-card.fxml"));
            vBox1.getChildren().add((Node) fxmlLoader.load());
             fxmlLoader = new FXMLLoader(getClass().getResource("../competition-card.fxml"));
            vBox.getChildren().add((Node) fxmlLoader.load());
        }
        HBox vbox = new HBox(0);
        vbox.getChildren().addAll(vBox1, vBox);
        vbox.setAlignment(Pos.CENTER);
        CompetitionsContainer.setContent(vbox);
    }

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
                Navigator.<CompetitionDialog>nextDialog(new FXMLLoader(getClass().getResource("../competition-dialog.fxml")), "Track a New Competition");

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

