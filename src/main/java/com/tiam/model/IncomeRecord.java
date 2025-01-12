package com.tiam.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class IncomeRecord {
    private final ObjectProperty<Integer> idProp = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> amountProp = new SimpleObjectProperty<>();
    private final ObjectProperty<String> dateProp = new SimpleObjectProperty<>();

    public int getId() {
        return idProp.get();
    }

    public void setId(int id) {
        idProp.set(id);
    }

    public ObjectProperty<Integer> idProperty() {
        return idProp;
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

    public String getDate() {
        return dateProp.get();
    }

    public void setLocalDate(String date) {
        dateProp.set(date);
    }

    public ObjectProperty<String> dateProperty() {
        return dateProp;
    }
}
