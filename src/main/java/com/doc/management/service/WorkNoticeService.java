package com.doc.management.service;

import com.doc.management.bean.WorkNoticeEntity;
import com.github.pagehelper.PageInfo;

public interface WorkNoticeService {

	PageInfo<WorkNoticeEntity> findWorkNoticeList(Integer pageNum, Integer pageSize);
	
	WorkNoticeEntity findWorkNoticeById(Integer id);
	
	Integer save(WorkNoticeEntity entity);
	
	Integer delete(Integer id);
}
