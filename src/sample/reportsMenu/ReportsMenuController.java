package sample.reportsMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sample.Main.Main;
import sample.adding.Adding;

import java.io.IOException;

public class ReportsMenuController {

    /*Controller class consists of methods which changes Reports menu scene to chosen*/

    public void report1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReportsMenuController.class.getResource("/sample/Reports/report1.fxml"));
        Parent rep1 = loader.load();
        Scene report1 = new Scene(rep1);
        Main.getWindow().setScene(report1);
    }

    public void report2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReportsMenuController.class.getResource("/sample/Reports/report2.fxml"));
        Parent rep2 = loader.load();
        Scene report2 = new Scene(rep2);
        Main.getWindow().setScene(report2);
    }

    public void report3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReportsMenuController.class.getResource("/sample/Reports/report3.fxml"));
        Parent rep3 = loader.load();
        Scene report3 = new Scene(rep3);
        Main.getWindow().setScene(report3);
    }

    public void report4(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReportsMenuController.class.getResource("/sample/Reports/report4.fxml"));
        Parent rep4 = loader.load();
        Scene report4 = new Scene(rep4);
        Main.getWindow().setScene(report4);
    }

    public void report5(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReportsMenuController.class.getResource("/sample/Reports/report5.fxml"));
        Parent rep5 = loader.load();
        Scene report5 = new Scene(rep5);
        Main.getWindow().setScene(report5);
    }

    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Adding.class.getResource("/sample/Main/Main.fxml"));
        Parent back = loader.load();
        Scene backk = new Scene(back);
        Main.getWindow().setScene(backk);
    }
}
