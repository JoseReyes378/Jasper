module org.example.demo1 {
    requires org.hibernate.orm.core;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires java.naming;
    requires java.sql;
    requires jasperreports;
    opens org.example.demo1 to javafx.fxml;
    opens org.example.demo1.model to org.hibernate.orm.core;
    exports org.example.demo1;
    exports org.example.demo1.Controller;
    exports org.example.demo1.model to jasperreports;
    opens org.example.demo1.Controller to javafx.fxml;
    exports org.example.demo1.reports;
    opens org.example.demo1.reports to javafx.fxml;
}






