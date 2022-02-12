package com.vtw.deploy.common.fileupload.message;

public class ResponseMessage {
	private String message;
	private Object data;
	private boolean success;
	
	public ResponseMessage() {
	}
	
	public ResponseMessage(String message, Object data, boolean success) {
		this.message = message;
		this.data = data;
		this.success = success;
	}
	
	public ResponseMessage(String message) {
		this.message = message;
	}

	public ResponseMessage(Object data) {
		this.data = data;
	}

	public ResponseMessage(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
