package com.philit.ehr.util;

import java.io.File;

import com.philit.ehr.EHRApp;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;


public class PlayerUtil {
	private static PlayerUtil instance;
	Context context;
	MediaPlayer player;

	public static synchronized PlayerUtil getInstance() {
		if (instance == null) {
			instance = new PlayerUtil();
		}
		return instance;
	}
	
	public static void playAnimAudio() {
	    new Thread(new Runnable() {
            @Override
            public void run() {
                getInstance().playAssetAudio("audio/show.wav");
            }
        }).start();
	}

	private PlayerUtil() {
		this.context = EHRApp.getInstance();
	}

	public void playAssetAudio(String audioPath) {

		try {
			AssetManager asm = context.getResources().getAssets();
			AssetFileDescriptor afd = asm.openFd(audioPath);
			if (player != null) {
				player.stop();
				player.release();
			}
			player = new MediaPlayer();
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),

			afd.getLength());
			player.prepare();
			player.start();
			Log.d("memo", "player.start()");
			player.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					stopPlaying();
					Log.d("memo", "stopPlaying()");
				}

			});
		} catch (Exception e) {
			stopPlaying();
		}
	}
	
	public void play(String audioPath, final CompleteCallback callback) {
	    if (audioPath == null || audioPath.equals("") || !new File(audioPath).exists()) {
	        return;
	    }
	    try {
            if (player != null) {
                player.stop();
                player.release();
            }
            player = new MediaPlayer();
            player.setDataSource(audioPath);
            player.prepare();
            player.start();
            Log.d("memo", "player.start()");
            player.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlaying();
                    if (callback != null) {
                        callback.complete();
                    }
                    Log.d("memo", "stopPlaying()");
                }

            });
        } catch (Exception e) {
            stopPlaying();
            if (callback != null) {
                callback.complete();
            }
        }
	}

	public void stopPlaying() {
		try {
			if (player != null && player.isPlaying()) {
				player.stop();
				player.release();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		player = null;
	}
	
	public interface CompleteCallback {
	    void complete();
	}
}
