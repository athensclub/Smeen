package smeen.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import smeen.views.MainView;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Smeen Islam");
        stage.setScene(new Scene(new MainView()));
        stage.show();
    }
}