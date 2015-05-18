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
        initializeRecipeNameList();

        mRecipeListView = (ListView) v.findViewById(R.id.recipeListView);
        mRecipeListView.setAdapter(new MyListAdaptor(getActivity(), R.layout.my_list_view, mRecipeNameList));


        return v;
    }

    private void initializeRecipeNameList() {
        mRecipeNameList = new ArrayList<>();
        mRecipeNameList.add(new Recipe("Pie", R.drawable.recipe_image_01));
        mRecipeNameList.add(new Recipe("Steak", R.drawable.recipe_image_01));
        mRecipeNameList.add(new Recipe("Chicken", R.drawable.recipe_image_01));
        mRecipeNameList.add(new Recipe("Cheekahn", R.drawable.recipe_image_01));
        mRecipeNameList.add(new Recipe("Tacos", R.drawable.recipe_image_01));
        mRecipeNameList.add(new Recipe("Salami", R.drawable.recipe_image_01));
        mRecipeNameList.add(new Recipe("Ribs", R.drawable.recipe_image_01));
        mRecipeNameList.add(new Recipe("Pizza", R.drawable.recipe_image_01, true, true));
        mRecipeNameList.add(new Recipe("Cheetos", R.drawable.recipe_image_01,true,false));
        mRecipeNameList.add(new Recipe("Cookies", R.drawable.recipe_image_01));
        mRecipeNameList.add(new Recipe("Sushi", R.drawable.recipe_image_01,true,true));
        mRecipeNameList.add(new Recipe("Sashimi", R.drawable.recipe_image_01,false,true));
        mRecipeNameList.add(new Recipe("Lamb", R.drawable.recipe_image_01,true,false));
        mRecipeNameList.add(new Recipe("Naan", R.drawable.recipe_image_01,false,true));
    }
}
