package com.tiam.model;

import com.tiam.service.Color;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ExpenseCategoryData {
    private final ObjectProperty<Color> colorProp = new SimpleObjectProperty<>();
    private final ObjectProperty<String> nameProp = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> idProp = new SimpleObjectProperty<>();


    public Color getColor() {
        return colorProp.get();
    }

    public void setColor(Color color) {
        colorProp.set(color);
    }

    public ObjectProperty<Color> colorProperty() {
        return colorProp;
    }

    public String getName() {
        return nameProp.get();
    }

    public void setName(String name) {
        nameProp.set(name);
    }

    public ObjectProperty<String> nameProperty() {
        return nameProp;
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
