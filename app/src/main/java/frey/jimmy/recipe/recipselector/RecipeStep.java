package frey.jimmy.recipe.recipselector;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by James on 5/19/2015.
 */
public class RecipeStep implements Serializable{
    private ArrayList<Ingredient> mRecipeStepIngredientList;
    private String mInstructions;
    private int mImageId;

    public RecipeStep(){
    }

    public RecipeStep(ArrayList<Ingredient> recipeStepIngredientList, String instructions, int imageId) {
        mRecipeStepIngredientList = recipeStepIngredientList;
        mInstructions = instructions;
        mImageId = imageId;
    }

    public ArrayList<Ingredient> getRecipeStepIngredientList() {
        return mRecipeStepIngredientList;
    }

    public void setRecipeStepIngredientList(ArrayList<Ingredient> recipeStepIngredientList) {
        mRecipeStepIngredientList = recipeStepIngredientList;
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

    public ArrayList<String> getRecipeStepIngredientStringList(){
        ArrayList<String> ingredientStringList = new ArrayList<>();
        for(Ingredient i : mRecipeStepIngredientList){
            ingredientStringList.add(i.toString());
        }
        return ingredientStringList;
    }
}
