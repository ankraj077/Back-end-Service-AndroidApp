package com.example.arajput9656.backservices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MyService extends Service {

    ScheduledExecutorService scheduleTaskExecutor ;
    static int num = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

       scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        // This schedule a runnable task every 2 minutes
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if(num == 5){
                    sendDataBack();
                    num = 0;
                    scheduleTaskExecutor.shutdownNow();
                }
                else{
                    num++;
                }
                System.out.println("Here Started");
            }
        }, 0, 2, TimeUnit.SECONDS);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduleTaskExecutor.shutdownNow();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    public void sendDataBack(){
        Intent intent = new Intent("data");
        intent.putExtra("data", "1");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
