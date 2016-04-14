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
	private final String MESSAGE = "message";// ʱ�䵽�������Ϣ
	private Context mContext;
	private TextView tv, tv2;
	private Button bt1, bt2, bt3, bt4, bt5, bt6,bt7;
	private final int Time = 5 * 1000;// Լ��ÿ��30��ִ��һ��
	private boolean isHanderType = false;
	private static final String ACTION_NAME = "android.intent.action.alarm.timer";// �㲥����
	private static final String ACTION_NAME2 = "android.intent.action.handler.timer";// �㲥����
	private int countHandler = 1;// handler���ʹ�������
	private int countAlarm = 0;// alarm���ʹ�������
	// Handler��ʽ����
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			handler.postDelayed(runnable, Time);

			Intent mIntent = new Intent(ACTION_NAME2);

			// ���͹㲥
			mIntent.putExtra(MESSAGE, "��" + countHandler + "��"
					+ "Handler��ʽ���͹����Ĺ㲥,  �ҽ���ͷ���" + countHandler + "��");

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

				tv.setText("��" + countAlarm + "��"
						+ "AlarmManager��ʽ���͹����Ĺ㲥,  ��ʱ����������ĵ�" + countAlarm
						+ "�μ�����");

				tv2.setText("��Handler�㲥��һ����Alarm������ִ��һ�Σ����������ִ�еڶ��Σ����ҷ���ʱִֻ��һ��");
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
		// ע��㲥
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

	// ע��㲥

	private void registerBoradcastReceiver() {

		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ACTION_NAME);
		myIntentFilter.addAction(ACTION_NAME2);

		// ע��㲥
		registerReceiver(mBroadcastReceiver, myIntentFilter);

	}

	// ���Ͷ�ʱ�㲥

	/**
	 * android�ṩ���������͵����ӣ� ELAPSED_REALTIME ��ָ������ʱ���󣬷��͹㲥�����������豸��
	 * 
	 * ELAPSED_REALTIME_WAKEUP
	 * ��ָ������ʾ�󣬷��͹㲥���������豸��ʱ��Ҫ��ϵͳ������ʱ��SystemClock.
	 * elapsedRealtime()���ȥ�ģ������÷������롣
	 * 
	 * 
	 * RTC ��ָ����ʱ�̣����͹㲥�����������豸
	 * 
	 * RTC_WAKEUP ��ָ����ʱ�̣����͹㲥���������豸
	 * 
	 * 
	 * AlarmManager�ṩ�ķ����� void set(int type, long
	 * triggerAtTime,PendingIntent operation) ����һ������
	 * 
	 * 
	 * void setRepeating(int type, long triggerAtTime, long
	 * interval,PendingIntent operation) ����һ�����ظ�������
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
			// Handler��ʽ
			handler.postDelayed(runnable, Time);// ÿTime��ִ��һ��runnable.
		} else {

			Intent mIntent = new Intent(ACTION_NAME);
			// ���͹㲥

			// ��Handler��ʱ�㲥��ͬ����ִֻ��һ�Σ�ִ�ж�ε��ǽ��ܵ��㲥��Ϣ����������û��
			// mIntent.putExtra(MESSAGE,
			// "��"+countAlarm+"��"+"AlarmManager��ʽ���͹����Ĺ㲥,  ��ʱ����������ĵ�"+countAlarm+"�μ�����");
			Toast.makeText(MainActivity.this, "����Alarm�㲥��ȫ������", 0).show();
			// AlarmManager��ʽ���͹㲥
			sendBroadcast(mIntent);
			// �����������ʼʱ�� ������// 5����͹㲥��ֻ����һ��

			PendingIntent pendIntent = PendingIntent.getBroadcast(mContext, 0,
					mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			// ��������ע��
			AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);


			switch (state) {

			case 1:
				/**
				 * 5����͹㲥��ֻ����һ�� elapsedRealtime() and elapsedRealtimeNanos()
				 * ����ϵͳ���������ڵ�ʱ�䣬�����豸������ߵ�ʱ�䡣��ʱ�ӱ���֤�ǵ����ģ�
				 * ��ʹCPU��ʡ��ģʽ�£���ʱ��Ҳ�������ʱ����ʱ�ӿ��Ա�ʹ���ڵ�����ʱ�������ܿ�Խϵͳ˯�ߵ�ʱ��Ρ�
				 * 
				 */
				long triggerAtTime = SystemClock.elapsedRealtime() + Time;
				manager.set(AlarmManager.ELAPSED_REALTIME, triggerAtTime,
						pendIntent);
				break;
			case 2:
				// ÿ��5���ظ����㲥
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

	// ȡ��Handler�㲥
	private void cancelHandlerBR() {

		handler.removeCallbacks(runnable);
		countHandler = 1;
		tv2.setText("�о�����5�뵽��ս������������");

	}

	// ȡ��Alarm�㲥
	private void cancelAlarmManagerBR() {
		Intent mIntent = new Intent(ACTION_NAME);

		PendingIntent pendIntent = PendingIntent.getBroadcast(mContext, 0,
				mIntent, 0);
		// �������intentƥ�䣨filterEquals(intent)�������ӻᱻȡ��

		// ��������ȡ��
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		manager.cancel(pendIntent);

		countAlarm = 0;
		tv.setText("�о�����5�뵽��ս������������");

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
		tv.setText("�о�����5�뵽��ս������������");
		tv2.setText("�о�����5�뵽��ս������������");

	}

	// ���Ͷ�ʱ����

	private void sendTimerService(boolean isHandler) {

		if (isHandler) {
			handler.postDelayed(runnable2, Time);// ÿTime��ִ��һ��runnable.

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
