package com.tiam.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sampleIncomeExpenseLineChart();
        sampleTrendLineChart();
        sampleExpenseBudgetBarChart();
        sampleExpenseChart();
    }

    private void sampleTrendLineChart() {
        trendLineChart.setTitle("Multi-Line Chart");

        // Create 8 series of data
        String[] categories = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug"};
        String[] seriesNames = {"Food", "Transportation", "Entertainment", "Bills", "Shopping"};

        for (String seriesName : seriesNames) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(seriesName);
            
            for (String category : categories) {
                int randomValue = (int) (Math.random() * 175);  // Generate a random value between 50 and 149
                series.getData().add(new XYChart.Data<>(category, randomValue));
            }
            trendLineChart.getData().add(series);
        }
    }

    private void sampleIncomeExpenseLineChart() {
        incomeExpeseLineChart.setTitle("Multi-Line Chart");

        // Create 8 series of data
        String[] categories = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug"};
        String[] seriesNames = {"Expenditure", "Income"};

        for (String seriesName : seriesNames) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(seriesName);
            
            for (String category : categories) {
                int randomValue = (int) (Math.random() * 80) + 20;  // Generate a random value between 50 and 149
                series.getData().add(new XYChart.Data<>(category, randomValue));
            }
            incomeExpeseLineChart.getData().add(series);
        }
    }

    private void sampleExpenseBudgetBarChart() {
        expenseBudgetBarChart.setTitle("Stacked Bar Chart");

        // Create 2 series
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series1.setName("Budget Spent");
        series2.setName("Budget Left");

        String[] categories = {"Food", "Transportation", "Entertainment", "Bills", "Shopping"};
        
        // Add random data for both series
        for (String category : categories) {
            int randomValue1 = (int) (Math.random() * 50) + 10;  // Generate a random value between 50 and 149
            series1.getData().add(new XYChart.Data<>(category, randomValue1));
            int randomValue2 = (int) (Math.random() * 40) + 10;  // Generate a random value between 50 and 149
            series2.getData().add(new XYChart.Data<>(category, randomValue2));
        }

        expenseBudgetBarChart.getData().add(series1);
        expenseBudgetBarChart.getData().add(series2);

    }

    private void sampleExpenseChart() {
        expensePieChart.setTitle("Pie Chart");

        // Create 8 pie sections with random values
        String[] sections = {"Food", "Transportation", "Entertainment", "Bills", "Shopping"};
        
        for (String section : sections) {
            int randomValue1 = (int) (Math.random() * 100) + 20;
            expensePieChart.getData().add(new PieChart.Data(section, randomValue1));
        }
    }

}
