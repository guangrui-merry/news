package com.fc.news.common.ui;

import com.fc.news.R;
import com.fc.news.common.model.entiy.News;
import com.fc.news.common.ui.base.MyBaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

/**
 * ���������ϸҳ��
 * @author Administrator
 *
 */
public class ActivityShow extends MyBaseActivity {
	/**������ҳ�Ľ�����*/
	private ProgressBar progressBar;
	/**��ҳ*/
	private WebView webView;
	/**��ǰ������*/
	private News news;
	/**���ذ�ť*/
	private ImageButton imgRtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		progressBar=(ProgressBar) findViewById(R.id.progressBar1);
		webView=(WebView) findViewById(R.id.webView1);
		imgRtn = (ImageButton) findViewById(R.id.title_bar);
		imgRtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			finish();
			}
		});
		news=(News) getIntent().getSerializableExtra("news");
		
		//����webview������  ����ģʽ ����
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		
		//���ü���ȫ����ļ���
		webView.setWebViewClient(viewclient);
		//���õ�����ʱ�ļ���
		webView.setWebChromeClient(chromeClient);
		//����·��
		webView.loadUrl("http://3g.shengjing360.com"+news.getLink());
		
	}
	
	private WebViewClient viewclient=new WebViewClient(){
		//�ڵ��������������ǲŻ���ã���д�˷�������true���������ҳ��������ӻ����ڵ�ǰ��webview����ת��������������Ǳߡ�
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			webView.loadUrl(url);
			return true;
		};
	};
	/**
	 * ��������ʹ��,���ݽ���
	 */
	private WebChromeClient chromeClient=new WebChromeClient(){

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
		
			progressBar.setProgress(newProgress);
			if(progressBar.getProgress()==100){
				progressBar.setVisibility(View.GONE);
			}
		}
	};
	
	
	
	
	
}
