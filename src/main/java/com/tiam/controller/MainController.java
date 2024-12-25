package com.tiam.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainController implements Initializable {

    @FXML
    private Button expense_btn;

    @FXML
    private Button income_btn;

    @FXML
    private StackPane main_container;

    @FXML
    private Label networth_label;

    @FXML
    private Button report_btn;

    @FXML
    public void switchTab(ActionEvent event) {
        main_container.getChildren().clear();
        expense_btn.getStyleClass().remove("selected");
        income_btn.getStyleClass().remove("selected");
        report_btn.getStyleClass().remove("selected");

        if (event.getTarget().equals(expense_btn)) {
            expense_btn.getStyleClass().add("selected");
            ExpensePaneController pane = new ExpensePaneController();
            main_container.getChildren().add(pane);
        } else if (event.getTarget().equals(income_btn)) {
            income_btn.getStyleClass().add("selected");
            IncomePaneController pane = new IncomePaneController();
            main_container.getChildren().add(pane);
        } else if (event.getTarget().equals(report_btn)) {
            report_btn.getStyleClass().add("selected");
            ReportPaneController pane = new ReportPaneController();
            main_container.getChildren().add(pane);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IncomePaneController pane = new IncomePaneController();
        main_container.getChildren().add(pane);
    }

}
