package com.vtw.deploy.schedule.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vtw.deploy.schedule.dto.ScheduleDTO;
import com.vtw.deploy.schedule.dto.ScheduleHistoryDTO;
import com.vtw.deploy.user.dto.UserCountDTO;

@Mapper
public interface ScheduleRepository {

	public int insertSchedule(ScheduleDTO schedule);

	public List<ScheduleDTO> selectScheduleList();

	public int deleteSchedule(int scheduleNo);

	public int updateSchedule(ScheduleDTO schedule);

	public ScheduleHistoryDTO selectOneSchedule(int scheduleNo);

	public int insertScheduleHistory(ScheduleHistoryDTO history);

	public List<ScheduleHistoryDTO> selectHistoryList(int scheduleNo);

	public List<UserCountDTO> selectTodayCount();

	public List<ScheduleDTO> selectTodayList(ScheduleDTO schedule);
}
