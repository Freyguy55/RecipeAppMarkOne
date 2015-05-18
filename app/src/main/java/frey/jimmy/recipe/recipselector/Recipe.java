package frey.jimmy.recipe.recipselector;

import java.io.Serializable;

/**
 * Created by James on 5/14/2015.
 */
public class Recipe implements Serializable{
    private String mRecipeName;
    private String mRecipeDescription;
    private int mRecipeImageId;
    private boolean mIsSweet;
    private boolean mIsLight;
    private int mTotalMinutes;
    private boolean mIsGood;

    public Recipe(String recipeName, int recipeImageId) {
        mRecipeName = recipeName;
        mRecipeImageId = recipeImageId;
        mIsLight = false;
        mIsSweet = false;
        mTotalMinutes = 60;
        mRecipeDescription = "This is a brief description";
        mIsGood = false;
    }

    public Recipe(String recipeName, int recipeImageId, boolean isSweet, boolean isLight) {
        mRecipeName = recipeName;
        mRecipeImageId = recipeImageId;
        mIsLight = isLight;
        mIsSweet = isSweet;
        mTotalMinutes = 30;
        mRecipeDescription = "This is also a description that is brief";
        mIsGood = true;
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

    public String getRecipeDescription() {
        return mRecipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        mRecipeDescription = recipeDescription;
    }

    public int getTotalMinutes() {
        return mTotalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        mTotalMinutes = totalMinutes;
    }

    public boolean isGood() {
        return mIsGood;
    }

    public void setIsGood(boolean isGood) {
        mIsGood = isGood;
    }

    public int getLikeDislikeImage(){
        if(mIsGood){
            return (R.drawable.recipe_image_01);
        } else{
            return (R.drawable.kirby_hurt);
        }
    }
}
