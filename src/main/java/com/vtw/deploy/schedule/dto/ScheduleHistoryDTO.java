package com.vtw.deploy.schedule.dto;

public class ScheduleHistoryDTO extends ScheduleDTO {

	private int historyNo;
	private String updateReason;
	private String type;

	public ScheduleHistoryDTO() {
		super();
	}

	public ScheduleHistoryDTO(int historyNo, String updateReason, String type) {
		super();
		this.historyNo = historyNo;
		this.updateReason = updateReason;
		this.type = type;
	}

	public int getHistoryNo() {
		return historyNo;
	}

	public void setHistoryNo(int historyNo) {
		this.historyNo = historyNo;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
