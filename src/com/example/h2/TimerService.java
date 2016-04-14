package com.example.h2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by coder80 on 2016/3/31.
 */
public class TimerService extends Service{
    private String TAG = TimerService.class.getSimpleName();

	String message;
	int count=1;

    
    @Override
    public void onCreate() {
        super.onCreate();
        sendMessage();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        count=1;
        Log.i(TAG, "UploadPOIService onDestroy here.... ");
    }

    private void sendMessage() {
    	//simulation HTTP request to server 
//        count++;
//    	message="第"+count+"次"+"执行定时服务， 真是个深思熟虑的选择";
       	Toast.makeText(getApplicationContext(), "执行定时服务， 真是个深思熟虑的选择", 0).show();
       	stopSelf();
    }
    
	public String  getMessage(){
		
		return message;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

//	/**
//	 * 返回一个Binder对象
//	 */
//	@Override
//	public IBinder onBind(Intent intent) {
//		return new MsgBinder();
//	}
//	
//	public class MsgBinder extends Binder{
//		/**
//		 * 获取当前Service的实例
//		 * @return
//		 */
//		public TimerService getService(){
//			return TimerService.this;
//		}
//	}



}
