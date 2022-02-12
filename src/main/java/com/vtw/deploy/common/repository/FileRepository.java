package com.vtw.deploy.common.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vtw.deploy.common.fileupload.dto.FileDTO;

@Mapper
public interface FileRepository {

	public int insertNoticeFile(FileDTO file);
	
	public List<FileDTO> selectNoticeFile(int typeNo);
	
	public int updateNoticeFile(int typeNo);
	
	public int deleteNoticeFile(int typeNo);
	
	//deploy
	public int insertDeployFile(FileDTO file);
	//알집만
	public FileDTO selectDeployFile(int deployNo);
	//파일들
	public List<FileDTO> selectDeployFiles(int deployNo);
}
