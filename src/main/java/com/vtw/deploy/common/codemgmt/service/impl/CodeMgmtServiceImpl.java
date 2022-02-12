package com.vtw.deploy.common.codemgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtw.deploy.common.codemgmt.dto.CodeMgmtDTO;
import com.vtw.deploy.common.codemgmt.repository.CodeMgmtRepository;
import com.vtw.deploy.common.codemgmt.service.CodeMgmtService;


@Service
public class CodeMgmtServiceImpl implements CodeMgmtService {
	
	@Autowired
	private CodeMgmtRepository repository;

	@Override
	public List<CodeMgmtDTO> selectParentCodeList() {
		return repository.selectParentCodeList();
	}

	@Override
	public List<CodeMgmtDTO> selectChildCodeList(String parentCodeId) {
		return repository.selectChildCodeList(parentCodeId);
	}

	@Override
	public int insertCode(CodeMgmtDTO codeMgmtDTO) {
		return repository.insertCode(codeMgmtDTO);
	}

	@Override
	public CodeMgmtDTO selectOneCodeByCodeId(String codeId) {
		return repository.selectOneCodeByCodeId(codeId);
	}
	
	@Override
	public int selectCheckParentDsplOrder(int dsplOrder) {
		return repository.selectCheckParentDsplOrder(dsplOrder);
	}
	
	@Override
	public List<CodeMgmtDTO> selectSearchCodeList(String type, String keyword) {
		Map<String,Object> map = new ConcurrentHashMap<String, Object>();
		map.put("type", type);
		map.put("keyword", keyword);
		return repository.selectSearchCodeList(map);
	} 
	
	@Override
	public int selectCheckChildDsplOrder(int dsplOrder, String parentCodeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dsplOrder", dsplOrder);
		map.put("parentCodeId", parentCodeId);
		return repository.selectCheckChildDsplOrder(map);
	}
	
	@Override
	public int selectCheckCodeId(String codeId) {
		return repository.selectCheckCodeId(codeId);
	}
	
	@Override
	public int selectCheckParentCodeId(String parentCodeId) {
		return repository.selectCheckParentCodeId(parentCodeId);
	}

	@Override
	public int updateCode(CodeMgmtDTO codeMgmtDTO) {
		return repository.updateCode(codeMgmtDTO);
	}
	
	@Override
	public int deleteCode(String codeId) {
		//지우려는 코드가 부모 코드인 자식 코드들이 있는지 확인
		List<CodeMgmtDTO> list = repository.selectChildCodeList(codeId);
		if(list.isEmpty()) {//자식 코드가 없을 경우에만 삭제할 수 있음
			return repository.deleteCode(codeId);
		} else {
			return 0;
		}//if~else end 
	}

}
