package com.vtw.deploy;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.vtw.deploy.common.fileupload.service.FileStorageService;



@SpringBootApplication
public class DeployProjectApplication  extends SpringBootServletInitializer {
	
	
	@Resource
	FileStorageService storageSerivce;
	

	public static void main(String[] args) {
		SpringApplication.run(DeployProjectApplication.class, args);
		
	}

}
