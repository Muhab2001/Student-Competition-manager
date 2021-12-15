import cards.ErrorDialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Competition;
import pages.LoginController;
import utils.CompetitionsMemory;
import utils.ExcelStorage;
import utils.Navigator;

import java.io.IOException;

/**
 * The Application handler, the starting and ending point of the process
 */
public class Main extends Application {
    /**
     *
     * @param stage the startup stage, the `LoginController`
     * @throws Exception when the fallback FXML file is corrupted
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            LoginController controller = fxmlLoader.getController();
            controller.addTopBar(stage);
            stage.getIcons().add(new Image("img/48.png"));
            stage.setTitle("KFUPM Medalist");
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            utils.ExcelStorage.getAllCompetitions(); // testing thr excel package}
        }catch (Exception e){
            stop();
            ErrorDialog dialog = Navigator.<ErrorDialog>nextDialog("error", "Error");
            dialog.fillContent("Startup Error!",
                    "Corrupted files have prevented the program from launching properly");
        }
    }

    /**
     * the endpoint of the application process
     * @throws Exception when the excel storage file is corrupted
     */
//    @Override
//    public void stop() throws Exception {
//        super.stop();
//        ExcelStorage.saveChanges(CompetitionsMemory.INSTANCE.getCompetitions());
//        System.out.println("Data Saved Successfully! "); // TODO: delete test log
//    }
}

