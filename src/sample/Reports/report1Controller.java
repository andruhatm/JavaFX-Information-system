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
import sample.acc_operation;
import sample.adding.Adding;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class report1Controller implements Initializable {

    public AnchorPane report1Pane;
    TableView<acc_operation> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table = new TableView<>();
        table.setPrefSize(770,290);
        table.setLayoutX(20);
        table.setLayoutY(65);
        report1Pane.getChildren().addAll(table);
        try {
            fillTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillTableView() throws SQLException {

        TableColumn<acc_operation,Integer> IdColumn = new TableColumn<>("Ид");
        IdColumn.setMinWidth(50);
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));

        TableColumn<acc_operation,Integer> RenterIdColumn = new TableColumn<>("Ид рентора");
        RenterIdColumn.setMinWidth(50);
        RenterIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_rentor"));


        TableColumn<acc_operation, Date> DateColumn = new TableColumn<>("Дата");
        DateColumn.setMinWidth(50);
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<acc_operation, Double> AmountColumn = new TableColumn<>("Сумма");
        AmountColumn.setMinWidth(50);
        AmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<acc_operation, String> commentColumn = new TableColumn<>("Комментарий");
        commentColumn.setMinWidth(50);
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        TableColumn<acc_operation,String> CurrencyTypeColumn = new TableColumn<>("Валюта");
        CurrencyTypeColumn.setMinWidth(50);
        CurrencyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        TableColumn<acc_operation,String> OperationTypeColumn = new TableColumn<>("Операция");
        OperationTypeColumn.setMinWidth(50);
        OperationTypeColumn.setCellValueFactory(new PropertyValueFactory<>("op_type"));

        table.getColumns().add(IdColumn);
        table.getColumns().add(RenterIdColumn);
        table.getColumns().add(DateColumn);
        table.getColumns().add(AmountColumn);
        table.getColumns().add(commentColumn);
        table.getColumns().add(CurrencyTypeColumn);
        table.getColumns().add(OperationTypeColumn);
        table.setItems(getAccOp());

    }

    //returns list of operations
    public ObservableList<acc_operation> getAccOp() throws SQLException {

        ObservableList<acc_operation> operations = FXCollections.observableArrayList();
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        String sql ="SELECT * FROM \"acc_operations\" join currency_type ct on acc_operations.id_currency = ct.\"Id_currency\" join operation_type ot on acc_operations.id_op_type = ot.\"Id_operation\"";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            System.out.println("rs next works");
            operations.add(new acc_operation(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDate(4),rs.getDouble(5),rs.getString(6),rs.getInt(7),rs.getString(11),rs.getString(14)));
        }
        return operations;
    }

    //returns to main menu
    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/reportsMenu/ReportsMenu.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }
}
