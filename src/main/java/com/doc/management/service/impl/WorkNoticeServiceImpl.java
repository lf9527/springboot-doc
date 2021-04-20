package com.doc.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doc.management.bean.WorkNoticeEntity;
import com.doc.management.dao.WorkNoticeMapper;
import com.doc.management.service.WorkNoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class WorkNoticeServiceImpl implements WorkNoticeService {

	@Autowired
	private WorkNoticeMapper workNoticeMapper;

	@Override
	public PageInfo<WorkNoticeEntity> findWorkNoticeList(Integer pageNum, Integer pageSize) {
		return PageHelper.startPage(pageNum, pageSize).setOrderBy("create_date desc")
					.doSelectPageInfo(() -> workNoticeMapper.findWorkNoticeList());
	}

	@Override
	public WorkNoticeEntity findWorkNoticeById(Integer id) {
		return workNoticeMapper.findWorkNoticeById(id);
	}

	@Override
	public Integer save(WorkNoticeEntity entity) {
		
		return workNoticeMapper.insert(entity);
	}

	@Override
	public Integer delete(Integer id) {
		
		return workNoticeMapper.delete(id);
	}
	
	
}
