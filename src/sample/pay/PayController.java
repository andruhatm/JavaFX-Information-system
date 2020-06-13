package sample.pay;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import sample.AlertBox.AlertBox;
import sample.Connect;
import sample.Main.Main;
import sample.adding.Adding;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PayController implements Initializable {
    @FXML
    public TextField amount;
    @FXML
    public TextArea comment;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox nameBox,op_typeBox,currencyBox;

    // returns latest Id number for a new row
    private Integer getMaxId() throws SQLException {
        int nev=1;
        Statement statement = Connect.getConnection().createStatement();
        String Id_c="select MAX(\"Id_op_num\") from \"acc_operations\" ";
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

    //returns list of renter names
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

    //returns list of operation types
    public ObservableList getOpType() throws SQLException {
        ObservableList <String> opType = FXCollections.observableArrayList();
        Connection connect = Connect.connect();
        Statement statement = connect.createStatement();
        String sql = "select * from \"operation_type\"";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            opType.add(rs.getString("operation_type"));
        }
        return opType;
    }

    //returns list of currency's
    public ObservableList getCurrency() throws SQLException {
        ObservableList <String> curT = FXCollections.observableArrayList();
        Connection connect = Connect.connect();
        Statement statement = connect.createStatement();
        String sql = "select currency_type from \"currency_type\"";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            curT.add(rs.getString("currency_type"));
        }
        return curT;
    }

    //fills comboboxes
    public void comboboxFill() throws SQLException {
        nameBox.setItems(getRenters());
        op_typeBox.setItems(getOpType());
        currencyBox.setItems(getCurrency());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            comboboxFill();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //checks if any fields aren't empty
    public void check(ActionEvent event) throws SQLException, IOException {
        boolean check = true;
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setColor(Color.color(1,0,0));
        if(amount.getText().isEmpty()){
            check =false;
            amount.setEffect(dropShadow);
        }
        else {
            amount.setEffect(null);
        }
        if(comment.getText().isEmpty()){
            check=false;
            comment.setEffect(dropShadow);
        }
        else {
            comment.setEffect(null);
        }
        if(date.getValue()==null){
            check =false;
            date.setEffect(dropShadow);
        }
        else {
            date.setEffect(null);
        }
        if(nameBox.getValue()==null){
            check =false;
            nameBox.setEffect(dropShadow);
        }
        else {
            nameBox.setEffect(null);
        }
        if(op_typeBox.getValue()==null){
            check =false;
            op_typeBox.setEffect(dropShadow);
        }
        else {
            op_typeBox.setEffect(null);
        }
        if(currencyBox.getValue()==null){
            check =false;
            currencyBox.setEffect(dropShadow);
        }
        else {
            currencyBox.setEffect(null);
        }
        if(check){
            charge();
        }
    }

    //creates request to db
    private void charge() throws SQLException, IOException {

        int Id=0,renId=0;
        double currentBal=0,newBal=0,sum1=0,sum2=0,Amount=Double.parseDouble(amount.getText());
        PreparedStatement pst,pst2;
        Connection connection = Connect.connect();
        Statement statement = connection.createStatement();
        //System.out.println(nameBox.getItems().get(nameBox.getSelectionModel().getSelectedIndex()).toString());

        String sql = "SELECT \"Acc_Id\" FROM \"Renters\" WHERE \"name\" = '" + nameBox.getSelectionModel().getSelectedItem().toString() +"' ";
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            System.out.println("rs is: "+rs.getInt("Acc_Id"));
            Id=rs.getInt("Acc_Id");
        }
        Statement statement2 = connection.createStatement();
        ResultSet res = statement2.executeQuery("SELECT \"ren_type\" FROM \"Renters\" WHERE \"Acc_Id\" = " + Id);
        while (res.next()){
            renId=res.getInt("ren_type");
        }
        Statement statement3 = connection.createStatement();
        ResultSet ress = statement3.executeQuery("SELECT \"Balance\" FROM \"Account\" WHERE \"Id\" = " + Id);
        while (ress.next()){
            currentBal=ress.getDouble("Balance");
        }
        int currencyId = currencyBox.getSelectionModel().getSelectedIndex()+1;

        Statement statement41 = connection.createStatement();
        ResultSet cur = statement41.executeQuery("SELECT \"value\" FROM \"currency_type\" WHERE \"Id_currency\" = " + currencyId);
        while (cur.next()){
            Amount*=cur.getDouble("value");
        }
        if(op_typeBox.getSelectionModel().getSelectedIndex()+1==4){
            newBal=currentBal+Amount;
        }
        else{
            newBal=currentBal-Amount;
        }
        System.out.println("Id renter : "+Id);
        System.out.println("current balance: "+currentBal+" plus: "+sum2+" minus: "+sum1+" new balance: "+newBal);
        try{
            pst=connection.prepareStatement("INSERT INTO \"acc_operations\" VALUES (?,?,?,?,?,?,?,?,?) ");
            pst.setInt(1,getMaxId());
            pst.setInt(2,Id);
            pst.setInt(3,Id);
            pst.setDate(4,Date.valueOf(date.getValue()));
            pst.setDouble(5, Amount);
            pst.setString(6,comment.getText());
            pst.setInt(7,renId);
            pst.setInt(8,currencyBox.getSelectionModel().getSelectedIndex()+1);
            pst.setInt(9,op_typeBox.getSelectionModel().getSelectedIndex()+1);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement statement4 = connection.createStatement();
        //returns sum of last 3 client payments ordered by date
        ResultSet sum = statement4.executeQuery("SELECT SUM(\"Amount\") FROM (SELECT * FROM \"acc_operations\" WHERE \"id_rentor\" = "+ Id +" AND \"id_op_type\" < 4 ORDER BY \"date\" DESC LIMIT 3) AS tab ;");
        while (sum.next()){
            sum1=sum.getDouble("sum");
        }
        Statement statement5 = connection.createStatement();
        ResultSet summ = statement5.executeQuery("SELECT SUM(\"Amount\") FROM (SELECT * FROM \"acc_operations\" WHERE \"id_rentor\" = "+ Id +" AND \"id_op_type\" = 4 ORDER BY \"date\" DESC LIMIT 3) AS tab ;");
        while (summ.next()){
            sum2=summ.getDouble("sum");
        }
        try{
            pst2=connection.prepareStatement("UPDATE \"Account\" SET  \"Balance\" = ?, \"Saldo\" =?, \"last_payment\" = ? WHERE \"Id\"="+ Id);
            pst2.setDouble(1,newBal);
            pst2.setDouble(2,sum2-sum1);
            pst2.setDate(3,Date.valueOf(date.getValue()));
            pst2.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        AlertBox alertBox = new AlertBox();
        alertBox.show("Success","Successfully charged");
        back();
    }

    //returns back to main scene
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/Main/Main.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }
}
