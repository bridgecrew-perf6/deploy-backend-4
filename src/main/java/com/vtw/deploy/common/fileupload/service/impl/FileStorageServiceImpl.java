package com.vtw.deploy.common.fileupload.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vtw.deploy.common.config.FileUploadConfig;
import com.vtw.deploy.common.fileupload.service.FileStorageService;
import com.vtw.deploy.script.config.DevelopServerConfig;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	
	@Autowired
	private FileUploadConfig config;
	
	@Autowired
	private DevelopServerConfig developConfig;
	
	//파일 업로드한 것 저장하고 싶은 폴더 path 지정
	private Path root = Paths.get("C:\\uploads"); //get() static method is used to create a Path.
	//private Path root2 = Paths.get(config.getPath()); => 이렇게 하면 bean 생성 에러 뜸
	//A Path can be created as an absolute or relative.
	//The following method can also be used to get a path: 
	//Path root3 = FileSystems.getDefault().getPath(config.getPath());
	
//	private Path zipRoot = Paths.get("/C:/data/deploy-project/deployment");
	
	private Path zipRoot = Paths.get("C:\\data/deploy-project/deployment");
	
	//yml 파일에서 파일 경로 얻어오는 메서드
	@Override
	public Path selectUploadPath() {
		
		return Paths.get(config.getPath());
	}
	
	@Override
	public Path selectZipPath() {		
		return Paths.get(developConfig.getDeployedSources());
	}
	
	//하위 폴더 용
	@Override
	public Path selectUploadPath(String name) {
		
		return Paths.get(config.getPath()+"\\"+name);//경로 구분 "\\"로 안해주면 에러 뜸. yml에서도 \\로 경로 구분함
	}
	
	//업로드 폴더 생성
	@Override
	public void initFolder() {
		
		Path root = this.selectUploadPath();
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
		  throw new RuntimeException("Could Not Initialize Folder For Upload!");
		}
	}
	
	//하위 폴더 용
	@Override
	public void initFolder(String name) {
		
		Path root = this.selectUploadPath(name);
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
		  throw new RuntimeException("Could Not Initialize Folder For Upload!");
		}
	}

	
	//업로드할 폴더 존재하는지 확인하는 메서드
	@Override
	public int checkIfFolderExists() {
		
		Path root = this.selectUploadPath();
		if(Files.notExists(root)) {
			return 0; //폴더 존재하지 않으면 0 리턴
		}else {
			return 1; //폴더 존재하면 1 리턴
		}
				
	}
	
	//하위 폴더 용
	@Override
	public int checkIfFolderExists(String name) {

		int result = this.checkIfFolderExists();
		
		if(result == 0) {
			this.initFolder();
		}
		
		Path root = this.selectUploadPath(name);
		if(Files.notExists(root)) {
			return 0; //폴더 존재하지 않으면 0 리턴
		} else {
			return 1; //폴더 존재하면 1 리턴
		}
	}
	
	//파일 업로드
	@Override
	  public String save(MultipartFile file) {
		Path root = this.selectUploadPath();
	    try {
	        Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);//똑같은 파일 존재하면 대체하도록 수정함
	        return root.toString();
	        
	    } catch (Exception e) {
	      throw new RuntimeException("Could Not Store The File. Error: " + e.getMessage());
	    }
	}
	
	//파일 업로드 하위 폴더 용
	@Override
	  public String save(MultipartFile file, String name) {
		Path root = this.selectUploadPath(name);
		try {
		     Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);//똑같은 파일 존재하면 대체하도록 수정함
		     return root.toString();
		        
		} catch (Exception e) {
		  throw new RuntimeException("Could Not Store The File. Error: " + e.getMessage());
		}
	}
	
	//파일 다운로드
	@Override
	public Resource selectOneFile(String filename) {
		
		Path root = this.selectUploadPath();
		
		try {
			Path file = root.resolve(filename);
		    Resource resource = new UrlResource(file.toUri());

		    if (resource.exists() || resource.isReadable()) {
		    return resource;
		    
		    } else {
		      throw new RuntimeException("Could Not Read The File!");
		    }
		} catch (MalformedURLException e) {
		  throw new RuntimeException("Error: " + e.getMessage());
		}
	}
	
	//zipdownload
	@Override
	public Resource selectZipFile(String filename) {		
		Path zipRoot = this.selectZipPath();
		
		try {
			Path file = zipRoot.resolve(filename);
		    Resource resource = new UrlResource(file.toUri());

		    if (resource.exists() || resource.isReadable()) {
		    return resource;
		    
		    } else {
		      throw new RuntimeException("Could Not Read The File!");
		    }
		} catch (MalformedURLException e) {
		  throw new RuntimeException("Error: " + e.getMessage());
		}
	}
	
	//파일 다운로드 하위 폴더 용
	@Override
	public Resource selectOneFile(String filename, String name) {
			
		Path root = this.selectUploadPath(name);
		
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
			return resource;
			    
			} else {
			   throw new RuntimeException("Could Not Read The File!");
			}
		} catch (MalformedURLException e) {
		  throw new RuntimeException("Error: " + e.getMessage());
		}
	}
	
	
	//업로드 폴더 내 파일 삭제
	@Override
	public void deleteFileList() {
		
		Path root = this.selectUploadPath();
		FileSystemUtils.deleteRecursively(root.toFile());
		
	}
	//업로드 폴더 내의 파일 불러오기
	@Override
	public Stream<Path> selectFileList() {
		
		Path root = this.selectUploadPath();
		
		//폴더 존재하지 않을 때 새로 폴더 만들도록 함.
		if(0 == this.checkIfFolderExists()) {
			this.initFolder();
		}
		
		try {
			return Files.walk(root, 1).filter(path -> !path.equals(root)).map(root::relativize);
			
		} catch (IOException e) {
			throw new RuntimeException("Could Not Load The Files");
		}
	}
	
	//업로드 폴더 내의 파일 불러오기
	@Override
	public Stream<Path> selectFileList(String name) {
			
		Path root = this.selectUploadPath(name);
			
		//폴더 존재하지 않을 때 새로 폴더 만들도록 함.
		if(0 == this.checkIfFolderExists(name)) {
			this.initFolder(name);
		}
			
		try {
			return Files.walk(root, 1).filter(path -> !path.equals(root)).map(root::relativize);
				
		} catch (IOException e) {
			throw new RuntimeException("Could Not Load The Files");
		}
	}

	
	

}
