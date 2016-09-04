package ganzy.com.ashh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by Ganesh on 1/23/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i(TAG, "Received callback, moving phone to normal mode");
        AudioManager audiomanager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audiomanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);

        Intent showDialogIntent = new Intent(context.getApplicationContext(),NoViewActivity.class);
        showDialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        showDialogIntent.putExtra(NoViewActivity.MOVED_OUT_FROM_SILENT, true);
        context.getApplicationContext().startActivity(showDialogIntent);
    }

}
