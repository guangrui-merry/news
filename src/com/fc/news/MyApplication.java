package com.fc.news;

import java.util.HashMap;

import com.fc.news.common.LogUtil;

import android.app.Application;
import android.content.res.Configuration;
/**
 * 
 * @author Administrator
 *  ȫ�ִ洢����
 *  ����Ӧ�ó���Ψһʵ��
 * ������ ��android��������ʱϵͳ�ᴴ��һ�� application���������洢ϵͳ��һЩ��Ϣ��
 * androidϵͳ��Ϊÿ����������ʱ����һ��Application��Ķ����ҽ�����һ��(����)��
 * ��application���������������������������ģ������������ھ͵������������������ڡ�
 * ��Ϊ����ȫ�ֵĵ����ģ������ڲ�ͬ��Activity,Service�л�õĶ�����ͬһ������
 * ����ͨ��Application������һЩ�����ݴ��ݣ����ݹ���,���ݻ���Ȳ�����
 *  
 */
public class MyApplication extends Application {

	/**������������Ӧ�ó��������*/
	private HashMap<String, Object> allData=new HashMap<String, Object>();
	/**������ */
	public  void addAllData(String key,Object value){		
		allData.put(key, value);	
	}
	/**ȡ����*/
	public Object getAllData(String key){
		if(allData.containsKey(key)){
			return allData.get(key);
		}
		return null;
	}
	/**ɾ��һ������*/
	public void delAllDataBykey(String key){
		if(allData.containsKey(key)){
			allData.remove(key);
		}
	}
	
	/**ɾ����������*/
	public void delAllData(){
		allData.clear();
	}
	
	/**����ģʽ*/
	private static MyApplication application;
	
	public static MyApplication getInstance(){
		LogUtil.d(LogUtil.TAG, "MyApplication onCreate");
		return application;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	
		application=this;
		
		LogUtil.d(LogUtil.TAG, "application oncreate");
		
	}
	
	
	/**�ڴ治���ʱ��*/
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		LogUtil.d(LogUtil.TAG, "MyApplication onLowMemory");
	}

	/**
	 * ������ʱ��
	 */
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		LogUtil.d(LogUtil.TAG, "MyApplication onTerminate");
	}

	/**���øı��ʱ��*/
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		LogUtil.d(LogUtil.TAG, "MyApplication onConfigurationChanged");
	}
	
	
	
	
}
