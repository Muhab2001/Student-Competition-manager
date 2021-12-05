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

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        LoginController controller = fxmlLoader.getController();
        controller.addTopBar(stage);
        stage.setTitle("KFUPM Medalist");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("img/48.png"));
        stage.setScene(scene);
        stage.show();
        utils.ExcelStorage.getAllCompetitions(); // testing thr excel package
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ExcelStorage.saveChanges(CompetitionsMemory.INSTANCE.getCompetitions());
        System.out.println("Data Saved Successfully! "); // TODO: delete test log
    }
}

