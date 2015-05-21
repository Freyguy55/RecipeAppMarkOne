package frey.jimmy.recipe.recipselector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by James on 5/21/2015.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Void> {
    private static final String IP_ADDRESS = "98.253.25.58";
    private static final String IMAGE_URL = "http://"+IP_ADDRESS+"/save/image/";
    private Context mContext;
    private ImageView mRecipeImageView;

    public DownloadImageTask(Context context, ImageView recipeImageView) {
        super();
        mContext = context;
        mRecipeImageView = recipeImageView;
    }

    @Override
    protected Void doInBackground(String... imageID) {
        String url = IMAGE_URL + imageID + ".jpg";
        downloadImage(url);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private void downloadImage(String url) {
        InputStream inputStream = null;
        try{
            URL imageUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode != 200){
                return;
            }
            inputStream = httpURLConnection.getInputStream();
            Bitmap bitMap = BitmapFactory.decodeStream(inputStream);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitMap);
            mRecipeImageView.setImageDrawable(bitmapDrawable);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
