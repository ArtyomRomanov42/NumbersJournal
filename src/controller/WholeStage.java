package controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static NumbersJournal.Main.isHeadbandLoaded;

public class WholeStage implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {
        if (!isHeadbandLoaded) {
            loadHeadbandScr();
        }
    }

    public AnchorPane root;

    private void loadHeadbandScr() {
        try {
            isHeadbandLoaded = true;

            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(("/resources/layout/headband.fxml"))));
            root.getChildren().setAll(pane);

            FadeTransition fadeStart = new FadeTransition(Duration.seconds(2), pane);
            fadeStart.setFromValue(0);
            fadeStart.setToValue(1);
            fadeStart.setCycleCount(1);

            FadeTransition fadeEnd = new FadeTransition(Duration.seconds(3), pane);
            fadeEnd.setFromValue(1);
            fadeEnd.setToValue(0);
            fadeEnd.setCycleCount(1);

            fadeStart.play();

            fadeStart.setOnFinished((e) -> fadeEnd.play());

            fadeEnd.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/layout/menu.fxml")));

                    root.getChildren().setAll(parentContent);

                } catch (IOException ex) {
                    // ошибка
                }
            });

        } catch (IOException ex) {
            // ничего не делаем...
        }
    }
}
