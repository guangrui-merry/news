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
 * 父类activity用来调试打印activity生命周期和节目的进入和退出动画
 * 
 * @author Administrator
 * 
 */
public class MyBaseActivity extends Activity {

	// 属性
	protected MyApplication app;// 全局存储容器
	protected ProgressDialog dialog;// 对话框
	protected FrameLayout layout_screenoff;// 关闭时的动画
	protected int screen_w,screen_h;

	/******************************** 【Activity LifeCycle For Debug Start】 ***************************************/
	/**
	 * 当前Activity创建时来调用(第一次启动,本Activity被销毁后再次启动,未对android:
	 * configChanges进行设置且配置发生改变时)
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

	/*********************** 封装了调整的方法 **********************************/
	// 普通跳转
	public void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	// 跳转传递数据
	public void openActivity(Class<?> pClass, Bundle pbBundle) {
		openActivity(pClass, pbBundle, null);
	}

	// 跳转带动画
	public void openActivity(Class<?> pClass, Bundle pbBundle, Uri uri) {
		Intent intent = new Intent(this, pClass);
		if (pbBundle != null) {
			intent.putExtras(pbBundle);
		}
		if (uri != null) {
			intent.setData(uri);
		}
		startActivity(intent);
		// 跳转后执行的动画 第一个参数是进来的activity 第二个是消失的activity
		overridePendingTransition(R.anim.screen_right_in,
				R.anim.screen_down_out);

	}

	// 传递action
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
		// 跳转后执行的动画 第一个参数是进来的activity 第二个是消失的activity
		overridePendingTransition(R.anim.screen_right_in,
				R.anim.screen_down_out);

	}
	/**************************公共功能封装****************************************/
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
