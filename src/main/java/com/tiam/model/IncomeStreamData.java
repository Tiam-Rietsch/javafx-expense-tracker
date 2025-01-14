package com.tiam.model;

import com.tiam.service.Color;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This model represents income stream categories
 * 
 * An income stream category has the following properties
 * - id
 * - name
 * - color (visual touch to identify the category)
 * 
 * @author TiamRietsch
 * @version 1.0
 */
public class IncomeStreamData {
    /**
     * The color  help to visually identify the task. the color is of type {@link Color}
     * 
     * @see com.tiam.service.Color  
     */
    private final ObjectProperty<Color> colorProp = new SimpleObjectProperty<>();

    /**
     * the name of the expense category
     */
    private final ObjectProperty<String> nameProp = new SimpleObjectProperty<>();

    /**
     * The id of the income stream in the database
     */
    private final ObjectProperty<Integer> idProp = new SimpleObjectProperty<>();


    /**
     * Sets the color of the income stream
     * @param color of type {@code Color}
     * 
     * @see com.tiam.service.Color
     */
    public void setColor(Color color) {
        colorProp.set(color);
    }

    /**
     * Sets the name of the income stream
     * @param name of type {@code String}
     */
    public void setName(String name) {
        nameProp.set(name);
    }

    /**
     * Retrieves the color of the income stream
     * @return an object of type {@code Color}
     * 
     * @see com.tiam.service.Color
     */
    public Color getColor() {
        return colorProp.get();
    }

    /**
     * Retrieve the name of the income stream
     * @return a {@code String} 
     */
    public String getName() {
        return nameProp.get();
    }

    /**
     * returns the color property of the income stream, used for UI binding
     * @return an object of type {@code ObjectProperty<Color>} which represents the color property 
     * 
     * @see com.tiam.service.Color
     */
    public ObjectProperty<Color> colorProperty() {
        return colorProp;
    }


    /**
     * returns the name property of the income stream, used for UI binding
     * @return an object of type {@code ObjectProperty<String>} which represents the name property
     * @return
     */
    public ObjectProperty<String> nameProperty() {
        return nameProp;
    }

    /**
     * returns the id of the income stream, id is directly from the database
     * @return an {@code int} correspondin to the id
     */
    public int getId() {
        return idProp.get();
    }

    /**
     * Sets the id of the income stream, as gotten from the database
     * @param id of type {@code int}
     */
    public void setId(int id) {
        idProp.set(id);
    }   

    /**
     * returns the id property of the income stream, used for UI binding
     * @return an object of type {@code ObjectProperty<Integer>} corresponding to the id property
     */
    public ObjectProperty<Integer> idProperty() {
        return idProperty();
    }
}
