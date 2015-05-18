package frey.jimmy.recipe.recipselector;

/**
 * Created by James on 5/14/2015.
 */
public class Recipe {
    private String mRecipeName;
    private int mRecipeImageId;
    private boolean mIsSweet;
    private boolean mIsLight;

    public Recipe(String recipeName, int recipeImageId) {
        mRecipeName = recipeName;
        mRecipeImageId = recipeImageId;
        mIsLight = false;
        mIsSweet = false;
    }

    public Recipe(String recipeName, int recipeImageId, boolean isSweet, boolean isLight) {
        mRecipeName = recipeName;
        mRecipeImageId = recipeImageId;
        mIsLight = isLight;
        mIsSweet = isSweet;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public void setRecipeName(String recipeName) {
        mRecipeName = recipeName;
    }

    public int getRecipeImageId() {
        return mRecipeImageId;
    }

    public void setRecipeImageId(int recipeImageId) {
        mRecipeImageId = recipeImageId;
    }

    public boolean isSweet() {
        return mIsSweet;
    }

    public void setIsSweet(boolean isSweet) {
        mIsSweet = isSweet;
    }

    public boolean isLight() {
        return mIsLight;
    }

    public void setIsLight(boolean isLight) {
        mIsLight = isLight;
    }
}
