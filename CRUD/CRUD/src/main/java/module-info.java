module com.mycompany.crud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.crud to javafx.fxml;
    exports com.mycompany.crud;
}
