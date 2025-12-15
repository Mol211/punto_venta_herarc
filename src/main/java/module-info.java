module ventaceramica {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires javafx.base;

  // Ikonli
  requires org.kordamp.ikonli.core;
  requires org.kordamp.ikonli.javafx;

  // Para JDBC y SLF4J
  requires java.sql;
  requires org.slf4j;

  exports org.mol211.ventaceramica.controller;
  exports org.mol211.ventaceramica.model;

  opens org.mol211.ventaceramica to javafx.fxml, javafx.graphics;
  opens org.mol211.ventaceramica.controller to javafx.fxml;
}