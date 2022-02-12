package com.vtw.deploy.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.vtw.deploy.user.dto.UserDTO;

@Mapper
public interface UserRepository {
	
	public int insertUser(UserDTO user);
	
	public int selectCheckId(String id);
	
	public UserDTO selectLogin(String id);
	
	public int updateUser(UserDTO user);
	
	public String selectPassword(UserDTO user);
	
	public int updatePassword(UserDTO user);
	
	public List<UserDTO> selectId(String email);
	
	public int selectCount(UserDTO user);

	@Select("SELECT * FROM MEMBER")
	public List<UserDTO> selectUserList();
	
	public int updateLoginToken(String id);
	
	public int updateLogoutToken(String id);
	
	public int insertLogin(String id);
	
	public String selectLoginConfirm(String id);
	
	public int insertLogout(String id);
}
