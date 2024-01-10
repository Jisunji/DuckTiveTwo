package com.example.ducktivetwo;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;

import Model.Data;

public class ExpenseNotificationHelper  {


    private static final String CHANNEL_ID = "expense_channel";
    private static final String CHANNEL_NAME = "Expense Channel";
    private static final int NOTIFICATION_ID = 1;




    public interface ThresholdCallback {
        void onThresholdLoaded(long expenseThreshold);

    }

    public static void scheduleExpenseNotificationAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ExpenseAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        // to set alarm by millisecs
        long intervalMillis = 5 * 60 * 1000;
        long triggerTime = System.currentTimeMillis() + intervalMillis;

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                intervalMillis,
                pendingIntent
        );

    }



    public static void showExpenseNotification(Context context) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            DatabaseReference expenseThresholdRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(user.getUid())
                    .child("Expense_Threshold");

            retrieveExpenseThresholdFromDatabase(expenseThresholdRef, new ThresholdCallback() {
                @Override
                public void onThresholdLoaded(long expenseThreshold) {

                    // Now you have the expense threshold, proceed with the notification logic
                    DatabaseReference mExpenseDatabase = FirebaseDatabase.getInstance().getReference()
                            .child("users")
                            .child(user.getUid())
                            .child("Expense_Data");

                    mExpenseDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int totalsum = 0;

                            for (DataSnapshot mysnapshot : snapshot.getChildren()) {
                                Data data = mysnapshot.getValue(Data.class);
                                totalsum += data.getAmount();
                            }


                            BigDecimal totalExpense = new BigDecimal(Double.toString(totalsum));
                            BigDecimal totalThreshold = new BigDecimal(Double.toString(expenseThreshold));

                            BigDecimal expensePercentage = totalExpense.divide(totalThreshold, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                            String title = "";
                            String message = "";

                            if (expensePercentage.compareTo(BigDecimal.valueOf(80)) >= 0 && expensePercentage.compareTo(BigDecimal.valueOf(100)) < 0) {
                                title = "Expense Near Threshold";
                                message = String.format("Your expense is near %.2f%% of the threshold.", expensePercentage);
                            } else if (expensePercentage.compareTo(BigDecimal.valueOf(100)) >= 0) {
                                title = "Expense Full";
                                message = String.format("Your expense has reached the threshold. (%.2f%%)", expensePercentage);
                            } else if (expensePercentage.compareTo(BigDecimal.valueOf(100)) > 0) {
                                title = "Expense Exceeding Threshold";
                                message = String.format("Your expense is exceeding the threshold. (%.2f%%)", expensePercentage);
                            } else if (expensePercentage.compareTo(BigDecimal.valueOf(0)) >= 0 && expensePercentage.compareTo(BigDecimal.valueOf(80)) < 0) {
                                // Your logic for the last condition


                            } else {


                            }

                            if (!title.isEmpty() && !message.isEmpty()) {
                                NotificationManager notificationManager =
                                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    NotificationChannel channel = new NotificationChannel(
                                            CHANNEL_ID,
                                            CHANNEL_NAME,
                                            NotificationManager.IMPORTANCE_HIGH
                                    );
                                    notificationManager.createNotificationChannel(channel);
                                }

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                        .setSmallIcon(R.drawable.duckduck) // Replace with your own notification icon
                                        .setContentTitle(title)
                                        .setContentText(message)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                                notificationManager.notify(NOTIFICATION_ID, builder.build());
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle errors, if any
                        }
                    });
                }
            });
        }
    }

    private static void retrieveExpenseThresholdFromDatabase(DatabaseReference reference, ThresholdCallback callback) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long expenseThreshold = dataSnapshot.getValue(Long.class);
                    callback.onThresholdLoaded(expenseThreshold);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors, if any
            }
        });
    }



}
