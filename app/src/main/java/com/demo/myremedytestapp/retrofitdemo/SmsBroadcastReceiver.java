package com.demo.myremedytestapp.retrofitdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class SmsBroadcastReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            Status status = (Status) bundle.get(SmsRetriever.EXTRA_STATUS);

            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    String msg = bundle.getString(SmsRetriever.EXTRA_SMS_MESSAGE);
                    Log.i("@@MSG : ", "onReceive: "+msg);
                    break;
                case CommonStatusCodes.TIMEOUT:
                    // Handle Timeout error
                    break;
            }
        }
    }
}
