package com.tiam.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

public class ReportPaneController extends StackPane {

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
    
}
