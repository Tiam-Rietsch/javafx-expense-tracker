package com.tiam.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tiam.model.IncomeRecordData;
import com.tiam.service.Accounts;
import com.tiam.service.Database;
import com.tiam.service.DateManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class IncomeRecordUpdateController {

    @FXML
    private TextField income_amt_tf;


    // database tools
    private Connection con;
    private PreparedStatement statement;

    private IncomeRecordData incomeRecord = null;

    // ------------------------------------------------------- Event Handlers

    public void updateIncome(ActionEvent event) {
        if (income_amt_tf.getText().isEmpty()) {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setHeaderText("please fill all the required fields");
            dialog.showAndWait();
        } else {
            String query = """
                    UPDATE IncomeRecord SET amount=%f, date_recorded="%s" WHERE id=%d
                    """.formatted(Double.parseDouble(income_amt_tf.getText()), DateManager.getCurrentDate(), incomeRecord.getId());
            con = Database.getConnection();
        
            try {
                statement = con.prepareStatement(query);
                statement.execute();

                Accounts.resetAccountOnIncomeUpdate(incomeRecord.getAmount(), Double.parseDouble(income_amt_tf.getText()));

                Alert dialog = new Alert(AlertType.INFORMATION);
                dialog.setContentText("New income recorded");
                dialog.showAndWait();

                income_amt_tf.getScene().getWindow().hide();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void closeForm(ActionEvent event) {
        income_amt_tf.getScene().getWindow().hide();
    }


    // -------------------------------------------------------------------- Utilities
    public void setSelectedIncomeRecord(IncomeRecordData incomeRecord) {
         this.incomeRecord = incomeRecord;
         income_amt_tf.setText(incomeRecord.getAmount().toString());
    }

}
