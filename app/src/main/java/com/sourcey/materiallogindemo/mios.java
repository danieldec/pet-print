package com.sourcey.materiallogindemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class mios extends AppCompatActivity implements View.OnClickListener {
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mios);
        b=(Button)findViewById(R.id.button1);
        b.setOnClickListener(this);
 }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification noti = new Notification.Builder(this)
                .setTicker("Pet Print")
                .setContentTitle("VAMOS A ENCONTRARLO!!")
                .setContentText("Se ha perdido un perrito")
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent).getNotification();
        noti.flags=Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }
}


