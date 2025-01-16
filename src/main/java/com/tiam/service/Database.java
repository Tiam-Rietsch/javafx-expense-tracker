package com.tiam.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.chart.PieChart.Data;

public class Database {

    public static Connection getConnection() {
        String url = "jdbc:sqlite:database/expense-tracker.db";
        Connection con = null;
        try {
            con = DriverManager.getConnection(url);
            Statement statment = con.createStatement();
            statment.execute("PRAGMA foreign_keys = ON");
            statment.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    public static void clcoseEverything(Connection con, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (con != null) con.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("error closing");
        }
    }

    // ------------------------------------------------- available income 

    public static Double getAvailableIncome() {
        String query = "SELECT value FROM Data WHERE label=\"available_income\"";
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {   
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return null;
    }

    public static void decreaseAvailableIncomeBy(Double value) {
        String query = "UPDATE Data SET value = value - %f WHERE label=\"available_income\"".formatted(value);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }
    }

    public static void increaseAvailableIncomeBy(Double value) {
        String query = "UPDATE Data SET value = value + %f WHERE label=\"available_income\"".formatted(value);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }
    }

    // --------------------------------------------------------- budget

    public static void decreaseAllBudgetsBy(Double amt) {
        String query = "UPDATE Budget SET amount = amount - %f".formatted(amt);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }
    }

    public static void increaseBudgetBy(int category_id, Double amt) {
        String query = "UPDATE Budget SET amount = amount + %f WHERE category_id = %d".formatted(amt, category_id);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }
    }

    public static Double getRemainingBudget() {
        String query = "SELECT value FROM Data WHERE label=\"remaining_budget\"";
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {   
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return null;
    }

    public static Double getBudgetForCategory(int category_id) {
        String query = "SELECT amount FROM Budget WHERE category_id=%d".formatted(category_id);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return 0.0;
    }

    public static void increaseRemainingBudgetBy(Double value) {
        String query = "UPDATE Data SET value = value + %f WHERE label=\"remaining_budget\"".formatted(value);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }
    }

    public static void decreaseRemainingBudgetBy(Double value) {
        String query = "UPDATE Data SET value = value - %f WHERE label=\"remaining_budget\"".formatted(value);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }
    }
    

    // ---------------------------------------------------------------- networth 

    public static void increaseNetworthBy(Double value) {
        String query = "UPDATE Data SET value= value + %f WHERE label=\"networth\"".formatted(value);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }
    }

    public static void decreaseNetworthBy(Double value) {
        String query = "UPDATE Data SET value= value - %f WHERE label=\"networth\"".formatted(value);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }
    }

    public static Double getNetworth() {
        String query = "SELECT value FROM Data WHERE label=\"networth\"";
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {   
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return 0.0;
    }


    // ----------------------------------------------------- other

    public static Double getTotalExpenseForCategory(int category_id) {
        String query = "SELECT SUM(amount) AS total_expenses FROM ExpenseRecord WHERE category_id=%d".formatted(category_id);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total_expenses");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return 0.0;
    }

    public static Double getTotalIncomeForStream(int stream_id) {
        String query = "SELECT SUM(amount) AS total_income FROM IncomeRecord WHERE stream_id=%d".formatted(stream_id);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total_income");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return 0.0;
    }

    public static int getExpenseCategoryCount() {
        String query = "SELECT COUNT(*) AS total_count FROM ExpenseCategory";
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }
        return 1;
    }

    public static HashMap<Integer, Double> getIncomeIdsForStream(int stream_id) {
        String query = "SELECT id, amount FROM IncomeRecord WHERE stream_id=%d".formatted(stream_id);
        HashMap<Integer, Double> map = new HashMap<>();
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                map.put(resultSet.getInt("id"), resultSet.getDouble("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return map;
    }

    public static HashMap<Integer, Double> getExpensesForCategory(int category_id) {
        String query = "SELECT id, amount FROM ExpenseRecord WHERE category_id=%d".formatted(category_id);
        HashMap<Integer, Double> map = new HashMap<>();
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                map.put(resultSet.getInt("id"), resultSet.getDouble("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return map;
    }

    public static Double getIncomeAmount(int id) {
        String query = "SELECT amount FROM IncomeRecord WHERE id=%d".formatted(id);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("amunt");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return 0.0;
    }
}
