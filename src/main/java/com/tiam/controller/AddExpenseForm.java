package com.tiam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddExpenseForm {

    @FXML
    private TextField expense_amt_tf;

    @FXML
    private TextField expense_reason_tf;

    @FXML
    void addExpense(ActionEvent event) {

    }

    @FXML
    public void closeForm(ActionEvent event) {
        expense_amt_tf.getScene().getWindow().hide();
    }

}
