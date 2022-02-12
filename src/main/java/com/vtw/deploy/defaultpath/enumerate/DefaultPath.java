package com.vtw.deploy.defaultpath.enumerate;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import com.vtw.deploy.defaultpath.dto.DefaultPathFormDTO;
import com.vtw.deploy.defaultpath.repository.DefaultPathRepository;

public enum DefaultPath {
	
	PORTAL_JAVA(1,"portalJava", value -> value.getPortalJava()),
	PORTAL_JS(2,"portalJs", value -> value.getPortalJs()),
	PORTAL_JSP(3,"portalJsp", value -> value.getPortalJsp()),
	PORTAL_XML(4,"portalXml", value -> value.getPortalXml()),
	
	TBWAPP_JAVA(5,"tbwappJava", value -> value.getTbwappJava()),
	TBWAPP_XML(6, "tbwappXml", value -> value.getTbwappXml()),
	
	CENTER_JAVA(7, "centerJava", value -> value.getCenterJava()),
	CENTER_XML(8, "centerXml", value -> value.getCenterXml()),
	
	DEVELOP_PORTAL(9, "developPortal" , value -> value.getDevelopPortal()),
	DEVELOP_TBWAPP(10, "developTbwapp", value -> value.getDevelopTbwapp()),
	DEVELOP_CENTER(11, "developCenter", value -> value.getDevelopCenter()),
	
	DEVELOP_TMP(12, "developTmp", value -> value.getDevelopTmp()),
	PRD_TMP(13, "prdTmp", value -> value.getPrdTmp());
	
	@Autowired
	private DefaultPathRepository repository;

	private int no;
	private String id;
	private Function<DefaultPathFormDTO,String> expression;

	private DefaultPath(int no, String id , Function<DefaultPathFormDTO,String> expression) {
		
		this.no = no;
		this.id = id;
		this.expression = expression;
	}
	
	public int getNo() {
		return no;
	}
	
	public String getId() {
		return id;
	}
	
	public Function<DefaultPathFormDTO,String> getExpression() {
		return expression;
	}
	
	public String getPath(DefaultPathFormDTO value) {
		return expression.apply(value);
	}
	
	public Boolean updateYN(DefaultPathFormDTO inputValue, DefaultPathFormDTO originalValue) {
		
		String inputPath = expression.apply(inputValue);
		String originalPath = expression.apply(originalValue);
		
		return inputPath.equals(originalPath) ? true : false;
	}
	
}
