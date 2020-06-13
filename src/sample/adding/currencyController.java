package sample.adding;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import sample.AlertBox.AlertBox;
import sample.Connect;
import sample.Filters.NumberTextFilter;
import sample.Main.Main;
import java.io.IOException;
import java.sql.*;

public class currencyController {

    public TextField name;
    public NumberTextFilter value;

    //returns latest Id number for a new row
    private Integer getMaxId() throws SQLException {
        int nev=1;
        Statement statement = Connect.getConnection().createStatement();
        String Id_c="select MAX(\"Id_currency\") from \"currency_type\" ";
        ResultSet rs = statement.executeQuery(Id_c);
        while(rs.next()){
            nev = rs.getInt(1)+1;
        }
        if(nev==0){
            nev=1;
        }
        System.out.println("last Id: "+nev);
        return nev;
    }

    // checks if all text-fields are filled and lighten them if not
    public void check(ActionEvent event) throws SQLException, IOException {
        boolean check = true;
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setColor(Color.color(1,0,0));
        if(name.getText().isEmpty()){
            check=false;
            name.setEffect(dropShadow);
        }
        else {
            name.setEffect(null);
        }
        if(value.getText().isEmpty()){
            check=false;
            value.setEffect(dropShadow);
        }
        else {
            value.setEffect(null);
        }
        if(check){
            addCurrency();
        }
    }

    // creates request to db
    public void addCurrency() throws SQLException, IOException {
        PreparedStatement pst;
        Connection connection = Connect.connect();
        try {
            pst=connection.prepareStatement("INSERT INTO \"currency_type\" VALUES (?,?,?)");
            pst.setInt(1,getMaxId());
            pst.setString(2,name.getText());
            pst.setDouble(3, Double.parseDouble(value.getText()));
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        AlertBox a = new AlertBox();
        a.show("Success","Currency successfully added!");
        back();
    }

    //returns to main menu
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/Main/Main.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }
}
