package com.example.johnnyliang.bossworkclock;

/**
 * Created by JohnnyLiang on 6/15/15.
 */
public class TimeCount implements Runnable {
    MainActivity activity;

    public void setActivity(MainActivity myActivity) {
        activity = myActivity;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                activity.doWork();
                Thread.sleep(1000); // Pause of 1 Second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }catch(Exception e){
            }
        }
    }
}
