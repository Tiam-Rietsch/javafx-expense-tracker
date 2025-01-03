package com.tiam.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.tiam.model.BudjetData;
import com.tiam.model.ExpenseCategoryData;
import com.tiam.service.Color;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class BudjetFormController implements Initializable {

    @FXML
    private TableColumn<BudjetData, String> allocation_col;

    @FXML
    private TableColumn<BudjetData, Double> amount_col;

    @FXML
    private TableView<BudjetData> budjet_table;

    @FXML
    private TableColumn<BudjetData, ExpenseCategoryData> category_col;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<BudjetData> budjets = FXCollections.observableArrayList();
        for (Color color : Color.colors) {
            ExpenseCategoryData expense = new ExpenseCategoryData("Category", color);
            BudjetData budjet = new BudjetData(expense, 0.0);
            budjets.add(budjet);
        }

        budjet_table.setItems(budjets);

        // the the cell factory for the first column
        category_col.setCellValueFactory(cellData -> cellData.getValue().expenseProperty());
        category_col.setCellFactory(cellData -> new TableCell<BudjetData, ExpenseCategoryData>() {
            private HBox hBox = new HBox();
            private Circle circle = new Circle(10);
            private Label label = new Label();

            {
                hBox.getChildren().addAll(circle, label);
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setSpacing(10);
            }

            @Override
            protected void updateItem(ExpenseCategoryData expense, boolean empty) {
                super.updateItem(expense, empty);

                if (empty || expense == null) {
                    setGraphic(null);
                } else {
                    label.setText(expense.getName());
                    circle.setFill(Paint.valueOf(expense.getColor().getHex()));
                    setGraphic(hBox);
                }
            }
        });

        // set the cell factory for the second column
        amount_col.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        amount_col.setCellFactory(cellData -> new TableCell<BudjetData, Double>() {
            private Label label = new Label();
            private VBox vBox = new VBox();
            private Spinner<Double> spinner = new Spinner<>(0, Double.MAX_VALUE, 1);
            private HBox hBox = new HBox();
            private Label unit_label = new Label("XAF");

            {
                hBox.getChildren().addAll(spinner, unit_label);
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setSpacing(10);
                vBox.getChildren().addAll(hBox, label);
                vBox.setAlignment(Pos.CENTER_LEFT);
                vBox.setSpacing(1);
                spinner.setEditable(true);
                label.setFont(Font.font(10));
            }

            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);

                if (empty || amount == null) {
                    setGraphic(null);
                } else {
                    BudjetData model = getTableRow().getItem();

                    if (model != null) {
                    
                        model.allocationProperty().addListener((obs, oldVal, newVal) -> {
                            if (newVal != null && newVal.equals(BudjetData.MANUAL)) {
                                unit_label.setText("XAF");
                            } else if (newVal != null) {
                                unit_label.setText("%");
                            }
                        });
                    }


                    if (model != null) {
                        model.amountProperty().bind(spinner.valueProperty());
                        label.textProperty().bind(model.amountProperty().asString());    
                    }
                    setGraphic(vBox);
                }
            }
        });
        amount_col.editableProperty().set(true);

        // set factory for the last column
        allocation_col.setCellValueFactory(cellData -> cellData.getValue().allocationProperty());
        allocation_col.setCellFactory(cellData -> new TableCell<BudjetData, String>() {
            private ComboBox<String> cb = new ComboBox<>();
            private HBox hBox = new HBox();

            {
                ObservableList<String> list = FXCollections.observableArrayList();
                list.addAll(BudjetData.MANUAL, BudjetData.AUTO);
                cb.setItems(list);
                hBox.getChildren().add(cb);
            }

            @Override
            protected void updateItem(String allocation, boolean empty) {
                super.updateItem(allocation, empty);
                BudjetData model = getTableRow().getItem();

                if (empty || allocation == null) {
                    setGraphic(null);
                } else {

                    setGraphic(hBox);

                    if (model != null) {
                        cb.setValue(allocation);
                        model.allocationProperty().bind(cb.valueProperty());                        
                    } 
                }
            }
        });
    }   

}
