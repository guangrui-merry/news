package com.fc.news.common.ui;

import java.util.ArrayList;

import org.json.JSONObject;

import com.fc.news.R;
import com.fc.news.common.HttpClientUtil;
import com.fc.news.common.LogUtil;
import com.fc.news.common.model.biz.ParserNews;
import com.fc.news.common.model.dao.NewsDBManager;
import com.fc.news.common.model.entiy.News;
import com.fc.news.common.ui.adapter.NewsAdapter;
import com.fc.news.common.ui.base.MyBaseActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 核心版新闻客户端
 * 
 * @author Administrator 1.获取控件 2.适配器 建立父类适配器 和新闻适配器(处理图片的网络下载) 实体类
 * 
 */
public class ActivityMain extends MyBaseActivity {

	private ListView listView;

	private NewsAdapter adapter;

	// private ArrayList<News> newlist;

	private NewsDBManager dbManager;

	// 解析数据 json
	private ParserNews newsParser;

	// 每页显示10行
	private int limit = 10;
	// 第几页
	private int offset;


	/**
	 * 异步加载数据方法
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what == 100) {
				// 存在数据库中
				loadDataFromDB(limit, offset);
				
			} else if (msg.what == 200) {
				showToast("网络连接错误");

			}
			dialog.dismiss();

		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newslist);

		listView = (ListView) findViewById(R.id.listview);
		// newlist = new ArrayList<News>();
		dbManager = NewsDBManager.getNewsDBManager(this);
		// 先加载缓存的新闻
		adapter = new NewsAdapter(this, listView);

		listView.setAdapter(adapter);
		// 滑动事件
		listView.setOnScrollListener(onScrollListener);
		// 单击事件 跳转
		listView.setOnItemClickListener(onItemClickListener);
		
		if (dbManager.getCount() > 0) {
			// 数据库加载
			loadDataFromDB(limit, offset);
		} else {
			// 网络异步加载数据
			loadData();
		}

	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			LogUtil.d("点击子条目"+position);
			News news = adapter.getAdapterData().get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("news", news);
			openActivity(ActivityShow.class, bundle);
		}

	};

	/**
	 * 数据库加载
	 * 
	 * @param limit2
	 *            每页多少行
	 * @param offset2
	 *            偏移多少行
	 */
	private void loadDataFromDB(int limit2, int offset2) {
		// 第一次是第0页 10行
		ArrayList<News> data = dbManager.queryNews(limit2, offset2);
		adapter.addendData(data, false);
		adapter.updateAdapter();
		// 假如第一次是 0 10 第二次10 10 第三次 20 10
		this.offset = offset + data.size();
	}


	/**
	 * 异步加载数据
	 */
	private void loadData() {
		dialog = ProgressDialog.show(this, null, "加载中，请稍候。。。");

		new Thread(new Runnable() {

			@Override
			public void run() {
				// http 下载数据
				// String path = CommonUtil.NETPATH + "/"+
				// "DoNewsStartList?size=10&typeId=1";
				String path = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
				try {
					newsParser = new ParserNews(ActivityMain.this);
					// 发送请求，得到返回数据
					String httpResponse = HttpClientUtil.httpGet(path);
					LogUtil.d("请求返回的数据", httpResponse);
					// 将返回的数据解析
					JSONObject object = new JSONObject(httpResponse);
					if (object.getString("message").equals("OK")) {
						newsParser.parser(object);
						handler.sendEmptyMessage(100);
					}
					/*
					 * if (httpResponse.getStatusLine().getStatusCode() == 200)
					 * { HttpEntity entity = httpResponse.getEntity(); // String
					 * result = EntityUtils.toString(entity); //
					 * System.out.println(str); // 解析并存在数据库
					 * newsParser.parser(result); handler.sendEmptyMessage(100);
					 * }
					 */
					else {
						handler.sendEmptyMessage(200);
					}

				} catch (Exception e) {
					e.printStackTrace();
					// 出错
					handler.sendEmptyMessage(200);
				}

			}
		}).start();
	}

	/**
	 * 滑动事件
	 */
	private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		// 滑动时触发的方法
		// totalItemCount总数量
		// firstVisibleItem 第一行
		//
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (totalItemCount > 20
					&& listView.getLastVisiblePosition() + 1 == totalItemCount) {
				
				loadDataFromDB(limit, offset);
			}

		}

	};

}
