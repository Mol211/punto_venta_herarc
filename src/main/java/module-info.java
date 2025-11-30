module org.mol211.ventaceramica {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;


    opens org.mol211.ventaceramica to javafx.fxml;
    exports org.mol211.ventaceramica;
    exports org.mol211.ventaceramica.controller;
    opens org.mol211.ventaceramica.controller to javafx.fxml;
}