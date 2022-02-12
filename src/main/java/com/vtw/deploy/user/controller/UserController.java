package com.vtw.deploy.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtw.deploy.common.fileupload.message.ResponseMessage;
import com.vtw.deploy.user.dto.UserDTO;
import com.vtw.deploy.user.service.JwtService;
import com.vtw.deploy.user.service.UserService;
import com.vtw.deploy.user.util.CryptoUtil;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private CryptoUtil cryptoUtil;
	
	//회원가입페이지 팀/직급 리스트
	@GetMapping("/register")
	public ResponseEntity<ResponseMessage> selectTeamList() {
		String message ="Successfully Fetched Team List";
		Map<String,Object> map = service.selectTeamList();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, map, true));
	}
	
	//회원가입
	@PostMapping
	public ResponseEntity<ResponseMessage> insertUser(@RequestBody UserDTO user) {
		String message = "";
		
		user.setId(cryptoUtil.decrypt(user.getId(), "secret"));
		user.setPassword(cryptoUtil.decrypt(user.getPassword(), "secret"));
		
		if(service.insertUser(user) == true) {
			message = "New User Created";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, user, true));
		} else {
			message = "Failed To Create New User";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, user, false));
		}
	}
	
	//아이디 중복체크
	@GetMapping("/check/{id}")
	public ResponseEntity<ResponseMessage> checkId(@PathVariable String id) {
		String message = "";
		if(service.selectCheckId(id) == false) {
			message = "This Id Is Already In Use";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,false,false));
		} else {
			message = "New User Can Use This Id";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, true, true));
		}
	}
	
	//로그인
	@Transactional
	@PostMapping("/session")
	public ResponseEntity<ResponseMessage> login(@RequestBody UserDTO user) {
		String token = null;
		
		user.setId(cryptoUtil.decrypt(user.getId(), "secret"));
		user.setPassword(cryptoUtil.decrypt(user.getPassword(), "secret"));
		
		UserDTO loginUser = service.login(user);
		if(loginUser != null) {
			//String status = service.selectLoginConfirm(user.getId());
		//	if(status == null || status.equals("LOGOUT")) {
				//로그인성공
				token = jwtService.createLoginToken(loginUser);
				service.insertLogin(user.getId());
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Succssfully Login", token, true));
			//}else {
				//중복로그인
			//	token = "signed";
			//	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("This Id Has Been Signed In", token, false));
			//}	
		}else {
			//아이디비번 다름
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Failed Login", null, false));
		}
		
	}
	
	//유저 정보 수정
	@PatchMapping
	public ResponseEntity<ResponseMessage> updateUser(@RequestBody UserDTO user){
		String message ="";
		if(service.updateUser(user) > 0) {
			message="Successfully Updated User";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,true,true));
		}else {
			message="Failed To Update User";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,false,false));
		}
		
	}
	
	//유저 비밀번호 체크
	@PostMapping("/check")
	public ResponseEntity<ResponseMessage> checkPassword(@RequestBody UserDTO user){
		String message ="";
		if(service.checkPassword(user)) {
			message="Successfully Checked Password";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,true,true));
		}else {
			message="Failed To Check Password";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,false,false));
		}
	}
	
	//유저 비밀번호 변경
	@PatchMapping("/password")
	public ResponseEntity<ResponseMessage> updatePassword(@RequestBody UserDTO user){
		String message = "";
		
		user.setPassword(cryptoUtil.decrypt(user.getPassword(), "secret"));
		
		if(service.updatePassword(user) > 0) {
			message="Successfully Updated Password";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,true,true));
		}else {
			message="Failed To Update Password";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,false,false));
		}
		
	}
	
	//아이디 찾기
	@GetMapping("/find")
	public ResponseEntity<ResponseMessage> findId(@RequestParam String email){
		String message="";
		List<UserDTO> id=service.selectId(email);
		if(id != null) {
			message="Successfully Found ID";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,id,true));
		}else {
			message="Failed To Find ID";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,null,false));
		}
	}
	
	//비밀번호 찾기
	@GetMapping("/find/password")
	public ResponseEntity<ResponseMessage> findPassword(@RequestParam String email, @RequestParam String id){
		String message="";
		
		if(service.selectCount(id, email) > 0) {
			message="Successfully Found Password";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,id,true));
		}else {
			message="Faild To Find Password";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,null,false));
		}
	}
	
	//유저 리스트
	@GetMapping
	public ResponseEntity<ResponseMessage> selectUserList() {
		String message ="Successfully Fetched User List";
		List<UserDTO> userList = service.selectUserList();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, userList, true));
	}
	
	@PatchMapping("/session")
	public ResponseEntity<ResponseMessage> logout(@RequestParam String id){
		String message="";
		if(service.insertLogout(id)>0) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, id, true));
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, id, false));
		}
	}
}
