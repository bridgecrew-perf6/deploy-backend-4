package com.vtw.deploy.script.util;

import java.util.Properties;


import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/*
 * 10.47.39.126,125 서버와 연결하기 위한 jschSession을 생성하는 creaetSession 메서드
 * 
 * @author 이성정
 */
@Component
public class JschSessionUtil {
	
	public final int SSH_PORT = 22;
	
	public Session createSession(String host, String username, String password) throws JSchException {
		
		Session session = null;
		
		try {
		//1.Jsch 객체를 생성한다.
		JSch jsch = new JSch();

		//2.세션 객체 생성하기 (인자 : 사용자 이름, 접속할 호스트 , 포트)
		session  = jsch.getSession(username, host, SSH_PORT);
		
		//3.비밀번호 설정
		session.setPassword(password);
		
		//4.세션관련 정보를 설정한다.
		Properties config = new Properties();
		//4-1. 호스트 정보를 검사하지 않는다.
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		
		//5.접속
		session.connect();
		}catch (Exception e) {
			throw new JSchException("session 객체를 생성하는데에 실패하였습니다.");
		}
		return session;
	}
	

	
}
