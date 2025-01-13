package com.tiam.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.tiam.model.ExpenseCategoryData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class ExpenseCardController extends AnchorPane {

    @FXML
    private Label amount_budjet_label;

    @FXML
    private Rectangle expense_rectangle;

    @FXML
    private Label title_label;

    @FXML
    private Group editExpenseCategory_btn;

    @FXML
    private Group deleteExpenseCategory_btn;

    private ExpenseCategoryData expenseCategory;
    private Runnable updateExpenseCategoryList;

    private Connection con;
    private PreparedStatement statement;

    public ExpenseCardController(ExpenseCategoryData expenseCategory) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/expense-card.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.expenseCategory = expenseCategory;

        this.expense_rectangle.setFill(Paint.valueOf(expenseCategory.getColor().getHex()));
        this.title_label.setText(expenseCategory.getName());
    }


    // ------------------------------------------------------- Event handlers
    public void showActionButtons(MouseEvent event) {
        if (!expenseCategory.getName().toUpperCase().equals("SAVINGS")) {
            editExpenseCategory_btn.setVisible(true);
            deleteExpenseCategory_btn.setVisible(true);    
        }
    }

    public void hideActionButtons(MouseEvent event) {
        editExpenseCategory_btn.setVisible(false);
        deleteExpenseCategory_btn.setVisible(false);
    }

    public void deleteExpenseCategory(ActionEvent event) {

    }

    public void editExpenseCategory(ActionEvent event) {
        
    }

    // --------------------------------------------------------- Utils
    public void setUpdateRunnable(Runnable runnable) {
        updateExpenseCategoryList = runnable;
    }

    
}
