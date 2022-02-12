package com.vtw.deploy.defaultpath.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtw.deploy.common.fileupload.message.ResponseMessage;
import com.vtw.deploy.defaultpath.dto.DefaultPathDTO;
import com.vtw.deploy.defaultpath.dto.DefaultPathFormDTO;
import com.vtw.deploy.defaultpath.service.DefaultPathService;

@RestController
@RequestMapping("/defaultPath")
public class DefaultPathController {

	@Autowired
	private DefaultPathService service;
	
	@PostMapping
	public ResponseEntity<ResponseMessage> insertDefaultPath(@RequestBody DefaultPathFormDTO defaultPathForm) {
		String message = "";
		
		if(service.updateDefaultPathList(defaultPathForm)==1) {
			message = "Successfully Updated DefaultPath";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, true, true));
		}else {
			message = "Failed To Update DefaultPath";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, false, false));
		}
		
	}
	
	@GetMapping("/list")
	public ResponseEntity<ResponseMessage> selectDefaultPath() {
		
		List<DefaultPathDTO> defautPathList = service.selectAllDefaultPath();
		
		String message = "";
		if(defautPathList!=null) {
			message = "Successfully Fetched DefaultPath";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, defautPathList, true));
		}else {
			message = "Failed To Fetched DefaultPath";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, false, false));
		}
		
		
		
	}
	
}
