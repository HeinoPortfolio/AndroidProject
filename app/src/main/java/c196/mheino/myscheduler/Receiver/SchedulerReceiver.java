package c196.mheino.myscheduler.Receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import c196.mheino.myscheduler.R;
import c196.mheino.myscheduler.UI.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class SchedulerReceiver extends BroadcastReceiver {


    static int notificationID = 1;
    //private String channel_id = "scheduler";
    private String CHANNEL_ID = "notification_channel";
    private String CHANNEL_DESCRIPTION = "notification channel description";

    // Keys.
    public static final String EXTRA_SCHEDULER_ALARM
            = "c196.mheino.myscheduler.EXTRA_SCHEDULER_ALARM";

    public static final String EXTRA_SCHEDULER_ALARM_TITLE
            = "c196.mheino.myscheduler.EXTRA_SCHEDULER_ALARM_TITLE";

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, intent.getStringExtra(EXTRA_SCHEDULER_ALARM)
                , Toast.LENGTH_LONG).show();

        String notificationTitle = intent.getStringExtra(EXTRA_SCHEDULER_ALARM_TITLE);
        String notificationContent = intent.getStringExtra(EXTRA_SCHEDULER_ALARM);


        createNotificationChannel(context, CHANNEL_ID);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_access_alarm_24)
                .setContentText(notificationContent)
                .setContentTitle(notificationTitle + "has an ID of: "
                        + Integer.toString(notificationID)).build();


        NotificationManager notificationManager =(NotificationManager)context
                .getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(notificationID++, notification);

    } // end onReceive.


    private void createNotificationChannel(Context context, String CHANNEL_ID) {


        // Only can be used with Oreo or API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Register the channel with the system.
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


    }

} // end class.










