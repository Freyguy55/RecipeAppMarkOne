package frey.jimmy.recipe.recipselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeListFragment extends Fragment {

    private static final String KEY_RECIPES_LIST = "keyRecipesList";
    public static final String EXTRA_RECIPE = "ExtraRecipe";
    ListView mRecipeListView;
    ArrayList<Recipe> mRecipes;

    public RecipeListFragment() {
    }

    public static RecipeListFragment createInstance(ArrayList<Recipe> recipes){
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_RECIPES_LIST, recipes);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        //Check if bundle exists and initialize variables.
        initializeRecipes();

        mRecipeListView = (ListView) v.findViewById(R.id.recipeListView);
        mRecipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Recipe r = mRecipes.get(i);
                Intent intent = new Intent(getActivity(),RecipeDisplayActivity.class);
                intent.putExtra(EXTRA_RECIPE,r);
                startActivity(intent);
            }
        });
        mRecipeListView.setAdapter(new MyListAdaptor(getActivity(), R.layout.my_list_view, mRecipes));


        return v;
    }

    private void initializeRecipes() {
        mRecipes = (ArrayList<Recipe>) getArguments().getSerializable(KEY_RECIPES_LIST);
    }
}
