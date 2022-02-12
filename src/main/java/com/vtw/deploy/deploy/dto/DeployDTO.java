package com.vtw.deploy.deploy.dto;

import java.util.Date;
import java.util.List;

import com.vtw.deploy.script.dto.ScriptDTO;

public class DeployDTO {
	
	//deploy
	private int deployNo;
	private String deployTitle;
	private String deployContent;
	private String writer;
	private String deployDate;
	private Date expectedDate;
	private String deployState;
	private String stateReason;
	private String team;
	private String id;
	
	private String startDate;
	private String endDate;
	
	//fileName
	private String name; 
	private String[] names;
	private String[] directoryPaths;
	
	//script
	private List<ScriptDTO> scriptDTO;
	
	private byte[] contentBlob;
	
	public DeployDTO() {
		// TODO Auto-generated constructor stub
	}		
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}



	public String getStateReason() {
		return stateReason;
	}


	public void setStateReason(String stateReason) {
		this.stateReason = stateReason;
	}


	public String getDeployState() {
		return deployState;
	}


	public void setDeployState(String deployState) {
		this.deployState = deployState;
	}


	public Date getExpectedDate() {
		return expectedDate;
	}


	public void setExpectedDate(Date expectedDate) {
		this.expectedDate = expectedDate;
	}


	public byte[] getContentBlob() {
		return contentBlob;
	}
	
	public void setContentBlob(byte[] contentBlob) {
		this.contentBlob = contentBlob;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public int getDeployNo() {
		return deployNo;
	}

	public void setDeployNo(int deployNo) {
		this.deployNo = deployNo;
	}

	public String getDeployTitle() {
		return deployTitle;
	}

	public void setDeployTitle(String deployTitle) {
		this.deployTitle = deployTitle;
	}

	public String getDeployContent() {
		return deployContent;
	}

	public void setDeployContent(String deployContent) {
		this.deployContent = deployContent;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getDeployDate() {
		return deployDate;
	}

	public void setDeployDate(String deployDate) {
		this.deployDate = deployDate;
	}

	public String[] getDirectoryPaths() {
		return directoryPaths;
	}

	public void setDirectoryPaths(String[] directoryPaths) {
		this.directoryPaths = directoryPaths;
	}

	public List<ScriptDTO> getScriptDTO() {
		return scriptDTO;
	}

	public void setScriptDTO(List<ScriptDTO> scriptDTO) {
		this.scriptDTO = scriptDTO;
	}

}
