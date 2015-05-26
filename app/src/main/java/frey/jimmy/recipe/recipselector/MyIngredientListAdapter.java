package frey.jimmy.recipe.recipselector;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
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
public class MyIngredientListAdapter extends ArrayAdapter<Ingredient> {

    private ArrayList<Ingredient> mIngredientList;
    private Context mContext;

    public MyIngredientListAdapter(Context context, int resource, ArrayList<Ingredient> ingredientList) {
        super(context, resource, ingredientList);
        mContext = context;
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
        final String ingredientLeft = mIngredientList.get(ingredientLeftPosition).toString();
        String ingredientRight = null;
        if (ingredientRightPosition < mIngredientList.size()) {
            ingredientRight = mIngredientList.get(ingredientRightPosition).toString();
        }
        //Left ingredient
        final TextView textViewIngredientLeft = (TextView) v.findViewById(R.id.textViewIngredientLeft);
        textViewIngredientLeft.setText(ingredientLeft);
        textViewIngredientLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((FragmentActivity)mContext).getSupportFragmentManager();
                DialogUnitConversionFragment.createInstance(mIngredientList.get(ingredientLeftPosition)).show(fm,"IngredientTag");
            }
        });
        //Right ingredient
        final TextView textViewIngredientRight = (TextView) v.findViewById(R.id.textViewIngredientRight);
        if (ingredientRight != null) {
            textViewIngredientRight.setText(ingredientRight);
            textViewIngredientRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = ((FragmentActivity)mContext).getSupportFragmentManager();
                    DialogUnitConversionFragment.createInstance(mIngredientList.get(ingredientLeftPosition)).show(fm,"IngredientTag");
                }
            });
        }
        return v;
    }
}
