package com.vtw.deploy.user.service;

import com.vtw.deploy.user.dto.UserDTO;

public interface JwtService {

	public String createLoginToken(UserDTO user);
}
