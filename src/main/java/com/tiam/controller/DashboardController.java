package com.tiam.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.tiam.model.ExpenseCategoryData;
import com.tiam.model.ExpenseRecordData;
import com.tiam.service.Accounts;
import com.tiam.service.Color;
import com.tiam.service.Database;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane budgetOverviewCard;

    @FXML
    private StackedBarChart<String, Number> expenseBudgetBarChart;

    @FXML
    private CategoryAxis expenseBudgetCategoryAxis;

    @FXML
    private NumberAxis expenseBudgetNumberAxis;

    @FXML
    private AnchorPane expenseOverviewCard;

    @FXML
    private PieChart expensePieChart;

    @FXML
    private CategoryAxis expenseTrendCategoryAxis;

    @FXML
    private NumberAxis expenseTrendLineAxis;

    @FXML
    private CategoryAxis incomeExpenseCategoryAxis;

    @FXML
    private NumberAxis incomeExpenseNumberAxis;

    @FXML
    private LineChart<String, Number> incomeExpeseLineChart;

    @FXML
    private AnchorPane incomeOverviewCard;

    @FXML
    private AnchorPane report_pane;

    @FXML
    private AnchorPane savingOverviewCard;

    @FXML
    private LineChart<String, Number> trendLineChart;

    @FXML
    private Label totalExpenses_label;

    @FXML
    private Label totalIncome_label;

    @FXML
    private Label remainingBudget_label;

    @FXML
    private Label unbudgetedIncome_label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // sampleIncomeExpenseLineChart();
        // sampleTrendLineChart();
        // sampleExpenseBudgetBarChart();
        // sampleExpenseChart();
        setCardData();
        fillIncomeExpenseLineChart();
        fillExpenseTrendLineChart();
        fillExpenseBudgetBarChart();
        fillExpensePieChart();
    }

    private void setCardData() {
        unbudgetedIncome_label.setText(Database.getAvailableIncome().toString() + " XAF");
        remainingBudget_label.setText(Database.getRemainingBudget().toString() + " XAF");
        totalExpenses_label.setText(Database.getTotalExpensesForMonth().toString() + " XAF");
        totalIncome_label.setText(Database.getTotalIncomeForMonth().toString() + " XAF");
    }

    private void fillExpenseTrendLineChart() {
        trendLineChart.setTitle("Spending Trend");   
        ObservableList<ExpenseCategoryData> expenseCategories = Database.fetchExpenseCategories();
        ArrayList<Color> colors = new ArrayList<>();

        for (ExpenseCategoryData category : expenseCategories) {
            ObservableList<ExpenseRecordData> expenses_list = Database.getTotalDailyExpensesForCategory(category.getId());
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(category.getName());
            
            for (ExpenseRecordData data : expenses_list) {
                series.getData().add(new XYChart.Data<>(data.getDate(), data.getAmount()));
            }

            colors.add(category.getColor());

            trendLineChart.getData().add(series);
        }


        Platform.runLater(() -> {
            int index = 0;
            for (Node node : trendLineChart.lookupAll(".chart-legend-item-symbol")) {
                node.setStyle("-fx-background-color: %s".formatted(colors.get(index).getHex()));
                index += 1;
            }

            index = 0;
            for (Node node : trendLineChart.lookupAll(".chart-series-line")) {
                node.setStyle("-fx-stroke: %s".formatted(colors.get(index).getHex()));
                index += 1;
            }

            index = 0;
            for (XYChart.Series<String, Number> series : trendLineChart.getData()) {
                for (XYChart.Data<String, Number> data : series.getData()) {
                    data.getNode().setStyle("-fx-background-color: %s".formatted(colors.get(index).getHex()));
                }
                index += 1;
            }

        });
    }

    private void fillIncomeExpenseLineChart() {
        incomeExpeseLineChart.setTitle("Income vs Expenses for the year");
        HashMap<String, Double> expenses_map = Database.getTotalExpensesForCurrentYear();
        HashMap<String, Double> income_map = Database.getTotalIncomeForCurrentYear();
        ArrayList<Color> colors = new ArrayList<>();

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Total Income");
        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Total Expenses");

        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };


        for (String month : months) {
            Double income = income_map.get(month);
            Double expense = expenses_map.get(month);
            if (income != null) incomeSeries.getData().add(new XYChart.Data<>(month, income));
            if (expense != null) expenseSeries.getData().add(new XYChart.Data<>(month, expense));
        }

        incomeExpeseLineChart.getData().add(incomeSeries);
        colors.add(Color.getColorFromName("Teal"));
        incomeExpeseLineChart.getData().add(expenseSeries);
        colors.add(Color.getColorFromName("Flamingo"));


        Platform.runLater(() -> {
            int index = 0;
            for (Node node : incomeExpeseLineChart.lookupAll(".chart-legend-item-symbol")) {
                node.setStyle("-fx-background-color: %s".formatted(colors.get(index).getHex()));
                index += 1;
            }

            index = 0;
            for (Node node : incomeExpeseLineChart.lookupAll(".chart-series-line")) {
                node.setStyle("-fx-stroke: %s".formatted(colors.get(index).getHex()));
                index += 1;
            }

            index = 0;
            for (XYChart.Series<String, Number> series : incomeExpeseLineChart.getData()) {
                for (XYChart.Data<String, Number> data : series.getData()) {
                    data.getNode().setStyle("-fx-background-color: %s".formatted(colors.get(index).getHex()));
                }
                index += 1;
            }

        });
    }


    private void fillExpenseBudgetBarChart() {
        expenseBudgetBarChart.setTitle("Budget Spent");
        HashMap<String, ExpenseCategoryData> barRawData = getTotalExpensePerCategory();
        ArrayList<Color> colors = new ArrayList<>();

        XYChart.Series<String, Number> budgetSpentSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> budgetLeftSeries = new XYChart.Series<>();
        budgetSpentSeries.setName("Budget Spent");
        budgetLeftSeries.setName("Budget Left");

        // First add all the data
        for (String category : barRawData.keySet()) {
            budgetSpentSeries.getData().add(new XYChart.Data<>(category, barRawData.get(category).getTotalExpenses()));
            budgetLeftSeries.getData().add(
                    new XYChart.Data<>(category, Database.getBudgetForCategory(barRawData.get(category).getId())
                            - barRawData.get(category).getTotalExpenses()));
            colors.add(barRawData.get(category).getColor());
        }

        // Add series to chart
        expenseBudgetBarChart.getData().add(budgetSpentSeries);
        expenseBudgetBarChart.getData().add(budgetLeftSeries);

        // Style the bars after they're created
        Platform.runLater(() -> {
            int index = 0;
            for (XYChart.Data<String, Number> data : budgetSpentSeries.getData()) {
                if (data.getNode() != null) {
                    data.getNode().setStyle("-fx-bar-fill: " + colors.get(index).getHex());
                }
                index++;
            }

            index = 0;
            for (XYChart.Data<String, Number> data : budgetLeftSeries.getData()) {
                if (data.getNode() != null) {
                    data.getNode().setStyle("-fx-bar-fill: derive(" + colors.get(index).getHex() + ", 70%)");
                }
                index++;
            }

            // Style legend
            int legendIndex = 0;
            for (Node node : expenseBudgetBarChart.lookupAll(".chart-legend-item-symbol")) {
                if (legendIndex == 0) {
                    node.setStyle("-fx-background-color: derive(#000000, 50%)"); // or just #000
                } else {
                    node.setStyle("-fx-background-color: derive(#000000, 90%)"); // This will be gray
                }
                legendIndex++;
            }
        });
    }

    private void fillExpensePieChart() {
        expensePieChart.setTitle("Total Expenses");
        HashMap<String, ExpenseCategoryData> pieRawData = getTotalExpensePerCategory();
        ArrayList<Color> colors = new ArrayList<>();

        for (String section : pieRawData.keySet()) {
            expensePieChart.getData().add(new PieChart.Data(section, pieRawData.get(section).getTotalExpenses()));

            PieChart.Data pieData = expensePieChart.getData().getLast();
            colors.add(pieRawData.get(section).getColor());
            pieData.getNode().setStyle("-fx-pie-color: %s".formatted(colors.getLast().getHex()));

        }

        Platform.runLater(() -> {
            int index = 0;
            for (Node node : expensePieChart.lookupAll(".chart-legend-item-symbol")) {
                node.setStyle("-fx-background-color : %s".formatted(colors.get(index).getHex()));
                index += 1;
            }
        });
    }

    private HashMap<String, ExpenseCategoryData> getTotalExpensePerCategory() {
        HashMap<String, ExpenseCategoryData> totalExpensesPerCategory = new HashMap<>();
        String query = """
                SELECT ExpenseCategory.name, SUM(ExpenseRecord.amount) as total_expenses, ExpenseCategory.color_name, ExpenseCategory.id
                FROM ExpenseRecord
                INNER JOIN ExpenseCategory ON ExpenseCategory.id = ExpenseRecord.category_id
                INNER JOIN Account ON Account.id = ExpenseCategory.account_id
                WHERE Account.id = %d
                GROUP BY ExpenseCategory.id
                """.formatted(Accounts.id);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ExpenseCategoryData data = new ExpenseCategoryData();
                data.setId(resultSet.getInt("id"));
                data.setName(resultSet.getString("name"));
                data.setColor(Color.getColorFromName(resultSet.getString("color_name")));
                data.setTotalExpenses(resultSet.getDouble("total_expenses"));
                totalExpensesPerCategory.put(resultSet.getString("name"), data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalExpensesPerCategory;
    }

}
