package frey.jimmy.recipe.recipselector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.UUID;

/**
 * Displays a large recipe image.
 */
public class RecipeImageFragment extends Fragment {

    private static final String KEY_RECIPE_ID = "keyRecipeId";
    public static final String ENDPOINT = "frey.jimmy.testbucket.s3-website-us-west-1.amazonaws.com";
    public static final String IMAGE_URL = "http://" + ENDPOINT + "/image/";
    public static final String IMAGE_EXTENSION = ".jpg";
    private ImageView mRecipeImageView;
    private Recipe mRecipe;

    public RecipeImageFragment() {
        // Required empty public constructor
    }

    public static RecipeImageFragment createInstance(UUID recipeUUID) {
        RecipeImageFragment fragment = new RecipeImageFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_RECIPE_ID, recipeUUID);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_image, container, false);
        initialize(v);
        return v;
    }

    private void initialize(View v) {
        mRecipeImageView = (ImageView) v.findViewById(R.id.recipeImageView);
        UUID id = (UUID) getArguments().getSerializable(KEY_RECIPE_ID);
        mRecipe = RecipeBook.get(getActivity()).getRecipe(id);
        if (mRecipe.getRecipeImageId() >= 0) {
            mRecipeImageView.setImageResource(R.drawable.loading_image);
            String imageId = String.valueOf(mRecipe.getRecipeImageId());
            new DownloadImageTask().execute(imageId);
            mRecipeImageView.invalidate();
        } else {
            mRecipeImageView.setImageResource(R.drawable.recipe_image_no_image);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... imageID) {
            String url = IMAGE_URL + imageID[0] + IMAGE_EXTENSION;
            System.out.println("Url trying to connect to: " + url.toString());
            Bitmap recipeBitmap = downloadImage(url);
            return recipeBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap recipeBitmap) {
            if (isAdded()) {
                if (recipeBitmap != null) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(getActivity().getResources(), recipeBitmap);
                    mRecipeImageView.setImageDrawable(bitmapDrawable);
                } else {
                    mRecipeImageView.setImageResource(R.drawable.error_image);
                }

            }
            super.onPostExecute(recipeBitmap);
        }

        private Bitmap downloadImage(String url) {
            InputStream inputStream = null;
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                int responseCode = httpURLConnection.getResponseCode();
                System.out.println("Response code: " + responseCode);
                if (responseCode != 200) {
                    System.out.println("Bananas response code not 200");
                    return null;
                }
                inputStream = httpURLConnection.getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(inputStream);
                return bitMap;
            } catch (SocketTimeoutException e){
                e.printStackTrace();
                return null;
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

