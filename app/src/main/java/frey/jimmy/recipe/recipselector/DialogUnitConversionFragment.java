package frey.jimmy.recipe.recipselector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by James on 5/26/2015.
 */
public class DialogUnitConversionFragment extends DialogFragment {
    private static final String KEY_INGREDIENT = "keyIngredient";

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
        Ingredient i = (Ingredient) getArguments().getSerializable(KEY_INGREDIENT);
        TextView t = new TextView(getActivity());
        t.setText(i.toString());
        return t;
    }
}
