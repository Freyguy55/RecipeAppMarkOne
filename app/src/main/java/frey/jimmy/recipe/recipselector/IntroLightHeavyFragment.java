package frey.jimmy.recipe.recipselector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by James on 5/17/2015.
 */
public class IntroLightHeavyFragment  extends Fragment{

    private static final String KEY_IS_SWEET_SELECTED = "keyIsSweetSelected";
    private Button buttonLight;
    private Button buttonHeavy;
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
        mIsSweetSelected = getArguments().getBoolean(KEY_IS_SWEET_SELECTED);
        buttonLight = (Button) v.findViewById(R.id.buttonSweet);
        buttonLight.setText(getString(R.string.button_text_light));
        buttonLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
          //      fm.beginTransaction().add(R.id.fragment_intro_select_container, ).commit();
            }
        });
        buttonHeavy = (Button) v.findViewById(R.id.buttonSavory);
        buttonHeavy.setText(getString(R.string.button_text_heavy));
        buttonHeavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
          //      fm.beginTransaction().add(R.id.fragment_intro_select_container, ).commit();
            }
        });
    }


}


