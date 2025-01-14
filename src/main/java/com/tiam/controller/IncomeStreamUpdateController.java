package com.tiam.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.tiam.model.IncomeStreamData;
import com.tiam.service.Color;
import com.tiam.service.Database;
import com.tiam.utils.ColorListCell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class IncomeStreamUpdateController implements Initializable {

    @FXML
    private ComboBox<Color> colors_cb;

    @FXML
    private TextField income_name_tf;

    private Connection con;
    private PreparedStatement statement;

    private IncomeStreamData incomeStream;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colors_cb.getItems().addAll(Color.colors);

        colors_cb.setCellFactory(_ -> new ColorListCell());
        colors_cb.setButtonCell(new ColorListCell());

        colors_cb.getSelectionModel().select(0);
    }

    // --------------------------------------------------------------- Event Handlers

    public void updateIncomeStream(ActionEvent event) {

        if (income_name_tf.getText().isEmpty() || colors_cb.getValue() == null) {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setContentText("Please fill in all the information required");
            dialog.showAndWait();
        } else {
            String query = """
                Update IncomeStream SET name="%s", color_name="%s" WHERE id=%d
                """.formatted(income_name_tf.getText(), colors_cb.getSelectionModel().getSelectedItem().getName(), incomeStream.getId());

            con = Database.getConnection();
            try {
                statement = con.prepareStatement(query);
                statement.execute();

                Alert dialog = new Alert(AlertType.INFORMATION);
                dialog.setContentText("Income category added successfully");
                dialog.showAndWait();

                income_name_tf.getScene().getWindow().hide();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void closeForm(ActionEvent event) {
        income_name_tf.getScene().getWindow().hide();
    }


    // ---------------------------------------------------------------------------- Utilities

    public void setIncomeStream(IncomeStreamData incomeStream) {
        this.incomeStream = incomeStream;
        colors_cb.getSelectionModel().select(incomeStream.getColor());
        income_name_tf.setText(incomeStream.getName());
    }

}
