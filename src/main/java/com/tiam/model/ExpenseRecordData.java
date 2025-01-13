package com.tiam.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ExpenseRecordData {
    private final ObjectProperty<String> reasonProp = new SimpleObjectProperty<>();
    private final ObjectProperty<Double> amountProp = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> idProp = new SimpleObjectProperty<>();
    private final ObjectProperty<String> dateProp = new SimpleObjectProperty<>();

    public ExpenseRecordData() {

    }

    public String getDate() {
        return dateProp.get();
    }

    public void setDate(String date) {
        dateProp.set(date);
    }

    public ObjectProperty<String> dateProperty() {
        return dateProp;
    }

    public String getReason() {
        return reasonProp.get();
    }

    public void setReason(String reason) {
        reasonProp.set(reason);
    }

    public ObjectProperty<String> reasonProperty() {
        return reasonProp;
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

    public Integer getId() {
        return idProp.get();
    }

    public void setId(int id) {
        idProp.set(id);
    }

    public ObjectProperty<Integer> idProperty() {
        return idProp;
    }
}
