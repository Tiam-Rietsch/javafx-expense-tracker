package com.tiam.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tiam.service.Accounts;
import com.tiam.service.Database;
import com.tiam.service.DateManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ExpenseRecordInsertController {

    @FXML
    private TextField expense_amt_tf;

    @FXML
    private TextField expense_reason_tf;

    private Connection con;
    private PreparedStatement statement;
    private int selectedExpenseId;

    // ----------------------------------------------------------------------- Event Handlers

    public void addExpenseRecord(ActionEvent event) {
        if (expense_amt_tf.getText().isEmpty() || expense_reason_tf.getText().isEmpty()) {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setContentText("Please fill all the fields");
            dialog.showAndWait();
        } else {
            String query = """
                    INSERT INTO ExpenseRecord (reason, amount, date_spent, category_id) VALUES ("%s", %f, "%s", %d)
                    """.formatted(expense_reason_tf.getText(), Double.parseDouble(expense_amt_tf.getText()), DateManager.getCurrentDate(), selectedExpenseId);
            con = Database.getConnection();
            try {
                statement = con.prepareStatement(query);
                System.out.println(query);
                statement.executeUpdate();
                
                Accounts.resetAccountOnExpenseInsert(Double.parseDouble(expense_amt_tf.getText()));

                Alert dialog = new Alert(AlertType.INFORMATION);
                dialog.setContentText("Expense recorded successfully");
                dialog.showAndWait();

                expense_amt_tf.getScene().getWindow().hide();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Database.clcoseEverything(con, statement, null);
            }
        }
    }

    @FXML
    public void closeForm(ActionEvent event) {
        expense_amt_tf.getScene().getWindow().hide();
    }

    // --------------------------------------------------------------------- Utilities

    public void setSelectedExpenseId(int id) {
        selectedExpenseId = id;
    }

}
