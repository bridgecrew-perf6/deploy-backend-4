package com.vtw.deploy.common.fileupload.dto;

import java.sql.Date;

public class FileDTO {
	
	private int fileNo,typeNo;
	private String name,directoryPath,deleteYN,type;
	private Date regdate;
	
	
	
	public FileDTO() {
	}
	
	public FileDTO(String name,String directoryPath,int typeNo) {
		this.name=name;
		this.directoryPath=directoryPath;
		this.typeNo=typeNo;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

	public int getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(int typeNo) {
		this.typeNo = typeNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirectoryPath() {
		return directoryPath;
	}

	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	public String getDeleteYN() {
		return deleteYN;
	}

	public void setDeleteYN(String deleteYN) {
		this.deleteYN = deleteYN;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	
	
}
