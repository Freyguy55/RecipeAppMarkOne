package frey.jimmy.recipe.recipselector;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Ingredient class for use in Recipe class.  The recipe class contains an array of ingredients.
 * Ingredients consist of a mQuantity, a unit of measurement, and a name.
 */
public class Ingredient implements Serializable{
    private double mQuantity;
    private String mUnit;
    private String mName;

    public Ingredient() {
        this(0.0,"NoIngredient");
    }

    public Ingredient(double quantity, String name) {
        mQuantity = quantity;
        mName = name;
    }

    public Ingredient(double quantity, String unit, String name) {
        mQuantity = quantity;
        mUnit = unit;
        mName = name;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public String getUnit() {
        return mUnit;
    }

    public String getName() {
        return mName;
    }

    public String toString(){
        DecimalFormat decimalFormat = new DecimalFormat("####.##");
        if(null == mUnit){
            return mQuantity + " " + mName;
        }
        return decimalFormat.format(mQuantity) + " " + mUnit + " " + mName;
    }

}
