package com.example.ducktivetwo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TasksAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");


        TasksNotificationHelper.showNotification(context, title, message);
    }
}
