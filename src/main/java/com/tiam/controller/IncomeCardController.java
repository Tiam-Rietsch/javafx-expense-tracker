package com.tiam.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.tiam.persistence.Color;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class IncomeCardController extends AnchorPane {

    @FXML
    private Label amount_label;

    @FXML
    private Rectangle income_rectangle;

    @FXML
    private Label title_label;

    private Color cardColor;
    private String title;
    private TableView<?> income_table;

    public IncomeCardController(String title, Color cardColor, TableView<?> income_table) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/income-card.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.title = title;
        this.cardColor = cardColor;
        this.income_table = income_table;

        income_rectangle.setFill(Paint.valueOf(cardColor.getHex()));
        title_label.setText(title);

    }

    @FXML
    public void showDetails(MouseEvent event) {

    }

}
