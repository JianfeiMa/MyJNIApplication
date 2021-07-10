package per.jianfei.myjniapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import java.io.IOException;

/**
 * 服务是相对于活动
 * 活动是可见，服务是不可见的，服务是在后台运行的，但是服务并不是线程
 */
public class MyService extends Service implements MediaPlayer.OnPreparedListener {
    private PlayerBinder mPlayerBinder;
    private MediaPlayer mMediaPlayer = null;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayerBinder = new PlayerBinder();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setOnPreparedListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mPlayerBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    public class PlayerBinder extends Binder {

        public void play() {
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(getAssets().openFd("song.mp3").getFileDescriptor());
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void pause() {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        }

        public void resume() {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
            }
        }

        public void stop() {
            mMediaPlayer.stop();
        }

        public void release() {
            mMediaPlayer.release();
        }

    }
}