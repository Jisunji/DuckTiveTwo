package com.example.ducktivetwo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ExpenseAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Trigger the notification logic here
        Log.d("AlarmReceiver", "Received alarm. Triggering notification.");

        ExpenseNotificationHelper.showExpenseNotification(context);

    }
}
