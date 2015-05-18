package frey.jimmy.recipe.recipselector;

import java.util.ArrayList;

/**
 * Created by James on 5/17/2015.
 */
public class RecipeBook {
    private static RecipeBook sRecipeBook;
    private ArrayList<Recipe> mRecipes;

    private RecipeBook(){
        mRecipes = new ArrayList<>();
        mRecipes.add(new Recipe("Pie", R.drawable.recipe_image_01));
        mRecipes.add(new Recipe("Steak", R.drawable.recipe_image_01));
        mRecipes.add(new Recipe("Chicken", R.drawable.recipe_image_01));
        mRecipes.add(new Recipe("Cheekahn", R.drawable.recipe_image_01));
        mRecipes.add(new Recipe("Tacos", R.drawable.recipe_image_01));
        mRecipes.add(new Recipe("Salami", R.drawable.recipe_image_01));
        mRecipes.add(new Recipe("Ribs", R.drawable.recipe_image_01));
        mRecipes.add(new Recipe("Pizza", R.drawable.recipe_image_01, true, true));
        mRecipes.add(new Recipe("Cheetos", R.drawable.recipe_image_01, true, false));
        mRecipes.add(new Recipe("Cookies", R.drawable.recipe_image_01));
        mRecipes.add(new Recipe("Sushi", R.drawable.recipe_image_01, true, true));
        mRecipes.add(new Recipe("Sashimi", R.drawable.recipe_image_01, false, true));
        mRecipes.add(new Recipe("Lamb", R.drawable.recipe_image_01, true, false));
        mRecipes.add(new Recipe("Naan", R.drawable.recipe_image_01, false, true));

    }

    public static RecipeBook get(){
        if(sRecipeBook==null){
            sRecipeBook = new RecipeBook();
        }
        return sRecipeBook;
    }

    public ArrayList<Recipe> getRecipes() {
        return mRecipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
    }

    public ArrayList<Recipe> getFilteredRecipes(boolean isSweet, boolean isLight){
        ArrayList<Recipe> filteredList = new ArrayList<>();
        for(Recipe r : mRecipes){
            if (r.isSweet()==isSweet && r.isLight()==isLight){
                filteredList.add(r);
            }
        }
        return filteredList;
    }
}
