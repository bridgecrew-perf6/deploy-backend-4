package com.vtw.deploy.user.service.impl;



import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vtw.deploy.user.dto.UserDTO;
import com.vtw.deploy.user.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

	private static final String ENCRYPT_STRING =  "secret";
	private static final Logger LOGGER  = LoggerFactory.getLogger(JwtService.class);
	private static final String DATA_KEY = "user";
	
	@Override
	public String createLoginToken(UserDTO user) {
		
		long curTime = System.currentTimeMillis();
		return  Jwts.builder()
				 .setHeaderParam("typ", "JWT")
				 .setExpiration(new Date(curTime + 360000))
				 .setIssuedAt(new Date(curTime))
				 .claim(DATA_KEY, user)
				 .signWith(SignatureAlgorithm.HS256, this.generateKey())
				 .compact();
	}
	
	private byte[] generateKey(){
		byte[] key = null;
		try {
			key = ENCRYPT_STRING.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Making secret Key Error :: ", e);
		}
		
		return key;
	}
	

	
}
