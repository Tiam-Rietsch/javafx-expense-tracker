package com.tiam.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tiam.model.ExpenseRecordData;
import com.tiam.service.Database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class ExpenseRecordUpdateController {
    @FXML
    private TextField expense_amt_tf;

    @FXML
    private TextField expense_reason_tf;

    private ExpenseRecordData expenseRecord;

    private Connection con;
    private PreparedStatement statement;

    // ---------------------------------------------------------------------- Event Hanlders

    public void updateExpenseRecord(ActionEvent event) {
        if (expense_amt_tf.getText().isEmpty() || expense_reason_tf.getText().isEmpty()) {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setContentText("Please fill all the fields");
            dialog.showAndWait();
        } else {
            String query = """
                    Update ExpenseRecord SET reason="%s", amount=%f WHERE id=%d
                    """.formatted(expense_reason_tf.getText(), Double.parseDouble(expense_amt_tf.getText()), expenseRecord.getId());
            con = Database.getConnection();
            try {
                statement = con.prepareStatement(query);
                statement.execute();

                Alert dialog = new Alert(AlertType.INFORMATION);
                dialog.setContentText("Expense successfully updated");
                dialog.showAndWait();

                expense_amt_tf.getScene().getWindow().hide();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void closeForm(ActionEvent event) {
        expense_amt_tf.getScene().getWindow().hide();
    }

    // -------------------------------------------------------------------- utilities
    
    public void setExpenseCategory(ExpenseRecordData expenseRecord) {
        this.expenseRecord = expenseRecord;
        expense_amt_tf.setText(expenseRecord.getAmount().toString());
        expense_reason_tf.setText(expenseRecord.getReason());
    }

}
