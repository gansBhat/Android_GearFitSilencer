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
import com.samsung.android.sdk.cup.ScupLabel;
import com.samsung.android.sdk.cup.ScupWidgetBase;

/**
 * Created by Ganesh on 1/23/2015.
 */
public class SilenceRemovedDialog extends ScupDialog {
    private static final String TAG = "SilenceDialog";

    public SilenceRemovedDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        Log.i(TAG, "Dialog created");
        super.onCreate();
        setBackEnabled(true);
        setTitle("Came out of slient mode");
        vibrate(VIBRATION_TYPE_MID);

        ScupLabel scupLabel = new ScupLabel(this);
        scupLabel.setText("Silent mode removed");
        scupLabel.setTextSize(6);
        scupLabel.setAlignment(ScupWidgetBase.ALIGN_CENTER);
        scupLabel.setTextColor(Color.GREEN);
        scupLabel.show();

        setBackPressedListener(new BackPressedListener() {
            @Override
            public void onBackPressed(ScupDialog arg0) {
                finish();
            }
        });
    }

}
