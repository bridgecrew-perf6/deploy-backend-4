package com.vtw.deploy.script.exception;

public class ScriptException extends Exception{

	private static final long serialVersionUID = -7781563464987898465L;

	public ScriptException(String message) {
		super(message);
	}
	
	public ScriptException(String message, Throwable cause) {
		super(message, cause);
	}

}
