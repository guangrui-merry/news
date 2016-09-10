package com.fc.news.common.ui.base;

import com.fc.news.MyApplication;
import com.fc.news.R;
import com.fc.news.common.LogUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;



/**
 * ����activity�������Դ�ӡactivity�������ںͽ�Ŀ�Ľ�����˳�����
 * 
 * @author Administrator
 * 
 */
public class MyBaseActivity extends Activity {

	// ����
	protected MyApplication app;// ȫ�ִ洢����
	protected ProgressDialog dialog;// �Ի���
	protected FrameLayout layout_screenoff;// �ر�ʱ�Ķ���
	protected int screen_w,screen_h;

	/******************************** ��Activity LifeCycle For Debug Start�� ***************************************/
	/**
	 * ��ǰActivity����ʱ������(��һ������,��Activity�����ٺ��ٴ�����,δ��android:
	 * configChanges�������������÷����ı�ʱ)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtil.d(LogUtil.TAG, getClass().getSimpleName() + "onCreate");
		app = (MyApplication) getApplication();
		screen_w=getWindowManager().getDefaultDisplay().getWidth();
		screen_h=getWindowManager().getDefaultDisplay().getHeight();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtil.d(LogUtil.TAG, getClass().getSimpleName() + "onDestroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtil.d(LogUtil.TAG, getClass().getSimpleName() + "onPause");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		LogUtil.d(LogUtil.TAG, getClass().getSimpleName() + "onRestart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtil.d(LogUtil.TAG, getClass().getSimpleName() + "onResume");

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtil.d(LogUtil.TAG, getClass().getSimpleName() + "onStart");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogUtil.d(LogUtil.TAG, getClass().getSimpleName() + "onStop");
	}

	/*********************** ��װ�˵����ķ��� **********************************/
	// ��ͨ��ת
	public void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	// ��ת��������
	public void openActivity(Class<?> pClass, Bundle pbBundle) {
		openActivity(pClass, pbBundle, null);
	}

	// ��ת������
	public void openActivity(Class<?> pClass, Bundle pbBundle, Uri uri) {
		Intent intent = new Intent(this, pClass);
		if (pbBundle != null) {
			intent.putExtras(pbBundle);
		}
		if (uri != null) {
			intent.setData(uri);
		}
		startActivity(intent);
		// ��ת��ִ�еĶ��� ��һ�������ǽ�����activity �ڶ�������ʧ��activity
		overridePendingTransition(R.anim.screen_right_in,
				R.anim.screen_down_out);

	}

	// ����action
	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	protected void openActivity(String pAction, Bundle pBundle) {
		openActivity(pAction, pBundle, null);
	}

	protected void openActivity(String pAction, Bundle pBundle, Uri uri) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		if (uri != null) {
			intent.setData(uri);
		}
		startActivity(intent);
		// ��ת��ִ�еĶ��� ��һ�������ǽ�����activity �ڶ�������ʧ��activity
		overridePendingTransition(R.anim.screen_right_in,
				R.anim.screen_down_out);

	}
	/**************************�������ܷ�װ****************************************/
	private Toast toast;
	public void showToast(int resId){
		showToast(getString(resId));
	}
	
	public void showToast(String msg){
		if(toast==null)
			toast=Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setText(msg);
		toast.show();
	}

}
