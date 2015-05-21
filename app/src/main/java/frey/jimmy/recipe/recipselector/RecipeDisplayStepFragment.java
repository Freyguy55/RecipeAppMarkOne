package frey.jimmy.recipe.recipselector;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;


/**
 * Displays recipe steps
 */
public class RecipeDisplayStepFragment extends Fragment {


    private static final String KEY_POSITION = "KeyPosition";

    public RecipeDisplayStepFragment() {
        // Required empty public constructor
    }

    public static RecipeDisplayStepFragment createInstance(UUID uuid, int position){
        RecipeDisplayStepFragment fragment = new RecipeDisplayStepFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_display_step, container, false);
        return v;
    }


}
