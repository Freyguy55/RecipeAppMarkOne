package frey.jimmy.recipe.recipselector;

import android.graphics.drawable.BitmapDrawable;

import java.io.Serializable;

/**
 * Created by James on 5/19/2015.
 */
public class RecipeStep implements Serializable{
    private String[] mIngredientsArray= {"1 cup stuff", "3 carrots", "1/4 cup onions", "15 cream puffs", "5 golden rings","2 turtle doves", "1 pear tree"};
    private String mInstructions;
    private int mImageId;

    public RecipeStep(){
        mInstructions = "Test step instructions";
    }

    public RecipeStep(String[] ingredientsArray, String instructions, BitmapDrawable stepImage) {
        mIngredientsArray = ingredientsArray;
        mInstructions = instructions;
    }

    public String[] getIngredientsArray() {
        return mIngredientsArray;
    }

    public void setIngredientsArray(String[] ingredientsArray) {
        mIngredientsArray = ingredientsArray;
    }

    public String getInstructions() {
        return mInstructions;
    }

    public void setInstructions(String instructions) {
        mInstructions = instructions;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }
}
