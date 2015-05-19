package frey.jimmy.recipe.recipselector;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;


public class RecipeDisplayFragment extends Fragment {
    private static final String KEY_INGREDIENTS_EXPANDED = "KeyIngredientsExpanded";
    private static final String KEY_INSTRUCTIONS_EXPANDED = "KeyInstructionsExpanded";
    boolean mIsIngredientExpanded = true;
    boolean mIsInstructionExpanded = true;
    private ImageView mIngredientsExpandCollapseImageView;
    private ImageView mInstructionsExpandCollapseImageView;
    private ScrollView mInstructionsScrollView;
    private GridView mIngredientsGridview;

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
        initializeView(v, savedInstanceState);


        return v;
    }

    private void initializeView(View v, Bundle savedInstanceState) {
        //Set view fields
        mIngredientsExpandCollapseImageView = (ImageView) v.findViewById(R.id.ingredientExpandCollapseImageView);
        mInstructionsExpandCollapseImageView = (ImageView) v.findViewById(R.id.instructionsExpandCollapseImageView);
        mIngredientsGridview = (GridView) v.findViewById(R.id.ingredientsGridView);
        mInstructionsScrollView = (ScrollView) v.findViewById(R.id.instructionsScrollView);

        //Initialize bundledStates
        if(savedInstanceState!=null){
            if(!savedInstanceState.getBoolean(KEY_INGREDIENTS_EXPANDED)){ //If ingredients should be collapsed
                toggleIngredientOpenClose(); //Collapse ingredients (initial state is always expanded)
            }
            if(!savedInstanceState.getBoolean(KEY_INSTRUCTIONS_EXPANDED)){
                toggleInstructionOpenClose(); //Collapse instructions (initial state is always expanded)
            }
        }

        //Wire buttons
        mIngredientsExpandCollapseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleIngredientOpenClose();
            }
        });

        mInstructionsExpandCollapseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleInstructionOpenClose();
            }
        });

    }



    private void toggleIngredientOpenClose() {
        if(mIsIngredientExpanded) { //It is currently expanded and should collapse
            mIngredientsExpandCollapseImageView.setImageResource(R.drawable.expander_open_holo_light);
            mIsIngredientExpanded = false;
            mIngredientsGridview.setVisibility(View.GONE);
        } else{  //It is currently collapsed and should expand
            mIngredientsExpandCollapseImageView.setImageResource(R.drawable.expander_close_holo_light);
            mIsIngredientExpanded = true;
            mIngredientsGridview.setVisibility(View.VISIBLE);
        }

    }

    private void toggleInstructionOpenClose() {
        if(mIsInstructionExpanded) { //It is currently expanded and should collapse
            mInstructionsExpandCollapseImageView.setImageResource(R.drawable.expander_open_holo_light);
            mIsInstructionExpanded = false;
            mInstructionsScrollView.setVisibility(View.GONE);
        } else{  //It is currently collapsed and should expand
            mInstructionsExpandCollapseImageView.setImageResource(R.drawable.expander_close_holo_light);
            mIsInstructionExpanded = true;
            mInstructionsScrollView.setVisibility(View.VISIBLE);
        }
    }

    //Save expanded or collapsed states
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_INGREDIENTS_EXPANDED, mIsIngredientExpanded);
        outState.putBoolean(KEY_INSTRUCTIONS_EXPANDED, mIsInstructionExpanded);
        super.onSaveInstanceState(outState);
    }
}
