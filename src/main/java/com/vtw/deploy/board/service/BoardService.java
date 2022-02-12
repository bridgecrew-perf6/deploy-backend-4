package com.vtw.deploy.board.service;

import java.util.List;
import java.util.Map;

import com.vtw.deploy.board.dto.BoardDTO;

public interface BoardService {

	public int insertNotice(BoardDTO board);
	
	public Map<String,Object> selectNotice();
	
	public Map<String,Object> selectNoticeDetail(int boardNo);
	
	public int deleteNotice(int boardNo);
	
	public int updateNotice(BoardDTO boardDTO,int boardNo);
	
	public Map<String,Object> selectSearchList(String team,String type,String word);
	
	public Map<String,Object> selectTeamNotice(String team);
	
	public List<BoardDTO> selectDashboardNotice();
	
}
