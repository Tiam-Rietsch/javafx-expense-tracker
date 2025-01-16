package com.tiam.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;


import com.tiam.model.BudgetData;
import com.tiam.model.ExpenseCategoryData;
import com.tiam.service.Accounts;
import com.tiam.service.Color;
import com.tiam.service.Database;
import com.tiam.service.DateManager;
import com.tiam.utils.BudgetAmountCell;
import com.tiam.utils.BudgetCategoryCell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;


/**
 * Controller for Budjet Form. manages all the UI interactions and business
 * logic for creating budjets i.e. allocating specific amount of money to an expense category
 * 
 * @auth Tiam
 */
public class BudgetFormController implements Initializable {

    /**
     * The column the contnains the amount allocated to each epense category.
     */
    @FXML
    private TableColumn<BudgetData, Double> amount_col;

    /**
     * The Table where all the budjeting is done. is has two columns,
     * one which displays the expense categories, and another with displays 
     * the amount allocated to those categories.
     */
    @FXML
    private TableView<BudgetData> budget_table;

    /**
     * The Column that contains all the expense categories created by the 
     * user
     */
    @FXML
    private TableColumn<BudgetData, ExpenseCategoryData> category_col;

    /**
     * The label that specifies the date from which the budget is applicable
     */
    @FXML
    private Label startDate_label;

    /**
     * The label that displays the date till which the budget is applicable
     */
    @FXML
    private Label endDate_label;

    @FXML
    private Label availableIncome_label;

    // database tools
    private Connection con;
    private PreparedStatement statement;
    private ResultSet resultSet;

    private final ObjectProperty<Double> availableIncomeProperty = new SimpleObjectProperty<>();

    private HashMap<Integer, Double> oldBudget_map = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillBudgetTable();

        startDate_label.setText(DateManager.parseString(LocalDate.now().withDayOfMonth(1)));
        endDate_label.setText(DateManager.parseString(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())));

        availableIncomeProperty.set(Database.getAvailableIncome());
        availableIncome_label.textProperty().bind(availableIncomeProperty.asString().concat(" XAF"));

    }   

    // ----------------------------------------------------------------------- Event Handlers
    public void createBudget(ActionEvent event) {
        con = Database.getConnection();

        for (BudgetData budget : budget_table.getItems()) {
            String query = "UPDATE Budget SET amount=%f WHERE id=%d".formatted(budget.getAmount(), budget.getId());
            System.out.println(query);
            try {
                statement = con.prepareStatement(query);
                statement.execute();

                Accounts.resetAccountOnBudgetUpdate(budget.getAmount() - oldBudget_map.get(budget.getId()));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Alert dialog = new Alert(AlertType.INFORMATION);
        dialog.setContentText("Budget successfully created");
        dialog.show();
        Database.clcoseEverything(con, statement, resultSet);
        budget_table.getScene().getWindow().hide();
      
    }


    // ----------------------------------------------------------------------- Utilities
    public ObservableList<BudgetData> fetchBudgetData() {
        ObservableList<BudgetData> list = FXCollections.observableArrayList();;
        String query = """
                SELECT Budget.id, Budget.amount, ExpenseCategory.name, ExpenseCategory.color_name, Budget.category_id
                FROM Budget
                INNER JOIN ExpenseCategory ON ExpenseCategory.id = Budget.category_id;
                """;
        con = Database.getConnection();

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ExpenseCategoryData data = new ExpenseCategoryData();
                data.setColor(Color.getColorFromName(resultSet.getString("color_name")));
                data.setId(resultSet.getInt("category_id"));;
                data.setName(resultSet.getString("name"));

                BudgetData budget = new BudgetData(data, resultSet.getDouble("amount"));
                budget.setId(resultSet.getInt("id"));
                list.add(budget);
                oldBudget_map.put(budget.getId(), budget.getAmount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void fillBudgetTable() {
        ObservableList<BudgetData> budgetDataList = fetchBudgetData();

        // cell factories and value factories of the budget table columns
        category_col.setCellValueFactory(cellData -> cellData.getValue().expenseProperty());
        category_col.setCellFactory(_ -> new BudgetCategoryCell());
        amount_col.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        amount_col.setCellFactory(_ -> new BudgetAmountCell(availableIncomeProperty));
        amount_col.editableProperty().set(true);

        budget_table.setItems(budgetDataList);
    }
}
