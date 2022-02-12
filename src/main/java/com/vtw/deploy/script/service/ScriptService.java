package com.vtw.deploy.script.service;


import java.util.List;
import java.util.Map;

import com.vtw.deploy.script.dto.ScriptDTO;
import com.vtw.deploy.script.exception.ScriptException;

public interface ScriptService {
	
	ScriptDTO addDirectoryToScript(ScriptDTO script, int num) throws ScriptException;
	
	String createCommand(ScriptDTO scriptDTO) throws Exception;
	
	ScriptDTO addAllOperationScripts(ScriptDTO script) throws Exception;
	
	List<ScriptDTO> selectScriptList(int deployNo);
	
	void copyXmlFile(List<Map<String, String>> xmlCommandList) throws ScriptException;
	
	int insertScriptList(List<ScriptDTO> scriptList,String timeStamp , int deployNo) throws Exception;
	
	Map<String, String> copyFileService(int deployNo) throws Exception;
	
}
