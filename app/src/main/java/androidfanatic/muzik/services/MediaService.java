package androidfanatic.muzik.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;

import androidfanatic.muzik.events.SongStatusEvent;
import androidfanatic.muzik.events.TogglePlayEvent;
import timber.log.Timber;

public class MediaService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    private static MediaPlayer mediaPlayer = null;

    public void play(String song_path) {
        if (mediaPlayer != null && song_path != null && !song_path.isEmpty()) {
            try {

                // stop previously playing song
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                // Reset
                mediaPlayer.reset();

                // Set new data source
                mediaPlayer.setDataSource(this, Uri.fromFile(new File(song_path)));


                // prepare new song
                mediaPlayer.prepareAsync();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        }

        EventBus.getDefault().register(this);
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        String song_path = intent.getStringExtra("song_path");
        Timber.d(song_path);
        play(song_path);
        return START_STICKY;
    }

    @Override public void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer = null;

        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onPrepared(MediaPlayer player) {
        player.start();
        EventBus.getDefault().post(new SongStatusEvent(SongStatusEvent.STATUS.PLAYING));
    }

    @Override public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override public void onCompletion(MediaPlayer mediaPlayer) {
        EventBus.getDefault().post(new SongStatusEvent(SongStatusEvent.STATUS.STOPPED));
    }

    @Subscribe
    public void toggle(TogglePlayEvent event) {
        Timber.d("Got toggle play event!");
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                EventBus.getDefault().post(new SongStatusEvent(SongStatusEvent.STATUS.PAUSED));
            } else {
                mediaPlayer.start();
                EventBus.getDefault().post(new SongStatusEvent(SongStatusEvent.STATUS.PLAYING));
            }
        } else {
            Timber.d("Media player null");
        }
    }
}

