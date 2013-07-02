package org.erikasv.automaticcallresponses;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by andresrtm on 1/07/13.
 */
public class SMSService extends Service {
    private static final int MAX_SMS_MESSAGE_LENGTH = 160;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendSMS(String phonenumber,String message) {

        SmsManager manager = SmsManager.getDefault();

        PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);


        int length = message.length();

        if (length > MAX_SMS_MESSAGE_LENGTH) {
            ArrayList<String> messagelist = manager.divideMessage(message);
            manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null);
        } else {
            manager.sendTextMessage(phonenumber, null, message, piSend, piDelivered);
        }

    }
}
