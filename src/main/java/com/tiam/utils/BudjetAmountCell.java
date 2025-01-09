package com.tiam.utils;


import com.tiam.model.BudjetData;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

public class BudjetAmountCell extends TableCell<BudjetData, Double> {
    private Spinner<Double> spinner;
    private Label label;

    public BudjetAmountCell() {
        spinner = new Spinner<>(0, Double.MAX_VALUE, 1);
        spinner.setEditable(true);
        spinner.getStyleClass().add("default-spinner");
        label = new Label();
        label.setTextFill(Paint.valueOf("black"));
        setAlignment(Pos.CENTER_LEFT);

        // bind the observable amount to the spinner
        spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (getTableRow() != null && getTableRow().getItem() != null) {
                BudjetData budgetData = getTableRow().getItem();
                budgetData.setAmount(newVal.doubleValue());
            } else {
                System.out.println("hello");
            }
        });

        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1 && !isEmpty()) {
                startEdit();
            }
        });

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
            BudjetData budjet = getTableRow().getItem();

            if (budjet != null) {
                label.textProperty().bind(budjet.amountProperty().asString());
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
