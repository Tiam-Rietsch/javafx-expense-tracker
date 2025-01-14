package com.tiam.controller;

import java.net.URL;
import java.util.ResourceBundle;


import com.tiam.model.BudgetData;
import com.tiam.model.ExpenseCategoryData;
import com.tiam.service.Color;
import com.tiam.utils.BudgetAmountCell;
import com.tiam.utils.BudgetCategoryCell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * Controller for Budjet Form. manages all the UI interactions and business
 * logic for creating budjets i.e. allocating specific amount of money to an expense category
 * 
 * @auth Tiam
 */
public class BudgetFormController implements Initializable {

    /**
     * The column the contnains the amount allocated to each epense category.
     */
    @FXML
    private TableColumn<BudgetData, Double> amount_col;

    /**
     * The Table where all the budjeting is done. is has two columns,
     * one which displays the expense categories, and another with displays 
     * the amount allocated to those categories.
     */
    @FXML
    private TableView<BudgetData> budget_table;

    /**
     * The Column that contains all the expense categories created by the 
     * user
     */
    @FXML
    private TableColumn<BudgetData, ExpenseCategoryData> category_col;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<BudgetData> budgets = FXCollections.observableArrayList();
        for (Color color : Color.colors) {
            ExpenseCategoryData expense = new ExpenseCategoryData();
            expense.setColor(color);
            expense.setName("Category");
            BudgetData budget = new BudgetData(expense, 0.0);
            budgets.add(budget);
        }

        budget_table.setItems(budgets);

        // cell factories and value factories of the budget table columns
        category_col.setCellValueFactory(cellData -> cellData.getValue().expenseProperty());
        category_col.setCellFactory(_ -> new BudgetCategoryCell());
        amount_col.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        amount_col.setCellFactory(_ -> new BudgetAmountCell());
        amount_col.editableProperty().set(true);
    }   

}
