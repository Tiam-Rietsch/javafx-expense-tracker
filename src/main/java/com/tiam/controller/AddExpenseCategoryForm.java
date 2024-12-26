package com.tiam.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.tiam.service.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class AddExpenseCategoryForm implements Initializable {

    @FXML
    private ComboBox<Color> colors_cb;

    @FXML
    private TextField expense_name_tf;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colors_cb.getItems().addAll(Color.colors);

        colors_cb.setCellFactory(listview -> new ListCell<Color>() {
            private HBox hBox = new HBox();
            private Label label = new Label();
            private Circle circle = new Circle(10);

            {
                hBox.getChildren().addAll(circle, label);
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.spacingProperty().set(10);
            }

            @Override
            protected void updateItem(Color color, boolean empty) {
                super.updateItem(color, empty);

                if (empty || color == null) {
                    setGraphic(null);
                } else {
                    this.circle.setFill(Paint.valueOf(color.getHex()));
                    this.label.setTextFill(Paint.valueOf("black"));
                    this.label.setText(color.getName());
                    setGraphic(hBox);
                }
            }
        });

        colors_cb.setButtonCell(new ListCell<Color>() {
            private HBox hBox = new HBox();
            private Label label = new Label();
            private Circle circle = new Circle(10);

            {
                hBox.getChildren().addAll(circle, label);
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.spacingProperty().set(10);
            }

            @Override
            protected void updateItem(Color color, boolean empty) {
                super.updateItem(color, empty);

                if (empty || color == null) {
                    setGraphic(null);
                } else {
                    this.circle.setFill(Paint.valueOf(color.getHex()));
                    this.label.setTextFill(Paint.valueOf("black"));
                    this.label.setText(color.getName());
                    setGraphic(hBox);
                }
            }            
        });
    }


    @FXML
    void addIncomeStream(ActionEvent event) {

    }

    @FXML
    public void closeForm(ActionEvent event) {
        expense_name_tf.getScene().getWindow().hide();
    }


}
