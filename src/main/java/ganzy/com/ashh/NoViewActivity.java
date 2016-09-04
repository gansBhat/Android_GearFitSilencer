package ganzy.com.ashh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.cup.Scup;
import com.samsung.android.sdk.cup.ScupDialog;

/**
 * Created by Ganesh on 1/23/2015.
 */
public class NoViewActivity extends Activity {


    private static final String TAG = "NoViewActivity";

    public static final String MOVED_OUT_FROM_SILENT = "MOVED_OUT_FROM_SILENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.i(TAG, "Activity created" + getIntent().getExtras().toString());

        if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().getExtras().getBoolean(MOVED_OUT_FROM_SILENT, false)) {
            try {
                initCups();
                launchMovedOutFromSilenceDialog();
            } catch (Throwable tr)  {
                Toast.makeText(this, "Looks like you dont have gear fit or gear fit manager!",Toast.LENGTH_LONG).show();
            }
        }  else {
            try {
                initCups();
                launchCupsDialog();
            } catch (Throwable tr)  {
                Toast.makeText(this, "Looks like you dont have gear fit or gear fit manager!",Toast.LENGTH_LONG).show();
            }
        }

        finish();
    }


    private void initCups() {
        Scup scup = new Scup();
        try {
// initialize an instance of Scup
            scup.initialize(getApplicationContext());
        } catch (SsdkUnsupportedException e) {
            if (e.getType() == SsdkUnsupportedException.VENDOR_NOT_SUPPORTED) {
// Vendor is not Samsung.
                Log.i(TAG, "Vendor not samsung");
                return;
            }
        }
        int versionCode = scup.getVersionCode();
        String versionName = scup.getVersionName();
        Log.i(TAG, "Init of Scp done");
    }


    private void launchCupsDialog() {
        SilenceDialog dialog = new SilenceDialog(getApplicationContext());
    }

    private void launchMovedOutFromSilenceDialog() {


        SilenceRemovedDialog dialog = new SilenceRemovedDialog(getApplicationContext());
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
