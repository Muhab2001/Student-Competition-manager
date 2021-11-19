package controllers;

import cards.CompetitionDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    // used to fetch data when the element is displayed
    @FXML
    public void initialize(){

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
        System.out.println("Tracking!");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition-dialog.fxml"));
        DialogPane dialogPane = fxmlLoader.load();
        CompetitionDialog dialogController = fxmlLoader.getController();
        dialogController.fillContent();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Track a new competition");
        dialog.setDialogPane(dialogPane);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
    }

    // retreiving an ID for a clicked object
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

