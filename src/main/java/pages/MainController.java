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
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.Navigator;
import utils.TopBarPane;
import utils.TopBarable;

import java.io.IOException;

public class MainController implements TopBarable {

    @FXML
    private VBox root;
    public MainController(){

    }

    // used to fetch data when the element is displayed
    @FXML
    public void initialize() throws IOException {
    // TODO: remove this snippet after testing menu population

        vBox1.setPadding(new Insets(14));
        vBox2.setPadding(new Insets(14));
        vBox2.setAlignment(Pos.CENTER);
        vBox1.setAlignment(Pos.CENTER);
        for(int i = 0; i < 10; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../competition-card.fxml"));
            vBox1.getChildren().add((Node) fxmlLoader.load());
             fxmlLoader = new FXMLLoader(getClass().getResource("../competition-card.fxml"));
            vBox2.getChildren().add((Node) fxmlLoader.load());
        }

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
    private VBox vBox1;

    @FXML
    private VBox vBox2;

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


    @Override
    public void addTopBar(Stage stage) {
        TopBarPane topBar = new TopBarPane(stage,"Competitions");
        root.getChildren().add(0,topBar);
    }
}

