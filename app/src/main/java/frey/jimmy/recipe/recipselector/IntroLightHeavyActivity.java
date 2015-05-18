package frey.jimmy.recipe.recipselector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


public class IntroLightHeavyActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_select);

        FragmentManager fm = getSupportFragmentManager();
        Fragment introLightHeavyFragment = fm.findFragmentById(R.id.fragment_intro_select_container);
        boolean isSweetSelected = getIntent().getBooleanExtra(IntroSelectFragment.EXTRA_SWEET_SELECTED, false);
        if (introLightHeavyFragment == null) {
            introLightHeavyFragment = IntroLightHeavyFragment.createInstance(isSweetSelected);
            fm.beginTransaction().add(R.id.fragment_intro_select_container, introLightHeavyFragment).commit();
        }
    }

}
