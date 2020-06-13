package sample.editing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import sample.AlertBox.AlertBox;
import sample.Connect;
import sample.Main.Main;
import sample.adding.Adding;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Edit implements Initializable {

    @FXML
    public ComboBox propTypeEd,renterBox;
    @FXML
    public TextField emailEd,nameEd,innEd,kppEd,bicEd,phoneEd;

    // returns renter names
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

    // returns property types
    private ObservableList getProp() throws SQLException {
        ObservableList<String> type = FXCollections.observableArrayList();
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        String sql ="select property_type from \"renter_type\"";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            type.add(rs.getString("property_type"));
        }
        return type;
    }

    // returns Id of chosen renter in renterBox
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

    // creates ArrayList<AbstractMap.SimpleEntry> object, compares values with ID's (returns value)
    public String propertyMap(int value) throws SQLException {
        String propValue = null;
        ArrayList <AbstractMap.SimpleEntry <String,Integer>> property
                = new ArrayList <AbstractMap.SimpleEntry <String,Integer>>();
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        String sql = "select * from \"renter_type\"";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            property.add(new AbstractMap.SimpleEntry(rs.getString("property_type"),rs.getInt("Id_type")));
        }
        for(int i=0;i<property.size();i++){
            AbstractMap.SimpleEntry<String,Integer > map =property.get(i);
            if(map.getValue().equals(value)){
                propValue=map.getKey();
            }
            else{
                System.out.println(map.getValue());
                //System.out.println(propType.getValue().toString());
            }
        }
        return propValue;
    }

    // fills all textEdits with info of chosen renter
    public void fill(ActionEvent contextMenuEvent) throws SQLException {
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from \"Renters\" where \"name\"= '"+renterBox.getValue()+"'");
        while(rs.next()){
            nameEd.setText(rs.getString("name"));
            phoneEd.setText(rs.getString("phone"));
            emailEd.setText(rs.getString("email"));
            //по таблице property_type

            propTypeEd.setValue(propertyMap(rs.getInt("ren_type")));
        }
        Statement statement1 =connection.createStatement();
        //по реквизиту нельзя нужен id
        String sql1 = "select * from \"Recvizit\" where \"Id_recvizit\"= "+ getId();
        System.out.println(sql1);
        ResultSet rs1 = statement1.executeQuery(sql1);
        while (rs1.next()){

            kppEd.setText(String.valueOf(rs1.getInt("KPP")));
            bicEd.setText(String.valueOf(rs1.getInt("BIC")));
            innEd.setText(String.valueOf(rs1.getBigDecimal("INN")));
        }
    }

    // creates request to db
    public void edit() throws IOException {
        Connection connection = Connect.connect();
        PreparedStatement pst1,pst2;
        try{
            pst2=connection.prepareStatement("UPDATE \"Renters\" SET  \"name\" = ?, \"phone\" =?, \"email\" = ?,\"ren_type\" = ? WHERE \"Acc_Id\"="+ getId());
            pst2.setString(1,nameEd.getText());
            pst2.setString(2,phoneEd.getText());
            pst2.setString(3,emailEd.getText());
            pst2.setInt(4,propTypeEd.getSelectionModel().getSelectedIndex()+1);
            pst2.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            pst1=connection.prepareStatement("UPDATE \"Recvizit\" SET \"INN\" =?, \"KPP\" = ?, \"BIC\" = ? WHERE \"Id_recvizit\" = "+ getId());
            //System.out.println("inn set "+BigDecimal.valueOf(Long.parseLong(innEd.getText())));
            pst1.setBigDecimal(1, BigDecimal.valueOf(Long.parseLong(innEd.getText())));
            //System.out.println("kpp set "+Integer.parseInt(kppEd.getText()));
            pst1.setInt(2,Integer.parseInt(kppEd.getText()));
            //System.out.println("bic set "+ Integer.parseInt(bicEd.getText()));
            pst1.setInt(3, Integer.parseInt(bicEd.getText()));
            pst1.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        AlertBox alertBox = new AlertBox();
        alertBox.show("Success","Successfully updated!");
        back();
    }

    //returns to main menu
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/Main/Main.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            renterBox.setItems(getRenters());
            propTypeEd.setItems(getProp());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //checks if any fields aren't empty
    public void check(ActionEvent event) throws IOException {
        boolean check = true;
        ArrayList<TextField> list = new ArrayList<>();
        list.add(nameEd);
        list.add(innEd);
        list.add(kppEd);
        list.add(bicEd);
        list.add(emailEd);
        list.add(phoneEd);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setColor(Color.color(1,0,0));
        for(int i=0;i<list.size();i++){
            if(list.get(i).getText().isEmpty()){
                check=false;
                list.get(i).setEffect(dropShadow);
            }
            else {
                list.get(i).setEffect(null);
            }
            if(propTypeEd.getValue()==null){
                check=false;
                propTypeEd.setEffect(dropShadow);
            }
            else {
                propTypeEd.setEffect(null);
            }
            if(renterBox.getValue()==null){
                check=false;
                renterBox.setEffect(dropShadow);
            }
            else {
                renterBox.setEffect(null);
            }
        }
        if(check){
            edit();
        }
    }

}
