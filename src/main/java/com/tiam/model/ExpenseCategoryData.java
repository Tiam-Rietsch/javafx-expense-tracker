package com.tiam.model;

import com.tiam.service.Color;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ExpenseCategoryData {
    private final SimpleObjectProperty<Color> colorProp = new SimpleObjectProperty<>();
    private final SimpleStringProperty nameProp = new SimpleStringProperty();

    public ExpenseCategoryData(String name, Color color) {
        this.colorProp.set(color);
        this.nameProp.set(name);
    }

    public Color getColor() {
        return colorProp.get();
    }

    public void setColor(Color color) {
        colorProp.set(color);
    }

    public SimpleObjectProperty<Color> colorProperty() {
        return colorProp;
    }

    public String getName() {
        return nameProp.get();
    }

    public void setName(String name) {
        nameProp.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return nameProp;
    }

}
