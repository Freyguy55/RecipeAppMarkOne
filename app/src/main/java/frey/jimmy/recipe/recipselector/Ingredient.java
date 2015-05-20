package frey.jimmy.recipe.recipselector;

import java.io.Serializable;

/**
 * Ingredient class for use in Recipe class.  The recipe class contains an array of ingredients.
 * Igredients consist of a quantity, a unit of measurement, and a name.
 */
public class Ingredient implements Serializable{
    private double quantity;
    private String unit;
    private String name;

    public Ingredient() {
        this(0.0,"NoIngredient");
    }

    public Ingredient(double quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public Ingredient(double quantity, String unit, String name) {
        this.quantity = quantity;
        this.unit = unit;
        this.name = name;
    }

    /* Getters and setters */
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
