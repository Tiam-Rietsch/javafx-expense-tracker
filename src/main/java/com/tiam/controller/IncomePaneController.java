package com.tiam.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class IncomePaneController extends StackPane {

    @FXML
    private AnchorPane empty_income_pane;


    public IncomePaneController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/income-pane.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void addIncomeStream(ActionEvent event) {

    }

}
