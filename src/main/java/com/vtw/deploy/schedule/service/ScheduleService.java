package com.vtw.deploy.schedule.service;

import java.util.List;

import com.vtw.deploy.schedule.dto.ScheduleDTO;
import com.vtw.deploy.schedule.dto.ScheduleHistoryDTO;
import com.vtw.deploy.user.dto.UserCountDTO;

public interface ScheduleService {

	int insertSchedule(ScheduleDTO schedule);

	List<ScheduleDTO> selectScheduleList();

	int deleteSchedule(ScheduleHistoryDTO schedule);

	int updateSchedule(ScheduleHistoryDTO schedule);

	List<ScheduleHistoryDTO> selectHistoryList(int scheduleNo);

	List<UserCountDTO> selectTodayCount();

	List<ScheduleDTO> selectTodayList(ScheduleDTO schedule);

}
