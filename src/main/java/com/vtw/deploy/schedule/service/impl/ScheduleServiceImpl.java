package com.vtw.deploy.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vtw.deploy.schedule.dto.ScheduleDTO;
import com.vtw.deploy.schedule.dto.ScheduleHistoryDTO;
import com.vtw.deploy.schedule.repository.ScheduleRepository;
import com.vtw.deploy.schedule.service.ScheduleService;
import com.vtw.deploy.user.dto.UserCountDTO;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository repository;

	@Transactional
	@Override
	public int insertSchedule(ScheduleDTO schedule) {
		return repository.insertSchedule(schedule);
	}

	@Override
	public List<ScheduleDTO> selectScheduleList() {
		return repository.selectScheduleList();
	}

	@Transactional
	@Override
	public int deleteSchedule(ScheduleHistoryDTO schedule) {

		// 삭제 이력
		ScheduleHistoryDTO history = repository.selectOneSchedule(schedule.getScheduleNo());
		history.setUpdateReason(schedule.getUpdateReason());
		history.setType("D");

		int result = repository.insertScheduleHistory(history);

		if (result > 0) {
			return repository.deleteSchedule(schedule.getScheduleNo());
		} else {
			return 0;
		}
	}

	@Transactional
	@Override
	public int updateSchedule(ScheduleHistoryDTO schedule) {

		// 수정 이력
		ScheduleHistoryDTO history = repository.selectOneSchedule(schedule.getScheduleNo());
		history.setUpdateReason(schedule.getUpdateReason());

		if (schedule.getComplete() != null) {// Y이면 - 완료 눌렀을때
			history.setComplete(schedule.getComplete());
			history.setType("C");
		} else {
			history.setType("U");
		}

		int result = repository.insertScheduleHistory(history);

		if (result > 0) {
			return repository.updateSchedule(schedule);
		} else {
			return 0;
		}
	}

	@Override
	public List<ScheduleHistoryDTO> selectHistoryList(int scheduleNo) {
		return repository.selectHistoryList(scheduleNo);
	}

	@Override
	public List<UserCountDTO> selectTodayCount() {
		return repository.selectTodayCount();
	}

	@Override
	public List<ScheduleDTO> selectTodayList(ScheduleDTO schedule) {
		return repository.selectTodayList(schedule);
	}

}
