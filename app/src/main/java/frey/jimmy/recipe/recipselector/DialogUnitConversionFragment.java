package frey.jimmy.recipe.recipselector;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by James on 5/26/2015.
 */
public class DialogUnitConversionFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{
    private static final String KEY_INGREDIENT = "keyIngredient";
    private Ingredient mIngredient;
    private Spinner mUnitSpinner;
    private TextView mIngredientQuantityTextView;
    private ConvertUnit mConvertUnit;

    public DialogUnitConversionFragment(){
        mConvertUnit = new ConvertUnit();
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
        mIngredient = (Ingredient) getArguments().getSerializable(KEY_INGREDIENT);
            getDialog().setTitle(mIngredient.getName());
            View v = inflater.inflate(R.layout.dialog_fragment_unit_conversion, container, false);

            mIngredientQuantityTextView = (TextView) v.findViewById(R.id.textViewIngredientQuantity);
            mIngredientQuantityTextView.setText(String.valueOf(mIngredient.getQuantity()));

            mUnitSpinner = (Spinner) v.findViewById(R.id.dialogUnitSpinner);
            mUnitSpinner.setOnItemSelectedListener(this);
            configureSpinner();

            return v;
    }

    private void configureSpinner() {
        mUnitSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mConvertUnit.getUnitsStringList()));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String fromUnit = mIngredient.getUnit();
        String toUnit = (String)adapterView.getItemAtPosition(i);
        double newQuantity = ConvertUnit.convertToUnit(mIngredient.getQuantity(),fromUnit,toUnit);
        mIngredientQuantityTextView.setText(String.valueOf((double)Math.round(newQuantity*100)/100));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
