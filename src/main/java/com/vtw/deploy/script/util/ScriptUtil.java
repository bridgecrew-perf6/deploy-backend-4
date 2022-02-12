package com.vtw.deploy.script.util;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.vtw.deploy.script.dto.ScriptDTO;

/*
 * script를 생성하여 db에 넣기 위한 메서드들이 포함되어있다.
 * 
 * @author 이성정
 */
@Component
public class ScriptUtil {

	public ScriptDTO addInfoToScript(ScriptDTO script) {

		String[] splitByDot = script.getSplitByDot();
		String fileType = splitByDot[1];
		script.setFileType(fileType);
		script.setFileNameByPath();
	
		// fileType이 java인경우 추가처리
		if (script.isJava()) {			
			script.setUsedPathToJavaType();
		} else if (script.isXml()) {
			script.setUsedPathToXmlType();
		} else if (script.isXlsx()) {
			script.setUsedPathToXlsxType();
		}
		return script;
	}

	public String createTimeStampStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStamp = format.format(date);
		return timeStamp;
	}
}
