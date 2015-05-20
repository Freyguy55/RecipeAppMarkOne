package frey.jimmy.recipe.recipselector;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


public class RecipeDisplayFragment extends Fragment {
    private static final String KEY_INGREDIENTS_EXPANDED = "KeyIngredientsExpanded";
    private static final String KEY_INSTRUCTIONS_EXPANDED = "KeyInstructionsExpanded";
    private static final String KEY_RECIPE = "keyRecipe";
    private Recipe mRecipe;
    boolean mIsIngredientExpanded = true;
    boolean mIsInstructionExpanded = true;
    private ImageView mIngredientsExpandCollapseImageView;
    private ImageView mInstructionsExpandCollapseImageView;
    private ScrollView mInstructionsScrollView;
    private GridView mIngredientsGridview;
    private String[] mIngredientsArray = {"1 cup stuff", "3 carrots", "1/4 cup onions", "15 cream puffs", "5 golden rings","2 turtle doves", "1 pear tree"};

    public RecipeDisplayFragment() {
    }

    public static RecipeDisplayFragment createInstance(Recipe r) {
        RecipeDisplayFragment fragment = new RecipeDisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_RECIPE,r);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_display, container, false);
        mRecipe = (Recipe) getArguments().getSerializable(KEY_RECIPE);
        initializeView(v, savedInstanceState);
        setRecipeData(v);


        return v;
    }

    private void setRecipeData(View v) {
        TextView nameTextView = (TextView) v.findViewById(R.id.recipeDisplayNameTextView);
        nameTextView.setText(mRecipe.getRecipeName());
        TextView prepTimeTextView = (TextView) v.findViewById(R.id.prepTimeTextView);
        prepTimeTextView.setText(mRecipe.getTotalMinutes() + " minutes");
        TextView timerTextView = (TextView) v.findViewById(R.id.textViewTimer);
        timerTextView.setText(mRecipe.getTotalMinutes()+":00");
        TextView servingSizeTextView = (TextView) v.findViewById(R.id.servingSizeTextView);
        servingSizeTextView.setText("Serves "+ mRecipe.getServesNumber());
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

        //Initialize gridview
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getActivity(),R.layout.my_simple_list_view_1,mIngredientsArray);
        mIngredientsGridview.setAdapter(myAdapter);

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
        if(mIsIngredientExpanded) { //It is currently expanded and should collapse
            mIngredientsExpandCollapseImageView.setImageResource(R.drawable.expander_close_holo_light);
            mIsIngredientExpanded = false;
            mIngredientsGridview.setVisibility(View.GONE);
        } else{  //It is currently collapsed and should expand
            mIngredientsExpandCollapseImageView.setImageResource(R.drawable.expander_open_holo_light);
            mIsIngredientExpanded = true;
            mIngredientsGridview.setVisibility(View.VISIBLE);
        }

    }

    private void toggleInstructionOpenClose() {
        if(mIsInstructionExpanded) { //It is currently expanded and should collapse
            mInstructionsExpandCollapseImageView.setImageResource(R.drawable.expander_close_holo_light);
            mIsInstructionExpanded = false;
            mInstructionsScrollView.setVisibility(View.GONE);
        } else{  //It is currently collapsed and should expand
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
