package com.fc.news.common.model.biz;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fc.news.common.LogUtil;
import com.fc.news.common.model.dao.NewsDBManager;
import com.fc.news.common.model.entiy.News;

import android.content.Context;

/**
 * j
 * ���ݽ���
 * @author Administrator
 *
 */
public class ParserNews {

	private Context context;
	public ParserNews(Context context){
		this.context=context;
	}
	//���� 
	//������������
	
	public ArrayList<News> parser(JSONObject jsonObject) throws Exception{
		NewsDBManager dbManager=NewsDBManager.getNewsDBManager(context);
		ArrayList<News> newsLit=new ArrayList<News>();
//		//��ȡ��������
//		JSONObject jsonObject=new JSONObject(result);
		//��ȡһ������ ��������
		JSONArray jsonArray=jsonObject.getJSONArray("data");
		//ѭ�������������
		for (int i = 0; i <jsonArray.length(); i++) {
			
			JSONObject object=jsonArray.getJSONObject(i);
			int nid = object.getInt("nid");
			String title = object.getString("title");
			String summary = object.getString("summary");
			String icon = object.getString("icon");
			String link = object.getString("link");
			int type = object.getInt("type");
			News news=new News(nid, title, summary, icon, link,type);
			LogUtil.d(news.getTitle());
			//�������ݿ���
			dbManager.insertNews(news);
			newsLit.add(news);
		}
		return newsLit;
	}
	
	
}
