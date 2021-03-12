package com.doc.management.dao;

import java.util.List;

import com.doc.management.bean.DocFileEntity;

public interface DocFileMapper {

	List<DocFileEntity> findDocFileList();
	
	DocFileEntity findDocFileById(Integer id);
	
	Integer insert(DocFileEntity entity);
	
	Integer delete(Integer id);
}
