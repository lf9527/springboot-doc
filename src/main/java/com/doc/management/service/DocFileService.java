package com.doc.management.service;

import java.util.List;

import com.doc.management.bean.DocFileEntity;
import com.github.pagehelper.PageInfo;

public interface DocFileService {

	PageInfo<DocFileEntity> findDocFileList(Integer pageNum, Integer pageSize);
	
	DocFileEntity findDocFileById(Long id);
	
	Integer save(DocFileEntity entity);
	
	Integer delete(Long id);
	
	/*List<DocFileVO> findDirFileList();*/
	
	/*List<DocFileVO> findDirFileListByDirPath(String dirFilePath);*/
	
	List<DocFileEntity> findAllDirFileList();
	
	/**
	 * 查询所有子级结构记录
	 * @param parentId
	 * @return List<DocFileEntity>
	 */
	List<DocFileEntity> findAllDirTreeList();
	
	/**
	 * 根据父级id查询所有子级结构记录
	 * @param parentId
	 * @return List<DocFileEntity>
	 */
	List<DocFileEntity> findAllDirTreeList(Long parentId);
	
	/**
	 * 查询所有父级结构记录
	 * @param id
	 * @return List<DocFileEntity>
	 */
	List<DocFileEntity> findAllParentDirFileList(Long id);

	Integer update(DocFileEntity entity);
}
