package org.erikasv.automaticcallresponses;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erikasv on 30/06/13.
 */
public class CallResponsesApplication extends Application implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences preferences;
    private String activeColor, inactiveColor;
    private boolean onlyContacts;

    private String TAG="OBJETO_APLICACION";

    @Override
    public void onCreate() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        refreshValues();
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void refreshValues() {
        activeColor = preferences.getString("activeColor", "#00FF00");
        inactiveColor = preferences.getString("inactiveColor", "#000000");
        onlyContacts = preferences.getBoolean("onlyContacts", true);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        refreshValues();
    }

    public String getActiveColor() {
        return activeColor;
    }

    public String getInactiveColor() {
        return inactiveColor;
    }

    public boolean isOnlyContacts() {
        return onlyContacts;
    }
}

