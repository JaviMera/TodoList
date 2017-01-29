package todo.javier.mera.todolist.broadcasts;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 1/27/2017.
 */

public class NotificationPublisher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(MainActivity.NOTIFICATION);

        // Cancel the notification upon click
        notification.flags |= PendingIntent.FLAG_CANCEL_CURRENT;

        int id = intent.getIntExtra(MainActivity.NOTIFICATION_ID, 0);
        manager.notify(id, notification);
    }
}
