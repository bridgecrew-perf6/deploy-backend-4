package com.vtw.deploy.common.codemgmt.controller;

import java.util.List;

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

import com.vtw.deploy.common.codemgmt.dto.CodeMgmtDTO;
import com.vtw.deploy.common.codemgmt.service.CodeMgmtService;
import com.vtw.deploy.common.fileupload.message.ResponseMessage;

@RestController
@RequestMapping({"/code"})
public class CodeMgmtController {
	
	@Autowired
	private CodeMgmtService service;
	//맨 처음 최상위 코드 정보 얻어오기 위해 작성함
	@GetMapping
	public ResponseEntity<ResponseMessage> selectParentCodeList() {
		List<CodeMgmtDTO> list = service.selectParentCodeList();
		String message = "Data Showing Now Is Parent Codes";
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, list, true));
	}
	
	//플러스 버튼 눌렀을 때 자식 리스트 얻어오기 위해 작성함
	@GetMapping("/childInfo/{parentCodeId}")
	public ResponseEntity<ResponseMessage> selectChildCodeList(@PathVariable String parentCodeId) {
		List<CodeMgmtDTO> list = service.selectChildCodeList(parentCodeId);
		String message = "Data Showing Now Is Child Codes";
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, list, true));
	}
	
	//업데이트코드 다이얼로그 오픈시에 정보 얻어오기 위해 작성함 
	@GetMapping("/dialog/{codeId}")
	public ResponseEntity<ResponseMessage> selectOneCodeByCodeId(@PathVariable String codeId) {
		CodeMgmtDTO codeMgmtDTO = service.selectOneCodeByCodeId(codeId);
		String message = "This Is The Code That You Are Trying To Update";
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, codeMgmtDTO, true));
	}
	
	//부모코드 순서 중복되면 에러메시지 띄우기 위해 작성함
	@GetMapping("/checkParentOrder/{dsplOrder}")
	public ResponseEntity<ResponseMessage> checkParnetDsplOrder(@PathVariable int dsplOrder) {
		int result = service.selectCheckParentDsplOrder(dsplOrder);
		String message = "";
		if(result==1) {
			 message="Inserted Order Is Already In Use";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, dsplOrder, true));
		} else {
			 message="This Display Order Is Not In Use";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, dsplOrder, false));
		} 
	}
	
	//자식 코드 디스플레이 순서 중복되면 에러메시지 띄우기 위해 작성함
	@GetMapping("/{parentCodeId}/check/{dsplOrder}")
	public ResponseEntity<ResponseMessage> checkChildDsplOrder(@PathVariable int dsplOrder, @PathVariable String parentCodeId) {
		int result = service.selectCheckChildDsplOrder(dsplOrder,parentCodeId);
		String message = "";
		if(result==1) {
			 message="Inserted Order Is Already In Use";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, dsplOrder, true));
		} else {
			 message="This Display Order Is Not In Use";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, dsplOrder, false));
		} 
	}
	
	//코드 아이디 중복 막기위해 작성함
	@GetMapping("/checkCodeId/{codeId}")
	public ResponseEntity<ResponseMessage> checkCodeId(@PathVariable String codeId) {
		int result = service.selectCheckCodeId(codeId);
		String message = "";
		if(result==1) {
			 message="Inserted Code Id Is Already In Use";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, codeId, true));
		} else {
			 message="This Code Id Is Not In Use";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, codeId, false));
		} 
	}
	
	//최상위코드 존재하지 않으면 에러메시지 뜨게하기 위해 작성함
	@GetMapping("/checkParentCodeId/{parentCodeId}")
	public ResponseEntity<ResponseMessage> checkParentCodeId(@PathVariable String parentCodeId) {
		
		int result = service.selectCheckParentCodeId(parentCodeId);
		String message = "";
		if(result >= 1) { 
			 message="This Parent Id Exists";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, parentCodeId, true));
		} else {
			 message="This Parent Id Does Not Exist";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, parentCodeId, false));
		}
	}
	
	//코드 검색
	@GetMapping("/search")
		public ResponseEntity<ResponseMessage> searchCode(@RequestParam String type, @RequestParam String keyword) {
			String message = "Successfully Brought The Code You Were Searching For";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,service.selectSearchCodeList(type, keyword),true));
	}
	
	//코드 입력
	@PostMapping
	public ResponseEntity<ResponseMessage> insertCode(@RequestBody CodeMgmtDTO codeMgmtDTO) {
		int result = service.insertCode(codeMgmtDTO);
		String message = "";
		if(result==1) {
			 message="Code Inserted Successfully";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, codeMgmtDTO, true));
		} else {
			 message="Attempt To Insert New Code Failed";
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, codeMgmtDTO, false));
		}
		
	}
	
	//코드 수정
	@PatchMapping
	public ResponseEntity<ResponseMessage> updateCode(@RequestBody CodeMgmtDTO codeMgmtDTO) {
		int result = service.updateCode(codeMgmtDTO);
		String message = "";
		if(result==1) {
			 message="Code Updated Successfully";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, codeMgmtDTO, true));
		} else {
			 message="Attempt To Update This Code Failed";
			 return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, codeMgmtDTO, false));
		}
	}
	
	//코드 삭제. 자식 코드 있을 경우엔 최상위 코드 삭제 불가능 함.
	@DeleteMapping("/{codeId}")
	public ResponseEntity<ResponseMessage> deleteCode(@PathVariable String codeId) {
		int result = service.deleteCode(codeId);
		String message = "";
		if(result==1) {
			 message="Code Deleted Successfully";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, codeId, true));
		} else {
			 message="Attempt To Delete This Code Failed";
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, codeId, false));
		}
	}

}
