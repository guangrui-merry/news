package com.fc.news.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * ��ȡͼƬ�Ĺ�����
 * @author Administrator
 *
 *  ���� ͼƬ��uri �����������
 *  1.���ļ�  2.���ڴ�
 *  2.ȡ���ļ� 2.1 �������ȡ  2.2 ���ļ���ȡ 2.3 ���ڴ��ȡ
 *  3.���յ��õķ��� �õ�ͼƬ ���ж��ǲ����ڴ� --�ļ� --����
 *  
 */
public class LoadImage {

	/**
	 * ������ ���ڴ� ֻҪ���رոó��� һֱ���
	 */
	private static Map<String,SoftReference<Bitmap>>
	softReferences=new HashMap<String, SoftReference<Bitmap>>();
	private Context context;
	/**
	 * �Զ���ص��ӿ� ���� ͼƬ ��ͼƬ·��
	 * @author Administrator
	 *
	 */
	public interface ImageLoadListener{
		void imageLoadOk(Bitmap bitmap,String url);
	}
	
	private ImageLoadListener listener;
	/**
	 * ���췽����ֵ
	 * @param context
	 * @param listener
	 */
	public LoadImage(Context context, ImageLoadListener listener) {
		this.context = context;
		this.listener = listener;
	}

	/**
	 * ����ͼƬ������·����
	 * @param url     ͼƬ��ԭ·�� ���� http://aa/t.jpg
 	 * @param bitmap  ��������õ�ͼƬ
	 */
	public void saveCachFile(String url,Bitmap bitmap){
		//http://aa/t.jpg  ��ȡ�ļ�����
		String name=url.substring(url.lastIndexOf("/")+1);
		//���ص�·��Ŀ¼Ӧ�ó��򻺴��ļ�
		File cacheDir=context.getCacheDir();
		if(!cacheDir.exists()){
			cacheDir.mkdir();
		}
		//���������
		OutputStream outStream=null;
		try {
			outStream=new FileOutputStream(new File(cacheDir,name));
			//��ͼƬ���ļ�
			bitmap.compress(CompressFormat.JPEG, 100, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(outStream!=null){
					outStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}

	/**
	 * ����ͼƬ�������û�����
	 * @param url  ͼƬ��ԭ·�� ���� http://aa/t.jpg
	 * @param bitmap   �����ļ���ͼƬ 
	 */
	public void saveSoftReferences(String url,Bitmap bitmap){
		//�����ڴ��� һ��ͼƬ
		SoftReference<Bitmap> softbitmap=new SoftReference<Bitmap>(bitmap);
		//���ڼ�����
		softReferences.put(url, softbitmap);
	}

	/**
	 * �õ��ڴ��ͼƬ ������
	 * @param url ͼƬ·��
	 * @return bitmap ͼƬ
	 */
	public Bitmap getBitmapSoftReferences(String url){
		Bitmap bitmap=null;
		//����ڴ漯���и���key �õ�values
		if(softReferences.containsKey(url)){
			//�õ��������е�ͼƬ
			bitmap=softReferences.get(url).get();
		}
		return bitmap;
		
	}
	/**
	 * �ӻ����ļ��л�ȡͼƬ
	 * @param url ͼƬ·��  com.app.azy.news.cache/xxx.png
	 * @return  �����ļ��е�ͼƬ
	 */
	private Bitmap getBitmapFromCache(String url){
		String name=url.substring(url.lastIndexOf("/")+1);
		//��ȡ��ǰ���µĻ����ļ�·��
		File cacheDir=context.getCacheDir();
		//�õ����ļ����������ļ�
		File[] files=cacheDir.listFiles();
		if(files==null){
			return null;
		}
		//ͼƬ�ļ�
		File bitFile=null;
		//�������ֺʹ�����ļ���һ�µ����ҵ�ͼƬ
		for (File file : files) {
			if(file.getName().equals(name)){
				bitFile=file;
				break;}
		}
		//���û���ҵ������ؿ�
		if(bitFile==null){
			return null;
		}
		/**
		 * ���ҵ��ļ� ת��Ϊbitmap
		 */
		Bitmap bitmap=BitmapFactory.decodeFile(bitFile.getAbsolutePath());
		return bitmap;
	}
	
	/**
	 * �첽��������ͼƬ
	 * @param url ͼƬ�������е�·��
	 *
	 */
	private void getBitmapAsync(String url){
		//�Զ�����첽������
		ImageAsyncTask imageAsyncTask=new ImageAsyncTask();
		//ִ�м���
		imageAsyncTask.execute(url);
	}
	
	/**
	 * �첽������
	 * 1.Url ���������·��
	 * 2.�� Void  ������һ��ʱ ���ݵ�����
	 * 3.���صļ��غ�����ݣ�Bitmap
	 */
	private class ImageAsyncTask extends AsyncTask<String, Void, Bitmap>{

		private String url;
		
		
		//ִ��֮ǰ ui�߳�
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		//��̨����  ���̵߳Ĵ��� �����޸�ui
		@Override
		protected Bitmap doInBackground(String... params) {
			//�������ظ���ui
			//publishProgress(values);
			url=params[0];
			Bitmap bitmap=null;
			try {
				//������������
				URL url=new URL(params[0]);
				HttpURLConnection conn=(HttpURLConnection) url.openConnection();
				//�õ������ֽ���
				InputStream is=conn.getInputStream();
				//�õ�ͼƬ
				bitmap=BitmapFactory.decodeStream(is);
				
				//������������
				saveSoftReferences(params[0], bitmap);
				//���ڻ����ļ���
				saveCachFile(params[0], bitmap);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}
		//��� ���߳���ִ���� publishProgress(values);
		//�ͻ�ִ�� �˷��� ʵ��һ��һ���ĸ���ui
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
		
		//doInBackground ִ��return 
		//������ִ�� ui�߳� ���ѽ�����ݸ��˷��� execute(url)
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if(listener!=null){
				//�߳̽����󷵻�ͼƬ��·��
				listener.imageLoadOk(result, url);
			}
		}

	}
	
	/**
	 * ���յ��õķ���:�Ȳ鿴�ڴ�����û�У��ٿ������ļ��� ��û�У����ֻ������������ͼƬ
	 * @param url ͼƬ·��
	 * @return  ͼƬ
	 */
	public  Bitmap getBitmap(String url){
		Bitmap bitmap=null;
		if(url==null || url.length()<=0){
			return bitmap;
		}
		//�ȿ��ڴ���
		bitmap=getBitmapSoftReferences(url);
		if(bitmap!=null){
			return bitmap;
		}
		//�ǲ��ǻ����ļ���
		bitmap=getBitmapFromCache(url);
		if(bitmap!=null){
			return bitmap;
		}
		getBitmapAsync(url);
		
		return bitmap;
	}
	
}
