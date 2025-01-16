package com.tiam.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This model is used to represent a budget record, usually used to represent rows in the budget table
 * <p>a budget record is made up of an expense category, and an ammount allocated</p>
 * 
 * @author Tiam Rietsch
 * 
 * @see ExpenseCategoryData
 */
public class BudgetData {

    /**
     * The expense category of the budget
     * 
     * @see ExpenseCategoryData
     */
    private final SimpleObjectProperty<ExpenseCategoryData> expenseProp = new SimpleObjectProperty<>();

    /**
     * The amount allocated to the expense category
     */
    private final SimpleObjectProperty<Double> amountProp = new SimpleObjectProperty<>();

    private final ObjectProperty<Integer> idProp = new SimpleObjectProperty<>();


    /**
     * Constructs a new {@code BudgetData} with the specified expense category and budget amount
     * @param expense the expense for which the budget is allocated (object of type {@link ExpenseCategory})
     * @param amount the amount allocated to the expense category (object of type {@code Double})
     * @see ExpenseCategory
     */
    public BudgetData(ExpenseCategoryData expense, Double amount) {
        expenseProp.set(expense);
        amountProp.set(amount);
    }

    public Integer getId() {
        return idProp.get();
    }

    public void setId(int id) {
        idProp.set(id);
    }

    public ObjectProperty<Integer> idProperty() {
        return idProp;
    }

    /**
     * Gets the expense category associated with the budget row
     * @return the expense category object of type ({@link ExpenseCategoryDdata})
     */
    public ExpenseCategoryData getExpense() {
        return expenseProp.get();
    }

    /**
     * Sets the expense category for the budget row
     * @param expense the expense category of the budget entry
     */
    public void setExpense(ExpenseCategoryData expense) {
        expenseProp.set(expense);
    }

    /**
     * Returns the expense category property object, used for UI binding
     * @return an object of type {@code ObjectProperty<ExpenseCategory>} corresponding to the property
     * 
     * @see ExpenseCategoryData
     */
    public ObjectProperty<ExpenseCategoryData> expenseProperty() {
        return expenseProp;
    }

    /**
     * returns the amount allocated to the expense category of the budget
     * @return a {@code Double} corresponding to the amount
     */
    public Double getAmount() {
        return amountProp.get();
    }

    /**
     * sets the amount allocated tot he expense category of the budget
     * @param amount of type {@code Double}
     */
    public void setAmount(Double amount) {
        amountProp.set(amount);
    }

    /**
     * returns the amount property of the budget, used for UI bindings
     * @return an object of type {@code ObjectProperty<Double>} corresponding to the property
     */
    public ObjectProperty<Double> amountProperty() {
        return amountProp;
    }
}
