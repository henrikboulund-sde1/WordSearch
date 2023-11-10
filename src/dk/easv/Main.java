package dk.easv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args)
    {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        Parent rootScene = loader.load();
        primaryStage.setTitle("WordSearch");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(rootScene));
        primaryStage.show();


    }
}
