package ganzy.com.ashh;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.samsung.android.sdk.cup.ScupButton;
import com.samsung.android.sdk.cup.ScupDialog;

import java.util.logging.LogRecord;

/**
 * Created by Ganesh on 1/23/2015.
 */
public class SilenceDialog extends ScupDialog implements  ScupButton.ClickListener{
    private static final String TAG = "SilenceDialog";
    private Handler handler ;
    private HandlerThread ht;

    public SilenceDialog(Context context) {
        super(context);
        ht = new HandlerThread("handerThread");
        ht.start();
        handler = new Handler(ht.getLooper());

    }

    @Override
    protected void onCreate() {
        Log.i(TAG, "Dialog created");
        super.onCreate();
        setBackEnabled(true);
        setTitle("Silence For");
        makeBtn("15m",7, Color.CYAN);
        makeBtn("30m",7, Color.YELLOW);
        makeBtn("1hr",7, Color.GREEN);
        makeBtn("∞",10, Color.RED);
        makeBtn("Ø",7, Color.WHITE);

        setBackPressedListener(new BackPressedListener() {
            @Override
            public void onBackPressed(ScupDialog arg0) {
                finish();
            }
        });


    }

    private void makeBtn(String text, int size, int color) {
        final ScupButton btn1 = new ScupButton(this);
        btn1.setAlignment(ScupButton.ALIGN_VERTICAL_CENTER);
        btn1.setPadding(4, 8, 4, 6);
        // Text parameter
        btn1.setText(text);
        btn1.setTextSize(size);
        btn1.setTextColor(color);
        btn1.setClickListener(this);
        // Show this button
        btn1.show();
    }

    @Override
    public void onClick(ScupButton scupButton) {
        Log.i(TAG, "button"+scupButton.getText()+" clicked");
        vibrate(ScupDialog.VIBRATION_TYPE_SHORT);

        if("15m".equals(scupButton.getText())) {
            registerAlarmCallback(15);
            showToast("Silent for 15 mins",
                    ScupDialog.TOAST_DURATION_SHORT);
            mutePhone();
        } else if("30m".equals(scupButton.getText())) {
            registerAlarmCallback(30);
            showToast("Silent for 30 mins",
                    ScupDialog. TOAST_DURATION_SHORT);
            mutePhone();
        } else if("1hr".equals(scupButton.getText())) {
            registerAlarmCallback(60);
            showToast("Silent for 1 hour",
                    ScupDialog.TOAST_DURATION_SHORT);
            mutePhone();
        } else if("Ø".equals(scupButton.getText())) {
            cancelPreviousAlarmCallbacks();
            showToast("Sound Restored",
                    ScupDialog.TOAST_DURATION_SHORT);
            phoneInNormal();
        } else {
            cancelPreviousAlarmCallbacks();
            showToast("Silent Forever",
                    ScupDialog.TOAST_DURATION_SHORT);
            mutePhone();
        }
        update();
        finish();
    }

    private void phoneInNormal() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Received callback, moving phone to normal mode");
                AudioManager audiomanager = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
                audiomanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        });
    }

    private void mutePhone() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Phone moved to vibrate mode");
                AudioManager audiomanager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                audiomanager.setRingerMode(getRingerMode());
            }
        });
    }

    private void registerAlarmCallback(final int timeInMin) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                AlarmManager alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getContext(), AlarmReceiver.class);
                PendingIntent  alarmIntent = PendingIntent.getBroadcast(getContext(), 125, intent, PendingIntent.FLAG_ONE_SHOT);

                alarmMgr.set(AlarmManager.RTC_WAKEUP,
                                    System.currentTimeMillis()+ 60 * 1000* timeInMin , alarmIntent);

                Log.i(TAG, "Registered for callback after "+timeInMin*60* 1000+" mills");
            }});
    }

    private void cancelPreviousAlarmCallbacks() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                AlarmManager alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getContext(), AlarmReceiver.class);
                PendingIntent  alarmIntent = PendingIntent.getBroadcast(getContext(), 125, intent, PendingIntent.FLAG_ONE_SHOT);
                alarmMgr.cancel(alarmIntent);
            }});

    }

    private int getRingerMode(){
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isVibrate =  mySharedPreferences.getBoolean("soundPrefVibrate", true);
        return isVibrate?AudioManager.RINGER_MODE_VIBRATE:AudioManager.RINGER_MODE_SILENT;
    }


}
