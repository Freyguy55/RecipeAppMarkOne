package frey.jimmy.recipe.recipselector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class IntroSelectActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_select);

        FragmentManager fm = getSupportFragmentManager();
        Fragment introSelectFragment = fm.findFragmentById(R.id.fragment_intro_select_container);
        if(introSelectFragment == null){
            introSelectFragment = IntroSelectFragment.createInstance();
            fm.beginTransaction().add(R.id.fragment_intro_select_container,introSelectFragment).commit();
        }
    }

}
