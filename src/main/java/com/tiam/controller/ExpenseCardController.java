package com.tiam.controller;

import java.io.IOException;

import com.tiam.persistence.Color;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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

    private String title;
    private Color color;

    public ExpenseCardController(String title, Color color) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/expense-card.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.title = title;
        this.color = color;

        this.expense_rectangle.setFill(Paint.valueOf(color.getHex()));
        this.title_label.setText(title);
    }
    
}
