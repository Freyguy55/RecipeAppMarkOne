package frey.jimmy.recipe.recipselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by James on 5/14/2015.
 */
public class MyListAdapter extends ArrayAdapter<Recipe> {

    private ArrayList<Recipe> mRecipeBook;

    public MyListAdapter(Context context, int resource, ArrayList<Recipe> recipeBook) {
        super(context, resource, recipeBook);
        mRecipeBook = recipeBook;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_list_view, null);
        }
        //Get recipe for this row
        Recipe recipe = mRecipeBook.get(position);
        //Set image
        ImageView recipeImage = (ImageView) v.findViewById(R.id.listImageView);
        recipeImage.setImageResource(recipe.getRecipeImageId());
        //Set name
        TextView textView = (TextView) v.findViewById((R.id.listTextView));
        textView.setText(recipe.getRecipeName());
        //Set description
        TextView description = (TextView) v.findViewById(R.id.listTextViewDescription);
        description.setText(recipe.getRecipeDescription());
        //Set time
        TextView time = (TextView) v.findViewById(R.id.timeTextView);
        time.setText(String.valueOf(recipe.getTotalMinutes()) + " minutes");
        //Set like/dislike image
        ImageView likeDislikeImageView = (ImageView) v.findViewById(R.id.likeDislikeImageView);
        int imageResourceId;
        switch (recipe.isGood()) {
            case Recipe.RECIPE_IS_BAD: {
                imageResourceId = R.drawable.kirby_sad;
                break;
            }
            case Recipe.RECIPE_IS_GOOD: {
                imageResourceId = R.drawable.recipe_image_01;
                break;
            }
            default: {
                imageResourceId = R.drawable.kirby_hurt;
                break;
            }
        }
        likeDislikeImageView.setImageResource(imageResourceId);
        return v;
    }
}
