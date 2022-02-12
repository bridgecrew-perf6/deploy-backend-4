package com.vtw.deploy.script.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.vtw.deploy.script.dto.ScriptDTO;

@Mapper
public interface ScriptRepository {
	
	//script serviceimple
	 @Select("SELECT * FROM SCRIPT WHERE DEPLOY_NO = #{deployNo}")
	 List<ScriptDTO> selectScriptList(int deployNo);
	 
	 
	 //deploy serviceimple
	 int insertScript(ScriptDTO script);
	 
	 public List<ScriptDTO> selectScriptDetail(int deployNo);	
}
