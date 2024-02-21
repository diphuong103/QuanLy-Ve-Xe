module com.example.qlvxbus {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.phuongbusbooking to javafx.fxml;
    exports com.example.phuongbusbooking;
    exports com.example.phuongbusbooking.Controllers;
    exports com.example.phuongbusbooking.Controllers.Admin;
    exports com.example.phuongbusbooking.Controllers.Client;
    exports com.example.phuongbusbooking.Models;
    exports com.example.phuongbusbooking.Views;
    opens com.example.phuongbusbooking.Controllers to javafx.fxml;
    opens com.example.phuongbusbooking.Controllers.Admin;
    opens com.example.phuongbusbooking.Controllers.Client;



}