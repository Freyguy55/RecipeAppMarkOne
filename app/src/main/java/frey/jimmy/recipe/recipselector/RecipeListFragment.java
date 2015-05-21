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
import java.util.UUID;


/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeListFragment extends Fragment {

    public static final String KEY_RECIPE_IDS = "keyRecipeIds";
    public static final String EXTRA_RECIPE_ID = "extraRecipeId";
    ListView mRecipeListView;
    ArrayList<Recipe> mRecipes;

    public RecipeListFragment() {
    }

    public static RecipeListFragment createInstance(ArrayList<UUID> recipeIds){
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_RECIPE_IDS, recipeIds);
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
                UUID id = mRecipes.get(i).getUuid();
                Intent intent = new Intent(getActivity(), RecipePagerActivity.class);
                intent.putExtra(EXTRA_RECIPE_ID, id);
                startActivity(intent);
            }
        });
        MyListAdapter myAdapter = new MyListAdapter(getActivity(), R.layout.my_list_view, mRecipes);
        mRecipeListView.setAdapter(myAdapter);


        return v;
    }

    private void initializeRecipes() {
        ArrayList<UUID> recipeIds = (ArrayList<UUID>) getArguments().getSerializable(KEY_RECIPE_IDS);
        mRecipes = RecipeBook.get(getActivity()).getFilteredRecipeList(recipeIds);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyListAdapter adapter = (MyListAdapter) mRecipeListView.getAdapter();
        adapter.notifyDataSetChanged();

    }
}
