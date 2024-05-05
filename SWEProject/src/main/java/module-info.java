module com.example.sweproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires junit;
    requires java.desktop;
    requires org.testfx.junit;
    requires org.testfx;
    requires jdk.dynalink;

    opens com.example.sweproject to javafx.fxml;
    exports com.example.sweproject;
}