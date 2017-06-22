package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;



@Entity
public class Files {
	
	@Id
	@GeneratedValue
	@Column(name="ID",length=4)
	private int id;
	
	@Column(name="FILENAME")
	private String file_name;
	
	@Column(name="MIMETYPE")
	private String mimeType;
	
	@Lob
	@Column(name="FILE")
	private byte[] content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	

}
