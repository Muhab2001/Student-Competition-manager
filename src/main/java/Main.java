import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("KFUPM Medalist");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        utils.ExcelStorage.getAllCompetitions(); // testing thr excel package
    }


}

