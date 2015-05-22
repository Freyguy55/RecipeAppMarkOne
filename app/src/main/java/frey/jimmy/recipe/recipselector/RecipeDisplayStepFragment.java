package frey.jimmy.recipe.recipselector;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Displays recipe steps
 */
public class RecipeDisplayStepFragment extends Fragment {
    private Recipe mRecipe;
    private ArrayList<RecipeStep> mRecipeStepArrayList;
    private RecipeStep mRecipeStep;
    private TextView mInstructionsTextView;
    private GridView mIngredientsGridview;
    private ArrayList<String> mIngredientStringList;
    private int mPosition;
    private static final String KEY_POSITION = "KeyPosition";
    private static final String KEY_UUID = "keyUuid";

    public RecipeDisplayStepFragment() {
        // Required empty public constructor
    }

    public static RecipeDisplayStepFragment createInstance(UUID uuid, int position){
        RecipeDisplayStepFragment fragment = new RecipeDisplayStepFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putSerializable(KEY_UUID, uuid);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_display_step, container, false);
        //Set member data variables
        UUID recipeId = (UUID) getArguments().getSerializable(KEY_UUID);
        mPosition = getArguments().getInt(KEY_POSITION);
        mRecipe = RecipeBook.get(getActivity()).getRecipe(recipeId);
        if(null==mRecipe){
            return v;
        }
        mRecipeStepArrayList = mRecipe.getRecipeStepList();
        mRecipeStep = mRecipeStepArrayList.get(mPosition);
        
        setRecipeData(v);
        return v;
    }

    private void setRecipeData(View v) {
        //set member views
        TextView textViewName = (TextView) v.findViewById(R.id.recipeStepDisplayNameTextView);
        textViewName.setText(mRecipe.getRecipeName());

        mIngredientsGridview = (GridView) v.findViewById(R.id.ingredientsGridView);
        mIngredientStringList = mRecipeStep.getRecipeStepIngredientStringList();
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getActivity(), R.layout.my_simple_list_view_1, mIngredientStringList);
        mIngredientsGridview.setAdapter(myAdapter);

        mInstructionsTextView = (TextView) v.findViewById(R.id.instructionsStepTextView);
        mInstructionsTextView.setText(mRecipeStep.getInstructions());

        TextView pageNumberTextView = (TextView) v.findViewById(R.id.pageNumberTextView);
        int page = mPosition + 1;
        pageNumberTextView.setText("Step " + page + " of " + mRecipeStepArrayList.size());

    }


}
