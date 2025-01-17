package com.tiam.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.tiam.model.ExpenseCategoryData;
import com.tiam.model.ExpenseRecordData;
import com.tiam.service.Accounts;
import com.tiam.service.Color;
import com.tiam.service.Database;
import com.tiam.service.DateManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
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
    private AnchorPane expense_pane;

    @FXML
    private VBox expense_category_container;

    @FXML
    private AnchorPane expense_detail_pane;

    @FXML
    private TableView<ExpenseRecordData> expense_table;

    @FXML
    private Group deleteRecordBtn;

    @FXML
    private Group editRecordBtn;

    @FXML
    private AnchorPane msg_container;

    @FXML
    private TableColumn<ExpenseRecordData, String> expenseReason_col;
    
    @FXML
    private TableColumn<ExpenseRecordData, Double> expenseAmount_col;

    @FXML
    private TableColumn<ExpenseRecordData, String> expenseDate_col;

    @FXML
    private Label expenditureTitle_label;

    @FXML
    private Label totalExpenses_label;

    private ExpenseCardController selectedExpenseCard;

    private Connection con;
    private PreparedStatement statement;
    private ResultSet resultSet;

    private Runnable networthUpdateRunnable;

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
        empty_expense_pane.heightProperty().addListener((_, _, newVal) -> {
            msg_container.layoutYProperty().set((newVal.doubleValue() - msg_container_height) / 2);
        });
        empty_expense_pane.widthProperty().addListener((_, _, newVal) -> {
            msg_container.layoutXProperty().set((newVal.doubleValue() - msg_container_width) / 2);
        });

        fillExpenseCategoryList();
    }

    /** -------------------------------------------------------------------- Event handlers  */

    @FXML
    public void addExpenseCategory(ActionEvent event) throws IOException {
        Stage form = new Stage();
        Parent formRoot = FXMLLoader.load(getClass().getResource("/view/expense-category-insert-form.fxml"));
        Scene scene = new Scene(formRoot);

        form.setTitle("New expense category");
        form.initStyle(StageStyle.UTILITY);
        form.resizableProperty().set(false);
        form.setScene(scene);

        empty_expense_pane.getScene().getRoot().setDisable(true);
        form.showAndWait();    
        empty_expense_pane.getScene().getRoot().setDisable(false);
        
        fillExpenseCategoryList();
    }

    @FXML 
    public void addExpenseRecord(ActionEvent event) throws IOException{
        if (selectedExpenseCard == null || !isBudgetCreated()) {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setContentText("First Create a Budget to be able to record an expenditure");
            dialog.showAndWait();
        } else {
            Stage form = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/expense-record-insert-form.fxml"));
            Scene scene = new Scene(loader.load());
            ((ExpenseRecordInsertController)loader.getController()).setSelectedExpenseId(selectedExpenseCard.getExpenseRecordId());
    
            form.setTitle("New expense category");
            form.initStyle(StageStyle.UTILITY);
            form.resizableProperty().set(false);
            form.setScene(scene);
    
            empty_expense_pane.getParent().getParent().getParent().setDisable(true);
            form.showAndWait();    
            empty_expense_pane.getParent().getParent().getParent().setDisable(false); 
            
            fillExpenseRecordTable();

            System.out.println("clicked");
        }
    }

    @FXML
    public void editExpenseRecord(ActionEvent event) throws IOException {
        ExpenseRecordData selectedExpenseRecord = expense_table.getSelectionModel().getSelectedItem(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/expense-record-update-form.fxml"));
        Scene scene = new Scene(loader.load());
        ((ExpenseRecordUpdateController)loader.getController()).setExpenseCategory(selectedExpenseRecord);
        Stage editForm = new Stage(StageStyle.UTILITY);
        editForm.setResizable(false);
        editForm.setScene(scene);
        this.getParent().getScene().getRoot().setDisable(true);
        editForm.showAndWait();
        this.getParent().getScene().getRoot().setDisable(false);

        fillExpenseRecordTable();

    }

    @FXML
    public void deleteExpenseRecord(ActionEvent event) throws IOException {
     Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setContentText("Are your sure you want to delete the selected record ?");
        dialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        ExpenseRecordData selectedRecord = expense_table.getSelectionModel().getSelectedItem();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String query = "DELETE FROM ExpenseRecord WHERE id=%d".formatted(selectedRecord.getId());
            con = Database.getConnection();

            try {
                statement = con.prepareStatement(query);
                statement.execute();

                Accounts.resetAccountOnExpenseDelete(selectedRecord.getAmount());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Database.clcoseEverything(con, statement, resultSet);
            }
        } 

        fillExpenseRecordTable();
    }

    @FXML
    public void createBudget(ActionEvent event) throws IOException {
        Stage form = new Stage();
        Parent formRoot = FXMLLoader.load(getClass().getResource("/view/budget-form.fxml"));
        Scene scene = new Scene(formRoot);

        form.setTitle("New expense category");
        form.initStyle(StageStyle.UTILITY);
        form.resizableProperty().set(false);
        form.setAlwaysOnTop(true);
        form.setScene(scene);

        empty_expense_pane.getParent().getParent().getParent().setDisable(true);
        form.showAndWait();    
        empty_expense_pane.getParent().getParent().getParent().setDisable(false);

        fillExpenseCategoryList();
    }

    public void handleExpenseCardClick(MouseEvent mouseEvent) {
        for (Node card : expense_category_container.getChildren()) {
            card.getStyleClass().remove("selected");

            if (card.equals(mouseEvent.getTarget())) {
                card.getStyleClass().add("selected");
                selectedExpenseCard = (ExpenseCardController) card;
                fillExpenseRecordTable();
            }
        }
    }

    @FXML
    public void showRecordActionButtons() {
        ExpenseRecordData selectedRecord = expense_table.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            editRecordBtn.setVisible(true);
            deleteRecordBtn.setVisible(true);
        } else {
            editRecordBtn.setVisible(false);
            deleteRecordBtn.setVisible(false);
        }
    }


    /** ----------------------------------------------------------------------------------- Utilities */

    public ObservableList<ExpenseCategoryData> fetchExpenseCategories() {
        ObservableList<ExpenseCategoryData> list = FXCollections.observableArrayList();
        String query = """
                SELECT ExpenseCategory.name, ExpenseCategory.id, ExpenseCategory.color_name 
                FROM ExpenseCategory
                INNER JOIN Account ON Account.id = ExpenseCategory.account_id 
                WHERE Account.id = %d
                """.formatted(Accounts.id);
        con = Database.getConnection();

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ExpenseCategoryData data = new ExpenseCategoryData();
                data.setName(resultSet.getString("name"));
                data.setColor(Color.getColorFromName(resultSet.getString("color_name")));
                data.setId(resultSet.getInt("id"));
                list.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.clcoseEverything(con, statement, resultSet);
        }

        return list;
    }
    
    public ObservableList<ExpenseRecordData> fetchExpenseRecords() {
        ObservableList<ExpenseRecordData> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM ExpenseRecord WHERE category_id=%d".formatted(selectedExpenseCard.getExpenseRecordId());
        con = Database.getConnection();

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ExpenseRecordData data = new ExpenseRecordData();
                data.setAmount(resultSet.getDouble("amount"));
                data.setReason(resultSet.getString("reason"));
                data.setId(resultSet.getInt("id"));
                data.setDate(resultSet.getString("date_spent"));
                list.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.clcoseEverything(con, statement, resultSet);
        }

        return list;
    }

    public void fillExpenseRecordTable() {
        expense_table.getItems().clear();
        expenditureTitle_label.setText(selectedExpenseCard.getSelectedExpense().getName());
        totalExpenses_label.setText(Database.getTotalExpenseForCategory(selectedExpenseCard.getSelectedExpense().getId()) + "XAF / "
        + Database.getBudgetForCategory(selectedExpenseCard.getSelectedExpense().getId()) + " XAF");
        showRecordActionButtons();

        if (selectedExpenseCard != null) {
            ObservableList<ExpenseRecordData> expenseRecordList = fetchExpenseRecords();
    
            expenseDate_col.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
            expenseReason_col.setCellValueFactory(cellData -> cellData.getValue().reasonProperty());
            expenseAmount_col.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
            
            expense_table.setItems(expenseRecordList);  
            expense_table.getSelectionModel().select(0);
            showRecordActionButtons();  
            selectedExpenseCard.updateTotalExpenseLabel();
        } else {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setContentText("first select an expense category");
            dialog.showAndWait();
        }

        if (networthUpdateRunnable != null) networthUpdateRunnable.run();
    }


    public void fillExpenseCategoryList() {
        ObservableList<ExpenseCategoryData> expenseCategoryList = fetchExpenseCategories();
        expense_category_container.getChildren().clear();
        expense_table.getItems().clear();

        if (expenseCategoryList.isEmpty()) {
            empty_expense_pane.setVisible(true);
            expense_pane.setVisible(false);
        } else {
            for (ExpenseCategoryData expenseCategory : expenseCategoryList) {
                ExpenseCardController card = new ExpenseCardController(expenseCategory);
                card.setUpdateRunnable(this::fillExpenseCategoryList);
                card.onMouseClickedProperty().set(event -> handleExpenseCardClick(event));
                card.prefWidthProperty().bind(expense_category_container.prefWidthProperty());
                expense_category_container.getChildren().add(card);
            }

            expense_category_container.getChildren().getLast().getStyleClass().add("selected");
            selectedExpenseCard = (ExpenseCardController) expense_category_container.getChildren().getLast();
            fillExpenseRecordTable();

            empty_expense_pane.setVisible(false);
            expense_pane.setVisible(true);
        }

        if (networthUpdateRunnable != null) networthUpdateRunnable.run();
    }

    public boolean isBudgetCreated() {
        con = Database.getConnection();
        String query = "SELECT * FROM Budget";

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.clcoseEverything(con, statement, resultSet);
        }

        return false;
    }

    public void setNetworthUpdateRunnable(Runnable runnable) {
        networthUpdateRunnable = runnable;
    }

}
