package frey.jimmy.recipe.recipselector;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;


/**
 * Displays recipe steps
 */
public class RecipeDisplayStepFragment extends Fragment {
    public static final String EXTRA_MINUTES_INT = "ExtraMinutesInt";
    private static final String KEY_INGREDIENTS_EXPANDED = "keyIngredientsExpanded";
    private static final String KEY_INSTRUCTIONS_EXPANDED = "keyInstructionsExpanded";
    private static final String KEY_IMAGE_EXPANDED = "keyImageExpanded";
    private static final String KEY_TIME_REMAINING = "keyTimeRemaining";
    private static final String KEY_POSITION = "KeyPosition";
    private static final String KEY_UUID = "keyUuid";
    private Recipe mRecipe;
    private ArrayList<RecipeStep> mRecipeStepArrayList;
    private RecipeStep mRecipeStep;
    private TextView mInstructionsTextView;
    private ScrollView mInstructionsScrollView;
    private ListView mIngredientsListView;
    private ArrayList<Ingredient> mIngredientList;
    private int mPosition;
    private ImageView mRecipeImageView;
    private TextView mTimerTextView;
    private Button mTimerStartButton;
    private Button mTimerPauseButton;
    private long mTimeRemaining;
    private boolean mIsIngredientsExpanded = true;
    private boolean mIsInstructionsExpanded = true;
    private boolean mIsImageViewExpanded = true;
    private ImageView mToggleIngredientsImageView;
    private ImageView mToggleInstructionsImageView;
    private ImageView mRecipeImageExpandCollapseImageView;

    public RecipeDisplayStepFragment() {
        // Required empty public constructor
    }

    public static RecipeDisplayStepFragment createInstance(UUID uuid, int position) {
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
        if (null == mRecipe) {
            return v;
        }
        mRecipeStepArrayList = mRecipe.getRecipeStepList();
        mRecipeStep = mRecipeStepArrayList.get(mPosition);
        setToggleVisibilityButtons(v, savedInstanceState);
        setRecipeData(v);
        startTimerServer(CountDownTimerService.TIMER_GET_TIME);  //Get time if paused
        return v;
    }

    private void setToggleVisibilityButtons(View v, Bundle savedInstanceState) {
        mTimerStartButton = (Button) v.findViewById(R.id.timerStartButton);
        mTimerPauseButton = (Button) v.findViewById(R.id.timerPauseButton);
        mTimerTextView = (TextView) v.findViewById(R.id.textViewTimer);

        mToggleIngredientsImageView = (ImageView) v.findViewById(R.id.ingredientExpandCollapseImageView);
        mToggleIngredientsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleIngredientOpenClose();
            }
        });

        mInstructionsScrollView = (ScrollView) v.findViewById(R.id.instructionsStepScrollView);
        mToggleInstructionsImageView = (ImageView) v.findViewById(R.id.instructionsExpandCollapseImageView);
        mToggleInstructionsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleInstructionOpenClose();
            }
        });



        mRecipeImageExpandCollapseImageView = (ImageView) v.findViewById(R.id.recipeImageViewExpandCollapse);
        mRecipeImageExpandCollapseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleImageViewOpenClose();
            }
        });

        mRecipeImageView = (ImageView) v.findViewById(R.id.recipeImageView);
        int imageId = mRecipeStep.getImageId();
        if (imageId < 0) {
            mRecipeImageView.setVisibility(View.GONE);
        } else {
            mRecipeImageView.setImageResource(R.drawable.loading_image);
            new DownloadImageTask().execute(String.valueOf(imageId));
        }

        //Initialize bundledStates
        if (savedInstanceState != null) {
            if (!savedInstanceState.getBoolean(KEY_INGREDIENTS_EXPANDED)) { //If ingredients should be collapsed
                toggleIngredientOpenClose(); //Collapse ingredients (initial state is always expanded)
            }
            if (!savedInstanceState.getBoolean(KEY_INSTRUCTIONS_EXPANDED)) {
                toggleInstructionOpenClose(); //Collapse instructions (initial state is always expanded)
            }
            if(!savedInstanceState.getBoolean(KEY_IMAGE_EXPANDED)){
                toggleImageViewOpenClose();
            }
            mTimeRemaining = savedInstanceState.getLong(KEY_TIME_REMAINING);
        } else {
            mTimeRemaining = mRecipe.getTotalMinutes() * 60 * 1000;
        }
        mTimerTextView.setText(formatTime(mTimeRemaining));
    }

    private String formatTime(long millisLeft) {
        String timeString = null;
        int seconds = (int) (millisLeft / ((double) 1000)) % 60;
        int minutes = (int) ((millisLeft / ((double) 1000 * 60)) % 60);
        int hours = (int) ((millisLeft / ((double) 1000 * 60 * 60)) % 24);
        if (hours > 0) {
            timeString = String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeString = String.format("%d:%02d", minutes, seconds);
        }
        return timeString;
    }

    private void toggleIngredientOpenClose() {
        if (mIsIngredientsExpanded) { //It is currently expanded and should collapse
            mToggleIngredientsImageView.setImageResource(R.drawable.expander_close_holo_light);
            mIsIngredientsExpanded = false;
            mIngredientsListView.setVisibility(View.GONE);
        } else {  //It is currently collapsed and should expand
            mToggleIngredientsImageView.setImageResource(R.drawable.expander_open_holo_light);
            mIsIngredientsExpanded = true;
            mIngredientsListView.setVisibility(View.VISIBLE);
        }
    }

    private void toggleInstructionOpenClose() {
        if (mIsInstructionsExpanded) { //It is currently expanded and should collapse
            mToggleInstructionsImageView.setImageResource(R.drawable.expander_close_holo_light);
            mIsInstructionsExpanded = false;
            mInstructionsScrollView.setVisibility(View.GONE);
        } else {  //It is currently collapsed and should expand
            mToggleInstructionsImageView.setImageResource(R.drawable.expander_open_holo_light);
            mIsInstructionsExpanded = true;
            mInstructionsScrollView.setVisibility(View.VISIBLE);
        }
    }

    private void toggleImageViewOpenClose() {
        if (mIsImageViewExpanded) { //It is currently expanded and should collapse
            mRecipeImageExpandCollapseImageView.setImageResource(R.drawable.expander_close_holo_light);
            mIsImageViewExpanded = false;
            mRecipeImageView.setVisibility(View.GONE);
        } else {  //It is currently collapsed and should expand
            mRecipeImageExpandCollapseImageView.setImageResource(R.drawable.expander_open_holo_light);
            mIsImageViewExpanded = true;
            mRecipeImageView.setVisibility(View.VISIBLE);
        }
    }

    private void setRecipeData(View v) {
        //set member views
        TextView textViewName = (TextView) v.findViewById(R.id.recipeStepDisplayNameTextView);
        textViewName.setText(mRecipe.getRecipeName());

        mIngredientsListView = (ListView) v.findViewById(R.id.recipeStepIngredientListView);
        mIngredientList = mRecipeStep.getRecipeStepIngredientList();
        if (mIngredientList != null) {
            mIngredientsListView.setAdapter(new MyIngredientListAdapter(getActivity(), R.layout.ingredient_list_view_layout, mIngredientList));
        }

        mInstructionsTextView = (TextView) v.findViewById(R.id.instructionsStepTextView);
        mInstructionsTextView.setText(mRecipeStep.getInstructions());

        TextView pageNumberTextView = (TextView) v.findViewById(R.id.pageNumberTextView);
        int page = mPosition + 1;
        pageNumberTextView.setText("Step " + page + " of " + mRecipeStepArrayList.size());

        //Timer start button
        mTimerStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerServer(CountDownTimerService.TIMER_START);
            }
        });
        //Timer pause button
        mTimerPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerServer((CountDownTimerService.TIMER_PAUSE));
            }
        });
        //Timer +2 Min Button
        Button addTwoMinButton = (Button) v.findViewById(R.id.timerAddTwoMinButton);
        addTwoMinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerServer(CountDownTimerService.TIMER_ADD_TWO_MIN);
            }
        });
        //Timer reset button
        Button timerResetButton = (Button) v.findViewById(R.id.timerResetButton);
        timerResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerServer(CountDownTimerService.TIMER_RESET);
                mTimeRemaining = mRecipe.getTotalMinutes() * 60 * 1000;
                mTimerTextView.setText(formatTime(mTimeRemaining));
            }
        });
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mTimeRemaining = intent.getLongExtra(CountDownTimerService.EXTRA_TIME_LEFT, 0);
                if (mTimeRemaining <= 0) {
                    mTimeRemaining = mRecipe.getTotalMinutes() * 60 * 1000;
                    mTimerTextView.setText(formatTime(mTimeRemaining));
                } else{
                    mTimerTextView.setText(formatTime(mTimeRemaining));
                }
            }
        }, new IntentFilter(CountDownTimerService.TIMER_BROADCAST_LOCATION));

    }

    private void startTimerServer(int command) {
        Intent i = new Intent(getActivity(), CountDownTimerService.class);
        i.putExtra(EXTRA_MINUTES_INT, mRecipe.getTotalMinutes());
        i.putExtra(TimerFinishedActivity.EXTRA_RECIPE_NAME, mRecipe.getRecipeName());
        i.putExtra(CountDownTimerService.EXTRA_COMMAND, command);
        getActivity().startService(i);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_INGREDIENTS_EXPANDED, mIsIngredientsExpanded);
        outState.putBoolean(KEY_INSTRUCTIONS_EXPANDED, mIsInstructionsExpanded);
        outState.putBoolean(KEY_IMAGE_EXPANDED, mIsImageViewExpanded);
        outState.putLong(KEY_TIME_REMAINING, mTimeRemaining);
        super.onSaveInstanceState(outState);
    }


    //Inner class for downloading image
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... imageID) {
            String url = RecipeImageFragment.IMAGE_URL + imageID[0] + RecipeImageFragment.IMAGE_EXTENSION;
            System.out.println("Url truing to connect to: " + url.toString());
            Bitmap recipeBitmap = downloadImage(url);
            return recipeBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap recipeBitmap) {
            if (isAdded()) {  //This hopefully fixes nullpointerexception on the getActivity() call below.
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
            } catch (SocketTimeoutException e) {
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
