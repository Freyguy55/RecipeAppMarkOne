package frey.jimmy.recipe.recipselector;


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
        mImageId = 9;
    }

    public RecipeStep(String[] ingredientsArray, String instructions, int imageId) {
        mIngredientsArray = ingredientsArray;
        mInstructions = instructions;
        mImageId = imageId;
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