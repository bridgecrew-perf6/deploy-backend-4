package com.vtw.deploy.common.fileupload.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.vtw.deploy.common.fileupload.dto.FileDTO;
import com.vtw.deploy.common.fileupload.dto.FileInfoDTO;
import com.vtw.deploy.common.fileupload.message.ResponseMessage;
import com.vtw.deploy.common.fileupload.service.FileStorageService;

@RestController
@RequestMapping({"/file"})
public class FileController {
	  
	  @Autowired
	  FileStorageService storageService;
	  
	  
	  //파일 업로드
	  @PostMapping("/upload")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {//앵귤러에서 file로 이름 붙여서 보냈음
	    String message = "";
	    FileDTO fileDTO = new FileDTO();
	    try {
	      String path = storageService.save(file);//지정 폴더에 파일 저장
	      fileDTO.setDirectoryPath(path);
	      fileDTO.setName(file.getOriginalFilename());
	      message = "File Uploaded Successfully";
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDTO,true));
	    } catch (Exception e) {
	      e.printStackTrace();
	      System.out.println( e.getMessage());
	      message = "File Upload Failed";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }
	  }

	  //파일 리스트 얻어오기
	  @GetMapping("/files")
	  public ResponseEntity<List<FileInfoDTO>> selectFileList() {
		  
		String message = "File List Info Loaded Successfully";
	    List<FileInfoDTO> fileInfos = storageService.selectFileList().map(path -> {
	      String filename = path.getFileName().toString();
	      String url = MvcUriComponentsBuilder
	          .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
	      return new FileInfoDTO(filename, url);
	    }).collect(Collectors.toList());

	      return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	  }
	  
	  //파일 다운로드
	  @GetMapping("/files/{filename:.+}")
	  public ResponseEntity<Resource> getFile(@PathVariable String filename) throws UnsupportedEncodingException {
	    Resource file = storageService.selectOneFile(filename);
	    
	    //한글 파일 다운로드 시 글자 깨짐 현상 방지
	    filename = URLEncoder.encode(filename,"UTF-8").replaceAll("\\+","%20");
	    return ResponseEntity.status(HttpStatus.OK)
	    	.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(file);
	  }
	  
	  //zip파일 다운로드
	  @GetMapping("/zipfile/{filename:.+}")
	  public ResponseEntity<Resource> getZipFile(@PathVariable String filename) throws UnsupportedEncodingException {
	    Resource file = storageService.selectZipFile(filename);
	    
	    filename = URLEncoder.encode(filename,"UTF-8").replaceAll("\\+","%20");
	    return ResponseEntity.status(HttpStatus.OK)
	    	.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(file);
	  }
	  
	  //여러개 파일 한번에 업로드
	  @PostMapping("/multi/upload")
	  public ResponseEntity<ResponseMessage> multiUploadFile(List<MultipartFile> files) { 
		  
		  String message = "";
		  
		  if(files==null) {
			  message = "No Files Available Now";
			  return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,null,true));
		  }
		  
		  List<String> names = new ArrayList<String>();
		  List<String> directoryPaths = new ArrayList<String>();
		  Map<String,Object> map = new ConcurrentHashMap<String,Object>();
		  
		  //폴더 있는지 확인함
		  int result = storageService.checkIfFolderExists();
		  //폴더 없으면 폴더 만듦
		  if(result == 0) {
			  storageService.initFolder();
		  }
		  
		  for(MultipartFile file:files) {
			 String path = storageService.save(file);
			 names.add(file.getOriginalFilename());
			 directoryPaths.add(path);
		  }
		  if(names.isEmpty()) {
			 message = "File Upload Failed";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,map,false));
	      }
		  
			 map.put("names", names);
			 map.put("directoryPaths", directoryPaths);

			 message = "File Uploaded Successfully";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,map,true));
		 
		 
	  }

}
