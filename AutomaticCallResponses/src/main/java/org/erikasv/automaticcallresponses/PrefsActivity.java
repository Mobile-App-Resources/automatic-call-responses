package org.erikasv.automaticcallresponses;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by erikasv on 1/07/13.
 */
public class PrefsActivity extends PreferenceActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
