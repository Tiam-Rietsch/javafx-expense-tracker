package com.tiam.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ExpensePaneController extends StackPane {

    @FXML
    private AnchorPane empty_expense_pane;


    public ExpensePaneController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/expenses-pane.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void createExpenseCategory(ActionEvent event) {

    }

}
