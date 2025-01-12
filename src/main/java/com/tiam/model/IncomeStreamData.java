package com.tiam.model;

import com.tiam.service.Color;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class IncomeStreamData {
    private final ObjectProperty<Color> colorProp = new SimpleObjectProperty<>();
    private final ObjectProperty<String> nameProp = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> idProp = new SimpleObjectProperty<>();

    public void setColor(Color color) {
        colorProp.set(color);
    }

    public void setName(String name) {
        nameProp.set(name);
    }

    public Color getColor() {
        return colorProp.get();
    }

    public String getName() {
        return nameProp.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return colorProp;
    }

    public ObjectProperty<String> nameProperty() {
        return nameProp;
    }

    public int getId() {
        return idProp.get();
    }

    public void setId(int id) {
        idProp.set(id);
    }   

    public ObjectProperty<Integer> idProperty() {
        return idProperty();
    }
}
