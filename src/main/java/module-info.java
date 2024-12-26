module com.tiam {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;

    opens com.tiam to javafx.graphics;
    opens com.tiam.controller to javafx.fxml;
}
