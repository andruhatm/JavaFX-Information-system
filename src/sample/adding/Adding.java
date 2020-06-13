package sample.adding;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import sample.AlertBox.AlertBox;
import sample.Connect;
import sample.Filters.NumberTextFilter;
import sample.Main.Main;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Adding {
    @FXML
    private TextField name,email;
    @FXML
    private NumberTextFilter inn,kpp,bic,phone;
    @FXML
    private DatePicker last_p;
    @FXML
    private ComboBox propType;
    private boolean check = true;

    // returns latest Id number for a new row
    private Integer getMaxId() throws SQLException {
        int nev=1;
        Statement statement = Connect.getConnection().createStatement();
        String Id_c="select MAX(\"Acc_Id\") from \"Renters\" ";
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
        check=true;

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setColor(Color.color(1,0,0));

        ArrayList<TextField> list = new ArrayList<>();
        list.add(name);
        list.add(inn);
        list.add(kpp);
        list.add(bic);
        list.add(phone);
        list.add(email);
        for(int i=0;i<list.size();i++) {
            if(last_p.getChronology()==null||last_p.getValue()==null){
                check=false;
                last_p.setEffect(dropShadow);
            }
            else{
                last_p.setEffect(null);
            }
            if(propType.getValue()==null){
                check=false;
                propType.setEffect(dropShadow);
            }
            else {
                propType.setEffect(null);
            }
            if (list.get(i).getText().isEmpty()){
                check = false;
                list.get(i).setEffect(dropShadow);
            }
            else{
                list.get(i).setEffect(null);
            }
        }
        if(check){
            adding();
        }
    }

    // creates request to db
    private void adding() throws IOException {
        if(check) {
            String Name = name.getText();
            System.out.println(Name);
            BigDecimal INN = BigDecimal.valueOf(Long.parseLong(inn.getText()));
            System.out.println(INN);
            String KPP = kpp.getText();
            System.out.println(KPP);
            String BIC = bic.getText();
            System.out.println(BIC);
            String Phone = phone.getText();
            System.out.println(Phone);
            String Email = email.getText();
            System.out.println(Email);
            Date lastP = Date.valueOf(last_p.getValue());
            System.out.println(lastP);

            PreparedStatement pst,pst2,pst3;
            Connection connection = Connect.connect();
            try{
                pst3 = connection.prepareStatement("INSERT INTO \"Recvizit\" VALUES (?,?,?,?)");
                pst3.setInt(1, getMaxId());
                pst3.setBigDecimal(2, INN);
                pst3.setInt(3, Integer.parseInt(KPP));
                pst3.setInt(4, Integer.parseInt(BIC));
                pst3.execute();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
            try {
                pst = connection.prepareStatement("INSERT INTO \"Renters\" VALUES (?,?,?,?,?,?)");
                pst.setInt(1, getMaxId());
                pst.setString(2, Name);
                pst.setString(3, Phone);
                pst.setString(4,Email);
                pst.setInt(5,getMaxId());
                pst.setInt(6,propertyMap());
                pst.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try{
                pst2= connection.prepareStatement("INSERT INTO \"Account\" VALUES (?,?,?,?)");
                pst2.setInt(1,getMaxId()-1);
                pst2.setDouble(2,0);
                pst2.setDouble(3,0);
                pst2.setDate(4,lastP);
                pst2.execute();
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
            AlertBox a = new AlertBox();
            a.show("Success","Successfully added!");
            back();
        }
    }

    // creates ArrayList<AbstractMap.SimpleEntry> object, compares ID's with values (returns id)
    public Integer propertyMap() throws SQLException {
        int propId=0;
        ArrayList <AbstractMap.SimpleEntry <Integer,String>> property
                = new ArrayList <AbstractMap.SimpleEntry <Integer, String>>();
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM \"renter_type\"";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            property.add(new AbstractMap.SimpleEntry(rs.getInt("Id_type"),rs.getString("property_type")));
        }
        for(int i=0;i<property.size();i++){
            AbstractMap.SimpleEntry<Integer,String > map = property.get(i);
            if(map.getValue().equals(propType.getValue().toString())){
                propId = map.getKey();
            }
        }
        return propId;
    }

    // fills Combobox onClicked
    public void comboboxfill() throws SQLException {
        propType.setItems(getPropType());
    }
    // returns ObserbavleList object with all property_type values
    public ObservableList getPropType() throws SQLException {
        ObservableList<String> type = FXCollections.observableArrayList();
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        String sql ="SELECT property_type FROM \"renter_type\"";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            type.add(rs.getString("property_type"));
        }
        return type;
    }

    // returns to main scene
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/Main/Main.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }
}
