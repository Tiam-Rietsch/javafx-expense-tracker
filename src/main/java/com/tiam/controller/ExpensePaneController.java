package com.tiam.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ExpensePaneController extends StackPane implements Initializable{

    @FXML
    private AnchorPane empty_expense_pane;

    @FXML
    private VBox expense_category_container;

    @FXML
    private AnchorPane expense_detail_pane;

    @FXML
    private TableView<?> expense_table;


    @FXML
    private AnchorPane msg_container;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int msg_container_height = 130;
        int msg_container_width = 470;
        empty_expense_pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            msg_container.layoutYProperty().set((newVal.doubleValue() - msg_container_height) / 2);
        });
        empty_expense_pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            msg_container.layoutXProperty().set((newVal.doubleValue() - msg_container_width) / 2);
        });
    }

    /** ---------------------- Event handlers  */

    @FXML
    public void addExpenseCategory(ActionEvent event) {

    }

    @FXML 
    public void addExpense(ActionEvent event) {

    }

    @FXML
    public void createBudjet(ActionEvent event) {

    }

    /** ---------------------- Utilities */

}
