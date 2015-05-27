package frey.jimmy.recipe.recipselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by James on 5/27/2015.
 */
public class ShoppingListArrayAdapter extends ArrayAdapter<Ingredient> {
    private Context mContext;
    private ArrayList<Ingredient> mIngredientList;

    public ShoppingListArrayAdapter(Context context, int resource, ArrayList<Ingredient> ingredientList) {
        super(context, resource, ingredientList);
        mContext = context;
        mIngredientList = ingredientList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.shopping_list_view_row_layout, parent, false);
        }

        final TextView ingredientTextView = (TextView) v.findViewById(R.id.textViewShoppingListIngredient);
        ingredientTextView.setText(mIngredientList.get(position).toString());

        final CheckBox ingredientCheckBox = (CheckBox) v.findViewById(R.id.checkBoxShoppingList);
        return v;
    }
}
