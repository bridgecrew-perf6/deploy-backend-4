package com.vtw.deploy.common.codemgmt.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vtw.deploy.common.codemgmt.dto.CodeMgmtDTO;


@Mapper
public interface CodeMgmtRepository {
	
	public List<CodeMgmtDTO> selectParentCodeList();
	
	public List<CodeMgmtDTO> selectChildCodeList(String parentCodeId);

	public List<CodeMgmtDTO> selectTeamList();
	
	public List<CodeMgmtDTO> selectPositionList();
	
	public List<CodeMgmtDTO> selectSearchCodeList(Map<String, Object> map);
	
	public CodeMgmtDTO selectOneCodeByCodeId(String codeId);
	
	public int insertCode(CodeMgmtDTO codeMgmtDTO);
	
	public int updateCode(CodeMgmtDTO codeMgmtDTO);
	
	public int selectCheckParentDsplOrder(int dsplOrder);
	
	public int selectCheckChildDsplOrder(Map<String, Object> map);
	
	public int selectCheckCodeId(String codeId);
	
	public int selectCheckParentCodeId(String parentCodeId);
	
	public int deleteCode(String codeId);
	
	
}
