package com.tiam.model;

import com.tiam.service.Color;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


/**
 * This model is used to represent various expense categories registered by the 
 * user.
 * 
 * <p>an expense category has attributes color, name, id</p>
 * 
 * @author Tiam Rietsch
 */
public class ExpenseCategoryData {
    /**
     * The color of the expense category, used to visually identify the expense category.
     * the color is of type {@link Color}
     */
    private final ObjectProperty<Color> colorProp = new SimpleObjectProperty<>();

    /**
     * The name of the expense category
     */
    private final ObjectProperty<String> nameProp = new SimpleObjectProperty<>();

    /**
     * The id of the expense category as gotten from the database
     */
    private final ObjectProperty<Integer> idProp = new SimpleObjectProperty<>();

    /**
     * returns the color of the expense category. the color is of type {@link Color}
     * @return an object of type {@code Color} corresponding to the color
     * 
     * @see com.tiam.service.Color
     */
    public Color getColor() {
        return colorProp.get();
    }

    /**
     * sets the color of the expense category
     * @param color of type {@code Color}
     * 
     * @see com.tiam.service.Color
     */
    public void setColor(Color color) {
        colorProp.set(color);
    }

    /**
     * returns the color property of the expense category, used for UI binding
     * @return an object of type {@code ObjectProperty<Color>} corresponding to the color
     * 
     * @see com.tiam.service.Color
     */
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
