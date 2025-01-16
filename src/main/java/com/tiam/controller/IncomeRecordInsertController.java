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

public class IncomeRecordInsertController {

    @FXML
    private TextField income_amt_tf;


    // database tools
    private Connection con;
    private PreparedStatement statement;

    private Integer selectedIncomeId = null;

    
    // ----------------------------------------------------------- Event Handlers
    
    public void addIncome(ActionEvent event) {
        if (income_amt_tf.getText().isEmpty()) {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setHeaderText("please fill all the required fields");
            dialog.showAndWait();
        } else {
            String query = """
                    INSERT INTO IncomeRecord (amount, date_recorded, stream_id) VALUES (%f, \"%s", %d)
                    """.formatted(Double.parseDouble(income_amt_tf.getText()), DateManager.getCurrentDate(), selectedIncomeId);

            con = Database.getConnection();
            try {
                statement = con.prepareStatement(query);
                statement.execute();

                Accounts.resetAccountOnIncomeInsert(Double.parseDouble(income_amt_tf.getText()));

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

    public void setSelectedIncomeId(Integer id) {
        selectedIncomeId = id;
    }

}
