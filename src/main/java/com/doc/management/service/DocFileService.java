package com.doc.management.service;

import java.util.List;

import com.doc.management.VO.DocFileVO;
import com.doc.management.bean.DocFileEntity;
import com.github.pagehelper.PageInfo;

public interface DocFileService {

	PageInfo<DocFileEntity> findDocFileList(Integer pageNum, Integer pageSize);
	
	DocFileEntity findDocFileById(Long id);
	
	Integer save(DocFileEntity entity);
	
	Integer delete(Integer id);
	
	List<DocFileVO> findDirFileList();
	
	List<DocFileVO> findDirFileListByDirPath(String dirFilePath);
	
	List<DocFileEntity> findAllDirFileList();
	
	List<DocFileEntity> findAllDirTreeList();
}
