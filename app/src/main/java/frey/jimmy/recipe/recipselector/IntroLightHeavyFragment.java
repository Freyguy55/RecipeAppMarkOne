package frey.jimmy.recipe.recipselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by James on 5/17/2015.
 */
public class IntroLightHeavyFragment extends Fragment {

    public static final String EXTRA_RECIPES = "ExtraRecipes";
    private static final String KEY_IS_SWEET_SELECTED = "keyIsSweetSelected";
    private Button buttonLight;
    private Button buttonHeavy;
    private TextView mSweetTextView;
    private boolean mIsSweetSelected;

    public static IntroLightHeavyFragment createInstance(boolean isSweetSelected) {
        IntroLightHeavyFragment fragment = new IntroLightHeavyFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_IS_SWEET_SELECTED, isSweetSelected);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_select, container, false);
        initialize(v);
        return v;
    }


    private void initialize(View v) {
        mSweetTextView = (TextView) v.findViewById(R.id.selectionTextView);
        mIsSweetSelected = getArguments().getBoolean(KEY_IS_SWEET_SELECTED);
        if (mIsSweetSelected) {
            mSweetTextView.setText(getString(R.string.button_text_sweet));
        } else {
            mSweetTextView.setText(getString(R.string.button_text_savory));
        }
        buttonLight = (Button) v.findViewById(R.id.buttonSweet);
        buttonLight.setText(getString(R.string.button_text_light));
        buttonLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(true);
            }
        });
        buttonHeavy = (Button) v.findViewById(R.id.buttonSavory);
        buttonHeavy.setText(getString(R.string.button_text_heavy));
        buttonHeavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(false);
            }
        });
    }

    private void nextActivity(boolean isLightSelected) {
        ArrayList<Recipe> filteredList = RecipeBook.get(getActivity()).getFilteredRecipes(mIsSweetSelected, isLightSelected);
        if (filteredList == null || filteredList.size() == 0) {
            Toast.makeText(getActivity(),"No recipes match your query.",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent(getActivity(), RecipeListActivity.class);
        i.putExtra(EXTRA_RECIPES, filteredList);
        startActivity(i);
    }


}


