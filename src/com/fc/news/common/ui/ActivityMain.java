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
 * ���İ����ſͻ���
 * 
 * @author Administrator 1.��ȡ�ؼ� 2.������ �������������� ������������(����ͼƬ����������) ʵ����
 * 
 */
public class ActivityMain extends MyBaseActivity {

	private ListView listView;

	private NewsAdapter adapter;

	// private ArrayList<News> newlist;

	private NewsDBManager dbManager;

	// �������� json
	private ParserNews newsParser;

	// ÿҳ��ʾ10��
	private int limit = 10;
	// �ڼ�ҳ
	private int offset;


	/**
	 * �첽�������ݷ���
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what == 100) {
				// �������ݿ���
				loadDataFromDB(limit, offset);
				
			} else if (msg.what == 200) {
				showToast("�������Ӵ���");

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
		// �ȼ��ػ��������
		adapter = new NewsAdapter(this, listView);

		listView.setAdapter(adapter);
		// �����¼�
		listView.setOnScrollListener(onScrollListener);
		// �����¼� ��ת
		listView.setOnItemClickListener(onItemClickListener);
		
		if (dbManager.getCount() > 0) {
			// ���ݿ����
			loadDataFromDB(limit, offset);
		} else {
			// �����첽��������
			loadData();
		}

	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			LogUtil.d("�������Ŀ"+position);
			News news = adapter.getAdapterData().get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("news", news);
			openActivity(ActivityShow.class, bundle);
		}

	};

	/**
	 * ���ݿ����
	 * 
	 * @param limit2
	 *            ÿҳ������
	 * @param offset2
	 *            ƫ�ƶ�����
	 */
	private void loadDataFromDB(int limit2, int offset2) {
		// ��һ���ǵ�0ҳ 10��
		ArrayList<News> data = dbManager.queryNews(limit2, offset2);
		adapter.addendData(data, false);
		adapter.updateAdapter();
		// �����һ���� 0 10 �ڶ���10 10 ������ 20 10
		this.offset = offset + data.size();
	}


	/**
	 * �첽��������
	 */
	private void loadData() {
		dialog = ProgressDialog.show(this, null, "�����У����Ժ򡣡���");

		new Thread(new Runnable() {

			@Override
			public void run() {
				// http ��������
				// String path = CommonUtil.NETPATH + "/"+
				// "DoNewsStartList?size=10&typeId=1";
				String path = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
				try {
					newsParser = new ParserNews(ActivityMain.this);
					// �������󣬵õ���������
					String httpResponse = HttpClientUtil.httpGet(path);
					LogUtil.d("���󷵻ص�����", httpResponse);
					// �����ص����ݽ���
					JSONObject object = new JSONObject(httpResponse);
					if (object.getString("message").equals("OK")) {
						newsParser.parser(object);
						handler.sendEmptyMessage(100);
					}
					/*
					 * if (httpResponse.getStatusLine().getStatusCode() == 200)
					 * { HttpEntity entity = httpResponse.getEntity(); // String
					 * result = EntityUtils.toString(entity); //
					 * System.out.println(str); // �������������ݿ�
					 * newsParser.parser(result); handler.sendEmptyMessage(100);
					 * }
					 */
					else {
						handler.sendEmptyMessage(200);
					}

				} catch (Exception e) {
					e.printStackTrace();
					// ����
					handler.sendEmptyMessage(200);
				}

			}
		}).start();
	}

	/**
	 * �����¼�
	 */
	private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		// ����ʱ�����ķ���
		// totalItemCount������
		// firstVisibleItem ��һ��
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
