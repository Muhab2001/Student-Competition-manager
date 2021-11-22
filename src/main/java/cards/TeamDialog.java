package cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TeamDialog {

    // used to fetch data when the element is displayed
    @FXML
    public void initialize() throws IOException {

        VBox vbox = new VBox(12);
        vbox.setPadding(new Insets(12));
        // TODO: replace with a dynamic population according to team number
        for(int i = 0; i < 10; i++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../student-card-edit.fxml"));
            vbox.getChildren().add((Node) fxmlLoader.load());

        }

        studentsContainer.setContent(vbox);
    }

    @FXML
    private Button confirm;

    @FXML
    private Label headerText;

    @FXML
    private ScrollPane studentsContainer;

    @FXML
    void mutateTeams(ActionEvent event) {
        // TODO: implementing a dynamic team mutation process
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setHeader(String header){
        headerText.setText(header);
    }

}