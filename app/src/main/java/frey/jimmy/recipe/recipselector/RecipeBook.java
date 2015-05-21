package frey.jimmy.recipe.recipselector;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by James on 5/17/2015.
 */
public class RecipeBook {
    public static final String SAVE_FILE = "recipes.dat";
    private static final int CATEGORY_SWEET_SAVORY = 0;
    private static final int CATEGORY_LIGHT_HEAVY = 1;
    private static final int CATEGORY_REGION = 2;
    private static RecipeBook sRecipeBook;
    private ArrayList<Recipe> mRecipes;
    private Context mAppContext;

    private RecipeBook(Context context) {
        mAppContext = context;
        loadRecipes();
        checkForNewRecipes();

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
            Toast.makeText(mAppContext, "Recipe " + r.getRecipeName(), Toast.LENGTH_LONG);
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

    //Checks if there are any new recipes to add to the saved list.
    public void checkForNewRecipes() {
        ObjectInputStream inputStream = null;
        try {
            Resources resources = mAppContext.getResources();
            inputStream = new ObjectInputStream(resources.openRawResource(R.raw.recipes));
            ArrayList<Recipe> defaultRecipes = (ArrayList<Recipe>) (inputStream.readObject());
            for (Recipe defaultRecipe : defaultRecipes) {
                if (!mRecipes.contains(defaultRecipe)) {  //If the local saved list does not have this recipe
                    mRecipes.add(defaultRecipe);  //Add this recipe because it is new.
                }
            }
            inputStream.close();
            saveRecipes();
            Toast.makeText(mAppContext, "Loaded " + mRecipes.size() + " recipe(s)", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveRecipes() {
        ObjectOutputStream objectOutputStream = null;
        try {
            OutputStream outputStream = mAppContext.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(mRecipes);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadRecipes() {
        // Check if the save file exists.
        File saveFile = new File(SAVE_FILE);
        if (!saveFile.exists()) {
            mRecipes = new ArrayList<>();  //If the file does not exist this is the first time
            saveRecipes();                 //loading and a blank arraylist needs to be saved.
        }
        ObjectInputStream objectInputStream = null;
        try {
            InputStream inputStream = mAppContext.openFileInput(SAVE_FILE);
            objectInputStream = new ObjectInputStream(inputStream);
            mRecipes = (ArrayList<Recipe>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
