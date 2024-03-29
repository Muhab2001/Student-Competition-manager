package utils;

import cards.TeamDialog;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;

/**
 * Class to handle FXML loading for page navigation, and dialog popups, and card population
 */
public class Navigator{


    public Navigator INSTANCE = new Navigator();

    private Navigator(){
    }


    /**
     * method to navigate between pages
     * @param dist filename without `.fxml` suffix
     * @param context event of the calling controller
     * @param <T> a node that implementes the interface Topbarable
     * @return the loaded FXML controller
     * @throws IOException when the FXML file is corrupted
     */
    public static <T extends TopBarable> T next(String dist, Event context) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Navigator.class.getClassLoader().getResource(dist+".fxml"));
        Stage stage = (Stage)((Node) context.getSource()).getScene().getWindow(); // get the current stage
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        T controller = fxmlLoader.getController();
        stage.getIcons().add(new Image("img/48.png"));
       controller.addTopBar(stage);
        stage.setScene(scene);
        stage.show();
        return controller;
    }

    public static <T extends TopBarable> T back(String dist, Stage stageIn) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Navigator.class.getClassLoader().getResource(dist+".fxml"));
        Stage stage = stageIn; // get the current stage
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        T controller = fxmlLoader.getController();
        stage.getIcons().add(new Image("img/48.png"));
        controller.addTopBar(stage);
        stage.setScene(scene);
        stage.show();
        return controller;
    }

    /**
     * method to open dialog pop ups
     * @param dist filename without `-dialog.fxml` suffix
     * @param dialogTitle dialog title
     * @param <T> node type
     * @return the loaded FXML controller
     * @throws IOException when the FXML file is corrupted
     */
    public static <T> T nextDialog(String dist, String dialogTitle) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Navigator.class.getClassLoader().getResource(dist + "-dialog.fxml"));
        DialogPane dialogPane = fxmlLoader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(dialogTitle);
        dialog.setDialogPane(dialogPane); // fxml as a dialog
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.show();
        return fxmlLoader.getController();

    }

    /**
     * method to populate certain cards (excluding cards requiring FXML loader)
     * @param dist filename without `.fxml` suffix
     * @param <T> node type
     * @return the loaded FXML controller
     * @throws IOException when the FXML file is corrupted
     */
    public static <T> T card(String dist) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Navigator.class.getClassLoader().getResource(dist+".fxml"));
        fxmlLoader.load();
        return fxmlLoader.getController();
    }


}
