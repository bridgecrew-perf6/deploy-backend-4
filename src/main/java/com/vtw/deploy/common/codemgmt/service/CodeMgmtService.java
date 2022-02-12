package com.vtw.deploy.common.codemgmt.service;

import java.util.List;

import com.vtw.deploy.common.codemgmt.dto.CodeMgmtDTO;


public interface CodeMgmtService {
	
	public List<CodeMgmtDTO> selectParentCodeList();
	
	public List<CodeMgmtDTO> selectChildCodeList(String parentCodeId);
	
	public List<CodeMgmtDTO> selectSearchCodeList(String type, String keyword);
	
	public CodeMgmtDTO selectOneCodeByCodeId(String codeId);
	
	public int insertCode(CodeMgmtDTO codeMgmtDTO);
	
	public int updateCode(CodeMgmtDTO codeMgmtDTO);
	
	public int selectCheckParentDsplOrder(int dsplOrder);
	
	public int selectCheckChildDsplOrder(int dsplOrder, String parentCodeId);
	
	public int selectCheckCodeId(String codeId);
	
	public int selectCheckParentCodeId(String parentCodeId);
	
	public int deleteCode(String codeId);
	
	

}
