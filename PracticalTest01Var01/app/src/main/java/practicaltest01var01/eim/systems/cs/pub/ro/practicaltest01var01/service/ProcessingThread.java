package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01.service;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;
import java.util.Random;

import practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01.general.Constants;


public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;


    private String text;
    private int numberOfClicks;

    public ProcessingThread(Context context, String text, int numberOfClicks) {
        this.context = context;

        text = text;
        numberOfClicks = numberOfClicks;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        //intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " +  text);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
