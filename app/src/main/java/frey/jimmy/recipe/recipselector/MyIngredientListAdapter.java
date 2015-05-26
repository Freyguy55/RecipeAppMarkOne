package frey.jimmy.recipe.recipselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by James on 5/14/2015.
 */
public class MyIngredientListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> mIngredientList;

    public MyIngredientListAdapter(Context context, int resource, ArrayList<String> ingredientList) {
        super(context, resource, ingredientList);
        mIngredientList = ingredientList;
    }

    @Override
    public int getCount() {
        if(mIngredientList.size()==0){
            return 0;
        }
        return (mIngredientList.size()+1)/2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.ingredient_list_view_layout, null);
        }
        //Get ingredients for this row
        final int ingredientLeftPosition = position * 2;
        final int ingredientRightPosition = ingredientLeftPosition + 1;
        String ingredientLeft = mIngredientList.get(ingredientLeftPosition);
        String ingredientRight = null;
        if (ingredientRightPosition < mIngredientList.size()) {
            ingredientRight = mIngredientList.get(ingredientRightPosition);
        }
        //Left ingredient
        final TextView textViewIngredientLeft = (TextView) v.findViewById(R.id.textViewIngredientLeft);
        textViewIngredientLeft.setText(ingredientLeft);
        textViewIngredientLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You clicked " + textViewIngredientLeft.getText() + " position: " + ingredientLeftPosition + " " + position, Toast.LENGTH_SHORT).show();
            }
        });
        //Right ingredient
        final TextView textViewIngredientRight = (TextView) v.findViewById(R.id.textViewIngredientRight);
        if (ingredientRight != null) {
            textViewIngredientRight.setText(ingredientRight);
            textViewIngredientRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "You clicked " + textViewIngredientRight.getText() + " position: " + ingredientRightPosition + " " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return v;
    }
}
