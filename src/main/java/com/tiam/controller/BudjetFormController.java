package com.tiam.controller;

import java.net.URL;
import java.util.ResourceBundle;


import com.tiam.model.BudjetData;
import com.tiam.model.ExpenseCategoryData;
import com.tiam.service.Color;
import com.tiam.utils.BudjetAmountCell;
import com.tiam.utils.BudjetCategoryCell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BudjetFormController implements Initializable {

    @FXML
    private TableColumn<BudjetData, Double> amount_col;

    @FXML
    private TableView<BudjetData> budjet_table;

    @FXML
    private TableColumn<BudjetData, ExpenseCategoryData> category_col;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(BudjetData.MANUAL, BudjetData.AUTO);
        // allocation_cb.setItems(list);

        ObservableList<BudjetData> budjets = FXCollections.observableArrayList();
        for (Color color : Color.colors) {
            ExpenseCategoryData expense = new ExpenseCategoryData();
            expense.setColor(color);
            expense.setName("Category");
            BudjetData budjet = new BudjetData(expense, 0.0);
            budjets.add(budjet);
        }

        budjet_table.setItems(budjets);

        // the the cell factory for the first column
        category_col.setCellValueFactory(cellData -> cellData.getValue().expenseProperty());
        category_col.setCellFactory(col -> new BudjetCategoryCell());


        // set the cell factory for the second column
        amount_col.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        amount_col.setCellFactory(col -> new BudjetAmountCell());
        amount_col.editableProperty().set(true);
    }   

}
