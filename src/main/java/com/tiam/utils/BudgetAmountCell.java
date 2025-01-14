package com.tiam.utils;


import com.tiam.model.BudgetData;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;


/**
 * Custom TableCell for displaying and updating budget amount in the budget tableview
 * 
 * @author Tiam Rietsch
 * @see BudjetData
 */
public class BudgetAmountCell extends TableCell<BudgetData, Double> {
    private Spinner<Double> spinner;
    private Label label;

    /**
     * Initialized a BudgetAmountCell
     * 
     * <p>by default the constructor creates a {@code Spinner} object for editing and a
     * {@code Label} object for display. The {@code amountProperty()} of the cell's model then listens to the changes
     * the the spinner's {@code valueProperty} to update it's content during editing</p>
     * 
     * @see BudgetData
     */
    public BudgetAmountCell() {
        spinner = new Spinner<>(0, Double.MAX_VALUE, 1);
        spinner.setEditable(true);
        spinner.getStyleClass().add("default-spinner");
        label = new Label();
        label.setTextFill(Paint.valueOf("black"));
        setAlignment(Pos.CENTER_LEFT);

        // bind the observable amount to the spinner
        spinner.valueProperty().addListener((_, _, newVal) -> {
            if (getTableRow() != null && getTableRow().getItem() != null) {
                BudgetData budgetData = getTableRow().getItem();
                budgetData.setAmount(newVal.doubleValue());
            }
        });

        // TODO: remove this so that only double click triggers editing
        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1 && !isEmpty()) {
                startEdit();
            }
        });

        // commit the editing when the enter key is pressed
        spinner.getEditor().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                commitEdit(spinner.getValue());
            }
        });
    }

    @Override
    protected void updateItem(Double amount, boolean empty) {
        super.updateItem(amount, empty);

        if (empty || amount == null) {
            setGraphic(null);
        } else {
            BudgetData budget = getTableRow().getItem();

            if (budget != null) {
                label.textProperty().bind(budget.amountProperty().asString());
            }
            setGraphic(isEditing() ? spinner : label);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        setGraphic(spinner);
        spinner.getValueFactory().setValue(getItem());
        spinner.getEditor().requestFocus();
        spinner.getEditor().selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(label);
    }

    @Override
    public void commitEdit(Double amount) {
        super.commitEdit(amount);
        setGraphic(label);
    }


}
