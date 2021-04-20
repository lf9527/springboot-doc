package com.doc.management.dao;

import java.util.List;

import com.doc.management.bean.DocFileEntity;

public interface DocFileMapper {

	List<DocFileEntity> findDocFileList();
	
	DocFileEntity findDocFileById(Long id);
	
	Integer insert(DocFileEntity entity);
	
	Integer delete(Long id);

	List<DocFileEntity> findAllDirFileList(Long parentId);
}
