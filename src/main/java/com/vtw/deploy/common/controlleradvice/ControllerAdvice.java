package com.vtw.deploy.common.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vtw.deploy.common.fileupload.message.ResponseMessage;
import com.vtw.deploy.script.exception.ScriptException;

@RestControllerAdvice
public class ControllerAdvice {
	
	@ExceptionHandler(ScriptException.class)
	public ResponseEntity<ResponseMessage> scriptExceptionMessage(ScriptException e) {
		
		String message = "ScriptException occur";
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message,e.getMessage(),true));
	}

}
