package com.vtw.deploy.deploy.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vtw.deploy.common.codemgmt.repository.CodeMgmtRepository;
import com.vtw.deploy.common.fileupload.dto.FileDTO;
import com.vtw.deploy.common.repository.FileRepository;
import com.vtw.deploy.deploy.dto.DeployDTO;
import com.vtw.deploy.deploy.repository.DeployRepository;
import com.vtw.deploy.deploy.service.DeployService;
import com.vtw.deploy.script.dto.ScriptDTO;
import com.vtw.deploy.script.exception.ScriptException;
import com.vtw.deploy.script.repository.ScriptRepository;
import com.vtw.deploy.script.service.ScriptService;
import com.vtw.deploy.script.util.ScriptUtil;

@Service
public class DeployServiceImpl implements DeployService {

	@Autowired
	private DeployRepository deployRepository;

	@Autowired
	private ScriptRepository scriptRepository;

	@Autowired
	private FileRepository fileRepository;

	// 이성정 작성
	@Autowired
	private ScriptService scriptService;

	@Autowired
	private ScriptUtil scriptUtil;
	
	@Autowired
	private CodeMgmtRepository codeRepository;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	// 이성정 작성 end

	@Transactional(rollbackFor = ScriptException.class) //체크 예외는 commit처리된다 따라서 rollbackFor 속성지정필요
	public int insertDeploy(DeployDTO deploy) throws Exception {

		// ** 순서 지켜져야함
		// * 변수
		// 파일관련
		String[] names = deploy.getNames();
		String[] directoryPaths = deploy.getDirectoryPaths();
		
		if(deploy.getDeployContent() != null) {
			deploy.setContentBlob(deploy.getDeployContent().getBytes());	
		}
		
		// 시간관련
		Date deployDate = new Date();
		String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deployDate);
		deploy.setDeployDate(formatDate);
		String timeStamp = scriptUtil.createTimeStampStr(deployDate);

		int totalResult = 0;
		
		// 1. 배포이력저장
		deployRepository.insertDeploy(deploy);
		
		// insert 이후 return deployNo
		int deployNo = deploy.getDeployNo();

		// deployDTO의 ScriptDTO scripts setlocalpath
		for (ScriptDTO scripts : deploy.getScriptDTO()) {
			if (scripts.getPortalScript() != null) {
				scripts.setLocalPath(scripts.getPortalScript());
			} else if (scripts.getCenterScript() != null) {
				scripts.setLocalPath(scripts.getCenterScript());
			} else if (scripts.getTbwappScript() != null) {
				scripts.setLocalPath(scripts.getTbwappScript());
			}
		}

		// 2. 여러 파일이력저장
		if (names != null) {
			for (int i = 0; i < names.length; i++) {
				FileDTO file = new FileDTO(names[i], directoryPaths[i], deployNo);
				fileRepository.insertDeployFile(file);
			}
		}

		// 3.script 저장
		if (!deploy.getScriptDTO().isEmpty()) {		
			// 이성정 작성
			List<ScriptDTO> scriptList = deploy.getScriptDTO();
			try {
				scriptService.insertScriptList(scriptList, timeStamp, deployNo);
			} catch (Exception e) {
				throw new ScriptException("스크립트를 생성하고 db에 넣는 것에 실패하였습니다 e : " + e.getMessage());
			}
		}

		// 4.알집 업로드 및 다운로드
		FileDTO file = new FileDTO();
		if (!deploy.getScriptDTO().isEmpty()) {
			try {
				Map<String, String> zipFileInformMap = scriptService.copyFileService(deployNo);
				file = new FileDTO(zipFileInformMap.get("fileName"), zipFileInformMap.get("directory"), deployNo);
			}catch(Exception e) {
				throw new ScriptException("파일다운로드에 실패하였습니다 e: " + e.getMessage());
			}
		}

		// 5. zip 파일이력저장
		if (deploy.getScriptDTO().isEmpty()) {
		} else {
			fileRepository.insertDeployFile(file);
		}
		
		totalResult = 1;
		return totalResult;
	};

	@Override
	public List<ScriptDTO> selectScriptDetail(int deployNo) {
		return scriptRepository.selectScriptDetail(deployNo);
	};

	@Override
	public Map<String, Object> selectDeploySearch(String searchCategory, String keyword, String startDate, String endDate) {
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		
		map.put("deploys", deployRepository.selectDeploySearch(searchCategory, keyword, startDate, endDate));
		
		map.put("teamList", codeRepository.selectTeamList());
		
		return map;
	}

	@Override
	public Map<String, Object> selectDeployDetail(int deployNo) {

		// 배포이력상세
		DeployDTO deploy = deployRepository.selectDeployDetail(deployNo);
		List<FileDTO> files = fileRepository.selectDeployFiles(deployNo);
		String deployZip = deployRepository.selectDeployZip(deployNo);
		
		if(deploy.getContentBlob() != null) {
			String deployContent = new String(deploy.getContentBlob());
			deploy.setDeployContent(deployContent);				
		}
		
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		map.put("deploy", deploy);
		map.put("files", files);
		
		if(deployZip != null) {
			map.put("deployZip", deployZip);			
		} else {}

		return map;
	}

	@Override
	public FileDTO selectDeployFile(int deployNo) {
		return fileRepository.selectDeployFile(deployNo);
	}
	
	@Override
	@Transactional
	public int updateDeployState(DeployDTO deploy) {
		return deployRepository.updateDeployState(deploy);
	}
	
	@Override
	public Map<String,Object> selectTeamDeployList(String codeName){
		Map<String,Object> map = new ConcurrentHashMap<String, Object>();

		if(codeName.equals("전체")) {
			map.put("deploys", deployRepository.selectDeployDash());
		}else {
			//팀별 게시판리스트
			map.put("deploys", deployRepository.selectTeamDeployDash(codeName));
		}

		//팀리스트
		map.put("teamList", codeRepository.selectTeamList());
		return map;
	}
	
	@Override
	public int deleteDeploy(int deployNo) {
		return deployRepository.deleteDeploy(deployNo);
	}

	@Override
	public Map<String,Object> selectTeamDeploy(String codeName){
		Map<String,Object> map = new ConcurrentHashMap<String, Object>();

		if(codeName.equals("전체")) {
			map.put("deploys", deployRepository.selectDeployDash());
		}else {
			//팀별 게시판리스트
			map.put("deploys", deployRepository.selectTeamDeploy(codeName));
		}
		//팀리스트
		map.put("teamList", codeRepository.selectTeamList());
		return map;
	}
	
}
