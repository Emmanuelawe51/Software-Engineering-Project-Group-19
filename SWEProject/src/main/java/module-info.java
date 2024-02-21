module com.example.sweproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.sweproject to javafx.fxml;
    exports com.example.sweproject;
}