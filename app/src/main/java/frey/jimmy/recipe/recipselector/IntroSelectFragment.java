package frey.jimmy.recipe.recipselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by James on 5/17/2015.
 */
public class IntroSelectFragment extends android.support.v4.app.Fragment {
    public static final String EXTRA_SWEET_SELECTED = "EXTRA_SWEET_SELECTED";
    private Button buttonSweet;
    private Button buttonSavory;

    public static IntroSelectFragment createInstance() {
        IntroSelectFragment fragment = new IntroSelectFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_select_advanced, container, false);
        initialize(v);
        return v;
    }


    private void initialize(View v) {
        buttonSweet = (Button) v.findViewById(R.id.buttonSweet);
        buttonSweet.setText(getString(R.string.button_text_sweet));
        buttonSweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(true);
            }
        });
        buttonSavory = (Button) v.findViewById(R.id.buttonSavory);
        buttonSavory.setText(getString(R.string.button_text_savory));
        buttonSavory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(false);
            }
        });
    }

    private void nextActivity(boolean sweetSelected) {
        Intent i = new Intent(getActivity(), IntroLightHeavyActivity.class);
        i.putExtra(EXTRA_SWEET_SELECTED, sweetSelected);
        startActivity(i);
    }


}
