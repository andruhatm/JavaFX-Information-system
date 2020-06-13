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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.AlertBox.AlertBox;
import sample.Connect;
import sample.Main.Main;
import sample.RenterBalance;
import sample.adding.Adding;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class report3Controller implements Initializable {

    public AnchorPane pane;
    public TextField smaller,bigger;
    TableView<RenterBalance> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table = new TableView<>();
        table.setPrefSize(540,260);
        table.setLayoutX(35);
        table.setLayoutY(80);
        pane.getChildren().addAll(table);
        try{
            fillTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //add columns to TableView obj
    private void fillTableView() throws SQLException {

        //Creating Id column
        TableColumn<RenterBalance,Integer> IdColumn = new TableColumn<>("Ид");
        IdColumn.setMinWidth(50);
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));

        //Creating name column
        TableColumn<RenterBalance,String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setPrefWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory <>("name"));

        //Creating balance column
        TableColumn<RenterBalance,Double> balanceColumn = new TableColumn<>("Баланс");
        balanceColumn.setMinWidth(50);
        balanceColumn.setCellValueFactory(new PropertyValueFactory <>("balance"));

        //Creating saldo column
        TableColumn<RenterBalance,Double> saldoColumn = new TableColumn<>("Сальдо");
        saldoColumn.setMinWidth(50);
        saldoColumn.setCellValueFactory(new PropertyValueFactory <>("saldo"));

        table.getColumns().add(IdColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(balanceColumn);
        table.getColumns().add(saldoColumn);
    }

    //returns list of renters with defined boundaries
    private ObservableList<RenterBalance> getInfo() throws SQLException {

        ObservableList<RenterBalance> info = FXCollections.observableArrayList();
        Connection connection = Connect.connect();
        PreparedStatement ps1=connection.prepareStatement("SELECT \"Acc_Id\",\"name\",\"Balance\",\"Saldo\",\"ren_type\" FROM \"Renters\" JOIN \"Account\" ON \"Renters\".\"Acc_Id\"=\"Account\".\"Id\" GROUP BY \"ren_type\",\"Balance\",\"Acc_Id\",\"Saldo\" HAVING \"Balance\" BETWEEN ? AND ? ORDER BY \"ren_type\"");
        ps1.setDouble(1, Double.parseDouble(bigger.getText()));
        ps1.setDouble(2, Double.parseDouble(smaller.getText()));
        ResultSet rs = ps1.executeQuery();
        while (rs.next()){
            info.add(new RenterBalance(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4)));
        }
        return info;
    }

    //set items to TableView
    public void show() throws SQLException {
        table.setItems(getInfo());
    }

    //checks if any fields aren't empty
    private void check(ActionEvent event) throws SQLException {
        boolean check=true;
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setColor(Color.color(1,0,0));
        if(bigger.getText().isEmpty()||smaller.getText().isEmpty()){
            check=false;
            bigger.setEffect(dropShadow);
            smaller.setEffect(dropShadow);
        }
        else{
            bigger.setEffect(null);
            smaller.setEffect(null);
        }
        if(check){
            show();
        }
    }

    //export info from table to Excel
    public void export(ActionEvent event) throws SQLException, IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Balances");
        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Ид");
        header.createCell(1).setCellValue("Имя");
        header.createCell(2).setCellValue("Баланс");
        header.createCell(3).setCellValue("Сальдо");

        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);

        sheet.setZoom(150);

        for(int i=0;i<table.getItems().size();i++){
            XSSFRow row =sheet.createRow(i);
            row.createCell(0).setCellValue(table.getItems().get(i).getId());
            row.createCell(1).setCellValue(table.getItems().get(i).getName());
            row.createCell(2).setCellValue(table.getItems().get(i).getBalance());
            row.createCell(3).setCellValue(table.getItems().get(i).getSaldo());
        }
        FileOutputStream fileOutputStream = new FileOutputStream("Balances Info.xlsx");
        wb.write(fileOutputStream);
        fileOutputStream.close();
        AlertBox a = new AlertBox();
        a.show("Экспорт","Успешно экспортировано!");
    }

    //returns back to main menu
    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/reportsMenu/ReportsMenu.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }
}
