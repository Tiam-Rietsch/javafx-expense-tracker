module com.tiam {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens com.tiam to javafx.graphics;
    opens com.tiam.controller to javafx.fxml;
}
