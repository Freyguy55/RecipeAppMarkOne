package frey.jimmy.recipe.recipselector;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.shopping_list_view_row_layout, parent, false);
        }
        final int finalPosition = position;
        final TextView ingredientTextView = (TextView) v.findViewById(R.id.textViewShoppingListIngredient);
        ingredientTextView.setText(mIngredientList.get(position).toString());
        ingredientTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient ingredientSelected = mIngredientList.get(finalPosition);
                if(ingredientSelected.getUnit()!=null && ingredientSelected.getUnit().length()!=0) {
                    FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
                    DialogUnitConversionFragment.createInstance(ingredientSelected).show(fm, "IngredientTag");
                }
            }
        });


        final Button buttonRemoveIngredient = (Button) v.findViewById(R.id.buttonDeleteShoppingItem);
        buttonRemoveIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(getItem(position));
                RecipeBook.get(getContext()).saveShoppingList();
            }
        });
        return v;
    }



}
