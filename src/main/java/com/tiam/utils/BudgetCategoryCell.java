package com.tiam.utils;

import com.tiam.model.BudgetData;
import com.tiam.model.ExpenseCategoryData;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Custom table cell for properly displaying expensecategories on the budjet table
 * 
 * @author Tiam Rietsch
 * @see ExpenseCategoryData
 */
public class BudgetCategoryCell extends TableCell<BudgetData, ExpenseCategoryData> {
    private Circle circle;
    private Label label;
    private HBox hBox;

    /**
     * Initializes a {@code BudgetCategoryCell}
     * 
     * <p>The constructor creates a {@code Circle} showing the color of the expense category as well as a
     * {@code Label} showing the name of the expense category, all wrapped inside an {@code Hbox}</p>
     */
    public BudgetCategoryCell() {
        circle = new Circle(10);

        label = new Label();
        label.setTextFill(Paint.valueOf("black"));

        hBox = new HBox();
        hBox.getChildren().addAll(circle, label);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
    }

    @Override
    protected void updateItem(ExpenseCategoryData expenseCategory, boolean empty) {
        super.updateItem(expenseCategory, empty);

        if (empty || expenseCategory == null) {
            setGraphic(null);
        } else {
            circle.setFill(Paint.valueOf(expenseCategory.getColor().getHex()));
            label.setText(expenseCategory.getName());
            setGraphic(hBox);
        }
    }
    
}
