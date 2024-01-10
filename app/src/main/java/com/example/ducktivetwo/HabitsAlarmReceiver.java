package com.example.ducktivetwo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HabitsAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");


        HabitsNotificationHelper.showNotification(context, title, message);
    }
}
