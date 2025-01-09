package com.tiam.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.tiam.model.IncomeStreamData;
import com.tiam.service.Color;

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

    private IncomeStreamData incomeStream;

    public IncomeCardController(IncomeStreamData incomeStream) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/income-card.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.incomeStream = incomeStream;
        income_rectangle.setFill(Paint.valueOf(incomeStream.getColor().getHex()));
        title_label.setText(incomeStream.getName());

    }


}
