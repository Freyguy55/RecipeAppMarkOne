package frey.jimmy.recipe.recipselector;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by James on 5/17/2015.
 */
public class RecipeBook {
    private static final int CATEGORY_SWEET_SAVORY = 0;
    private static final int CATEGORY_LIGHT_HEAVY = 1;
    private static final int CATEGORY_REGION = 2;
    private static RecipeBook sRecipeBook;
    private ArrayList<Recipe> mRecipes;
    private Context mAppContext;

    private RecipeBook(Context context) {
        mAppContext = context;

        mRecipes = new ArrayList<>();
        mRecipes.add(new Recipe("Pie", R.drawable.meat, false, false, 25, "It's so tasty!", true));
        mRecipes.add(new Recipe("Steak", R.drawable.meat, false, false, 15, "Ooey gooey inside.", false));
        mRecipes.add(new Recipe("Chicken", R.drawable.meat, true, false, 20, "Yummy tummy.", false));
        mRecipes.add(new Recipe("Cheekahn", R.drawable.meat));
        mRecipes.add(new Recipe("Tacos", R.drawable.meat));
        mRecipes.add(new Recipe("Salami", R.drawable.meat, false, false, 2, "Salami goes with everything!", true));
        mRecipes.add(new Recipe("Ribs", R.drawable.meat));
        mRecipes.add(new Recipe("Pizza", R.drawable.meat, true, true, 10, "How does it taste?", false));
        mRecipes.add(new Recipe("Cheetos", R.drawable.meat, true, false, 5, "It's so easy!", false));
        mRecipes.add(new Recipe("Cookies", R.drawable.meat));
        mRecipes.add(new Recipe("Sushi", R.drawable.meat, true, true));
        mRecipes.add(new Recipe("Sashimi", R.drawable.meat, false, true, 35, "Laaadiiidaaa! Yuppers.", false));
        mRecipes.add(new Recipe("Lamb", R.drawable.meat, true, false));
        mRecipes.add(new Recipe("Naan", R.drawable.meat, false, true));

    }

    public static RecipeBook get(Context context) {
        if (sRecipeBook == null) {
            sRecipeBook = new RecipeBook(context.getApplicationContext());
        }
        return sRecipeBook;
    }

    public ArrayList<Recipe> getRecipes() {
        return mRecipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
    }

    public ArrayList<Recipe> getFilteredRecipes(boolean isSweet, boolean isLight) {
        ArrayList<Recipe> filteredList = new ArrayList<>();
        for (Recipe r : mRecipes) {
            if (r.isSweet() == isSweet && r.isLight() == isLight) {
                filteredList.add(r);
            }
        }
        return filteredList;
    }

    public ArrayList<String> getSpinnerList(int category) {
        Set<String> spinnerSet = new HashSet<>();
        switch (category) {
            case CATEGORY_SWEET_SAVORY:
                spinnerSet.add(mAppContext.getString(R.string.button_text_sweet));
                spinnerSet.add(mAppContext.getString(R.string.button_text_savory));
                break;
            case CATEGORY_LIGHT_HEAVY:
                spinnerSet.add(mAppContext.getString(R.string.button_text_light));
                spinnerSet.add(mAppContext.getString(R.string.button_text_heavy));
                break;
            case CATEGORY_REGION:
                for (Recipe r : mRecipes) {
                    spinnerSet.add(r.getRegion());
                }
                break;
        }
        return new ArrayList<String>(spinnerSet);
    }
}
