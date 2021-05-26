package com.doc.management.dao;

import java.util.List;

import com.doc.management.bean.DocFileEntity;

public interface DocFileMapper {

	List<DocFileEntity> findDocFileList();
	
	DocFileEntity findDocFileById(Long id);
	
	Integer existsDocFile(DocFileEntity entity);
	
	Integer insert(DocFileEntity entity);
	
	Integer delete(Long id);

	/**
	 * 查询所有子级结构记录
	 * @param id
	 * @return List<DocFileEntity>
	 */
	List<DocFileEntity> findAllDirFileList(Long parentId);
	
	/**
	 * 查询所有父级结构记录
	 * @param id
	 * @return
	 */
	List<DocFileEntity> findAllParentDirFileList(Long id);

	Integer update(DocFileEntity entity);
	
}
