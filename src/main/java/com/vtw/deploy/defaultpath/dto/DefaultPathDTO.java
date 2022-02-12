package com.vtw.deploy.defaultpath.dto;

import java.util.Date;

public class DefaultPathDTO {

	private String id;

	private String category;

	private String fileType;

	private String path;

	private char type;
	
	private Date updateDate;
	
	private String editorId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getEditorId() {
		return editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}

	public DefaultPathDTO(String category, String fileType) {

		this.category = category;
		this.fileType = fileType;
	}

	public DefaultPathDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public void setProperty(String id, String path) {
		this.id = id;
		this.path = path;
	}
	
}
