package com.tiam.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;



/**
 * represents a row in the budjet table
 * 
 * <p>This class defines the budjet table data model used to represent each row, where each row is made up of
 * an <b>expense category</b> and a <b>budjet amount</b>. It uses javafx properties ({@link SimpleObjectProperty}) to 
 * represent the attributes of each row, to allow for easy binding and dynamic updates within the table cell.</p>
 * 
 * @see SimpleObjectProperty
 */
public class BudjetData {

    private final SimpleObjectProperty<ExpenseCategoryData> expenseProp = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Double> amountProp = new SimpleObjectProperty<>();


    /**
     * Constructs a new {@code BudjetData} with the specified expense category and budjet amount
     * @param expense the expense for which the budjet is allocated (object of type {@link ExpenseCategory})
     * @param amount the amount allocated to the expense category (object of type {@code Double})
     * @see ExpenseCategory
     */
    public BudjetData(ExpenseCategoryData expense, Double amount) {
        expenseProp.set(expense);
        amountProp.set(amount);
    }

    /**
     * Gets the expense category associated with the budjet row
     * @return the expense category object of type ({@link ExpenseCategoryDdata})
     */
    public ExpenseCategoryData getExpense() {
        return expenseProp.get();
    }

    /**
     * Sets the expense category for the budjet row
     * @param expense the expense category of the budjet entry
     */
    public void setExpense(ExpenseCategoryData expense) {
        expenseProp.set(expense);
    }

    /**
     * Returns the expense category property object
     * @return a {@link SimpleObjectProperty} wrapping the expense category
     */
    public ObjectProperty<ExpenseCategoryData> expenseProperty() {
        return expenseProp;
    }

    public Double getAmount() {
        return amountProp.get();
    }

    public void setAmount(Double amount) {
        amountProp.set(amount);
    }

    public ObjectProperty<Double> amountProperty() {
        return amountProp;
    }
}
