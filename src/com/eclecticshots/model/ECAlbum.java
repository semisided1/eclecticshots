/**
 * 
 */
package com.eclecticshots.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author dd
 *
 */
@Entity
public class ECAlbum {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String coverurl;
	private String picasaurl;
	private String feedurl;
	private String aorder;
	
	
	public ECAlbum(String name, String coverurl, String picasaurl, String feedurl, String aorder) {
		this.name = name;
		this.coverurl = coverurl;
		this.picasaurl = picasaurl;
		this.feedurl = feedurl;
		this.aorder = aorder;
	}
	
	public String toString(){
		String out = "<ecalbum><id>" + this.id + "</id>" +
		"<name>" + this.name + "</name>" + 
		"<coverurl>" + this.coverurl + "</coverurl>" +
		"<picasaurl>" + this.picasaurl + "</picasaurl>" +
		"<feedurl>" + this.feedurl + "</feedurl>" +
		"</ecalbum>";
		return out;
	}

	public String getPicasaurl() {
		return this.picasaurl;
	}
	
	public void setPicasaurl(String url) {
		this.picasaurl = url;
	}

	public String getFeedurl() {
		return this.feedurl;
	}
	
	public void setFeedurl(String url) {
		this.feedurl = url;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getId() {
		return this.id.toString();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAorder() {
		return this.aorder;
	}

	public void setAorder(String o) {
		this.aorder = o;
	}

	public String getCoverurl() {
		return coverurl;
	}

	public void setCoverurl(String url){
		this.coverurl = url;
	}
	
	
}
