package sample.AlertBox;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class AlertBox {
    //displays
    public void show(String title, String text) throws IOException {
        Stage window = new Stage();
        window.setTitle(title);
        window.setMinWidth(250);
        window.setHeight(150);
        window.initModality(Modality.APPLICATION_MODAL);

        Label l1 = new Label();
        Button b1 = new Button();
        l1.setText(text);
        b1.setText("OK");
        l1.setFont(Font.font("System",29));
        b1.setPrefWidth(40);
        b1.setOnAction(actionEvent -> window.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(50,25,30,25));
        layout.getChildren().addAll(l1,b1);
        layout.setAlignment(Pos.BOTTOM_CENTER);

        Scene alerting = new Scene(layout);
        window.setScene(alerting);
        window.showAndWait();
    }
}
