package com.vtw.deploy.board.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtw.deploy.board.dto.BoardDTO;
import com.vtw.deploy.board.service.BoardService;
import com.vtw.deploy.common.fileupload.message.ResponseMessage;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;
	
	//공지사항 등록
	@PostMapping
	public ResponseEntity<ResponseMessage> insertBoard(@RequestBody BoardDTO board){

		int result = service.insertNotice(board);
		String message = "";
		if(result > 0) {
			message="Post Registered Successfully";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,board.getBoardNo(),true));
		}else {
			message="Posting Failed";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, result, false));
		}
	
	}
	
	//공지사항 리스트
	@GetMapping
	public ResponseEntity<ResponseMessage> selectNotice(){
		String message = "Successfully Fetched Noticelist";
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,service.selectNotice(),true));
	}
	
	//공지사항 상세내용
	@GetMapping("/{boardNo}")
	public ResponseEntity<ResponseMessage> selectNoticeDetail(@PathVariable int boardNo){
		 Map<String,Object> map = service.selectNoticeDetail(boardNo);
		 String message = "";
		 if(map != null) {
			 message="Successfully Fetched Notice";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,map,true));
		 }else {
			 message="Failed Fetched Noticelist";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, map, false));
		 }
	}
	
	//공지사항 글 삭제
	@DeleteMapping("/{boardNo}")
	public ResponseEntity<ResponseMessage> deleteNotice(@PathVariable int boardNo){
		 int result = service.deleteNotice(boardNo);
		 String message = "";
		 if(result > 0) {
			 message="Successfully Delete Notice";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,result,true));
		 }else {
			 message="Failed Delete Notice";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, result, false));
		 }
	}
	
	//공지사항 글 수정
	@PatchMapping("/{boardNo}")
	public ResponseEntity<ResponseMessage> updateNotice(@RequestBody BoardDTO boardDTO,@PathVariable int boardNo){
		String message = "";
		int result = service.updateNotice(boardDTO, boardNo);
		if(result > 0) {
			message = "Successfully Update Noticelist";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,boardNo,true));
		}else {
			message = "Failed Update Noticelist";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,boardNo,false));
		}
	}
	
	//공지사항 검색
	@GetMapping("/search")
	public ResponseEntity<ResponseMessage> searchNotice(@RequestParam String team, @RequestParam String type, @RequestParam String word){
		String message = "Successfully Fetched Noticelist";
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,service.selectSearchList(team,type, word),true));
		
	}
	
	//공지사항 팀
	@GetMapping("/team")
	public ResponseEntity<ResponseMessage> selectTeamNotice(@RequestParam String codeName){
		String message = "Successfully Fetched TeamNotice";
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,service.selectTeamNotice(codeName),true));
		
	}
	
	//대쉬보드
	@GetMapping("/dashboard")
	public ResponseEntity<ResponseMessage> selectDashboardNotice(){
		String message = "Successfully Fetched DashboardNotice";
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,service.selectDashboardNotice(),true));
	}
	
}
