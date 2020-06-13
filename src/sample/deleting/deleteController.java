package sample.deleting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Connect;
import sample.Renters;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class deleteController implements Initializable {

    public AnchorPane del_Pane;
    TableView<Renters> del_table;

    // returns ObservableList obj filled with all data from "Renters" and "renter_type"
    public ObservableList<Renters> getRenters() throws SQLException {

        ObservableList<Renters> renters = FXCollections.observableArrayList();
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        String sql ="select * from \"Renters\" JOIN renter_type  on \"Renters\".ren_type = renter_type.\"Id_type\"";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            renters.add(new Renters(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(8)));
            System.out.print(rs.getInt(1)+ " ");
            System.out.print(rs.getString(2)+ " ");
            System.out.print(rs.getString(3)+ " ");
            System.out.print(rs.getString(4)+ " ");
            System.out.print(rs.getInt(5)+ " ");
            System.out.println(rs.getString(8));
        }
        return renters;
    }

    // add columns to TableView obj
    public void fillTableView() throws SQLException {
        //Creating Id column
        TableColumn<Renters,Integer> IdColumn = new TableColumn<>("Ид");
        IdColumn.setMinWidth(50);
        IdColumn.setCellValueFactory(new PropertyValueFactory <>("Id"));

        //Creating name column
        TableColumn<Renters,String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory <>("name"));

        //Creating phone column
        TableColumn<Renters,String> phoneColumn = new TableColumn<>("Телефон");
        phoneColumn.setMinWidth(50);
        phoneColumn.setCellValueFactory(new PropertyValueFactory <>("phone"));

        //Creating email column
        TableColumn<Renters,String> emailColumn = new TableColumn<>("почта");
        emailColumn.setMinWidth(50);
        emailColumn.setCellValueFactory(new PropertyValueFactory <>("email"));

        //Creating renter_type column
        TableColumn<Renters,String> renter_typeColumn = new TableColumn<>("Тип");
        renter_typeColumn.setMinWidth(50);
        renter_typeColumn.setCellValueFactory(new PropertyValueFactory <>("renter_type"));

        //Adding columns
        del_table.getColumns().add(IdColumn);
        del_table.getColumns().add(nameColumn);
        del_table.getColumns().add(phoneColumn);
        del_table.getColumns().add(emailColumn);
        del_table.getColumns().add(renter_typeColumn);
        del_table.setItems(getRenters());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        del_table = new TableView<>();
        del_table.setPrefSize(660,205);
        del_table.setLayoutX(25);
        del_table.setLayoutY(70);
        del_Pane.getChildren().addAll(del_table);
        try {
            fillTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // creates request to db
    public void deleteRenter(ActionEvent event) throws SQLException {
        int choosenId = del_table.getSelectionModel().getSelectedItem().getId();
        System.out.println(choosenId);
        Connection connection = Connect.connect();
        PreparedStatement pst1,pst2,pst3,pst4;
        try{
            pst4 = connection.prepareStatement("delete from \"acc_operations\" where \"id_rentor\" = ?");
            pst4.setInt(1,choosenId);
            pst4.execute();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        try{
            pst1 = connection.prepareStatement("delete from \"Account\" where \"Id\" = ?");
            pst1.setInt(1,choosenId);
            pst1.execute();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        try{
            pst2 = connection.prepareStatement("delete from \"Renters\" where \"Acc_Id\" = ?");
            pst2.setInt(1,choosenId);
            pst2.execute();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        try{
            pst3 = connection.prepareStatement("delete from \"Recvizit\" where \"Id_recvizit\" = ?");
            pst3.setInt(1,choosenId);
            pst3.execute();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        del_table.getItems().remove(del_table.getSelectionModel().getSelectedItem());
    }

    // returns to main menu
    public void back(javafx.event.ActionEvent event) throws IOException {
        System.out.println("In method");
        Parent table = FXMLLoader.load(getClass().getResource("/sample/Main/Main.fxml"));
        Scene tableView = new Scene(table);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableView);
        window.show();
    }
}
