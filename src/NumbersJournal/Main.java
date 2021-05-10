package NumbersJournal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static boolean isHeadbandLoaded = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image app_ic = new Image("/resources/drawable/ic_confused.png");

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/layout/whole_stage.fxml")));
        primaryStage.getIcons().add(app_ic);
        String title = "Numbers Journal";
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
