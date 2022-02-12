package com.vtw.deploy.user.service;

import java.util.List;
import java.util.Map;

import com.vtw.deploy.user.dto.UserDTO;

public interface UserService {

	public boolean insertUser(UserDTO user);
	
	public boolean selectCheckId(String id);
	
	public Map<String,Object> selectTeamList();
	
	public UserDTO login(UserDTO user);
	
	public int updateUser(UserDTO user);
	
	public boolean checkPassword(UserDTO user);
	
	public int updatePassword(UserDTO user);
	
	public List<UserDTO> selectId(String email);
	
	public int selectCount(String id, String email);

	public List<UserDTO> selectUserList();
	
	public String selectLoginConfirm(String id);
	
	public int insertLogin(String id);
	
	public int insertLogout(String id);
}
