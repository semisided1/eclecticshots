package com.eclecticshots.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Model class which will store the Photo Items
 * 
 * @author Darrell Dupas
 * 
 */
@Entity
public class ECPhoto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4374168083777299054L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String photourl;
	private String notes;
	private String albumname;
	
	public ECPhoto(String photourl, String notes, String albumname) {
		this.photourl = photourl;
		this.notes = notes;
		this.albumname = albumname;
	}
	/*
	public String toString() {
		String out = "<ecphoto><id>" + this.id + "</id>" +
		"<photourl>" + this.photourl + "</photourl>" +
		"<notes>" + this.notes + "</notes>" +
		"<albumname>" + this.albumname + "</albumname></ecphoto>";
		return out;
	}
	*/
	
	public Long getId() {
		return this.id;
	}
	
	public String getPhotourl() {
		return this.photourl;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void addNote(String note){
		this.notes = this.notes + "<br>" + note;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public String getAlbumname() {
		return this.albumname;
	}
	public void setAblbumname(String albumname) {
		this.albumname = albumname;
	}
	
	public String picasaSizeURL(int size) {
		int i,j;
		String f,g;
		i = photourl.lastIndexOf('/');
		j = photourl.lastIndexOf('/', i-1);
		f = photourl.substring(0,j+1);
		g = photourl.substring(i);
		return (f + "s" + size + g);
	}
	
	
}
