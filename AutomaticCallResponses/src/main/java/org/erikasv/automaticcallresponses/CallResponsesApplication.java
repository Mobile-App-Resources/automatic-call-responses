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

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_PROFILE_NAME,
            MySQLiteHelper.COLUMN_PROFILE_SMS, MySQLiteHelper.COLUMN_PROFILE_ACTIVE};

    private String TAG="OBJETO_APLICACION";

    @Override
    public void onCreate() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        refreshValues();
        preferences.registerOnSharedPreferenceChangeListener(this);
        dbHelper = new MySQLiteHelper(this);
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

    public void openDb() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void closeDb() {
        dbHelper.close();
    }

    public Profile createProfile(String name, String sms) {

        Profile newProfile = new Profile(name, sms, false);

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PROFILE_NAME, newProfile.getName());
        values.put(MySQLiteHelper.COLUMN_PROFILE_SMS, newProfile.getSms());
        values.put(MySQLiteHelper.COLUMN_PROFILE_ACTIVE, newProfile.isActive() ? 1 : 0);
        long insertId = database.insert(MySQLiteHelper.TABLE_PROFILE, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PROFILE,
                allColumns, null, null, null, null, null);

        newProfile.setId(insertId);
        return newProfile;
    }

    public void deleteProfile(Profile profile) {
        long id = profile.getId();
        Log.v(CallResponsesApplication.class.getName(), "Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_PROFILE, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<Profile>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PROFILE,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Profile profile = cursorToProfile(cursor);
            profiles.add(profile);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return profiles;
    }

    public void activateProfile(Profile prof){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PROFILE_ACTIVE, 1);
        prof.setActive(true);

        int update= database.update(MySQLiteHelper.TABLE_PROFILE,values,
                MySQLiteHelper.COLUMN_ID+" = "+prof.getId(),null);
        Log.v(TAG,update+"");
    }

    public void desActivateProfile(Profile prof){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PROFILE_ACTIVE, 0);

        int update= database.update(MySQLiteHelper.TABLE_PROFILE,values,
                MySQLiteHelper.COLUMN_ID+" = "+prof.getId(),null);
        Log.v(TAG,update+"");
    }

    private Profile cursorToProfile (Cursor cursor) {
        long id = cursor.getLong(0);
        String name = cursor.getString(1);
        String sms = cursor.getString(2);
        boolean active = (cursor.getInt(3) == 1);
        return new Profile(id, name, sms, active);
    }

    class MySQLiteHelper extends SQLiteOpenHelper {

        public static final String TABLE_PROFILE = "profiles";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_PROFILE_NAME = "name";
        public static final String COLUMN_PROFILE_SMS = "message";
        public static final String COLUMN_PROFILE_ACTIVE = "active";

        private static final String DATABASE_NAME = "automaticcallresponses.db";
        private static final int DATABASE_VERSION = 1;

        public MySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_PROFILE + " (" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_PROFILE_NAME + " text not null, " +
                    COLUMN_PROFILE_SMS + " text not null, " +
                    COLUMN_PROFILE_ACTIVE + " integer not null);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(MySQLiteHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
            onCreate(db);
        }
    }
}

