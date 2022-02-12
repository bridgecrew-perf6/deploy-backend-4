package com.vtw.deploy.script.dto;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vtw.deploy.defaultpath.dto.DefaultPathDTO;
import com.vtw.deploy.script.exception.ScriptException;



public class ScriptDTO {
	
	private int scriptNo;
	private int deployNo;
	private String category;
	private String fileType;
	private String localPath;
	private String directoryPath;
	private String backupScript;
	private String reflectScript;
	private String rollbackScript;
	private String jarScript;
	
	//진영 write
	private String centerScript;
	private String portalScript;
	private String tbwappScript;
	
	private String deployTitle;
	private String deployContent;
	private Date fileDate;
	//진영 end
	
	private String fileName;
	private String usedPath;
	private String timeStamp;
	private String prdTmpDirectory;
	private String operationDirectory;
	
	@JsonIgnore
	public final String TARGET_PATH = "target/classes";
	
	@JsonIgnore
	public final String MKDIR_COMMAND = "mkdir -p ";

	public ScriptDTO() {

	}

	public Date getFileDate() {
		return fileDate;
	}

	
	public String getDeployTitle() {
		return deployTitle;
	}

	public void setDeployTitle(String deployTitle) {
		this.deployTitle = deployTitle;
	}

	public String getDeployContent() {
		return deployContent;
	}

	public void setDeployContent(String deployContent) {
		this.deployContent = deployContent;
	}

	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

	@JsonIgnore
	public int getScriptNo() {
		return scriptNo;
	}


	public void setScriptNo(int scriptNo) {
		this.scriptNo = scriptNo;
	}

	@JsonIgnore
	public int getDeployNo() {
		return deployNo;
	}


	public void setDeployNo(int deployNo) {
		this.deployNo = deployNo;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public String getLocalPath() {
		return localPath;
	}


	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}


	public String getDirectoryPath() {
		return directoryPath;
	}


	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}


	public String getBackupScript() {
		return backupScript;
	}


	public void setBackupScript(String backupScript) {
		this.backupScript = backupScript;
	}


	public String getReflectScript() {
		return reflectScript;
	}


	public void setReflectScript(String reflectScript) {
		this.reflectScript = reflectScript;
	}


	public String getRollbackScript() {
		return rollbackScript;
	}


	public void setRollbackScript(String rollbackScript) {
		this.rollbackScript = rollbackScript;
	}


	public String getJarScript() {
		return jarScript;
	}


	public void setJarScript(String jarScript) {
		this.jarScript = jarScript;
	}


	public String getCenterScript() {
		return centerScript;
	}


	public void setCenterScript(String centerScript) {
		this.centerScript = centerScript;
	}


	public String getPortalScript() {
		return portalScript;
	}


	public void setPortalScript(String portalScript) {
		this.portalScript = portalScript;
	}


	public String getTbwappScript() {
		return tbwappScript;
	}


	public void setTbwappScript(String tbwappScript) {
		this.tbwappScript = tbwappScript;
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public String getUsedPath() {
		return usedPath;
	}

	public void setUsedPath(String usedPath) {
		this.usedPath = usedPath;
	}
	

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	

	public String getPrdTmpDirectory() {
		return prdTmpDirectory;
	}

	public void setPrdTmpDirectory(String prdTmpDirectory) {
		this.prdTmpDirectory = prdTmpDirectory;
	}

	public String getOperationDirectory() {
		return operationDirectory;
	}

	public void setOperationDirectory(String operationDirectory) {
		this.operationDirectory = operationDirectory;
	}

	public ScriptDTO(String category, String localPath) {
		this.category = category;
		this.localPath = localPath;
	}

	public ScriptDTO(String category, String fileType, String localPath) {

		this.category = category;
		this.fileType = fileType;
		this.localPath = localPath;
	}
	
	@JsonIgnore
	public String[] getSplitByDot() {
		
		String[] splitByDot = localPath.split("\\.");
		
		return splitByDot;
	}
	
	@JsonIgnore
	public Boolean isJava() {
		
		if(fileType.equals("java")) {
			return true;
		}
		return false;
	}
	
	@JsonIgnore
	public Boolean isXml() {
		
		if(fileType.equals("xml")) {
			return true;
		}
		return false;
	}
	
	@JsonIgnore
	public Boolean isXlsx() {
		
		if(fileType.equals("xlsx")) {
			return true;
		}
		return false;
	}
	
	public void setUsedPathToJavaType() {
		
		usedPath = splitJavaPath(getSplitByDot()[0]);
	}
	
	public void setUsedPathToXmlType() {
		
		if(localPath.contains("src")) {
			usedPath = localPath.split("src")[1];
		} else {
			usedPath = localPath;
		}
	}
	
	public void setUsedPathToXlsxType() {
		
		if(localPath.contains("/webapp/WEB-INF")) {
			usedPath = localPath.split("WEB-INF")[1];
		} else if (localPath.contains("/resources/egovframework")) {
			usedPath = localPath.replace("resources", "classes").split("main")[1];
		}
	}
	
	@JsonIgnore
	public void setFileNameByPath() {
		
		String[] splitBySlash = localPath.split("/");
		fileName = splitBySlash[splitBySlash.length - 1];
		
	}
	
	@JsonIgnore
	public String splitJavaPath(String splitPath) {
		
		String javaPath = "";
		if (category.equals("portal") || category.equals("center")) {
			
			javaPath = splitPath.split("src/main/java")[1];
			
		} else if (category.equals("tbwapp")) {
			
			javaPath = splitPath.split("src")[1];
			
		}
		
		return javaPath + ".class";
	}
	
	public int getCategoryIndex(int portalNo, int tbwNo, int centerNo) {
		
		int no = 0 ;
		
		if (!fileType.equals("xml")) {
			
			if (category.equals("portal")) {
				
				no = portalNo++;
				
			} else if (category.equals("tbwapp")) {
				
				no = tbwNo++;
				
			} else if (category.equals("center")) {
				
				no = centerNo++;
			}
			
		} else if (fileType.equals("xml")) {
			
			no = -1;
		}
	
		return no;      
	}
	
	@JsonIgnore
	public Map<String,String> getSuffixForDirectory() {
		
		String prefix = "/" + timeStamp;
		String suffix = prefix + "/" + category;
		String fileSuffix = "/" + fileName;
		String xmlSuffix = prefix + "_" + deployNo + "/" + category;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("suffix", suffix);
		map.put("fileSuffix", fileSuffix);
		map.put("xmlSuffix", xmlSuffix);
		
		return map;
	}
	
	@JsonIgnore
	public Boolean isFileNameNull() {
		
		if(fileName==null) {
			
			return true;	
		}
		return false;
	}
	
	@JsonIgnore
	public DefaultPathDTO getDefaultPathByCategoryAndFileType() {
		
		return new DefaultPathDTO(category, fileType);
	}
	
	public void setOperationDirectoryByOperationPath(String operationPath) throws ScriptException{
		
		if (fileType.equals("jsp") || fileType.equals("js") || fileType.equals("css")) {
			
			usedPath = localPath.split("webapp")[1];
			
		} else if (fileType.equals("java") || fileType.equals("xml") || fileType.equals("xlsx")){
			
			
		} else {
			throw new ScriptException("구현되지 않은 fileType이 포함되어 있습니다. (java, js, jsp, xml, css, xlsx만 가능");
		}
		
		operationDirectory =  operationPath + usedPath;
	}
	
	public void setAllScripts() {
		
		String backUpDirectory = operationDirectory + "." + timeStamp;
		
		if(fileType.equals("java")) {
			prdTmpDirectory = prdTmpDirectory.replace(".java", ".class");
		}
		
		reflectScript = "cp " + prdTmpDirectory + " " + operationDirectory;
		backupScript = "cp " + operationDirectory + " " + backUpDirectory;
		rollbackScript = "mv " + backUpDirectory + " " + operationDirectory;
		
	}
	
	@JsonIgnore
	public Boolean isPortal() {
		if(category.equals("portal")) {
			return true;
		}
		return false;
	}
	
	@JsonIgnore
	public Boolean isTbwapp() {
		if(category.equals("tbwapp")) {
			return true;
		}
		return false;
	}
	
	@JsonIgnore
	public Boolean isCenter() {
		if(category.equals("center")) {
			return true;
		}
		return false;
	}
	
	public String splitLocalPathBySrc() {
		return localPath.split("src")[1];
	}
	
	@JsonIgnore
	public String splitDirectoryPathByMkdir() { 
		return directoryPath.split(MKDIR_COMMAND)[1];
	}
	
	@JsonIgnore
	public String getPathForCommand() {
		return TARGET_PATH + usedPath;
	}
	
	public void addMkdirCommandToMap(Map<String, String> map) {
		
		map.put("mkdirCommand", directoryPath);	
	}
	
	public void addXmlDstAndXmlServerDirToMap(Map<String, String> map , String xmlSource) {
		
		map.put("xmlDst", directoryPath + "/" + fileName);
		map.put("XmlServerDir", xmlSource +"/"+fileName);
	}
	
}
