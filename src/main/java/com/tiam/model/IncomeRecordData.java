package com.tiam.model;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


/**
 * <p>This model represent a record inside an inceome stream, mainly used to define the rows
 * of the income_table for a given income stream.</p>
 * 
 * <p>The properties of an income record are: id, amount, dadte</p>
 *  
 * @author Tiam Rietsch
 * 
 * @see IncomeStreamData
 */
public class IncomeRecordData {
    /**
     * The id of the income record in the database
     */
    private final ObjectProperty<Integer> idProp = new SimpleObjectProperty<>();

    /**
     * The amount to be recorded
     */
    private final ObjectProperty<Double> amountProp = new SimpleObjectProperty<>();

    /**
     * The date the income record is added to the database
     */
    private final ObjectProperty<String> dateProp = new SimpleObjectProperty<>();


    /**
     * returns the id of the income record, as gotten from the database
     * @return an {@code int} corresponding to the id
     */
    public int getId() {
        return idProp.get();
    }

    /**
     * sets the id of the income record
     * @param id of type {@code int}
     */
    public void setId(int id) {
        idProp.set(id);
    }

    /**
     * returns the id property of the income record, used for UI binding
     * @return an object of type {@code ObjectProperty<Integer>} correseponding ot the id property
     */
    public ObjectProperty<Integer> idProperty() {
        return idProp;
    }

    /**
     * returns the amount  of the income record
     * @return an object of type {@code Double}
     */
    public Double getAmount() {
        return amountProp.get();
    }

    /**
     * sets teh amount of the income record
     * @param amount of type {@code Double}
     */
    public void setAmount(Double amount) {
        amountProp.set(amount);
    }

    /**
     * returns the amount property of the income record, used for UI binding
     * @return an object of type {@code ObjectProperty<Double>} corresponding to the amount property
     */
    public ObjectProperty<Double> amountProperty() {
        return amountProp;
    }

    /**
     * returns the formatted date the inomce is recorded in the database. 
     * usually, date is formated according to the specifications {@code dd MMM yyyy}
     * 
     * <p>exampple: 01 Jan 2025<p>
     * 
     * @return a {@code String} that corresponds to the formatted
     * 
     * @see DateManager
     */
    public String getDate() {
        return dateProp.get();
    }

    /**
     * sets the date the record is added to the database.
     * usually, date is formated according to the specifications {@code dd MMM yyyy}
     * 
     * <p>Example: 01 Jan 2025</p>
     * 
     * @param date of type {@code String}
     * 
     * @see DateManager
     */
    public void setDate(String date) {
        dateProp.set(date);
    }

    /**
     * returns the date property of the income stream, used for UI bindings
     * @return an object of type {@code ObjectProperty<String>} corresponding ot the date property
     */
    public ObjectProperty<String> dateProperty() {
        return dateProp;
    }
}
