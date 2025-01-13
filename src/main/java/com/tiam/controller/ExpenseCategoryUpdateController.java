package com.tiam.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.tiam.model.ExpenseCategoryData;
import com.tiam.service.Color;
import com.tiam.service.Database;
import com.tiam.utils.ColorListCell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ExpenseCategoryUpdateController implements Initializable {
        @FXML
    private ComboBox<Color> colors_cb;

    @FXML
    private TextField expense_name_tf;

    private ExpenseCategoryData expenseCategory;

    private Connection con;
    private PreparedStatement statement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colors_cb.getItems().addAll(Color.colors);

        colors_cb.setCellFactory(_ -> new ColorListCell());
        colors_cb.setButtonCell(new ColorListCell());
    }
    
    public void updateExpenseCategory(ActionEvent event) {
        if (!expense_name_tf.getText().isEmpty() || colors_cb.getValue() != null) {
            String query = """
                    UPDATE ExpenseCategory SET name="%s", color_name="%s" WHERE id=%d
                    """.formatted(expense_name_tf.getText(), colors_cb.getValue().getName(), expenseCategory.getId());
            con = Database.getConnection();

            try {
                statement = con.prepareStatement(query);
                statement.execute();

                Alert dialog = new Alert(AlertType.INFORMATION);
                dialog.setContentText("Expense Category Updated");
                dialog.showAndWait(); 
                
                expense_name_tf.getScene().getWindow().hide();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setContentText("Please fill in all the fields");
            dialog.showAndWait();
        }
    }

    @FXML
    public void closeForm(ActionEvent event) {
        expense_name_tf.getScene().getWindow().hide();
    }

    public void setExpenseCategory(ExpenseCategoryData expenseCategory) {
        this.expenseCategory = expenseCategory;

        colors_cb.getSelectionModel().select(expenseCategory.getColor());
        expense_name_tf.setText(expenseCategory.getName());
    }

}
