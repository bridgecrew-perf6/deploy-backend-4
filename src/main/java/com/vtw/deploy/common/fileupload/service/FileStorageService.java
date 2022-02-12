package com.vtw.deploy.common.fileupload.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


/**
 	 @author 정진하
 	 @Description 파일 저장한 폴더 초기화, 업로드한 파일 저장, 파일로드, 파일 리스트 정보 얻어오기, 지정폴더에서 파일 전부 삭제
	 			  init, deleteFileList는 업로드 파일 저장 폴더의 초기화 및 파일 삭제가 필요할 때 사용하시면 되는 메서드로,
		          DeployProjectApplication.java 파일에 코드 주석처리 해놓았습니다.
*/
public interface FileStorageService {
	  
	  
	  
	  public void initFolder();
	  
	  public void initFolder(String name);
	  
	  public int checkIfFolderExists();
	  
	  public int checkIfFolderExists(String name);
	  
	  public Path selectUploadPath();
	  
	  public Path selectUploadPath(String name);

	  public String save(MultipartFile file);
	  
	  public String save(MultipartFile file, String name);

	  public Resource selectOneFile(String filename);
	  
	  public Resource selectOneFile(String filename, String name);

	  public void deleteFileList();

	  public Stream<Path> selectFileList();
	  
	  public Stream<Path> selectFileList(String name);
	  
	  
	  public Resource selectZipFile(String filename);
	  
	  public Path selectZipPath();

}
