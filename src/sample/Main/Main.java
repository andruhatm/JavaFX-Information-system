package sample.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage window;

    //returns Application Stage obj
    public static Stage getWindow(){
        return window;
    }

    //set new obj to main window
    public static void setWindow(Stage window) {
        Main.window = window;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Main/Main.fxml"));
        window.setTitle("Аренда помещений");
        Scene main = new Scene(root);
        window.setScene(main);
        window.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
