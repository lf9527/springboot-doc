package com.doc.management.dao;

import java.util.List;

import com.doc.management.bean.WorkNoticeEntity;

public interface WorkNoticeMapper {

	List<WorkNoticeEntity> findWorkNoticeList();
	
	WorkNoticeEntity findWorkNoticeById(Integer id);
	
	Integer insert(WorkNoticeEntity entity);
	
	Integer delete(Integer id);
}
