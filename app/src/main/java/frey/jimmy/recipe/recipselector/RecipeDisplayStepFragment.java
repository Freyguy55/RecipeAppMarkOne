package frey.jimmy.recipe.recipselector;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;


/**
 * Displays recipe steps
 */
public class RecipeDisplayStepFragment extends Fragment {
    private Recipe mRecipe;
    private ArrayList<RecipeStep> mRecipeStepArrayList;
    private RecipeStep mRecipeStep;
    private TextView mInstructionsTextView;
    private ListView mIngredientsListView;
    private ArrayList<String> mIngredientStringList;
    private int mPosition;
    private ImageView mRecipeStepImageView;
    private static final String KEY_POSITION = "KeyPosition";
    private static final String KEY_UUID = "keyUuid";
    private TextView mTextViewTimer;

    public RecipeDisplayStepFragment() {
        // Required empty public constructor
    }

    public static RecipeDisplayStepFragment createInstance(UUID uuid, int position){
        RecipeDisplayStepFragment fragment = new RecipeDisplayStepFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putSerializable(KEY_UUID, uuid);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_display_step, container, false);
        //Set member data variables
        UUID recipeId = (UUID) getArguments().getSerializable(KEY_UUID);
        mPosition = getArguments().getInt(KEY_POSITION);
        mRecipe = RecipeBook.get(getActivity()).getRecipe(recipeId);
        if(null==mRecipe){
            return v;
        }
        mRecipeStepArrayList = mRecipe.getRecipeStepList();
        mRecipeStep = mRecipeStepArrayList.get(mPosition);
        
        setRecipeData(v);
        return v;
    }

    private void setRecipeData(View v) {
        //set member views
        TextView textViewName = (TextView) v.findViewById(R.id.recipeStepDisplayNameTextView);
        textViewName.setText(mRecipe.getRecipeName());

        mIngredientsListView = (ListView) v.findViewById(R.id.recipeStepIngredientListView);
        mIngredientStringList = mRecipeStep.getRecipeStepIngredientStringList();
        if (mIngredientStringList != null) {
            mIngredientsListView.setAdapter(new MyIngredientListAdapter(getActivity(),R.layout.ingredient_list_view_layout,mIngredientStringList));
        }

        mInstructionsTextView = (TextView) v.findViewById(R.id.instructionsStepTextView);
        mInstructionsTextView.setText(mRecipeStep.getInstructions());

        TextView pageNumberTextView = (TextView) v.findViewById(R.id.pageNumberTextView);
        int page = mPosition + 1;
        pageNumberTextView.setText("Step " + page + " of " + mRecipeStepArrayList.size());

        mTextViewTimer = (TextView) v.findViewById(R.id.textViewTimerStep);
        mTextViewTimer.setText(String.valueOf(mRecipe.getTotalMinutes())+":00");

        mRecipeStepImageView = (ImageView) v.findViewById(R.id.recipeStepImageView);
        int imageId = mRecipeStep.getImageId();
        if(imageId<0){
            mRecipeStepImageView.setVisibility(View.GONE);
        } else{
            mRecipeStepImageView.setImageResource(R.drawable.loading_image);
            new DownloadImageTask().execute(String.valueOf(imageId));
        }
    }

    //Inner class for downloading image
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private static final String IP_ADDRESS = "98.253.25.58";
        private static final String IMAGE_URL = "http://" + IP_ADDRESS + "/save/image/";

        @Override
        protected Bitmap doInBackground(String... imageID) {
            String url = IMAGE_URL + imageID[0];
            System.out.println("Url truing to connect to: " + url.toString());
            Bitmap recipeBitmap = downloadImage(url);
            return recipeBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap recipeBitmap) {
            if(isAdded()) {  //This hopefully fixes nullpointerexception on the getActivity() call below.
                if (recipeBitmap != null) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(getActivity().getResources(), recipeBitmap);
                    mRecipeStepImageView.setImageDrawable(bitmapDrawable);
                }
            }
            super.onPostExecute(recipeBitmap);
        }

        private Bitmap downloadImage(String url) {
            InputStream inputStream = null;
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
                int responseCode = httpURLConnection.getResponseCode();
                System.out.println("Response code: " + responseCode);
                if (responseCode != 200) {
                    System.out.println("Bananas response code not 200");
                    return null;
                }
                inputStream = httpURLConnection.getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(inputStream);
                return bitMap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
