package com.tiam.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

import com.tiam.model.ExpenseCategoryData;
import com.tiam.service.Accounts;
import com.tiam.service.Database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ExpenseCardController extends AnchorPane {

    @FXML
    private Label amount_budget_label;

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

        updateTotalExpenseLabel();
    }

    // ------------------------------------------------------- Event handlers
    public void showActionButtons(MouseEvent event) {
        editExpenseCategory_btn.setVisible(true);
        deleteExpenseCategory_btn.setVisible(true);
    }

    public void hideActionButtons(MouseEvent event) {
        editExpenseCategory_btn.setVisible(false);
        deleteExpenseCategory_btn.setVisible(false);
    }

    public void deleteExpenseCategory(ActionEvent event) {
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setContentText("Are your sure you want to delete the selected record ?");
        dialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        HashMap<Integer, Double> expense_map = Database.getExpensesForCategory(expenseCategory.getId());
        Double category_budget = Database.getBudgetForCategory(expenseCategory.getId());

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String query = "DELETE FROM ExpenseCategory WHERE id=%d".formatted(expenseCategory.getId());
            con = Database.getConnection();

            try {
                statement = con.prepareStatement(query);
                statement.execute();

                for (Integer id : expense_map.keySet()) {
                    Accounts.resetAccountOnExpenseDelete(expense_map.get(id));
                }
                Accounts.resetAccountOnExpenseCategoryDelete(category_budget);

                updateExpenseCategoryList.run();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void editExpenseCategory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/expense-category-update-form.fxml"));
        Scene scene = new Scene(loader.load());
        ((ExpenseCategoryUpdateController) loader.getController()).setExpenseCategory(expenseCategory);
        Stage editForm = new Stage(StageStyle.UTILITY);
        editForm.setScene(scene);
        editForm.setResizable(false);
        editForm.setTitle("update expense category");
        this.getParent().getScene().getRoot().setDisable(true);
        editForm.showAndWait();
        this.getParent().getScene().getRoot().setDisable(false);

        updateExpenseCategoryList.run();
    }

    // --------------------------------------------------------- Utils

    public void setUpdateRunnable(Runnable runnable) {
        updateExpenseCategoryList = runnable;
    }

    public int getExpenseRecordId() {
        return expenseCategory.getId();
    }

    public ExpenseCategoryData getSelectedExpense() {
        return expenseCategory;
    }

    public void updateTotalExpenseLabel() {
        amount_budget_label.setText(Database.getTotalExpenseForCategory(expenseCategory.getId()) + "XAF / "
        + Database.getBudgetForCategory(expenseCategory.getId()) + " XAF");
    }
}
