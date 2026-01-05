package electionInfoManager.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class JavaFXController extends Application {
    Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/foundationWindow.fxml"));
        Scene scene = new Scene(loader.load());
        this.primaryStage = stage;
        primaryStage.setTitle("Election Information System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}