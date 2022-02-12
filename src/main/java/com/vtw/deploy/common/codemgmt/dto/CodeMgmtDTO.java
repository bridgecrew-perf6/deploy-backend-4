package com.vtw.deploy.common.codemgmt.dto;

import java.sql.Date;

public class CodeMgmtDTO {
	
	private String codeId;
	private String codeName;
	private String parentCodeId;
	private char codeUseYN;
	private int dsplOrder;
	private Date regdate, updateDate;
	private String registerer, modifier;
	
	public CodeMgmtDTO() {
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getParentCodeId() {
		return parentCodeId;
	}

	public void setParentCodeId(String parentCodeId) {
		this.parentCodeId = parentCodeId;
	}

	public char getCodeUseYN() {
		return codeUseYN;
	}

	public void setCodeUseYN(char codeUseYN) {
		this.codeUseYN = codeUseYN;
	}

	public int getDsplOrder() {
		return dsplOrder;
	}

	public void setDsplOrder(int dsplOrder) {
		this.dsplOrder = dsplOrder;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRegisterer() {
		return registerer;
	}

	public void setRegisterer(String registerer) {
		this.registerer = registerer;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	

}
