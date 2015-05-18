package frey.jimmy.recipe.recipselector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeListFragment extends Fragment {

    ListView mRecipeListView;
    ArrayList<Recipe> mRecipeNameList;

    public RecipeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        //Check if bundle exists and initialize variables.
        initializeRecipes();

        mRecipeListView = (ListView) v.findViewById(R.id.recipeListView);
        mRecipeListView.setAdapter(new MyListAdaptor(getActivity(), R.layout.my_list_view, mRecipeNameList));


        return v;
    }

    private void initializeRecipes() {

    }
}
