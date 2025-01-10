package com.tiam.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ReportPaneController extends StackPane implements Initializable {

    @FXML
    private AnchorPane empty_report_pane;

    @FXML
    private AnchorPane msg_container;
    

    public ReportPaneController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/report-pane.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int msg_container_height = 130;
        int msg_container_width = 470;
        empty_report_pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            msg_container.layoutYProperty().set((newVal.doubleValue() - msg_container_height) / 2);
        });
        empty_report_pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            msg_container.layoutXProperty().set((newVal.doubleValue() - msg_container_width) / 2);
        });

        openDashboard();

    }

    public void openDashboard() {
        try {
            Stage dashboardWindow = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
            Scene dashboardScene = new Scene(root);

            dashboardWindow.setScene(dashboardScene);
            dashboardWindow.initStyle(StageStyle.UTILITY);
            dashboardWindow.setAlwaysOnTop(true);
            dashboardWindow.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
}
