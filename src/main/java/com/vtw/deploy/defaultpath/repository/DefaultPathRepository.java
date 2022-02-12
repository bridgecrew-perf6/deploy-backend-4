package com.vtw.deploy.defaultpath.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.vtw.deploy.defaultpath.dto.DefaultPathDTO;

@Mapper
public interface DefaultPathRepository {

	@Select("SELECT path FROM DEFAULT_PATH WHERE id='developTmp' ")
	String selectDevelopTmp();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='prdTmp' ")
	String selectPrdTmp();

	@Select("SELECT path FROM DEFAULT_PATH WHERE category=#{category} and file_type = #{fileType} ")
	String selectOperationPath(DefaultPathDTO lookUpVo);

	@Select("SELECT path FROM DEFAULT_PATH WHERE id='developPortal' ")
	String selectDevelopPortal();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='developCenter' ")
	String selectDevelopCenter();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='developTbwapp' ")
	String selectDevelopTbwapp();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='centerJava' ")
	String selectCenterJava();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='centerXml' ")
	String selectCenterXml();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='portalJava' ")
	String selectPortalJava();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='portalJs' ")
	String selectPortalJs();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='portalJsp' ")
	String selectPortalJsp();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='portalXml' ")
	String selectPortalXml();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='tbwappJava' ")
	String selectTbwappJava();
	
	@Select("SELECT path FROM DEFAULT_PATH WHERE id='tbwappXml' ")
	String selectTbwappXml();
	
	int updateDefaultPath(DefaultPathDTO defaultPath);
	
	@Select("SELECT id,path FROM DEFAULT_PATH")
	List<DefaultPathDTO> selectAllDefaultPath();


	
}
