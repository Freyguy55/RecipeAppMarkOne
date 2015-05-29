package frey.jimmy.recipe.recipselector;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;


public class TimerFinishedActivity extends Activity implements MediaPlayer.OnPreparedListener {
    public static final String EXTRA_RECIPE_NAME = "extraRecipeName";
    private static final java.lang.String KEY_PLAY_ALARM = "keyPlayAlarm";
    private MediaPlayer mMediaPlayer = null;
    private AudioManager mAudioManager;
    private int mOriginalVolume;
    private boolean playAlarm = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_finished);
        if (savedInstanceState != null) {
            playAlarm = savedInstanceState.getBoolean(KEY_PLAY_ALARM);
        }
        TextView activityAlarmRecipeNameTextView = (TextView) findViewById(R.id.alarmActivityRecipeNameTextView);
        String recipeName = getIntent().getStringExtra(EXTRA_RECIPE_NAME);
        activityAlarmRecipeNameTextView.setText(recipeName);
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mOriginalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        Button stopAlarmButton = (Button) findViewById(R.id.stopAlarmButton);
        stopAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAlarm = false;
                stopAlarm();
            }
        });
        Button backButton = (Button) findViewById(R.id.buttonAlarmBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (playAlarm) {
            playAlarm();
        }

    }

    private void playAlarm() {
        stopAlarm();
        try {
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(this, soundUri);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, AudioManager.FLAG_PLAY_SOUND);
    }


    private void stopAlarm() {
        if (mMediaPlayer != null) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, mOriginalVolume, AudioManager.FLAG_PLAY_SOUND);
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        stopAlarm();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_PLAY_ALARM, playAlarm);
        super.onSaveInstanceState(outState);
    }
}
