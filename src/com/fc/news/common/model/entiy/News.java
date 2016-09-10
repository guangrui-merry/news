package com.fc.news.common.model.entiy;

import java.io.Serializable;

@SuppressWarnings("serial")
public class News implements Serializable{
	/**����id*/
	private int nid;
	/**����*/
	private String title="";
	/**ժҪ*/
	private String summary="";
	/**ͼ��*/
	private String icon="";
	/**��ҳ����*/
	private String link="";
	/**��������*/
	private int type;
	
	public News(int nid, String title, String summary, String list_image,
			 String url,int type) {
		this.nid = nid;
		this.title = title;
		this.summary = summary;
		this.icon = list_image;
		this.link = url;
		this.type=type;
	}

	public int getNid() {
		return nid;
	}
	public String getTitle() {
		return title;
	}
	public String getSummary() {
		return summary;
	}
	public String getIcon() {
		return icon;
	}
	public String getLink() {
		return link;
	}
	public int getType() {
		return type;
	}
	@Override
	public String toString() {
		return "News [nid=" + nid + ", title=" + title + ", summary=" + summary
				+ ", icon=" + icon + 
				 ", url=" + link +  ", type=" + type+"]";
	}	
}
