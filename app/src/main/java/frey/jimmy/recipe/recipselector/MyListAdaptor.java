package frey.jimmy.recipe.recipselector;

import android.content.Context;
import android.media.Image;
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
public class MyListAdaptor extends ArrayAdapter<Recipe> {

    private ArrayList<Recipe> mRecipeBook;

    public MyListAdaptor(Context context, int resource, ArrayList<Recipe> recipeBook) {
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
        //Set text
        TextView textView = (TextView) v.findViewById((R.id.listTextView));
        textView.setText(recipe.getRecipeName());
        return v;
    }
}
