package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01.general.Constants;

/**
 * Created by User on 03-Apr-18.
 */

public class PracticalTest01Var01Service extends Service{
    ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String text = intent.getStringExtra(Constants.RECEIVED_TEXT);
        int numberOfClicks = intent.getIntExtra(Constants.COUNTER, -1);
        processingThread = new ProcessingThread(this, text, numberOfClicks);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
