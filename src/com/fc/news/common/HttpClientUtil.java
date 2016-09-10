package com.fc.news.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.Environment;
import android.util.Log;

/**
 * �������ع�����
 * @author Administrator
 *
 */
public class HttpClientUtil {

	/**
	 * java ��url��������
	 * @param url ����·��
	 * @return  �õ����������� �ַ���
	 * @throws Exception 
	 */
	public static String UrlNet(String path) throws Exception{
		
		StringBuffer sb=new StringBuffer();
		URL url;
		//��������
		url = new URL(path);
		//�õ�������  ��ͨ�ֽ���
		InputStream in=url.openStream();
		//in.read();
		byte[] ch=new byte[1024];
		int count=0;
		
		//���浽�ļ���
		//��ȡ��ǰsd·��
		File f=new File(Environment.getExternalStorageDirectory(),"ab");
		f.mkdir();
		File testNet=new File(f,"test.txt");
		FileOutputStream out=new FileOutputStream(testNet);
		
		while((count=in.read(ch))!=-1){
			
			out.write(ch, 0, count);
			out.flush();
			sb.append(new String(ch,0,count));
			System.out.println(new String(ch,0,count));
			
		}
		out.close();
		in.close();
		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	public static String HttpURLConnectionNet(String path) throws Exception{
		
		String str="";
		URL url;
		//��������
		url = new URL(path);
		
		HttpURLConnection  httpConnection=(HttpURLConnection) url.openConnection();
		//�õ�������
		InputStream in=httpConnection.getInputStream();
		
		//in.read();
		byte[] ch=new byte[1024];
		int count=0;
		while((count=in.read(ch))!=-1){
			
			str+=new String(ch,0,count);
			//cou+=new String(ch,0,count).length();
			System.out.println(str);
		}
		in.close();
		//System.out.println("����"+cou);
		return str;
	}

	//��׿�ṩ���������� HttpClient
	//��������
	private static HttpClient mHttpClient;
	private static int Timeout=5000;//��ʱʱ��
	private static int MaxTotalConnections=8;//�����������
	
	/**����HTTPclient���󣺵ĳ�ʱʱ�䣬�����������*/
	public static synchronized HttpClient getHttpClient(){
		if(mHttpClient==null){
			//������������
			HttpParams params=new BasicHttpParams();
			//�������ӳ��г�ʱʱ��
			ConnManagerParams.setTimeout(params, Timeout);
			ConnManagerParams.setMaxTotalConnections(params, MaxTotalConnections);
			//���ӳ�ʱ���ⶨ����ͨ��������������������ӵĳ�ʱʱ��
			HttpConnectionParams.setConnectionTimeout(params, Timeout);
		
			mHttpClient=new DefaultHttpClient(params);
		}
		return mHttpClient;
	}
	
	/**�������磬�������ص�����*/
	public static String httpGet(String url){
		try {
			HttpClient httpClient=getHttpClient();
//			HttpClient httpClient=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(url);
			HttpResponse response=httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String resultStr = EntityUtils.toString(entity, "GBK");
			return resultStr;
		} catch (Exception e) {
			Log.d("jiaojian", e.toString());
			return "null";
		}
	}
	
}
