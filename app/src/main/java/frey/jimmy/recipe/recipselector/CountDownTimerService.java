package frey.jimmy.recipe.recipselector;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class CountDownTimerService extends Service {
    public static final String TIMER_BROADCAST_LOCATION = CountDownTimerService.class.getName() + "CountDownTimerService";
    public static final String EXTRA_TIME_LEFT = "extraTimeLeft";
    public static final String EXTRA_COMMAND = "extraCommand";
    public static final int TIMER_START = 0;
    public static final int TIMER_PAUSE = 1;
    public static final int TIMER_RESET = 5;
    public static final int TIMER_GET_TIME = 9;
    public static final int TIMER_ADD_TWO_MIN = 2;
    private static final String SHARED_PREF_NAME = "CountdownTimerServicesSharedPref";
    private static final String PREF_LONG_TIME_REMAINING = "prefLongTimeRemaining";



    private static CountDownTimer mRecipeTimer;
    private LocalBroadcastManager mLocalBroadcastManager;
    private String mRecipeName;
    private static long mSavedTimeRemaining;

    public CountDownTimerService() {
    }


    private void sendUpdateTimeBroadcast(long l) {
        Intent i = new Intent(TIMER_BROADCAST_LOCATION);
        i.putExtra(EXTRA_TIME_LEFT, l);
        mLocalBroadcastManager.sendBroadcast(i);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("BANANAS started service");
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        //Handle random null intent that can be resent.
        if (intent == null) {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        mRecipeName = intent.getStringExtra(TimerFinishedActivity.EXTRA_RECIPE_NAME);
        int recipeTotalMinutes = intent.getIntExtra(RecipeDisplayFragment.EXTRA_MINUTES_INT, 0) * 60 * 1000;
        int command = intent.getIntExtra(EXTRA_COMMAND, 0);

        switch (command) {
            case TIMER_START: {
                if (mRecipeTimer == null) {
                    SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                    if (preferences != null) {
                        mSavedTimeRemaining = preferences.getLong(PREF_LONG_TIME_REMAINING, -1);
                        if (mSavedTimeRemaining > 0) {
                            startTimer((int) mSavedTimeRemaining);
                        } else {
                            startTimer(recipeTotalMinutes);
                        }
                    }
                }
                break;
            }

            case TIMER_PAUSE: {
                SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if (mRecipeTimer != null) {
                    mRecipeTimer.cancel();
                    mRecipeTimer = null;
                    editor.putLong(PREF_LONG_TIME_REMAINING, mSavedTimeRemaining).commit();
                } else {
                    mSavedTimeRemaining = preferences.getLong(PREF_LONG_TIME_REMAINING, -1);
                    editor.putLong(PREF_LONG_TIME_REMAINING, mSavedTimeRemaining).commit();
                }
                stopSelf();
                break;
            }

            case TIMER_RESET: {
                if (mRecipeTimer != null) {
                    mRecipeTimer.cancel();
                    mRecipeTimer = null;
                }
                mSavedTimeRemaining = -1;
                SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong(PREF_LONG_TIME_REMAINING, mSavedTimeRemaining).commit();
                sendUpdateTimeBroadcast(mSavedTimeRemaining);
                stopSelf();
                break;
            }

            case TIMER_ADD_TWO_MIN: {
                if (mRecipeTimer != null) {
                    mRecipeTimer.cancel();
                    mRecipeTimer = null;
                    startTimer((int) mSavedTimeRemaining + 120 * 1000);
                } else {
                    SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                    mSavedTimeRemaining = preferences.getLong(PREF_LONG_TIME_REMAINING, -1);
                    if (mSavedTimeRemaining <= 0) {
                        mSavedTimeRemaining = recipeTotalMinutes + 120 * 1000;
                    } else {
                        mSavedTimeRemaining = mSavedTimeRemaining + 120 * 1000;
                    }
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong(PREF_LONG_TIME_REMAINING, mSavedTimeRemaining).commit();
                    sendUpdateTimeBroadcast(mSavedTimeRemaining);

                    stopSelf();
                }

                break;
            }

            case TIMER_GET_TIME: {
                SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                if (preferences != null) {
                    mSavedTimeRemaining = preferences.getLong(PREF_LONG_TIME_REMAINING, -1);
                    sendUpdateTimeBroadcast(mSavedTimeRemaining);
                }
                stopSelf();
                break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startTimer(int recipeTotalMinutes) {
        mRecipeTimer = new CountDownTimer(recipeTotalMinutes, 100) {
            @Override
            public void onTick(long l) {
                mSavedTimeRemaining = l;
                sendUpdateTimeBroadcast(l);
            }

            @Override
            public void onFinish() {
                sendUpdateTimeBroadcast(0);
                if (mRecipeTimer != null) {
                    mRecipeTimer.cancel();
                }
                mSavedTimeRemaining = -1;
                mRecipeTimer = null;
                Intent i = new Intent(CountDownTimerService.this, TimerFinishedActivity.class);
                i.putExtra(TimerFinishedActivity.EXTRA_RECIPE_NAME, mRecipeName);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                stopSelf();
            }
        };
        mRecipeTimer.start();
    }

    @Override
    public void onDestroy() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(PREF_LONG_TIME_REMAINING, mSavedTimeRemaining).commit();
        System.out.println("BananasDestroy" + mSavedTimeRemaining);
        super.onDestroy();
    }
}
