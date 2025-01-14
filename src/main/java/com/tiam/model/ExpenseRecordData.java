package com.tiam.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This class represents the expense records, mainly used to define rows in the expense table
 * for given expense categories
 * 
 * <p>The attributes are:: reason, amount, id, date</p>
 * 
 * @author Tiam Rietsch
 * @see ExpenseCategoryData
 */
public class ExpenseRecordData {
    /*
     * The reason for the given expenditure
     */
    private final ObjectProperty<String> reasonProp = new SimpleObjectProperty<>();

    /**
     * The amount spent
     */
    private final ObjectProperty<Double> amountProp = new SimpleObjectProperty<>();

    /**
     * The id of the expenditure record as in the database
     */
    private final ObjectProperty<Integer> idProp = new SimpleObjectProperty<>();

    /**
     * The date the expenditure is made
     */
    private final ObjectProperty<String> dateProp = new SimpleObjectProperty<>();

    public ExpenseRecordData() {

    }

    /**
     * returns the date the expenditure is made, formated according to {@code dd MMM yyyy} format
     * @return a {@code String} corresponding to the date
     */
    public String getDate() {
        return dateProp.get();
    }

    /**
     * sets the date the expenditure is made, date is formatted according to {@code dd MMM yyyy} format
     * @param date of type {@code String}
     */
    public void setDate(String date) {
        dateProp.set(date);
    }

    /**
     * returns the date property of the record, used for UI binding
     * @return an object of type {@code ObjectProperty<String>} corresponding to the date property
     */
    public ObjectProperty<String> dateProperty() {
        return dateProp;
    }

    /**
     * get the reason for the expenditure
     * @return a {@code String} corresponding to the reason
     */
    public String getReason() {
        return reasonProp.get();
    }

    /**
     * sets the reason for the expenditure
     * @param reason of type {@code reason}
     */
    public void setReason(String reason) {
        reasonProp.set(reason);
    }

    /**
     * returns the reason property of the record, used for UI binding
     * @return an object of type {@code ObjectProperty<String>} corresponding to the reason
     */
    public ObjectProperty<String> reasonProperty() {
        return reasonProp;
    }

    /**
     * returns the amount spent
     * @return a {@code Double} corresponding to that amount
     */
    public Double getAmount() {
        return amountProp.get();
    }

    /**
     * sets the amount spent 
     * @param amount of type {@code Double}
     */
    public void setAmount(Double amount) {
        amountProp.set(amount);
    }

    /**
     * returns the amount property of the record, used for UI binding
     * @return an object of type {@code ObjectProperty<Double>} corresponding to the amount property
     */
    public ObjectProperty<Double> amountProperty() {
        return amountProp;
    }

    /**
     * returns the id of the record as gotten from the dateabase
     * @return a {@code Integer} corresponding to the id
     */
    public Integer getId() {
        return idProp.get();
    }

    /**
     * sets the id of the record gotten from the database
     * @param id of type {@code int}
     */
    public void setId(int id) {
        idProp.set(id);
    }

    /**
     * returns the id property of the record, used for UI binding
     * @return an object of type {@code ObjectProperty<Integer>} corresponding to the id property
     */
    public ObjectProperty<Integer> idProperty() {
        return idProp;
    }
}
