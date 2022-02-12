package com.vtw.deploy.schedule.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.vtw.deploy.common.fileupload.message.ResponseMessage;
import com.vtw.deploy.schedule.dto.ScheduleDTO;
import com.vtw.deploy.schedule.dto.ScheduleHistoryDTO;
import com.vtw.deploy.schedule.service.ScheduleService;
import com.vtw.deploy.user.dto.UserCountDTO;

@RestController
@RequestMapping({ "/schedule" })
public class ScheduleController {

	@Autowired
	private ScheduleService service;

	@PostMapping
	public ResponseEntity<ResponseMessage> insertSchedule(@RequestBody ScheduleDTO schedule) {// requestBody 써야 값 받아짐

		String message = "";

		if (service.insertSchedule(schedule) > 0) {
			message = "Successfully Inserted Schedule";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, true, true));
		} else {
			message = "Failed To Insert Schedule";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, false, true));
		}
	}

	@GetMapping
	public ResponseEntity<ResponseMessage> selectScheduleList() {

		String message = "";
		List<ScheduleDTO> list = service.selectScheduleList();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, list, true));
	}

	@DeleteMapping("/{scheduleNo}")
	public ResponseEntity<ResponseMessage> deleteSchedule(@PathVariable("scheduleNo") int scheduleNo) {// 삭제 시 삭제 사유가 없는 경우

		ScheduleHistoryDTO schedule = new ScheduleHistoryDTO();
		schedule.setScheduleNo(scheduleNo);
		String message = "";

		if (service.deleteSchedule(schedule) > 0) {
			message = "Successfully Deleted Schedule";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, true, true));
		} else {
			message = "Failed To Delete Schedule";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, false, true));
		}
	}

	@PostMapping("/deleteReason")
	public ResponseEntity<ResponseMessage> deleteScheduleReason(@RequestBody ScheduleHistoryDTO schedule) {// 삭제 시 삭제 사유가 있는 경우

		String message = "";

		if (service.deleteSchedule(schedule) > 0) {
			message = "Successfully Deleted Schedule";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, true, true));
		} else {
			message = "Failed To Delete Schedule";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, false, true));
		}
	}

	@PatchMapping
	public ResponseEntity<ResponseMessage> updateSchedule(@RequestBody ScheduleHistoryDTO schedule) {

		String message = "";

		if (service.updateSchedule(schedule) > 0) {
			message = "Successfully Updated Schedule";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, true, true));
		} else {
			message = "Failed To Update Schedule";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, false, true));
		}
	}

	@GetMapping("/history/{scheduleNo}")
	public ResponseEntity<ResponseMessage> selectHistoryList(@PathVariable("scheduleNo") int scheduleNo) {

		String message = "Successfully Fetched Schedule History List";
		List<ScheduleHistoryDTO> list = service.selectHistoryList(scheduleNo);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, list, true));
	}

	@GetMapping("/today")
	public ResponseEntity<ResponseMessage> selectTodayCount() {// 오늘의 일정 개개인 개수

		String message = "Successfully Fetched Today's Schedule Count List";
		List<UserCountDTO> count = service.selectTodayCount();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, count, true));
	}

	@PostMapping("/todayList")
	public ResponseEntity<ResponseMessage> selectTodayList(@RequestBody ScheduleDTO schedule) {// 오늘의 일정 상세 리스트

		String message = "Successfully Fetched Today's Schedule List";
		List<ScheduleDTO> list = service.selectTodayList(schedule);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, list, true));
	}

}
