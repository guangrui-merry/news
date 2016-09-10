package com.fc.news.common.ui.adapter;

import com.fc.news.R;
import com.fc.news.common.CommonUtil;
import com.fc.news.common.LoadImage;
import com.fc.news.common.model.entiy.News;
import com.fc.news.common.ui.base.MyBaseAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ��������������
 * 
 * 1.holdview �󶨿ؼ� �����ؼ���ֵ ͼƬΪĬ��
 * 
 * 2.ͼƬ����
 * 
 * 
 * @author Administrator
 * 
 */
public class NewsAdapter extends MyBaseAdapter<News> {

	/**
	 * ����ͼƬ֮ǰĬ��ͼƬ
	 */
	private Bitmap defaultBitmap;
	// ͼƬ������
	private LoadImage loadImage;
	private ListView listview;

	public NewsAdapter(Context c, ListView lv) {
		super(c);
		defaultBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.cccc);
		listview = lv;
		loadImage = new LoadImage(c, listener);
	}

	// �ص��Ľӿ�
	private LoadImage.ImageLoadListener listener = new LoadImage.ImageLoadListener() {

		@Override
		public void imageLoadOk(Bitmap bitmap, String url) {
			// ������findviewById() �õ� ÿ��listview ��ͼƬ ͨ���첽���� ��ʾͼƬ
			ImageView iv = (ImageView) listview.findViewWithTag(url);

			if (iv != null) {
				iv.setImageBitmap(bitmap);
				// NewsAdapter ad =(NewsAdapter) listview.getAdapter();
				// ad.updateAdapter();
			}

		}
	};

	/*** ����ÿһ������Ŀ����ͼ */
	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		HoldView holdView = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_list_news, null);
			holdView = new HoldView(convertView);
			convertView.setTag(holdView);
		} else {
			holdView = (HoldView) convertView.getTag();
		}
		News news = list.get(position);
		holdView.tv_title.setText(news.getTitle());
		holdView.tv_text.setText(news.getSummary());
		holdView.iv_list_image.setImageBitmap(defaultBitmap);// Ĭ��ͼƬ

		// 1���õ�ͼƬ�ĵ�ַ
		String uriImage = news.getIcon();
		// ��ÿ��ͼƬ�ؼ������� ��ͼƬ��������Ϊ��ʾ
		holdView.iv_list_image.setTag(CommonUtil.NETPATH + uriImage);
		// ��ȡͼƬ 1. �ȴ����� 2.����Ѿ����ع��� ���ڱ����ļ����´�����������ļ���ȡ 3.�����򲻹ر� �ٴν���ý��� ���ڴ��ж�ȡ
		Bitmap bitmap = loadImage.getBitmap(uriImage);
		// �����������������ϼ��� �������ļ����ڴ�
		if (bitmap != null) {
			holdView.iv_list_image.setImageBitmap(bitmap);
		}
		return convertView;
	}

	/** ��ǩ�� */
	public class HoldView {
		public ImageView iv_list_image;
		public TextView tv_title;
		public TextView tv_text;

		public HoldView(View view) {
			this.iv_list_image = (ImageView) view.findViewById(R.id.imageView1);
			tv_title = (TextView) view.findViewById(R.id.textView1);
			tv_text = (TextView) view.findViewById(R.id.textView2);
		}

	}

}
