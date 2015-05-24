package frey.jimmy.recipe.recipselector;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class CountDownTimerService extends Service {
    public static final String TIMER_BROADCAST_LOCATION = CountDownTimerService.class.getName() + "CountDownTimerService";
    public static final String EXTRA_TIME_LEFT = "extraTimeLeft";
    private CountDownTimer mRecipeTimer;
    public CountDownTimerService() {
    }

    private String formatTime(int millisLeft) {
        String timeString = null;
        int seconds = (int) (millisLeft / 1000) % 60 ;
        int minutes = (int) ((millisLeft / (1000*60)) % 60);
        int hours   = (int) ((millisLeft / (1000*60*60)) % 24);
        if(hours > 0){
            timeString = String.format("%d:%02d:%02d",hours,minutes,seconds);
        } else{
            timeString = String.format("%d:%02d",minutes,seconds);
        }
        return timeString;
    }

    private void sendUpdateTimeBroadcast(String timerFormattedString) {
        Intent i = new Intent(TIMER_BROADCAST_LOCATION);
        i.putExtra(EXTRA_TIME_LEFT, timerFormattedString);
        System.out.println("Sending time: " + timerFormattedString);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("BANANAS started service");
        int timeInMillis = intent.getIntExtra(RecipeDisplayFragment.EXTRA_MINUTES_INT,0);
        mRecipeTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                String timerFormattedString = formatTime((int)l);
                sendUpdateTimeBroadcast(timerFormattedString);
            }

            @Override
            public void onFinish() {

            }
        };
        mRecipeTimer.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
