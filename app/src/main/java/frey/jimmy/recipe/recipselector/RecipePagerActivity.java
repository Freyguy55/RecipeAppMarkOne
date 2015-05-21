package frey.jimmy.recipe.recipselector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;


public class RecipePagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_pager);
        UUID uuid = (UUID)  getIntent().getSerializableExtra(RecipeListFragment.EXTRA_RECIPE_ID);
        mRecipe = RecipeBook.get(this).getRecipe(uuid);
        mViewPager = (ViewPager) findViewById(R.id.recipeViewPager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return RecipeDisplayFragment.createInstance(mRecipe.getUuid());
                } else {
                    return RecipeDisplayStepFragment.createInstance(mRecipe.getUuid(), position + 1);
                }
            }

            @Override
            public int getCount() {
                List stepList = mRecipe.getRecipeStepList();
                if(stepList == null){
                    return 1;
                }
                return mRecipe.getRecipeStepList().size() + 1; //Add one because initial page is always the main recipe page and always exists.
            }
        });

        mViewPager.setCurrentItem(0);
    }


}
