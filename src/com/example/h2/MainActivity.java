package com.example.h2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// private TimerService mTimerService;
	private final String MESSAGE = "message";// 时间到后接受消息
	private Context mContext;
	private TextView tv, tv2;
	private Button bt1, bt2, bt3, bt4, bt5, bt6,bt7;
	private final int Time = 5 * 1000;// 约定每隔30秒执行一次
	private boolean isHanderType = false;
	private static final String ACTION_NAME = "android.intent.action.alarm.timer";// 广播名称
	private static final String ACTION_NAME2 = "android.intent.action.handler.timer";// 广播名称
	private int countHandler = 1;// handler发送次数计数
	private int countAlarm = 0;// alarm发送次数计数
	// Handler方式发送
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			handler.postDelayed(runnable, Time);

			Intent mIntent = new Intent(ACTION_NAME2);

			// 发送广播
			mIntent.putExtra(MESSAGE, "第" + countHandler + "次"
					+ "Handler方式发送过来的广播,  我将带头冲锋" + countHandler + "次");

			sendBroadcast(mIntent);

		}
	};

	Runnable runnable2 = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext, TimerService.class);
			startService(intent);
			handler.postDelayed(runnable2, Time);

		}
	};

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			String message = intent.getStringExtra(MESSAGE);

			if (action.equals(ACTION_NAME)) {

				tv.setText("第" + countAlarm + "次"
						+ "AlarmManager方式发送过来的广播,  是时候表演真正的第" + countAlarm
						+ "次技术了");

				tv2.setText("和Handler广播不一样，Alarm是首先执行一次，五秒后在在执行第二次，并且发送时只执行一次");
				// Toast.makeText(MainActivity.this, message+countAlarm,
				// 0).show();
				countAlarm++;
			} else if (action.equals(ACTION_NAME2)) {

				if (!TextUtils.isEmpty(message)) {
					tv2.setText(message);
				}

				countHandler++;

			}

		}

	};

	// ServiceConnection conn = new ServiceConnection() {
	// @Override
	// public void onServiceDisconnected(ComponentName name) {
	//
	// }
	//
	// @Override
	// public void onServiceConnected(ComponentName name, IBinder service) {
	// // TODO Auto-generated method stub
	// mTimerService = ((TimerService.MsgBinder)service).getService();
	// tv.setText(mTimerService.getMessage());
	//
	//
	// }
	//
	//
	//
	//
	// };

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 注册广播
		registerBoradcastReceiver();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mContext = MainActivity.this;
		tv = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		bt1 = (Button) findViewById(R.id.button1);
		bt2 = (Button) findViewById(R.id.button2);

		bt3 = (Button) findViewById(R.id.button3);
		bt4 = (Button) findViewById(R.id.button4);
		bt5 = (Button) findViewById(R.id.button5);

		bt6 = (Button) findViewById(R.id.button6);

		bt7 = (Button) findViewById(R.id.button7);
		bt1.setOnClickListener(onClickListener);
		bt2.setOnClickListener(onClickListener);
		bt3.setOnClickListener(onClickListener);
		bt4.setOnClickListener(onClickListener);
		bt5.setOnClickListener(onClickListener);
		bt6.setOnClickListener(onClickListener);
		bt7.setOnClickListener(onClickListener);

	}

	View.OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button1:
				sendTimerBoaadCastReceiver(true,-1);
				bt1.setEnabled(false);
				break;
			case R.id.button2:
				sendTimerBoaadCastReceiver(false,2);
				bt2.setEnabled(false);
				break;
			case R.id.button3:
				sendTimerService(true);
				bt3.setEnabled(false);
				break;
			case R.id.button4:
				sendTimerService(false);
				bt4.setEnabled(false);
				break;
			case R.id.button5:
				cancelAlLBR();
				break;
			case R.id.button6:
				cancelAlLService();
				break;
			case R.id.button7:
				sendTimerBoaadCastReceiver(false,1);
				bt7.setEnabled(false);
				break;

			}

		}
	};

	// 注册广播

	private void registerBoradcastReceiver() {

		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ACTION_NAME);
		myIntentFilter.addAction(ACTION_NAME2);

		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);

	}

	// 发送定时广播

	/**
	 * android提供了四种类型的闹钟： ELAPSED_REALTIME 在指定的延时过后，发送广播，但不唤醒设备。
	 * 
	 * ELAPSED_REALTIME_WAKEUP
	 * 在指定的演示后，发送广播，并唤醒设备延时是要把系统启动的时间SystemClock.
	 * elapsedRealtime()算进去的，具体用法看代码。
	 * 
	 * 
	 * RTC 在指定的时刻，发送广播，但不唤醒设备
	 * 
	 * RTC_WAKEUP 在指定的时刻，发送广播，并唤醒设备
	 * 
	 * 
	 * AlarmManager提供的方法： void set(int type, long
	 * triggerAtTime,PendingIntent operation) 设置一个闹钟
	 * 
	 * 
	 * void setRepeating(int type, long triggerAtTime, long
	 * interval,PendingIntent operation) 设置一个会重复的闹钟
	 * 
	 * 
	 * void setInexactRepeating(int type, long triggerAtTime,
	 * longinterval, PendingIntent operation)
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	private void sendTimerBoaadCastReceiver(boolean isHandler, int state) {
		if (isHandler) {
			// Handler方式
			handler.postDelayed(runnable, Time);// 每Time秒执行一次runnable.
		} else {

			Intent mIntent = new Intent(ACTION_NAME);
			// 发送广播

			// 和Handler定时广播不同这里只执行一次，执行多次的是接受到广播消息，所以这里没用
			// mIntent.putExtra(MESSAGE,
			// "第"+countAlarm+"次"+"AlarmManager方式发送过来的广播,  是时候表演真正的第"+countAlarm+"次技术了");
			Toast.makeText(MainActivity.this, "发送Alarm广播，全军出击", 0).show();
			// AlarmManager方式发送广播
			sendBroadcast(mIntent);
			// 触发服务的起始时间 这里是// 5秒后发送广播，只发送一次

			PendingIntent pendIntent = PendingIntent.getBroadcast(mContext, 0,
					mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			// 进行闹铃注册
			AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);


			switch (state) {

			case 1:
				/**
				 * 5秒后发送广播，只发送一次 elapsedRealtime() and elapsedRealtimeNanos()
				 * 返回系统启动到现在的时间，包含设备深度休眠的时间。该时钟被保证是单调的，
				 * 即使CPU在省电模式下，该时间也会继续计时。该时钟可以被使用在当测量时间间隔可能跨越系统睡眠的时间段。
				 * 
				 */
				long triggerAtTime = SystemClock.elapsedRealtime() + Time;
				manager.set(AlarmManager.ELAPSED_REALTIME, triggerAtTime,
						pendIntent);
				break;
			case 2:
				// 每隔5秒重复发广播
				manager.setInexactRepeating(
						AlarmManager.ELAPSED_REALTIME_WAKEUP,
						SystemClock.elapsedRealtime(), Time, pendIntent);
				break;
			case 3:
				break;
			case 4:
				break;

			}

		}

	}

	// 取消Handler广播
	private void cancelHandlerBR() {

		handler.removeCallbacks(runnable);
		countHandler = 1;
		tv2.setText("敌军还有5秒到达战场，碾碎他们");

	}

	// 取消Alarm广播
	private void cancelAlarmManagerBR() {
		Intent mIntent = new Intent(ACTION_NAME);

		PendingIntent pendIntent = PendingIntent.getBroadcast(mContext, 0,
				mIntent, 0);
		// 与上面的intent匹配（filterEquals(intent)）的闹钟会被取消

		// 进行闹铃取消
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		manager.cancel(pendIntent);

		countAlarm = 0;
		tv.setText("敌军还有5秒到达战场，碾碎他们");

	}

	private void cancelAlLBR() {

		cancelHandlerBR();
		cancelAlarmManagerBR();
		bt1.setEnabled(true);
		bt2.setEnabled(true);

	}

	private void cancelAlLService() {
		handler.removeCallbacks(runnable2);
		ServiceUtil.cancleAMServicer(mContext);
		ServiceUtil.stopHandlerService(mContext);

		bt3.setEnabled(true);
		bt4.setEnabled(true);
		bt6.setEnabled(true);
		tv.setText("敌军还有5秒到达战场，碾碎他们");
		tv2.setText("敌军还有5秒到达战场，碾碎他们");

	}

	// 发送定时服务

	private void sendTimerService(boolean isHandler) {

		if (isHandler) {
			handler.postDelayed(runnable2, Time);// 每Time秒执行一次runnable.

		} else {
			ServiceUtil.startAMService(mContext);

		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		cancelAlLBR();
		cancelAlLService();
		// unbindService(conn);
		unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

}
