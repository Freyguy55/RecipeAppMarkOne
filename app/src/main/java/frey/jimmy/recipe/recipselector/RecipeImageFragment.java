package frey.jimmy.recipe.recipselector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.UUID;

/**
 * Displays a large recipe image.
 */
public class RecipeImageFragment extends Fragment {

    private static final String KEY_RECIPE_ID = "keyRecipeId";
    private ImageView mRecipeImageView;
    private Recipe mRecipe;

    public RecipeImageFragment() {
        // Required empty public constructor
    }

    public static RecipeImageFragment createInstance(UUID recipeUUID) {
        RecipeImageFragment fragment = new RecipeImageFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_RECIPE_ID, recipeUUID);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_image, container, false);
        initialize(v);
        return v;
    }

    private void initialize(View v) {
        mRecipeImageView = (ImageView) v.findViewById(R.id.recipeImageView);
        UUID id = (UUID) getArguments().getSerializable(KEY_RECIPE_ID);
        mRecipe = RecipeBook.get(getActivity()).getRecipe(id);
        if (mRecipe.getRecipeImageId() >= 0) {
            mRecipeImageView.setImageResource(R.drawable.loading_image);
        } else {
            mRecipeImageView.setImageResource(R.drawable.recipe_image_no_image);
        }
    }
}

