package com.fc.news.common.ui.base;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * �����������ĸ���
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	//�����Ķ���
	protected Context context;
	//���������
	protected LayoutInflater inflater;
	//���ͼ���
	protected ArrayList<T> list=new ArrayList<T>();
	
	
	/**
	 * ���췽��
	 * @param c
	 */
	public MyBaseAdapter(Context c) {
		super();
		//����activity
		this.context=c;
		inflater=LayoutInflater.from(context);
		
	}

	/**
	 * �����������е�����
	 */
	public ArrayList<T> getAdapterData(){
		return list;
	}
	
	/**
	 * ��װ���һ����¼����
	 * t һ�����ݵĶ���
	 * isClearOld �Ƿ����ԭ����
	 */
	public void appendData(T t,boolean isClearOld){
		if(t==null){ //�ǿ���֤
			return;
		}
		if(isClearOld){//���true ���ԭ����
			list.clear();
		}//���һ�������ݵ����
		list.add(t);
		
	}
	/**
	 * ��Ӷ�����¼
	 * @param alist ���ݼ���
	 * @param isClearOld �Ƿ����ԭ����
	 */
	public void addendData(ArrayList<T> alist,boolean isClearOld){
		if(alist==null){
			return;
		}
		if(isClearOld){
			list.clear();
		}
		list.addAll(alist);
	}
	
	/**
	 * ���һ����¼ ����һ��
	 * @param t
	 * @param isClearOld
	 */
	public void appendDataTop(T t,boolean isClearOld){
		if(t==null){ //�ǿ���֤
			return;
		}
		if(isClearOld){//���true ���ԭ����
			list.clear();
		}//���һ�������ݵ���һ��
		list.add(0,t);
	}
	
	
	/**
	 * ��Ӷ�����¼������
	 * @param alist ���ݼ���
	 * @param isClearOld �Ƿ����ԭ����
	 */
	public void addendDataTop(ArrayList<T> alist,boolean isClearOld){
		if(alist==null){
			return;
		}
		if(isClearOld){
			list.clear();
		}
		list.addAll(0,alist);
	}

	/**
	 * ��������������
	 */
	public void updateAdapter(){
		this.notifyDataSetChanged();
	}
	/**
	 * �������������
	 */
	public void clearAdapter(){
		list.clear();
	}
	
	@Override
	public int getCount() {
		if(list==null){
			return 0;
		}else{
			return list.size();
		}
		
	}

	@Override
	public T getItem(int position) {
		if(list==null){
			return null;
		}
		//����Ѿ�û�������˷��ؿ�
		if(position>list.size()-1){
			return null;
		}
		return list.get(position);
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getMyView(position,convertView,parent);
	}
	/**
	 * �û�����ʵ�ִ˷��� ���ز�ͬ�Ĳ���
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View getMyView(int position, View convertView, ViewGroup parent);

}
