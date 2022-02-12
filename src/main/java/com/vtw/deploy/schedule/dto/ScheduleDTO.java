package com.vtw.deploy.schedule.dto;

import java.sql.Date;

public class ScheduleDTO {

	private int scheduleNo;
	private String scheduleTitle;
	private String writer;// 아이디
	private String name;// 이름
	private String startDate;// 분까지. 하루종일이면 날짜만
	private String endDate;
	private String complete;
	private String scheduleContent;
	private Date regdate;
	private Date updateDate;
	private String completeDate;// 분까지
	private String team;// 팀별로 보여줄때

	public ScheduleDTO() {
		super();
	}

	public ScheduleDTO(int scheduleNo, String scheduleTitle, String writer, String name, String startDate,
			String endDate, String complete, String scheduleContent, Date regdate, Date updateDate, String completeDate,
			String team) {
		super();
		this.scheduleNo = scheduleNo;
		this.scheduleTitle = scheduleTitle;
		this.writer = writer;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.complete = complete;
		this.scheduleContent = scheduleContent;
		this.regdate = regdate;
		this.updateDate = updateDate;
		this.completeDate = completeDate;
		this.team = team;
	}

	public int getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(int scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public String getScheduleTitle() {
		return scheduleTitle;
	}

	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public String getScheduleContent() {
		return scheduleContent;
	}

	public void setScheduleContent(String scheduleContent) {
		this.scheduleContent = scheduleContent;
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

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

}
