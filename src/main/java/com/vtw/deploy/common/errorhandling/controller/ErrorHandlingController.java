package com.vtw.deploy.common.errorhandling.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/error"})
public class ErrorHandlingController 
implements ErrorController {

	@GetMapping
	public void handleError(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String referer = request.getHeader("referer");
		
		if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	        
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	response.sendRedirect(referer+"#/not-found");
	        } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	response.sendRedirect(referer+"#/error");
	        } else {
	        	response.sendRedirect(referer+"#/error");
	        }
	    }
	}

	@Override
	public String getErrorPath() {
		return null;
	}
	

}
