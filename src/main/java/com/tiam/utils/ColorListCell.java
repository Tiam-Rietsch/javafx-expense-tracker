package com.tiam.utils;

import com.tiam.model.IncomeStreamData;
import com.tiam.service.Color;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


/**
 * 
 */
public class ColorListCell extends ListCell<Color> {
    private HBox hBox = new HBox();
    private Circle circle = new Circle(10);
    private Label label = new Label();

    public ColorListCell() {
        hBox.getChildren().addAll(circle, label);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    protected void updateItem(Color color, boolean empty) {
        super.updateItem(color, empty);

        if (empty || color == null) {
            setGraphic(null);
        } else {
            this.circle.setFill(Paint.valueOf(color.getHex()));
            this.label.setText(color.getName());
            this.label.setTextFill(Paint.valueOf("black"));
            setGraphic(hBox);
        }
    }
}
