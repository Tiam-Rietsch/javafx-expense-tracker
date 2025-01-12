package com.tiam.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.tiam.model.ExpenseCategoryData;
import com.tiam.service.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ExpensePaneController extends StackPane implements Initializable{

    @FXML
    private AnchorPane empty_expense_pane;

    @FXML
    private VBox expense_category_container;

    @FXML
    private AnchorPane expense_detail_pane;

    @FXML
    private TableView<?> expense_table;


    @FXML
    private AnchorPane msg_container;

    public ExpensePaneController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/expenses-pane.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int msg_container_height = 130;
        int msg_container_width = 470;
        empty_expense_pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            msg_container.layoutYProperty().set((newVal.doubleValue() - msg_container_height) / 2);
        });
        empty_expense_pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            msg_container.layoutXProperty().set((newVal.doubleValue() - msg_container_width) / 2);
        });

        testCard();
    }

    /** ---------------------- Event handlers  */

    @FXML
    public void addExpenseCategory(ActionEvent event) throws IOException {
        Stage form = new Stage();
        Parent formRoot = FXMLLoader.load(getClass().getResource("/view/add-expense-category-form.fxml"));
        Scene scene = new Scene(formRoot);

        form.setTitle("New expense category");
        form.initStyle(StageStyle.UTILITY);
        form.resizableProperty().set(false);
        form.setAlwaysOnTop(true);
        form.setScene(scene);

        empty_expense_pane.getParent().getParent().getParent().setDisable(true);
        form.showAndWait();    
        empty_expense_pane.getParent().getParent().getParent().setDisable(false);
        
    }

    @FXML 
    public void addExpense(ActionEvent event) throws IOException{
        Stage form = new Stage();
        Parent formRoot = FXMLLoader.load(getClass().getResource("/view/add-expense-form.fxml"));
        Scene scene = new Scene(formRoot);

        form.setTitle("New expense category");
        form.initStyle(StageStyle.UTILITY);
        form.resizableProperty().set(false);
        form.setAlwaysOnTop(true);
        form.setScene(scene);

        empty_expense_pane.getParent().getParent().getParent().setDisable(true);
        form.showAndWait();    
        empty_expense_pane.getParent().getParent().getParent().setDisable(false);
    }

    @FXML
    public void createBudjet(ActionEvent event) throws IOException {
        Stage form = new Stage();
        Parent formRoot = FXMLLoader.load(getClass().getResource("/view/budjet-form.fxml"));
        Scene scene = new Scene(formRoot);

        form.setTitle("New expense category");
        form.initStyle(StageStyle.UTILITY);
        form.resizableProperty().set(false);
        form.setAlwaysOnTop(true);
        form.setScene(scene);

        empty_expense_pane.getParent().getParent().getParent().setDisable(true);
        form.showAndWait();    
        empty_expense_pane.getParent().getParent().getParent().setDisable(false);
    }

    public void handleExpenseCardClick(MouseEvent mouseEvent) {
        for (Node card : expense_category_container.getChildren()) {
            card.getStyleClass().remove("selected");

            if (card.equals(mouseEvent.getTarget())) {
                card.getStyleClass().add("selected");
            }
        }
    }
    /** ---------------------- Utilities */

    public void testCard() {
        ExpenseCategoryData expenseCategory = new ExpenseCategoryData();
        expenseCategory.setColor(Color.colors[0]);
        expenseCategory.setName("Savings");
        ExpenseCardController card = new ExpenseCardController(expenseCategory);
        card.onMouseClickedProperty().set(event -> handleExpenseCardClick(event));
        card.prefWidthProperty().bind(expense_category_container.prefWidthProperty());
        expense_category_container.getChildren().add(card);
        // for (Color color : Color.colors) {

        // }
    }

}
