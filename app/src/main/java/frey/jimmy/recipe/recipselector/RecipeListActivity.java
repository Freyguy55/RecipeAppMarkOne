package frey.jimmy.recipe.recipselector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class RecipeListActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        FragmentManager fm = getSupportFragmentManager();
        Fragment recipeListFragment = fm.findFragmentById(R.id.recipe_list_fragment_container);
        if(recipeListFragment == null){
            recipeListFragment = RecipeListFragment.createInstance((ArrayList<Recipe>) getIntent().getSerializableExtra(IntroLightHeavyFragment.EXTRA_RECIPES));
            fm.beginTransaction().add(R.id.recipe_list_fragment_container, recipeListFragment).commit();
        }

    }
}
