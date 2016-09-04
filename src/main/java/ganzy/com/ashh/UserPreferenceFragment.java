package ganzy.com.ashh;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ganzy.ashh2.R;

/**
 * Created by Ganesh on 1/24/2015.
 */
public class UserPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_preference);
    }
}
