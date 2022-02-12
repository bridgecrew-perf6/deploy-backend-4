package com.vtw.deploy.defaultpath.service;

import java.util.List;

import com.vtw.deploy.defaultpath.dto.DefaultPathDTO;
import com.vtw.deploy.defaultpath.dto.DefaultPathFormDTO;

public interface DefaultPathService {
	
	List<DefaultPathDTO> parseDefaultPathForm(DefaultPathFormDTO defaultPathForm);
	
	int updateDefaultPathList(DefaultPathFormDTO defaultPathForm);
	
	int updateDefaultPath(DefaultPathDTO defaultPath);
	
	List<DefaultPathDTO> selectAllDefaultPath();

}
