package frey.jimmy.recipe.recipselector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;


public class RecipeDisplayActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);

        FragmentManager fm = getSupportFragmentManager();
        Fragment recipeDisplayFragment = fm.findFragmentById(R.id.fragment_intro_select_container);
        if(recipeDisplayFragment == null){
            UUID id = (UUID) getIntent().getSerializableExtra(RecipeListFragment.EXTRA_RECIPE_ID);
            recipeDisplayFragment = RecipeDisplayFragment.createInstance(id);
            fm.beginTransaction().add(R.id.fragment_intro_select_container,recipeDisplayFragment).commit();
        }
    }

}
