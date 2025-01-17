package com.tiam.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


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
        String query = """
                SELECT available_income FROM Account
                WHERE id = %d
                """.formatted(Accounts.id);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {   
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("available_income");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return null;
    }

    public static void decreaseAvailableIncomeBy(Double value) {
        String query = """
                UPDATE Account SET available_income = available_income - %f 
                WHERE id = %d
                """.formatted(value, Accounts.id);
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
        String query = """
                UPDATE Account SET available_income = available_income + %f 
                WHERE id = %d
                """.formatted(value, Accounts.id);
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
        String query = """
                UPDATE Budget SET amount = amount - %f
                FROM Budget
                INNER JOIN ExpenseCategory ON Budget.category_id = ExpenseCategory.id
                INNER JOIN Account ON Account.id = ExpenseCategory.account_id
                WHERE Account.id = %d
                """.formatted(amt, Accounts.id);
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
        String query = """
                SELECT remaining_budget
                FROM Account
                WHERE id = %d
                """.formatted(Accounts.id);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {   
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("remaining_budget");
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

    public static Double getTotalBudgetForMonth() {
        String query = """
                SELECT SUM(amount) as total_budget 
                FROM Budget
                INNER JOIN ExpenseCategory ON ExpenseCategory.id = Budget.category_id
                INNER JOIN Account ON ExpenseCategory.account_id = Account.id
                WHERE Account.id = %d
                """.formatted(Accounts.id);
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total_budget");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return 0.0;
    }
    

    public static void increaseRemainingBudgetBy(Double value) {
        String query = """
                UPDATE Account SET remaining_budget = remaining_budget + %f 
                WHERE id = %d
                """.formatted(value, Accounts.id);
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
        String query = """
                UPDATE Account SET remaining_budget = remaining_budget - %f 
                WHERE id = %d
                """.formatted(value, Accounts.id);
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
        String query = """
                UPDATE Account SET networth = networth + %f 
                WHERE id = %d
                """.formatted(value, Accounts.id);
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
        String query =  """
            UPDATE Account SET networth = networth - %f 
            WHERE id = %d
            """.formatted(value, Accounts.id);
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
        String query = """
                SELECT networth FROM Account
                WHERE id=%d
                """.formatted(Accounts.id);
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

    public static Double getTotalExpensesForMonth() {
        String query = """
                SELECT SUM(amount) AS total_expenses
                FROM ExpenseRecord
                INNER JOIN ExpenseCategory ON ExpenseRecord.category_id = ExpenseCategory.id
                INNER JOIN Account ON Account.id = ExpenseCategory.account_id
                WHERE Account.id=%d
                """.formatted(Accounts.id);
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

    public static int getExpenseCategoryCount() {
        String query = """
                SELECT Count(*) AS total_count
                FORM ExpenseCategory
                INNER JOIN Account ON ExpenseCategory.category_id = Account.id
                WHERE Account.id = %d"
                """.formatted(Accounts.id);
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

    public static int getCurrentAccountId() {
        String query = "SELECT id FROM Account WHERE month=\"%s\" AND year=\"%s\"".formatted(DateManager.getCurrentMonth(), DateManager.getCurrentYear());
        String insertQuery = """
                INSERT INTO Account (available_income, remaining_budget, networth, month, year)
                VALUES ("0.0", "0.0", "0.0", "%s", "%s")
                """.formatted(DateManager.getCurrentMonth(), DateManager.getCurrentYear());
        Connection con = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                statement = con.prepareStatement(insertQuery);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clcoseEverything(con, statement, resultSet);
        }

        return getCurrentAccountId();
    }
}
