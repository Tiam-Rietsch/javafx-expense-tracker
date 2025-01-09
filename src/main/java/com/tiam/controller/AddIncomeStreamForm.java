package com.tiam.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.tiam.service.Color;
import com.tiam.utils.ColorListCell;

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

public class AddIncomeStreamForm implements Initializable {

    @FXML
    private ComboBox<Color> colors_cb;

    @FXML
    private TextField income_name_tf;

    @FXML
    public void addIncomeStream(ActionEvent event) {

    }

    @FXML
    public void closeForm(ActionEvent event) {
        income_name_tf.getScene().getWindow().hide();
    }

    @Override
    public  void initialize(URL location, ResourceBundle resources) {
        colors_cb.getItems().addAll(Color.colors);

        colors_cb.setCellFactory(listView -> new ColorListCell());
        colors_cb.setButtonCell(new ColorListCell());

        colors_cb.getSelectionModel().select(0);
    }

}
