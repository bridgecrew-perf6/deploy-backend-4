package com.vtw.deploy.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vtw.deploy.board.dto.BoardDTO;

@Mapper
public interface BoardRepository {
	
	public int insertNotice(BoardDTO board);
	
	public List<BoardDTO> selectNotice();
	
	public BoardDTO selectNoticeDetail(int boardNo);
	
	public int deleteNotice(int boardNo);
	
	public int updateNotice(BoardDTO boardDTO);
	
	public List<BoardDTO> selectSearchList(BoardDTO board);
	
	public List<BoardDTO> selectTeamNotice(String team);
	
	public int updateViewCount(int boardNo);
	
	public List<BoardDTO> selectDashboardNotice();
}
