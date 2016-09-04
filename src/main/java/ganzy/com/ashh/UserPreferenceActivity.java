package ganzy.com.ashh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.cup.Scup;

/**
 * Created by Ganesh on 1/24/2015.
 */
public class UserPreferenceActivity extends Activity {

    private static final String TAG = "NoViewActivity";
    boolean isCreateCalled = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchCupsDialog();
        getFragmentManager().beginTransaction().add(android.R.id.content, new UserPreferenceFragment()).commit();
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void launchCupsDialog() {
        Scup scup = new Scup();
        try {
        // initialize an instance of Scup
            scup.initialize(getApplicationContext());
            Log.i(TAG, "Initializing SCup is done from app side!");
        } catch (SsdkUnsupportedException e) {
            if (e.getType() == SsdkUnsupportedException.VENDOR_NOT_SUPPORTED) {
        // Vendor is not Samsung.
                Log.i(TAG, "Vendor not samsung");
                Toast.makeText(this, "Vendor is not samusng",Toast.LENGTH_LONG);
                return;
            }
        }
        int versionCode = scup.getVersionCode();
        String versionName = scup.getVersionName();
        Log.i(TAG, "Init of Scp done");
        SilenceDialog dialog = new SilenceDialog(getApplicationContext());
    }
}
