package frey.jimmy.recipe.recipselector;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by James on 5/14/2015.
 */
public class Recipe implements Serializable {
    public static final int RECIPE_IS_BAD = 0;
    public static final int RECIPE_IS_GOOD = 1;
    public static final int RECIPE_IS_UNKNOWN = 2;
    private String mRecipeName;
    private String mRecipeDescription;
    private int mServesNumber;
    private boolean mIsSweet;
    private boolean mIsLight;
    private int mTotalMinutes;
    private String mRegion;
    private ArrayList<RecipeStep> mRecipeStepList;
    private ArrayList<Ingredient> mRecipeIngredientList;
    private int mRecipeImageId;
    private int mIsGood;
    private String mInstructions;

    public Recipe(String recipeName, String recipeDescription, int servesNumber, boolean isSweet, boolean isLight, int totalMinutes, String region, ArrayList<RecipeStep> recipeStepList, ArrayList<Ingredient> recipeIngredientList, int recipeImageId, int isGood, String instructions) {
        mRecipeName = recipeName;
        mRecipeDescription = recipeDescription;
        mServesNumber = servesNumber;
        mIsSweet = isSweet;
        mIsLight = isLight;
        mTotalMinutes = totalMinutes;
        mRegion = region;
        mRecipeStepList = recipeStepList;
        mRecipeIngredientList = recipeIngredientList;
        mRecipeImageId = recipeImageId;
        mIsGood = isGood;
        mInstructions = instructions;
    }

    public String getRecipeName() {
        return mRecipeName;
    }


    public int getRecipeImageId() {
        return mRecipeImageId;
    }


    public boolean isSweet() {
        return mIsSweet;
    }


    public boolean isLight() {
        return mIsLight;
    }

    public String getRecipeDescription() {
        return mRecipeDescription;
    }

    public int getTotalMinutes() {
        return mTotalMinutes;
    }


    public int isGood() {
        return mIsGood;
    }

    public void setIsGood(int isGood) {
        mIsGood = isGood;
    }

    public String getRegion() {
        return mRegion;
    }


    public int getServesNumber() {
        return mServesNumber;
    }

    public ArrayList<RecipeStep> getRecipeStepList() {
        return mRecipeStepList;
    }


    public ArrayList<Ingredient> getRecipeIngredientList() {
        return mRecipeIngredientList;
    }

    public ArrayList<String> getRecipeIngredientStringList(){
        ArrayList<String> ingredientStringList = new ArrayList<>();
        for(Ingredient i : mRecipeIngredientList){
            ingredientStringList.add(i.toString());
        }
        return ingredientStringList;
    }

    public String getInstructions() {
        return mInstructions;
    }
}

