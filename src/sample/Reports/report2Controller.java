package sample.Reports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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

public class report2Controller implements Initializable {

    TableView<acc_operation> table1;
    @FXML
    public AnchorPane pane;
    @FXML
    ComboBox renterBox;

    //returns Id of chosen renter in renterBox
    public int getId() throws SQLException {
        int Id=0;
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        String sql = "select \"Acc_Id\" from \"Renters\" where name = '" + renterBox.getSelectionModel().getSelectedItem().toString() +"' ";
        System.out.println(sql);
        ResultSet result = statement.executeQuery(sql);
        while (result.next()){
            Id=result.getInt("Acc_Id");
        }
        return Id;
    }

    //fills ComboBox on click
    public void onTable(ActionEvent event){
        try {
            fillTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table1 = new TableView<>();
        table1.setPrefSize(770,230);
        table1.setLayoutX(20);
        table1.setLayoutY(140);
        pane.getChildren().addAll(table1);
        try {
            renterBox.setItems(getRenters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TableColumn<acc_operation,Integer> IdColumn = new TableColumn<>("Ид");
        IdColumn.setPrefWidth(60);
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));

        TableColumn<acc_operation,Integer> RenterIdColumn = new TableColumn<>("Ид Рентора");
        RenterIdColumn.setPrefWidth(120);
        RenterIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_rentor"));


        TableColumn<acc_operation, Date> DateColumn = new TableColumn<>("Дата");
        DateColumn.setMinWidth(110);
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<acc_operation, Double> AmountColumn = new TableColumn<>("Сумма");
        AmountColumn.setMinWidth(50);
        AmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<acc_operation, String> commentColumn = new TableColumn<>("Комментарий");
        commentColumn.setPrefWidth(100);
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));


        TableColumn<acc_operation,Integer> CurrencyTypeColumn = new TableColumn<>("Валюта");
        CurrencyTypeColumn.setMinWidth(20);
        CurrencyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        TableColumn<acc_operation,Integer> OperationTypeColumn = new TableColumn<>("Операция");
        OperationTypeColumn.setPrefWidth(200);
        OperationTypeColumn.setCellValueFactory(new PropertyValueFactory<>("op_type"));

        table1.getColumns().add(IdColumn);
        table1.getColumns().add(RenterIdColumn);
        //table1.getColumns().add(RecvizitIdColumn);
        table1.getColumns().add(DateColumn);
        table1.getColumns().add(AmountColumn);
        table1.getColumns().add(commentColumn);
        //table1.getColumns().add(RenterTypeColumn);
        table1.getColumns().add(CurrencyTypeColumn);
        table1.getColumns().add(OperationTypeColumn);
    }

    //returns all renter names
    public ObservableList getRenters() throws SQLException {
        ObservableList <String> renters = FXCollections.observableArrayList();
        Connection connect = Connect.connect();
        Statement statement = connect.createStatement();
        String sql = "select name from \"Renters\"";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            renters.add(rs.getString("name"));
        }
        return renters;
    }

    private void fillTableView() throws SQLException {
        table1.setItems(getAccOp());
    }

    //returns list of operations
    private ObservableList<acc_operation> getAccOp() throws SQLException {
        ObservableList<acc_operation> operations = FXCollections.observableArrayList();
        Connection connection = Connect.connect();
        PreparedStatement pst1;
        pst1=connection.prepareStatement("SELECT * FROM \"acc_operations\" join currency_type ct on acc_operations.id_currency = ct.\"Id_currency\" join operation_type ot on acc_operations.id_op_type = ot.\"Id_operation\" WHERE \"id_rentor\"="+getId());
        ResultSet rs =pst1.executeQuery();
        while (rs.next()){
            System.out.println("rs next works");
            operations.add(new acc_operation(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDate(4),rs.getDouble(5),rs.getString(6),rs.getInt(7),rs.getString(11),rs.getString(14)));
        }
        return operations;
    }

    //returns back to main scene
    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/reportsMenu/ReportsMenu.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }
}
