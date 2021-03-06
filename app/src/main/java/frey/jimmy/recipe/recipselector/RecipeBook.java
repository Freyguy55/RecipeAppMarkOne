package frey.jimmy.recipe.recipselector;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by James on 5/17/2015.
 */
public class RecipeBook {
    public static final String SAVE_FILE_RECIPE = "recipes.dat";
    public static final String SAVE_FILE_SHOPPING_LIST = "shoppinglist.dat";
    private static final int CATEGORY_SWEET_SAVORY = 0;
    private static final int CATEGORY_LIGHT_HEAVY = 1;
    private static final int CATEGORY_REGION = 2;
    private static RecipeBook sRecipeBook;
    private ArrayList<Recipe> mRecipes;
    private Context mAppContext;
    private ArrayList<Ingredient> mShoppingList;

    private RecipeBook(Context context) {
        mAppContext = context;
        loadRecipes();
        loadShoppingList();
        checkForNewRecipes();

    }

    public static RecipeBook get(Context context) {
        if (sRecipeBook == null) {
            sRecipeBook = new RecipeBook(context.getApplicationContext());
        }
        return sRecipeBook;
    }

    public static void deleteLocalFile(Context context) {
        ArrayList<Recipe> emptyRecipeList = new ArrayList<>();
        ObjectOutputStream objectOutputStream = null;
        try {
            OutputStream outputStream = context.openFileOutput(SAVE_FILE_RECIPE, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(emptyRecipeList);
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

    public ArrayList<Recipe> getRecipes() {
        return mRecipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
    }

    public Recipe getRecipe(UUID id) {
        for (Recipe r : mRecipes) {
            if (r.getUuid().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public ArrayList<UUID> getRecipeIds() {
        ArrayList<UUID> idList = new ArrayList<>();
        for (Recipe r : mRecipes) {
            idList.add(r.getUuid());
        }
        return idList;
    }

    public ArrayList<UUID> getFilteredRecipeIds(boolean isSweet, boolean isLight) {
        ArrayList<UUID> filteredList = new ArrayList<>();
        for (Recipe r : mRecipes) {
            if (r.isSweet() == isSweet && r.isLight() == isLight) {
                filteredList.add(r.getUuid());
            }
        }
        return filteredList;
    }


    public ArrayList<UUID> getFilteredRecipeIdsByName(ArrayList<UUID> list, String name) {
        if (name == null) return list;
        ArrayList<UUID> filteredList = new ArrayList<>();
        //Parse name query.
        name = name.toLowerCase();
        String[] parsedQuery = name.split(" ");
        for (UUID id : list) {
            for (String s : parsedQuery) {
                if (getRecipe(id).getRecipeName().toLowerCase().contains(s)) {
                    filteredList.add(id);
                }
            }
        }
        return filteredList;
    }

    public ArrayList<UUID> getFilteredRecipeIdsBySweet(ArrayList<UUID> list, boolean isSweet) {
        ArrayList<UUID> filteredList = new ArrayList<>();
        for (UUID id : list) {
            if (getRecipe(id).isSweet() == isSweet) {
                filteredList.add(id);
            }
        }
        return filteredList;
    }

    public ArrayList<UUID> getFilteredRecipeIdsByLight(ArrayList<UUID> list, boolean isLight) {
        ArrayList<UUID> filteredList = new ArrayList<>();
        for (UUID id : list) {
            if (getRecipe(id).isLight() == isLight) {
                filteredList.add(id);
            }
        }
        return filteredList;
    }

    public ArrayList<UUID> getFilteredRecipeIdsByRegion(ArrayList<UUID> list, String region) {
        ArrayList<UUID> filteredList = new ArrayList<>();
        for (UUID id : list) {
            if (getRecipe(id).getRegion().equals(region)) {
                filteredList.add(id);
            }
        }
        return filteredList;
    }


    public ArrayList<Recipe> getFilteredRecipeList(ArrayList<UUID> ids) {
        ArrayList<Recipe> filteredList = new ArrayList<>();
        for (UUID id : ids) {
            for (Recipe r : mRecipes) {
                if (r.getUuid().equals(id)) {
                    filteredList.add(r);
                }
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

    //Returns a list of all units in recipes currently
    public ArrayList<String> getAllIngredientUnits() {
        ArrayList<String> listOfAllUnits = new ArrayList<>();
        listOfAllUnits.add("cups");
        for (Recipe r : mRecipes) {
            for (Ingredient i : r.getRecipeIngredientList()) {
                if (!listOfAllUnits.contains(i.getUnit()) && i.getUnit() != null) {
                    listOfAllUnits.add(i.getUnit());
                }
            }
        }
        return listOfAllUnits;
    }

    public ArrayList<Ingredient> getShoppingList() {
        return mShoppingList;
    }

    public void setShoppingList(ArrayList<Ingredient> shoppingList) {
        mShoppingList = shoppingList;
        saveShoppingList();
    }

    public void addListToShoppingList(ArrayList<Ingredient> shoppingList) {
        mShoppingList.addAll(shoppingList);
        saveShoppingList();
    }

    public void addIngredientToShoppingList(Ingredient i) {
        mShoppingList.add(i);
        saveShoppingList();
    }

    public void clearShoppingList() {
        mShoppingList.clear();
        saveShoppingList();
    }

    public void removeIngredientFromShoppingList(int pos) {
        mShoppingList.remove(pos);
        saveShoppingList();
    }


    public void saveShoppingList() {
        ObjectOutputStream objectOutputStream = null;
        try {
            OutputStream outputStream = mAppContext.openFileOutput(SAVE_FILE_SHOPPING_LIST, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(mShoppingList);
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

    public void loadShoppingList() {
        // Check if the save file exists.
        File saveFile = mAppContext.getFileStreamPath(SAVE_FILE_SHOPPING_LIST);
        if (!saveFile.exists()) {
            mShoppingList = new ArrayList<>();  //If the file does not exist this is the first time
            saveShoppingList();                 //loading and a blank arraylist needs to be saved.
        }
        ObjectInputStream objectInputStream = null;
        try {
            InputStream inputStream = mAppContext.openFileInput(SAVE_FILE_SHOPPING_LIST);
            objectInputStream = new ObjectInputStream(inputStream);
            mShoppingList = (ArrayList<Ingredient>) objectInputStream.readObject();
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

    //Checks if there are any new recipes to add to the saved list.
    public void checkForNewRecipes() {
        new DownloadRecipeTask().execute();
    }

    public void saveRecipes() {
        ObjectOutputStream objectOutputStream = null;
        try {
            OutputStream outputStream = mAppContext.openFileOutput(SAVE_FILE_RECIPE, Context.MODE_PRIVATE);
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

    public void loadRecipes() {
        // Check if the save file exists.
        File saveFile = mAppContext.getFileStreamPath(SAVE_FILE_RECIPE);
        if (!saveFile.exists()) {
            mRecipes = new ArrayList<>();  //If the file does not exist this is the first time
            saveRecipes();                 //loading and a blank arraylist needs to be saved.
        }
        ObjectInputStream objectInputStream = null;
        try {
            InputStream inputStream = mAppContext.openFileInput(SAVE_FILE_RECIPE);
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

    public static class IngredientComparator implements Comparator<Ingredient> {
        @Override
        public int compare(Ingredient ingredient1, Ingredient ingredient2) {
            return ingredient1.getName().compareTo(ingredient2.getName());
        }
    }

    //Download recipes from web
    private class DownloadRecipeTask extends AsyncTask<String, Void, ArrayList<Recipe>> {
        private static final String RECIPE_URL = "http://s3-us-west-1.amazonaws.com/frey.jimmy.testbucket/recipes/recipes.dat";

        @Override
        protected ArrayList<Recipe> doInBackground(String... imageID) {
            System.out.println("Url trying to connect to: " + RECIPE_URL);
            return downloadRecipe(RECIPE_URL);

        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> defaultRecipeList) {
            if (defaultRecipeList != null) {
                for (Recipe defaultRecipe : defaultRecipeList) {
                    if (!mRecipes.contains(defaultRecipe)) {  //If the local saved list does not have this recipe
                        mRecipes.add(defaultRecipe);  //Add this recipe because it is new.
                    }
                }
            }
            super.onPostExecute(defaultRecipeList);
        }

        private ArrayList<Recipe> downloadRecipe(String url) {
            InputStream inputStream = null;
            ArrayList<Recipe> defaultRecipeList = null;
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                int responseCode = httpURLConnection.getResponseCode();
                System.out.println("Response code: " + responseCode);
                if (responseCode != 200) {
                    System.out.println("Bananas response code not 200");
                    return null;
                }
                ObjectInputStream objectInputStream = new ObjectInputStream(httpURLConnection.getInputStream());
                defaultRecipeList = (ArrayList<Recipe>) objectInputStream.readObject();
                return defaultRecipeList;
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
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
    }
}
