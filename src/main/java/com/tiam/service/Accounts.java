package com.tiam.service;

public class Accounts {
    public static void  resetAccountsOnIncomeDelete(Double income) {
        Double available_income = Database.getAvailableIncome();

        if (available_income < income) {
            Database.decreaseAvailableIncomeBy(available_income);
            Database.decreaseRemainingBudgetBy(income - available_income);

            // uniformly remove the excess income from every budget record
            Double shared_portion = (income - available_income) / Database.getExpenseCategoryCount();
            Database.decreaseAllBudgetsBy(shared_portion);
        } else {
            Database.decreaseAvailableIncomeBy(income);
        }

        Database.decreaseNetworthBy(income);
    }

    public static void resetAccountOnIncomeInsert(Double income) {
        Database.increaseAvailableIncomeBy(income);
        Database.increaseNetworthBy(income);
    }

    public static void resetAccountOnIncomeUpdate(Double oldIncome, Double newIncome) {
        if (newIncome <= oldIncome) {
            resetAccountsOnIncomeDelete(oldIncome - newIncome);
        } else {
            resetAccountOnIncomeInsert(newIncome - oldIncome);
        }
    }

    public static void resetAccountOnExpenseDelete(Double expense) {
        Database.increaseRemainingBudgetBy(expense);
        Database.increaseNetworthBy(expense);
    }

    public static void resetAccountOnExpenseCategoryDelete(Double budget) {
        Database.increaseAvailableIncomeBy(budget);
        Database.decreaseRemainingBudgetBy(budget);
    }

    public static void resetAccountOnExpenseInsert(Double expense) {
        Database.decreaseRemainingBudgetBy(expense);
        Database.decreaseNetworthBy(expense);
    }

    public static void resetAccountOnExpenseUpdate(Double oldExpense, Double newExpense) {
        Database.increaseRemainingBudgetBy(newExpense - oldExpense);
        Database.increaseNetworthBy(newExpense - oldExpense);
    }

    public static void resetAccountOnBudgetUpdate(Double amount) {
        Database.decreaseAvailableIncomeBy(amount);
        Database.increaseRemainingBudgetBy(amount);
    }
}
