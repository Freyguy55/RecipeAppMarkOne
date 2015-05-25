package frey.jimmy.recipe.recipselector;

import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class CountDownTimerService extends Service {
    public static final String TIMER_BROADCAST_LOCATION = CountDownTimerService.class.getName() + "CountDownTimerService";
    public static final String EXTRA_TIME_LEFT = "extraTimeLeft";
    private CountDownTimer mRecipeTimer;
    private LocalBroadcastManager mLocalBroadcastManager;
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
        if(intent==null){
            return super.onStartCommand(intent, flags, startId);
        }
        if(mRecipeTimer == null) {
            int recipeTotalMinutes = intent.getIntExtra(EXTRA_TIME_LEFT,0);
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
            mRecipeTimer = new CountDownTimer(5000, 100) {
                @Override
                public void onTick(long l) {
                    sendUpdateTimeBroadcast(l);
                }

                @Override
                public void onFinish() {
                    sendUpdateTimeBroadcast(0);
                    mRecipeTimer = null;
                    Intent i = new Intent(CountDownTimerService.this, TimerFinishedActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            };
            mRecipeTimer.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
