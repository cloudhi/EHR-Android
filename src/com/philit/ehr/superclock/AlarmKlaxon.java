/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.philit.ehr.superclock;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.philit.ehr.R;
import com.philit.ehr.activity.MainActivity;

/**
 * Manages alarms and vibe. Runs as a service so that it can continue to play
 * if another activity overrides the AlarmAlert dialog.
 */
public class AlarmKlaxon extends Service {

    /** Play alarm up to 10 minutes before silencing */
    private static final int ALARM_TIMEOUT_SECONDS = 10 * 60;

    private static final long[] sVibratePattern = new long[] { 500, 500 };

    private boolean mPlaying = false;
    private Vibrator mVibrator;
    private MediaPlayer mMediaPlayer;
    private Alarm mCurrentAlarm;
    private long mStartTime;
    private TelephonyManager mTelephonyManager;
    private int mInitialCallState;
    
    //新增
    private long time=0;

    // Internal messages
    private static final int KILLER = 1000;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KILLER:
                    if (Log.LOGV) {
                        Log.v("*********** Alarm killer triggered ***********");
                    }
                    sendKillBroadcast((Alarm) msg.obj);
                    stopSelf();
                    break;
            }
        }
    };

    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String ignored) {
            // The user might already be in a call when the alarm fires. When
            // we register onCallStateChanged, we get the initial in-call state
            // which kills the alarm. Check against the initial call state so
            // we don't kill the alarm during a call.
            if (state != TelephonyManager.CALL_STATE_IDLE
                    && state != mInitialCallState) {
                sendKillBroadcast(mCurrentAlarm);
                stopSelf();
            }
        }
    };

    @Override
    public void onCreate() {
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Listen for incoming calls to kill the alarm.
        mTelephonyManager =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(
                mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        AlarmAlertWakeLock.acquireCpuWakeLock(this);
    }

    @Override
    public void onDestroy() {
        stop();
        // Stop listening for incoming calls.
        mTelephonyManager.listen(mPhoneStateListener, 0);
        AlarmAlertWakeLock.releaseCpuLock();
    }

    
    public class LocalBinder extends Binder {
		public AlarmKlaxon getService() {
			return AlarmKlaxon.this;
		}
	}
	
	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
		
	}
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // No intent, tell the system not to restart us.
        if (intent == null) {
            stopSelf();
            return START_NOT_STICKY;
        }

        final Alarm alarm = intent.getParcelableExtra(
                Alarms.ALARM_INTENT_EXTRA);

        if (alarm == null) {
            Log.v("AlarmKlaxon failed to parse the alarm from the intent");
            stopSelf();
            return START_NOT_STICKY;
        }

        if (mCurrentAlarm != null) {
            sendKillBroadcast(mCurrentAlarm);
        }
        
        //修改为提醒
        play2(alarm);
        //play(alarm);
        mCurrentAlarm = alarm;
        // Record the initial call state here so that the new alarm has the
        // newest state.
        mInitialCallState = mTelephonyManager.getCallState();

        return START_STICKY;
    }

    private void sendKillBroadcast(Alarm alarm) {
        long millis = System.currentTimeMillis() - mStartTime;
        int minutes = (int) Math.round(millis / 60000.0);
        Intent alarmKilled = new Intent(Alarms.ALARM_KILLED);
        alarmKilled.putExtra(Alarms.ALARM_INTENT_EXTRA, alarm);
        alarmKilled.putExtra(Alarms.ALARM_KILLED_TIMEOUT, minutes);
        sendBroadcast(alarmKilled);
    } 

    // Volume suggested by media team for in-call alarms.
    private static final float IN_CALL_VOLUME = 0.125f;

    //修改
    private void play2(Alarm alarm) {
    	NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	Notification notification=new Notification();
    	Intent intent=new Intent(this,MainActivity.class);
    	PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intent, 0);
        notification.icon=R.drawable.ic_launcher;
        String label=getResources().getString(R.string.medicine_notice_body);
        if (alarm.label!=null) {
        	if (!alarm.label.equals("")) {
            	label=alarm.label;
    		}
		}
        notification.tickerText=label;
        
        if (alarm.vibrate) {
        	Log.v("klaxon vibrate start..........");
            mVibrator.vibrate(500);
        } else {
        	Log.v("klaxon vibrate cancel..........");
            mVibrator.cancel();
        }
        if (!alarm.silent) {
            Uri alert = alarm.alert;
            // Fall back on the default alarm if the database does not have an
            // alarm stored.
            if (alert != null) {
            	if (!alarm.alert.equals("silent")) {
            		notification.defaults=Notification.DEFAULT_SOUND;
				}
            }
        }
        //notification.defaults=Notification.DEFAULT_SOUND;
        /* 
        * notification.flags |= FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知 
        * notification.flags |= FLAG_INSISTENT; //重复发出声音，直到用户响应此通知 
        * notification.flags |= FLAG_ONGOING_EVENT; //将此通知放到通知栏的"Ongoing"即"正在运行"组中 
        * notification.flags |= FLAG_NO_CLEAR; //表明在点击了通知栏中的"清除通知"后，此通知不清除， 
        * //经常与FLAG_ONGOING_EVENT一起使用 
        * notification.number = 1; //number字段表示此通知代表的当前事件数量，它将覆盖在状态栏图标的顶部 
        * //如果要使用此字段，必须从1开始 
        * notification.iconLevel = ; // 
        */ 
        notification.flags |=Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(this, getResources().getString(R.string.medicine_notice_title), label, pendingIntent);
        notificationManager.notify(1,notification);
	}
    
    private void play(Alarm alarm) {
        // stop() checks to see if we are already playing.
        stop();

        if (Log.LOGV) {
            Log.v("AlarmKlaxon.play() " + alarm.id + " alert " + alarm.alert);
        }

        if (!alarm.silent) {
            Uri alert = alarm.alert;
            // Fall back on the default alarm if the database does not have an
            // alarm stored.
            if (alert == null) {
                alert = RingtoneManager.getDefaultUri(
                        RingtoneManager.TYPE_ALARM);
                if (Log.LOGV) {
                    Log.v("Using default alarm: " + alert.toString());
                }
            }

            // TODO: Reuse mMediaPlayer instead of creating a new one and/or use
            // RingtoneManager.
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e("Error occurred while playing audio.");
                    mp.stop();
                    mp.release();
                    mMediaPlayer = null;
                    return true;
                }
            });

            try {
                // Check if we are in a call. If we are, use the in-call alarm
                // resource at a low volume to not disrupt the call.
                if (mTelephonyManager.getCallState()
                        != TelephonyManager.CALL_STATE_IDLE) {
                    Log.v("Using the in-call alarm");
                    mMediaPlayer.setVolume(IN_CALL_VOLUME, IN_CALL_VOLUME);
                    /*setDataSourceFromResource(getResources(), mMediaPlayer,
                            R.raw.in_call_alarm);*/
                } else {
                    mMediaPlayer.setDataSource(this, alert);
                }
                startAlarm(mMediaPlayer);
            } catch (Exception ex) {
                Log.v("Using the fallback ringtone");
                // The alert may be on the sd card which could be busy right
                // now. Use the fallback ringtone.
                try {
                    // Must reset the media player to clear the error state.
                    mMediaPlayer.reset();
                    /*setDataSourceFromResource(getResources(), mMediaPlayer,
                            R.raw.fallbackring);*/
                    startAlarm(mMediaPlayer);
                } catch (Exception ex2) {
                    // At this point we just don't play anything.
                    Log.e("Failed to play fallback ringtone", ex2);
                }
            }
        }

        /* Start the vibrator after everything is ok with the media player */
        //修改
        //在CalcScreen页面控制
        if (alarm.vibrate) {
        	Log.v("klaxon vibrate start..........");
            mVibrator.vibrate(sVibratePattern, 0);
        } else {
        	Log.v("klaxon vibrate cancel..........");
            mVibrator.cancel();
        }

        enableKiller(alarm);
        mPlaying = true;
        mStartTime = System.currentTimeMillis();
    }

    // Do the common stuff when starting the alarm.
    private void startAlarm(MediaPlayer player)
            throws java.io.IOException, IllegalArgumentException,
                   IllegalStateException {
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        // do not play alarms if stream volume is 0
        // (typically because ringer mode is silent).
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            player.setAudioStreamType(AudioManager.STREAM_ALARM);
            player.setLooping(true);
            player.prepare();
            player.start();
        }
    }

    private void setDataSourceFromResource(Resources resources,
            MediaPlayer player, int res) throws java.io.IOException {
        AssetFileDescriptor afd = resources.openRawResourceFd(res);
        if (afd != null) {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
                    afd.getLength());
            afd.close();
        }
    }

    /**
     * Stops alarm audio and disables alarm if it not snoozed and not
     * repeating
     */
    public void stop() {
        if (Log.LOGV) Log.v("AlarmKlaxon.stop()");
        if (mPlaying) {
            mPlaying = false;

            Intent alarmDone = new Intent(Alarms.ALARM_DONE_ACTION);
            sendBroadcast(alarmDone);

            // Stop audio playing
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }

            // Stop vibrator
            mVibrator.cancel();
        }
        disableKiller();
    }
    
    //新增
    public void start(){
    	mMediaPlayer.start();
    }
    
    public void pause(){
    	mMediaPlayer.pause();
    }

    /**
     * Kills alarm audio after ALARM_TIMEOUT_SECONDS, so the alarm
     * won't run all day.
     *
     * This just cancels the audio, but leaves the notification
     * popped, so the user will know that the alarm tripped.
     */
    private void enableKiller(Alarm alarm) {
        mHandler.sendMessageDelayed(mHandler.obtainMessage(KILLER, alarm),
                1000 * ALARM_TIMEOUT_SECONDS);
    }

    private void disableKiller() {
        mHandler.removeMessages(KILLER);
    }


}
