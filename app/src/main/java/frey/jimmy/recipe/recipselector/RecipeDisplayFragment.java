package frey.jimmy.recipe.recipselector;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RecipeDisplayFragment extends Fragment {


    public RecipeDisplayFragment() {
    }
    public static RecipeDisplayFragment createInstance() {
        RecipeDisplayFragment fragment = new RecipeDisplayFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_display, container, false);

        return v;
    }



}
