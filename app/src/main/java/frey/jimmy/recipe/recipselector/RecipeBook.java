package frey.jimmy.recipe.recipselector;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
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
        loadRecipeList();

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
            Toast.makeText(mAppContext,"Recipe " + r.getRecipeName(),Toast.LENGTH_LONG);
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
                spinnerSet.add(mAppContext.getString(R.string.spinner_all_text));
                spinnerSet.add(mAppContext.getString(R.string.button_text_sweet));
                spinnerSet.add(mAppContext.getString(R.string.button_text_savory));
                break;
            case CATEGORY_LIGHT_HEAVY:
                spinnerSet.add(mAppContext.getString(R.string.spinner_all_text));
                spinnerSet.add(mAppContext.getString(R.string.button_text_light));
                spinnerSet.add(mAppContext.getString(R.string.button_text_heavy));
                break;
            case CATEGORY_REGION:
                spinnerSet.add(mAppContext.getString(R.string.spinner_all_text));
                for (Recipe r : mRecipes) {
                    spinnerSet.add(r.getRegion());
                }
                break;
        }
        ArrayList<String> list = new ArrayList<>(spinnerSet);
        Collections.sort(list);
        return list;
    }

    private void loadRecipeList() {
        try {
            Resources resources = mAppContext.getResources();
            ObjectInputStream inputStream =  new ObjectInputStream(resources.openRawResource(R.raw.recipes));
            mRecipes = (ArrayList<Recipe>) (inputStream.readObject());
            inputStream.close();
            Toast.makeText(mAppContext,"Loaded " + mRecipes.size() + " recipe(s)",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
