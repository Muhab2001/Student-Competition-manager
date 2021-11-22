package pages;

import cards.CompetitionDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainController {

    public MainController(){

    }

    // used to fetch data when the element is displayed
    @FXML
    public void initialize() throws IOException {
    // TODO: remove this snippet after testing menu population
        HBox hbox1 = new HBox(16);
        HBox hbox = new HBox(16);
        hbox.setPadding(new Insets(16));
        hbox1.setPadding(new Insets(16));
        for(int i = 0; i < 10; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition-card.fxml"));
            hbox.getChildren().add((Node) fxmlLoader.load());
             fxmlLoader = new FXMLLoader(getClass().getResource("../competition-card.fxml"));
            hbox1.getChildren().add((Node) fxmlLoader.load());
        }
        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(hbox, hbox1);
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
        System.out.println("Tracking!");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition-dialog.fxml"));
        DialogPane dialogPane = fxmlLoader.load();
        CompetitionDialog dialogController = fxmlLoader.getController();
//        dialogController.fillContent(); should not be pre-populated on new creation
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Track a new competition");
        dialog.setDialogPane(dialogPane); // fxml as a dialog
        dialog.initStyle(StageStyle.UNDECORATED); // TODO: custom bar
        dialog.show();
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

