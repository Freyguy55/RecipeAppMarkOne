package frey.jimmy.recipe.recipselector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

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
    private int mRecipeStepImageId;
    private int mIsGood;
    private String mInstructions;
    private UUID mUuid;

    public Recipe(String recipeName, String recipeDescription, int servesNumber, boolean isSweet, boolean isLight, int totalMinutes, String region, ArrayList<RecipeStep> recipeStepList, ArrayList<Ingredient> recipeIngredientList, int recipeImageId, int recipeStepImageId, int isGood, String instructions) {
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
        mRecipeStepImageId = recipeStepImageId;
        mIsGood = isGood;
        mInstructions = instructions;
        mUuid = UUID.randomUUID();
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

    public void setRecipeStepList(ArrayList<RecipeStep> recipeStepList) {
        mRecipeStepList = recipeStepList;
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

    public UUID getUuid() {
        return mUuid;
    }

    public void setRecipeName(String recipeName) {
        mRecipeName = recipeName;
    }

    public void setRecipeDescription(String recipeDescription) {
        mRecipeDescription = recipeDescription;
    }

    public void setServesNumber(int servesNumber) {
        mServesNumber = servesNumber;
    }

    public void setIsSweet(boolean isSweet) {
        mIsSweet = isSweet;
    }

    public void setIsLight(boolean isLight) {
        mIsLight = isLight;
    }

    public void setTotalMinutes(int totalMinutes) {
        mTotalMinutes = totalMinutes;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public void setRecipeIngredientList(ArrayList<Ingredient> recipeIngredientList) {
        mRecipeIngredientList = recipeIngredientList;
    }

    public void setRecipeImageId(int recipeImageId) {
        mRecipeImageId = recipeImageId;
    }

    public void setInstructions(String instructions) {
        mInstructions = instructions;
    }

    public void setUuid(UUID uuid) {
        mUuid = uuid;
    }

    public int getRecipeStepImageId() {
        return mRecipeStepImageId;
    }

    public void setRecipeStepImageId(int recipeStepImageId) {
        mRecipeStepImageId = recipeStepImageId;
    }

    @Override
    public boolean equals(Object recipe) {
        Recipe r = (Recipe) recipe;
        return this.mUuid.equals(r.mUuid);
    }
}

