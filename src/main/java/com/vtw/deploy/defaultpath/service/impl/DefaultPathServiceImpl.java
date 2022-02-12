package com.vtw.deploy.defaultpath.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vtw.deploy.defaultpath.dto.DefaultPathDTO;
import com.vtw.deploy.defaultpath.dto.DefaultPathFormDTO;
import com.vtw.deploy.defaultpath.enumerate.DefaultPath;
import com.vtw.deploy.defaultpath.repository.DefaultPathRepository;
import com.vtw.deploy.defaultpath.service.DefaultPathService;

@Service
public class DefaultPathServiceImpl implements DefaultPathService {

	@Autowired
	private DefaultPathRepository repository;

	@Override
	public List<DefaultPathDTO> parseDefaultPathForm(DefaultPathFormDTO defaultPathForm) {

		List<DefaultPathDTO> defaultPathList = new ArrayList<DefaultPathDTO>();

		DefaultPath[] values = DefaultPath.values();
		int count = DefaultPath.values().length;
		DefaultPathFormDTO originalDefaultPathForm = setAllDefaultPathToDTO();

		for (int i = 0; i < count; i++) {

			if (values[i].updateYN(defaultPathForm, originalDefaultPathForm) == false) {
				
				DefaultPathDTO defaultPath = new DefaultPathDTO();
				defaultPath.setProperty(values[i].getId(), values[i].getPath(defaultPathForm));
				defaultPathList.add(defaultPath);
				
			}
		}

		return defaultPathList;
	}

	@Override
	public int updateDefaultPath(DefaultPathDTO defaultPath) {

		return repository.updateDefaultPath(defaultPath);
	}

	@Override
	@Transactional
	public int updateDefaultPathList(DefaultPathFormDTO defaultPathForm) {

		int updateResult = 1;
		String user = defaultPathForm.getUser();
		Date date = new Date();
		try {
			for (DefaultPathDTO defaultPath : parseDefaultPathForm(defaultPathForm)) {
				defaultPath.setEditorId(user);
				defaultPath.setUpdateDate(date);
				updateResult = updateDefaultPath(defaultPath);
			}
		} catch (Exception e) {
			updateResult = 0;
		}

		return updateResult;
	}

	@Override
	public List<DefaultPathDTO> selectAllDefaultPath() {

		return repository.selectAllDefaultPath();
	}

	public DefaultPathFormDTO setAllDefaultPathToDTO() {

		DefaultPathFormDTO defaultPathForm = new DefaultPathFormDTO(); // db정보 담을 defaultPathFormDTO

		List<DefaultPathDTO> originalDefaultPathList = selectAllDefaultPath(); // db정보

		for (DefaultPathDTO origianlDefaultPath : originalDefaultPathList) {

			String id = origianlDefaultPath.getId();
			String path = origianlDefaultPath.getPath();

			if (id.equals("portalJava")) {
				defaultPathForm.setPortalJava(path);
			} else if (id.equals("portalJs")) {
				defaultPathForm.setPortalJs(path);
			} else if (id.equals("portalJsp")) {
				defaultPathForm.setPortalJsp(path);
			} else if (id.equals("portalXml")) {
				defaultPathForm.setPortalXml(path);
			} else if (id.equals("tbwappJava")) {
				defaultPathForm.setTbwappJava(path);
			} else if (id.equals("tbwappXml")) {
				defaultPathForm.setTbwappXml(path);
			} else if (id.equals("centerJava")) {
				defaultPathForm.setCenterJava(path);
			} else if (id.equals("centerXml")) {
				defaultPathForm.setCenterXml(path);
			} else if (id.equals("prdTmp")) {
				defaultPathForm.setPrdTmp(path);
			} else if (id.equals("developCenter")) {
				defaultPathForm.setDevelopCenter(path);
			} else if (id.equals("developPortal")) {
				defaultPathForm.setDevelopPortal(path);
			} else if (id.equals("developTbwapp")) {
				defaultPathForm.setDevelopTbwapp(path);
			} else if (id.equals("developTmp")) {
				defaultPathForm.setDevelopTmp(path);
			}

		}

		return defaultPathForm;
	}

}
