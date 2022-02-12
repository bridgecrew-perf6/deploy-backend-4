package com.vtw.deploy.user.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vtw.deploy.common.codemgmt.repository.CodeMgmtRepository;
import com.vtw.deploy.user.dto.UserDTO;
import com.vtw.deploy.user.repository.UserRepository;
import com.vtw.deploy.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CodeMgmtRepository codeRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public boolean insertUser(UserDTO user) {
		
		//비밀번호 암호화
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//회원가입
		return 1==userRepository.insertUser(user);
	}
	@Override
	public boolean selectCheckId(String id) {
		
		//아이디 중복체크
		return 0==userRepository.selectCheckId(id);
	}
	
	@Override
	public UserDTO login(UserDTO user) {
		
		//로그인한 아이디 정보 불러오기
		UserDTO loginUser = userRepository.selectLogin(user.getId());
		
		if(loginUser == null) {
			return null;
		}
		
		//로그인 비번 확인
		boolean check = passwordEncoder.matches(user.getPassword(), loginUser.getPassword());
		
		if(check) {
			return loginUser;
		}else {
			return null;
		}
		
	}
	
	@Override
	public Map<String, Object> selectTeamList() {
		
		Map<String,Object> map = new ConcurrentHashMap<String, Object>();
		//팀 리스트
		map.put("team", codeRepository.selectTeamList());
		//직급 리스트
		map.put("position", codeRepository.selectPositionList());
		return map;
	}
	
	@Override
	public int updateUser(UserDTO user) {
		
		//회원정보 수정
		return userRepository.updateUser(user);
	}
	
	@Override
	public boolean checkPassword(UserDTO user) {
		
		//비밀번호 체크
		return passwordEncoder.matches(user.getPassword(), userRepository.selectPassword(user));
	}
	
	@Override
	public int updatePassword(UserDTO user) {
		
		//비밀번호 변경
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.updatePassword(user);
	}
	
	@Override
	public List<UserDTO> selectId(String email) {
		
		//이메일로 아이디찾기
		return userRepository.selectId(email);
	}
	
	@Override
	public int selectCount(String id, String email) {
		
		UserDTO user = new UserDTO();
		user.setId(id);
		user.setEmail(email);
		
		//비밀번호 찾기 (아이디/이메일로 확인)
		return userRepository.selectCount(user);
	}
	
	@Override
	public List<UserDTO> selectUserList() {
		
		//유저리스트
		return userRepository.selectUserList();
	}
	
	@Override
	public String selectLoginConfirm(String id) {
		
		//중복로그인 체크
		return userRepository.selectLoginConfirm(id);
	}
	
	@Override
	public int insertLogin(String id) {
		
		//로그인시
		return userRepository.insertLogin(id);
	}
	
	@Override
	public int insertLogout(String id) {
		
		//로그아웃시
		return userRepository.insertLogout(id);
	}
}
