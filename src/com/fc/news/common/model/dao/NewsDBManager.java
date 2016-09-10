package com.fc.news.common.model.dao;

import java.util.ArrayList;

import com.fc.news.common.model.entiy.News;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ���ݿ������
 * @author Administrator
 *
 */
public class NewsDBManager {

	//����ģʽ
	private static NewsDBManager dbManager;
	private DBOpenHelper helper;
	
	private NewsDBManager(Context context){
		helper=new DBOpenHelper(context);
	}
	//ͬ����
	public static NewsDBManager getNewsDBManager(Context context){
		if(dbManager==null){
			synchronized (NewsDBManager.class) {
				if(dbManager==null){
					dbManager=new NewsDBManager(context);
				}
			}
		}
		return dbManager;
	}
	
	/**
	 * ���
	 */
	public void insertNews(News news){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		
		values.put("nid", news.getNid());
		values.put("title", news.getTitle());
		values.put("summary", news.getSummary());
		values.put("icon", news.getIcon());
		values.put("link", news.getLink());
		values.put("type", news.getType());
		db.insert("news", null, values);
		db.close();
	}
	
	/** �������� */
	public long getCount() {
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select count(*) from news",null);
		long len = 0;
		if (cursor.moveToFirst()) {
			len = cursor.getLong(0);
		}
		cursor.close();
		db.close();
		return len;
	}

	
	/** ��ѯ���� */
	public ArrayList<News> queryNews(int count, int offset) {
		ArrayList<News> newsList=new ArrayList<News>();
		SQLiteDatabase db=helper.getWritableDatabase();
		String sql="select * from news order by _id desc limit "+count+" offset "+offset;
		Cursor cursor=db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				int nid = cursor.getInt(cursor.getColumnIndex("nid"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String summary = cursor.getString(cursor.getColumnIndex("summary"));
				String icon = cursor.getString(cursor.getColumnIndex("icon"));
				String link = cursor.getString(cursor.getColumnIndex("link"));
				int type = cursor.getInt(cursor.getColumnIndex("type"));
				News news = new News(nid, title, summary, icon, link,type);
				newsList.add(news);
			} while (cursor.moveToNext());
			cursor.close();
			db.close();
		}
		return newsList;
	}
	
}
