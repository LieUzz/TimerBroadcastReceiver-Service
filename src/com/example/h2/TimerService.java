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
//    	message="��"+count+"��"+"ִ�ж�ʱ���� ���Ǹ���˼���ǵ�ѡ��";
       	Toast.makeText(getApplicationContext(), "ִ�ж�ʱ���� ���Ǹ���˼���ǵ�ѡ��", 0).show();
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
//	 * ����һ��Binder����
//	 */
//	@Override
//	public IBinder onBind(Intent intent) {
//		return new MsgBinder();
//	}
//	
//	public class MsgBinder extends Binder{
//		/**
//		 * ��ȡ��ǰService��ʵ��
//		 * @return
//		 */
//		public TimerService getService(){
//			return TimerService.this;
//		}
//	}



}
