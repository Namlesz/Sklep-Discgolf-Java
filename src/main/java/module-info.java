module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.sql;
    requires ojdbc10;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    requires jjwt.api;

    exports org.example;
    opens org.example to org.hibernate.orm.core;
    exports org.example.Controllers;
    opens org.example.Controllers to org.hibernate.orm.core;
    exports org.example.Observable;
    opens org.example.Observable to org.hibernate.orm.core;
    exports org.example.Database;
    opens org.example.Database to org.hibernate.orm.core;
}