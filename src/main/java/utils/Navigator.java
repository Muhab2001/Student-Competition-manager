package utils;

import cards.TeamDialog;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;


public class Navigator{


    public Navigator INSTANCE = new Navigator();

    private Navigator(){
    }


    // method for navigating to a page
    public static <T> T next(String dist, Event context) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Navigator.class.getClassLoader().getResource(dist+".fxml"));
        Stage stage = (Stage)((Node) context.getSource()).getScene().getWindow(); // get the current stage
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setScene(scene);
        stage.show();
        return fxmlLoader.getController();
    }

    // method for showing dialog
    public static <T> T nextDialog(String dist, String dialogTitle) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Navigator.class.getClassLoader().getResource(dist + "-dialog.fxml"));
        DialogPane dialogPane = fxmlLoader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(dialogTitle);
        dialog.setDialogPane(dialogPane); // fxml as a dialog
        dialog.initStyle(StageStyle.UNDECORATED); // TODO: custom bar
        dialog.show();
        return fxmlLoader.getController();

    }

    public static <T> T card(String dist) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Navigator.class.getClassLoader().getResource(dist+".fxml"));
        fxmlLoader.load();
        return fxmlLoader.getController();
    }

}
