package com.tiam.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.tiam.service.Database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class MainController implements Initializable {

    @FXML
    private Button expense_btn;

    @FXML
    private Button income_btn;

    @FXML
    private AnchorPane main_container;

    @FXML
    private Label totalBudget_label;

    @FXML
    private Button report_btn;

    @FXML
    private Label availableIncome_label;

    @FXML
    private Label totalExpenses_label;

    private void applyPane(StackPane pane) {
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        main_container.getChildren().add(pane);
    }

    @FXML
    public void switchTab(ActionEvent event) {
        main_container.getChildren().clear();
        expense_btn.getStyleClass().remove("selected");
        income_btn.getStyleClass().remove("selected");
        report_btn.getStyleClass().remove("selected");
        StackPane pane = null; 

        if (event.getTarget().equals(expense_btn)) {
            expense_btn.getStyleClass().add("selected");
            pane = new ExpensePaneController();
            ((ExpensePaneController)pane).setNetworthUpdateRunnable(this::updateNetworthLabel);

        } else if (event.getTarget().equals(income_btn)) {
            income_btn.getStyleClass().add("selected");
            pane = new IncomePaneController();
            ((IncomePaneController)pane).setNetworthUpdateRunnable(this::updateNetworthLabel);
        } else if (event.getTarget().equals(report_btn)) {
            report_btn.getStyleClass().add("selected");
            pane = new ReportPaneController();
        }

        applyPane(pane);

    }

    public void updateNetworthLabel() {
        totalBudget_label.setText(Database.getTotalBudgetForMonth().toString() + " XAF");
        availableIncome_label.setText(Database.getAvailableIncome().toString() + " XAF");
        totalExpenses_label.setText(Database.getTotalExpensesForMonth().toString() + " XAF");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IncomePaneController pane = new IncomePaneController();
        pane.setNetworthUpdateRunnable(this::updateNetworthLabel);
        applyPane(pane);

        updateNetworthLabel();
    }


}
