package com.doc.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.doc.management.bean.WorkNoticeEntity;
import com.doc.management.service.WorkNoticeService;
import com.github.pagehelper.PageInfo;

@RequestMapping("/api/workNotice")
@RestController
public class WorkNoticeController {

	@Autowired
	private WorkNoticeService workNoticeService;
	
	@CrossOrigin
	@RequestMapping(value = "/{pageNum}/{pageSize}", method = RequestMethod.GET)
	public PageInfo<WorkNoticeEntity> getDataList(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
		return workNoticeService.findWorkNoticeList(pageNum, pageSize);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public WorkNoticeEntity getWorkNotice(@PathVariable("id") Integer id) {
		return workNoticeService.findWorkNoticeById(id);
	}
	
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Integer save(@RequestBody WorkNoticeEntity entity) {
		
		return workNoticeService.save(entity);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Integer delete(@PathVariable("id") Integer id) {
		
		return workNoticeService.delete(id);
	}
}
