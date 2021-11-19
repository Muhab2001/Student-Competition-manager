package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

public class WebsiteController {

    // used to fetch data when the element is displayed
    @FXML
    public void initialize(){

    }

    @FXML
    private Button returnBtn;

    @FXML
    private Label websiteLink;

    @FXML
    private WebView websiteViewer;

    @FXML
    void navigateBack(ActionEvent event) {

    }

}
