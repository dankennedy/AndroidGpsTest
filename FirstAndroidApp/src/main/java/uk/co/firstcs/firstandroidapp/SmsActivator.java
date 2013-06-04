package uk.co.firstcs.firstandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsActivator extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        if (bundle != null) {
            // start the service to start logging
            //Toast.makeText(context, locationData, Toast.LENGTH_SHORT).show();
        }
    }
}
