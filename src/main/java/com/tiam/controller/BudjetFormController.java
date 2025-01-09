package com.tiam.controller;

import java.net.URL;
import java.util.ResourceBundle;


import com.tiam.model.BudjetData;
import com.tiam.model.ExpenseCategoryData;
import com.tiam.service.Color;
import com.tiam.utils.BudjetAmountCell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

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
        category_col.setCellFactory(cellData -> new TableCell<BudjetData, ExpenseCategoryData>() {
            private final HBox hBox = new HBox();
            private final Circle circle = new Circle(10);
            private final Label label = new Label();

            {
                hBox.getChildren().addAll(circle, label);
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setSpacing(10);
            }

            @Override
            protected void updateItem(ExpenseCategoryData expense, boolean empty) {
                super.updateItem(expense, empty);

                if (empty || expense == null) {
                    setGraphic(null);
                } else {
                    label.setText(expense.getName());
                    label.textFillProperty().set(Paint.valueOf("black"));
                    circle.setFill(Paint.valueOf(expense.getColor().getHex()));
                    setGraphic(hBox);
                }
            }
        });

        // set the cell factory for the second column
        amount_col.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        amount_col.setCellFactory(col -> new BudjetAmountCell());
        amount_col.editableProperty().set(true);

        // allocation_cb.setValue(BudjetData.MANUAL);
    }   

}
