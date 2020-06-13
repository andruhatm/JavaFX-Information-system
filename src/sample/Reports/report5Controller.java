package sample.Reports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.Connect;
import sample.Main.Main;
import sample.RenterBalance;
import sample.adding.Adding;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class report5Controller implements Initializable {

    public AnchorPane pane;
    TableView<RenterBalance> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table = new TableView<>();
        table.setPrefSize(540,260);
        table.setLayoutX(35);
        table.setLayoutY(80);
        pane.getChildren().addAll(table);
        try {
            fillTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //add columns to TableView obj
    private void fillTableView() throws SQLException {
        //Creating Id column
        TableColumn<RenterBalance,Integer> IdColumn = new TableColumn<>("Ид");
        IdColumn.setMinWidth(50);
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        //Creating name column
        TableColumn<RenterBalance,String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory <>("name"));
        //Creating balance column
        TableColumn<RenterBalance,Double> balanceColumn = new TableColumn<>("Баланс");
        balanceColumn.setMinWidth(50);
        balanceColumn.setCellValueFactory(new PropertyValueFactory <>("balance"));
        //Creating saldo column
        TableColumn<RenterBalance,Double> saldoColumn = new TableColumn<>("Сальдо");
        saldoColumn.setMinWidth(50);
        saldoColumn.setCellValueFactory(new PropertyValueFactory <>("saldo"));

        table.getColumns().add(IdColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(balanceColumn);
        table.getColumns().add(saldoColumn);
        table.setItems(getInfo());
    }

    //add columns to TableView obj
    private ObservableList<RenterBalance> getInfo() throws SQLException {
        ObservableList<RenterBalance> info = FXCollections.observableArrayList();
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        String sql ="SELECT \"Acc_Id\",\"name\",\"Balance\",\"Saldo\" FROM \"Renters\" JOIN \"Account\" ON \"Renters\".\"Acc_Id\"=\"Account\".\"Id\" WHERE \"Balance\"<0 ";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            info.add(new RenterBalance(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4)));
        }
        return info;
    }

    //returns back to main menu
    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/reportsMenu/ReportsMenu.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }
}
