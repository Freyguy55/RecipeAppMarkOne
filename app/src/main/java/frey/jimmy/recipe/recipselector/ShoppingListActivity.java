package frey.jimmy.recipe.recipselector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;


public class ShoppingListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        FragmentManager fm = getSupportFragmentManager();
        Fragment ingredientFragment = fm.findFragmentById(R.id.shoppingListFragmentContainer);
        if (ingredientFragment == null) {
            ingredientFragment = IngredientFragment.newInstance();
            fm.beginTransaction().add(R.id.shoppingListFragmentContainer, ingredientFragment).commit();
        }
    }
}
