package com.tiam.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class BudjetData {
    public static final String MANUAL = "manual";
    public static final String AUTO = "auto";

    private final SimpleObjectProperty<ExpenseCategoryData> expenseProp = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Double> amountProp = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> allocationProp = new SimpleObjectProperty<>();


    public BudjetData(ExpenseCategoryData expense, Double amount) {
        expenseProp.set(expense);
        amountProp.set(amount);
        allocationProp.set(MANUAL);

    }

    public ExpenseCategoryData getExpense() {
        return expenseProp.get();
    }

    public void setExpense(ExpenseCategoryData expense) {
        expenseProp.set(expense);
    }

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

    public String getAllocation() {
        return allocationProp.get();
    }

    public void setAllocation(String allocation) {
        allocationProp.set(allocation);
    }

    public ObjectProperty<String> allocationProperty() {
        return allocationProp;
    }
}
