package sample.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    /*Controller class consists of methods which changes main scene to chosen*/
    @FXML
    public void deleting(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/sample/deleting/delete.fxml"));
        Parent delete = loader.load();
        Scene deletingRenter = new Scene(delete);
        Main.getWindow().setScene(deletingRenter);
    }
    @FXML
    public void newRenter(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/sample/adding/adding.fxml"));
        Parent newRenter = loader.load();
        Scene addingRenter = new Scene(newRenter);
        Main.getWindow().setScene(addingRenter);
}
    @FXML
    public void edit(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/sample/editing/editing.fxml"));
        Parent edit = loader.load();
        Scene editingRenter = new Scene(edit);
        Main.getWindow().setScene(editingRenter);
    }
    @FXML
    public void pay(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/sample/pay/Pay.fxml"));
        Parent pay = loader.load();
        Scene paying = new Scene(pay);
        Main.getWindow().setScene(paying);
    }
    @FXML
    public void reports(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/sample/reportsMenu/ReportsMenu.fxml"));
        Parent report = loader.load();
        Scene reports = new Scene(report);
        Main.getWindow().setScene(reports);
    }
    @FXML
    public void exchange(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/sample/exchange/exchange.fxml"));
        Parent exchange = loader.load();
        Scene exchangescene = new Scene(exchange);
        Main.getWindow().setScene(exchangescene);
    }
    @FXML
    public void newPropType(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/sample/adding/propertyType.fxml"));
        Parent newprop = loader.load();
        Scene propType = new Scene(newprop);
        Main.getWindow().setScene(propType);
    }
    @FXML
    public void newCurrency(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/sample/adding/currency.fxml"));
        Parent newCurrency = loader.load();
        Scene curAdd = new Scene(newCurrency);
        Main.getWindow().setScene(curAdd);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main fxml successfully loaded");
    }
}

