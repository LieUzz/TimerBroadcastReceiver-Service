package com.example.h2;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;


/**
 * Created by coder80 on 2014/10/31.
 */

public class ServiceUtil {
    private final static String ServiceName="com.example.h2.TimerService";
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager.getRunningServices(50);

        if(null == serviceInfos || serviceInfos.size() < 1) {
            return false;
        }

        for(int i = 0; i < serviceInfos.size(); i++) {
            if(serviceInfos.get(i).service.getClassName().contains(className)) {
                isRunning = true;
                break;
            }
        }
        Log.i("ServiceUtil-AlarmManager", className + " isRunning =  " + isRunning);
        return isRunning;
    }
;
    public static void startAMService(Context context){
        Log.i("ServiceUtil-AlarmManager", "invokeTimerPOIService wac called.." );
        PendingIntent alarmSender = null;
        Intent startIntent = new Intent(context, TimerService.class);
        startIntent.setAction(ServiceName);




        try {
    
            alarmSender = PendingIntent.getService(context, 0, startIntent, 0);
        } catch (Exception e) {
            Log.i("ServiceUtil-AlarmManager", "failed to start " + e.toString());
        }
        AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5*1000, alarmSender);
    }

    public static void cancleAMServicer(Context context){
        Log.i("ServiceUtil-AlarmManager", "cancleAlarmManager to start ");
        Intent intent = new Intent(context,TimerService.class);
    	intent.setAction(ServiceName);
        PendingIntent pendingIntent=PendingIntent.getService(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm=(AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
    }
    
    
    
    //Æô¶¯service ·½Ê½2  
    //  
    public static void startHandlerService(Context cxt){  
        Intent intent = new Intent(cxt,TimerService.class);  

        cxt.startService(intent);
    }  
      
    public  static void stopHandlerService(Context cxt){  
        Intent intent = new Intent(cxt,TimerService.class);  
 
        cxt.stopService(intent);
    }  
}
