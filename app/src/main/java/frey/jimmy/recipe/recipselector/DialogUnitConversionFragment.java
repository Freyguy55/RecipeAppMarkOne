package frey.jimmy.recipe.recipselector;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by James on 5/26/2015.
 */
public class DialogUnitConversionFragment extends DialogFragment {
    private static final String KEY_INGREDIENT = "keyIngredient";
    private Ingredient mIngredient;
    private Spinner unitSpinner;
    private TextView ingredientQuantityTextView;

    public DialogUnitConversionFragment(){
    }

    public static DialogUnitConversionFragment createInstance(Ingredient i){
        DialogUnitConversionFragment dialogFragment = new DialogUnitConversionFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_INGREDIENT, i);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Select a unit");
        mIngredient = (Ingredient) getArguments().getSerializable(KEY_INGREDIENT);
        View v = inflater.inflate(R.layout.dialog_fragment_unit_conversion, container, false);

        ingredientQuantityTextView = (TextView) v.findViewById(R.id.textViewIngredientQuantity);
        ingredientQuantityTextView.setText(String.valueOf(mIngredient.getQuantity()));

        unitSpinner = (Spinner) v.findViewById(R.id.dialogUnitSpinner);
        configureSpinner();

        TextView ingredientName = (TextView) v.findViewById(R.id.textViewIngredientName);
        ingredientName.setText(mIngredient.getName());
        return v;
    }

    private void configureSpinner() {
        unitSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,RecipeBook.get(getActivity()).getAllIngredientUnits()));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
