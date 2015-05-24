package frey.jimmy.recipe.recipselector;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;


public class RecipeDisplayFragment extends Fragment {
    private static final String KEY_INGREDIENTS_EXPANDED = "KeyIngredientsExpanded";
    private static final String KEY_INSTRUCTIONS_EXPANDED = "KeyInstructionsExpanded";
    private static final String KEY_RECIPE_ID = "keyRecipeId";
    boolean mIsIngredientExpanded = true;
    boolean mIsInstructionExpanded = true;
    private Recipe mRecipe;
    private ImageView mIngredientsExpandCollapseImageView;
    private ImageView mInstructionsExpandCollapseImageView;
    private ImageView mLikeDislikeImageView;
    private ScrollView mInstructionsScrollView;
    private ListView mIngredientsListView;
    private ArrayList<String> mIngredientStringList;

    public RecipeDisplayFragment() {
    }

    public static RecipeDisplayFragment createInstance(UUID id) {
        RecipeDisplayFragment fragment = new RecipeDisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_RECIPE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_display_with_listview, container, false);
        UUID id = (UUID) getArguments().getSerializable(KEY_RECIPE_ID);
        mRecipe = RecipeBook.get(getActivity()).getRecipe(id);
        initializeView(v, savedInstanceState);
        setRecipeData(v);


        return v;
    }

    private void setRecipeData(View v) {
        //Load data into appropriate fields
        //Name
        TextView nameTextView = (TextView) v.findViewById(R.id.recipeStepDisplayNameTextView);
        nameTextView.setText(mRecipe.getRecipeName());
        //Prep time
        TextView prepTimeTextView = (TextView) v.findViewById(R.id.prepTimeTextView);
        prepTimeTextView.setText(mRecipe.getTotalMinutes() + " minutes");
        //Timer
        TextView timerTextView = (TextView) v.findViewById(R.id.textViewTimer);
        timerTextView.setText(mRecipe.getTotalMinutes() + ":00");
        //Serving size
        TextView servingSizeTextView = (TextView) v.findViewById(R.id.servingSizeTextView);
        servingSizeTextView.setText("Serves " + mRecipe.getServesNumber());
        //Instructions
        TextView instructionsTextView = (TextView) v.findViewById(R.id.instructionsTextView);
        instructionsTextView.setText(mRecipe.getInstructions());
        //Ingredients
        mIngredientStringList = mRecipe.getRecipeIngredientStringList();
        if (mIngredientStringList != null) {
            mIngredientsListView.setAdapter(new MyIngredientListAdapter(getActivity(),R.layout.ingredient_list_view_layout,mIngredientStringList));
        } else {

        }
        //Like or dislike image
        mLikeDislikeImageView = (ImageView) v.findViewById(R.id.recipeDisplayLikeImageView);
        switch (mRecipe.isGood()) {
            case Recipe.RECIPE_IS_BAD: {
                mLikeDislikeImageView.setImageResource(R.drawable.dislike);
                break;
            }
            case Recipe.RECIPE_IS_GOOD: {
                mLikeDislikeImageView.setImageResource(R.drawable.like);
                break;
            }
            default:
                mLikeDislikeImageView.setImageResource(R.drawable.like_dislike);
                break;
        }
        mLikeDislikeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mRecipe.isGood()) {
                    case Recipe.RECIPE_IS_BAD: {
                        mRecipe.setIsGood(Recipe.RECIPE_IS_UNKNOWN);
                        mLikeDislikeImageView.setImageResource(R.drawable.like_dislike);
                        break;
                    }
                    case Recipe.RECIPE_IS_GOOD: {
                        mRecipe.setIsGood(Recipe.RECIPE_IS_BAD);
                        mLikeDislikeImageView.setImageResource(R.drawable.dislike);
                        break;
                    }
                    default: {
                        mRecipe.setIsGood(Recipe.RECIPE_IS_GOOD);
                        mLikeDislikeImageView.setImageResource(R.drawable.like);
                        break;
                    }
                }
                RecipeBook.get(getActivity()).saveRecipes();
            }
        });
    }

    private void initializeView(View v, Bundle savedInstanceState) {
        //Set view fields
        mIngredientsExpandCollapseImageView = (ImageView) v.findViewById(R.id.ingredientExpandCollapseImageView);
        mInstructionsExpandCollapseImageView = (ImageView) v.findViewById(R.id.instructionsExpandCollapseImageView);
        mIngredientsListView = (ListView) v.findViewById(R.id.ingredientsListView);
        mInstructionsScrollView = (ScrollView) v.findViewById(R.id.instructionsScrollView);

        //Initialize bundledStates
        if (savedInstanceState != null) {
            if (!savedInstanceState.getBoolean(KEY_INGREDIENTS_EXPANDED)) { //If ingredients should be collapsed
                toggleIngredientOpenClose(); //Collapse ingredients (initial state is always expanded)
            }
            if (!savedInstanceState.getBoolean(KEY_INSTRUCTIONS_EXPANDED)) {
                toggleInstructionOpenClose(); //Collapse instructions (initial state is always expanded)
            }
        }

        //Wire Ingredients open/close button
        mIngredientsExpandCollapseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleIngredientOpenClose();
            }
        });

        //Wire instructions open/close button
        mInstructionsExpandCollapseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleInstructionOpenClose();
            }
        });

    }


    private void toggleIngredientOpenClose() {
        if (mIsIngredientExpanded) { //It is currently expanded and should collapse
            mIngredientsExpandCollapseImageView.setImageResource(R.drawable.expander_close_holo_light);
            mIsIngredientExpanded = false;
            mIngredientsListView.setVisibility(View.GONE);
        } else {  //It is currently collapsed and should expand
            mIngredientsExpandCollapseImageView.setImageResource(R.drawable.expander_open_holo_light);
            mIsIngredientExpanded = true;
            mIngredientsListView.setVisibility(View.VISIBLE);
        }

    }

    private void toggleInstructionOpenClose() {
        if (mIsInstructionExpanded) { //It is currently expanded and should collapse
            mInstructionsExpandCollapseImageView.setImageResource(R.drawable.expander_close_holo_light);
            mIsInstructionExpanded = false;
            mInstructionsScrollView.setVisibility(View.GONE);
        } else {  //It is currently collapsed and should expand
            mInstructionsExpandCollapseImageView.setImageResource(R.drawable.expander_open_holo_light);
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
