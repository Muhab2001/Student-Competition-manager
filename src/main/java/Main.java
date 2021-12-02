import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Competition;
import utils.CompetitionsMemory;
import utils.ExcelStorage;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("KFUPM Medalist");
        stage.setResizable(false);
        stage.getIcons().add(new Image("img/32.png"));
        stage.setScene(scene);
        stage.show();
        utils.ExcelStorage.getAllCompetitions(); // testing thr excel package
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ExcelStorage.saveChanges(CompetitionsMemory.INSTANCE.getCompetitions());
    }
}

