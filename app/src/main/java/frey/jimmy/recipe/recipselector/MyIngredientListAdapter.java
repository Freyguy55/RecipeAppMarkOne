package frey.jimmy.recipe.recipselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.ingredient_list_view_layout, null);
        }
        //Get ingredients for this row
        int ingredientLeftPosition = position * 2;
        int ingredientRightPosition = ingredientLeftPosition + 1;
        String ingredientLeft = mIngredientList.get(ingredientLeftPosition);
        String ingredientRight = null;
        if (ingredientRightPosition < mIngredientList.size()) {
            ingredientRight = mIngredientList.get(ingredientRightPosition);
        }

        TextView textViewIngredientLeft = (TextView) v.findViewById(R.id.textViewIngredientLeft);
        textViewIngredientLeft.setText(ingredientLeft);

        TextView textViewIngredientRight = (TextView) v.findViewById(R.id.textViewIngredientRight);
        if (ingredientRight != null) {
            textViewIngredientRight.setText(ingredientRight);
        }
        return v;
    }
}
