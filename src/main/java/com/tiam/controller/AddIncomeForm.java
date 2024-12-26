package com.tiam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddIncomeForm {

    @FXML
    private TextField income_amt_tf;

    @FXML
    public void addIncome(ActionEvent event) {

    }

    @FXML
    public void closeForm(ActionEvent event) {
        income_amt_tf.getScene().getWindow().hide();
    }

}
