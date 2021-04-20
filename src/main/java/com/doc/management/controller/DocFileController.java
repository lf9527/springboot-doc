package com.doc.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doc.management.VO.DocFileVO;
import com.doc.management.bean.DocFileEntity;
import com.doc.management.service.DocFileService;
import com.github.pagehelper.PageInfo;

@RequestMapping("/api/docFile")
@RestController
public class DocFileController {

	@Autowired
	private DocFileService docFileService;
	
	@CrossOrigin
	@RequestMapping(value = "/getAllDirFile", method = RequestMethod.GET)
	public List<DocFileEntity> getAllDirFileList() {
		return docFileService.findAllDirFileList();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getAllDirTree", method = RequestMethod.GET)
	public List<DocFileEntity> getAllDirTreeList() {
		return docFileService.findAllDirTreeList();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getAllData", method = RequestMethod.GET)
	public List<DocFileVO> getAllDataList() {
		return docFileService.findDirFileList();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getAllData/dirPath", method = RequestMethod.GET)
	public List<DocFileVO> getAllDataByDirPath(@RequestParam("dirFilePath") String dirFilePath) {
		return docFileService.findDirFileListByDirPath(dirFilePath);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/{pageNum}/{pageSize}", method = RequestMethod.GET)
	public PageInfo<DocFileEntity> getDataList(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
		return docFileService.findDocFileList(pageNum, pageSize);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DocFileEntity getDocFile(@PathVariable("id") Long id) {
		return docFileService.findDocFileById(id);
	}
	
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Integer save(@RequestBody DocFileEntity entity) {
		
		return docFileService.save(entity);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Integer delete(@PathVariable("id") Long id) {
		
		return docFileService.delete(id);
	}
}
