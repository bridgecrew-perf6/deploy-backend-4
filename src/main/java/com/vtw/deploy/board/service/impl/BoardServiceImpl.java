package com.vtw.deploy.board.service.impl;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vtw.deploy.board.dto.BoardDTO;
import com.vtw.deploy.board.repository.BoardRepository;
import com.vtw.deploy.board.service.BoardService;
import com.vtw.deploy.common.codemgmt.repository.CodeMgmtRepository;
import com.vtw.deploy.common.fileupload.dto.FileDTO;
import com.vtw.deploy.common.repository.FileRepository;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private CodeMgmtRepository codeRepository;
	
	@Transactional
	@Override
	public int insertNotice(BoardDTO board) {
		
		String[] names =board.getNames();
		String[] directoryPaths = board.getDirectoryPaths();
		
		//String -> byte[] 형변환 후 setter
		board.setContentBlob(board.getContent().getBytes());
		
		//공지사항 DB 등록
		int result=boardRepository.insertNotice(board);
		
	
		if(names != null) {
			for(int i=0; i<names.length;i++) {
				FileDTO file = new FileDTO(names[i],directoryPaths[i],board.getBoardNo());
				//파일 DB 등록
				result=fileRepository.insertNoticeFile(file);
			}
		}
		
		if(result==1) {
			return 1;
		}
		
		return 0;
	}
	
	@Override
	public Map<String,Object> selectNotice() {
			
		Map<String,Object> map = new ConcurrentHashMap<String, Object>();
		
		//게시판리스트
		map.put("noticeList", boardRepository.selectNotice());
		//팀리스트
		map.put("teamList", codeRepository.selectTeamList());
		return map;
	}
	
	@Transactional
	@Override
	public Map<String,Object> selectNoticeDetail(int boardNo) {
		 
		 Map<String,Object> map = new ConcurrentHashMap<String,Object>();
		 
		 //조회수 증가
		 boardRepository.updateViewCount(boardNo);
		 
		 //해당 공지사항 상세
		 BoardDTO board = boardRepository.selectNoticeDetail(boardNo);
		 
		// byte[] -> String 형변환 후 setter
		 String content = new String(board.getContentBlob());
		 board.setContent(content);
		 
		 //공지사항 상세 내용
		 map.put("board", board);
		 //해당 공지사항에 포함된 파일
		 map.put("files", fileRepository.selectNoticeFile(boardNo));
		return map;
	}
	
	@Transactional
	@Override
	public int deleteNotice(int boardNo) {
		
		//공지사항 삭제
		int result = boardRepository.deleteNotice(boardNo);
		//해당 공지사항 파일은 DELETE_YN 컬럼에 Y 로 업데이트
		fileRepository.updateNoticeFile(boardNo);
		
		return result;
	}
	
	@Transactional
	@Override
	public int updateNotice(BoardDTO boardDTO,int boardNo) {
		
		String[] names =boardDTO.getNames();
		String[] directoryPaths = boardDTO.getDirectoryPaths();
		boardDTO.setBoardNo(boardNo);
		
		//공지사항에 포함된 파일 삭제
		fileRepository.deleteNoticeFile(boardNo);
		
		//String -> byte[] 형변환 후 setter
		boardDTO.setContentBlob(boardDTO.getContent().getBytes());
		
		//공지사항 업데이트
		boardRepository.updateNotice(boardDTO);
		
		if(names != null) {
			for(int i=0; i<names.length;i++) {
				FileDTO file = new FileDTO(names[i],directoryPaths[i],boardNo);
				//파일 DB 등록
				fileRepository.insertNoticeFile(file);
			}//for end
		}//if end
		return 1;
	}//updateNotice end
	
	@Override
	public Map<String, Object> selectSearchList(String team,String type, String word) {
		Map<String,Object> map = new ConcurrentHashMap<String, Object>();
		
		BoardDTO board = new BoardDTO();
		board.setType(type);
		board.setWord(word);
		board.setTeam(team);
		
		//공지사항 검색
		map.put("noticeList", boardRepository.selectSearchList(board));
		//팀리스트
		map.put("teamList", codeRepository.selectTeamList());
		
		return map;
	}
	
	@Override
	public Map<String,Object> selectTeamNotice(String team) {
		
		Map<String,Object> map = new ConcurrentHashMap<String, Object>();
		
		//팀별 게시판리스트
		map.put("noticeList", boardRepository.selectTeamNotice(team));
		//팀리스트
		map.put("teamList", codeRepository.selectTeamList());
		
		return map;
	}
	
	@Override
	public List<BoardDTO> selectDashboardNotice() {
		
		//대쉬보드 공지사항 리스트
		return boardRepository.selectDashboardNotice();
	}
	
}
