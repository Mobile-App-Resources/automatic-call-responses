package org.erikasv.automaticcallresponses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by andresrtm on 1/07/13.
 */
public class IncommingCallReceiver extends BroadcastReceiver {
    private static long timeStarted = -1L; // IMPORTANT!
    private static long timeAnswered;
    private static long timeEnded;
    private static boolean isRoaming;
    private static String callerNumber;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras == null) return;
        String state = extras.getString(TelephonyManager.EXTRA_STATE);
        if (state == null) return;

        // phone is ringing
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

            timeStarted = System.currentTimeMillis();
            callerNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            isRoaming = tm.isNetworkRoaming();

            // set timeAnswered to -1;
            timeAnswered = -1L;

            Log.d("TEST", "timeStarted: " + timeStarted);
            Log.d("TEST", "caller number: " + callerNumber);
            Log.d("TEST", "isRoaming: " + isRoaming);

        }
        // call was answered -> Esto no nos interesa
        else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) && timeStarted != -1L) {
            timeAnswered = System.currentTimeMillis();
            Log.d("TEST", "timeAnswered: " + timeAnswered);
        }
        // call was ended
        /** EXISTIO UNA LLAMADA, pero no diferncia enrte si fue contestada o no*/
        else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE) && timeStarted != -1L) {
            /**Aqui verifico que sea perdida*/
            if (timeAnswered == -1L) {
                timeEnded = System.currentTimeMillis();
                Log.d("TEST", "timeEnded: " + timeEnded);
                timeStarted = -1L; // DON'T FORGET!
            }
        }
    }
}
