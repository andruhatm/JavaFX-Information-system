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
import sample.Recvizit;
import sample.adding.Adding;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class report4Controller implements Initializable {

    public AnchorPane pane;
    TableView<Recvizit> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table = new TableView<>();
        table.setPrefSize(550,230);
        table.setLayoutX(25);
        table.setLayoutY(85);
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
        TableColumn<Recvizit,Integer> IdColumn = new TableColumn<>("Ид");
        IdColumn.setMinWidth(50);
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));

        //Creating name column
        TableColumn<Recvizit,String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Creating INN column
        TableColumn<Recvizit, BigDecimal> InnColumn = new TableColumn<>("ИНН");
        InnColumn.setMinWidth(50);
        InnColumn.setCellValueFactory(new PropertyValueFactory<>("INN"));

        //Creating KPP column
        TableColumn<Recvizit,Integer> KppColumn = new TableColumn<>("КПП");
        KppColumn.setMinWidth(50);
        KppColumn.setCellValueFactory(new PropertyValueFactory<>("KPP"));

        //Creating BIC column
        TableColumn<Recvizit,Integer> BicColumn = new TableColumn<>("БИК");
        BicColumn.setMinWidth(50);
        BicColumn.setCellValueFactory(new PropertyValueFactory<>("BIC"));

        table.getColumns().add(IdColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(InnColumn);
        table.getColumns().add(KppColumn);
        table.getColumns().add(BicColumn);
        table.setItems(getInfo());
    }

    //returns list of renters with recvizits
    private ObservableList<Recvizit> getInfo() throws SQLException {
        ObservableList<Recvizit> info = FXCollections.observableArrayList();
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        String sql ="select \"Acc_Id\",\"name\",\"INN\",\"KPP\",\"BIC\" from \"Renters\" join \"Recvizit\" on \"Renters\".\"Acc_Id\"=\"Recvizit\".\"Id_recvizit\" ";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            info.add(new Recvizit(rs.getInt(1), rs.getString(2),rs.getBigDecimal(3),rs.getInt(4),rs.getInt(5)));
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
