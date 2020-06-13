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
import sample.Main.Main;

import java.io.IOException;
import java.sql.*;

public class propertyTypeController {

    public TextField name;

    // checks if all text-fields are filled and lighten them if not
    public void check(ActionEvent event) throws IOException {
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
        if(check){
            addPropertyType();
        }
    }

    // creates request to db
    private void addPropertyType() throws IOException {
        PreparedStatement pst;
        Connection connection = Connect.connect();
        try {
            pst=connection.prepareStatement("INSERT INTO \"renter_type\" VALUES (?,?)");
            pst.setInt(1,getMaxId());
            pst.setString(2,name.getText());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        AlertBox a = new AlertBox();
        a.show("Success","Property type has been added!");
        back();
    }

    //returns latest Id number for a new row
    private Integer getMaxId() throws SQLException {
        int nev=1;
        Statement statement = Connect.getConnection().createStatement();
        String Id_c="select MAX(\"Id_type\") from \"renter_type\" ";
        ResultSet rs = statement.executeQuery(Id_c);
        while(rs.next()){
            nev = rs.getInt(1)+1;
        }
        /*if(nev==0){
            nev=1;
        }*/
        System.out.println("last Id: "+nev);
        return nev;
    }

    //returns to main menu
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/Main/Main.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }
}
