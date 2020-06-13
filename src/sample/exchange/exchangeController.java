package sample.exchange;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Connect;
import sample.Currency;
import sample.Main.Main;
import sample.adding.Adding;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class exchangeController implements Initializable {

    public AnchorPane pane;
    public TextField CurValue,CurName,Id;
    public TableView <Currency> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setPrefSize(550,220);
        table.setLayoutX(25);
        table.setLayoutY(85);
        table.setEditable(true);
        try {
            fillTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // adds columns to TableView obj
    private void fillTableView() throws SQLException{
        //Creating Id column
        TableColumn<Currency,Integer> IdColumn = new TableColumn<>("Id");
        IdColumn.setMinWidth(50);
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));

        //Creating name column
        TableColumn<Currency,String> currencyName = new TableColumn<>("Name");
        currencyName.setMinWidth(50);
        currencyName.setCellValueFactory(new PropertyValueFactory<>("currencyName"));

        //Creating name column
        TableColumn<Currency,Double> valueColumn = new TableColumn<>("Rate");
        valueColumn.setMinWidth(50);
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        table.getColumns().add(IdColumn);
        table.getColumns().add(currencyName);
        table.getColumns().add(valueColumn);
        table.setItems(getInfo());
    }

    // returns ObservableList obj filled with all data from "currency_type"
    private ObservableList<Currency> getInfo() throws SQLException {
        ObservableList<Currency> info = FXCollections.observableArrayList();
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        String sql ="SELECT * FROM \"currency_type\" ";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            info.add(new Currency(rs.getInt(1), rs.getString(2),rs.getDouble(3)));
        }
        return info;
    }

    // creates request to db
    public void update(ActionEvent event) throws SQLException {
        Connection connection = Connect.connect();
        PreparedStatement pst1,pst2;
        try{
            pst1=connection.prepareStatement("UPDATE \"currency_type\" SET \"Id_currency\" = ?, \"currency_type\" =?, \"value\" = ? WHERE \"Id_currency\"="+table.getSelectionModel().getSelectedItem().getId());
            pst1.setInt(1,Integer.parseInt(Id.getText()));
            pst1.setString(2,CurName.getText());
            pst1.setDouble(3,Double.parseDouble(CurValue.getText()));
            pst1.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(getInfo());
    }

    //fills TextFields with info of chosen row
    public void onTableSelect(MouseEvent mouseEvent) {
        Id.setText(String.valueOf(table.getSelectionModel().getSelectedItem().getId()));
        CurName.setText(table.getSelectionModel().getSelectedItem().getCurrencyName());
        CurValue.setText(String.valueOf(table.getSelectionModel().getSelectedItem().getValue()));
    }

    //returns to main menu
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/Main/Main.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }
}
