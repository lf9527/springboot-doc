package com.doc.management.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.doc.management.VO.DocFileVO;
import com.doc.management.bean.DocFileEntity;
import com.doc.management.dao.DocFileMapper;
import com.doc.management.service.DocFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class DocFileServiceImpl implements DocFileService {

	@Autowired
	private DocFileMapper docFileMapper;
	
	@Value("${filedir.path}")
	private String dirPath;

	@Override
	public PageInfo<DocFileEntity> findDocFileList(Integer pageNum, Integer pageSize) {
		return PageHelper.startPage(pageNum, pageSize)
					.doSelectPageInfo(() -> docFileMapper.findDocFileList());
	}

	@Override
	public DocFileEntity findDocFileById(Integer id) {
		return docFileMapper.findDocFileById(id);
	}

	@Override
	public Integer save(DocFileEntity entity) {
		
		return docFileMapper.insert(entity);
	}

	@Override
	public Integer delete(Integer id) {
		
		return docFileMapper.delete(id);
	}

	@Override
	public List<DocFileVO> findDirFileList() {
		File dir = new File(dirPath);
		Long initId = 0L;
		List<DocFileVO> dirFileList = new ArrayList<DocFileVO>();
		DocFileVO entity = new DocFileVO();
		entity.setId(initId);
		entity.setParentId(-1L);
		entity.setFilePath(dirPath);
		entity.setIsDir(true);
		String fileName = dirPath.substring(dirPath.lastIndexOf("/") + 1);
		entity.setFileName(fileName);
		dirFileList.add(entity);
		System.out.println("date : " + new Date());
		this.findFileList(dir, dirFileList, entity, initId, fileName);
		System.out.println("end date : " + new Date());
		return dirFileList;
	}
	
	private void findFileList(File dir, List<DocFileVO> dirFileList, DocFileVO parentEntity, Long initId, String fileName) {
		if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        String[] files = dir.list();
        List<DocFileVO> dirFileTemp = new ArrayList<DocFileVO>();
        for (int i = 0; i < files.length; i++) {
        	String tempFileName = files[i];
            File file = new File(dir, tempFileName);
            initId++;
            DocFileVO entity = new DocFileVO();
            entity.setId(initId);
            entity.setParentId(parentEntity.getId());
            String filePath = parentEntity.getFilePath() + File.separator + tempFileName;
            if (file.isDirectory()) {
            	entity.setIsDir(true);
            }
            else{
            	entity.setIsDir(false);
            }
            entity.setFilePath(filePath);
            entity.setFileName(tempFileName);
            dirFileTemp.add(entity);
            findFileList(file, dirFileTemp, entity, initId, tempFileName);
        }
        parentEntity.setChildren(dirFileTemp);
    }

	@Override
	public List<DocFileVO> findDirFileListByDirPath(String dirFilePath) {
		File dir = new File(dirFilePath);
		if (!dir.exists() || !dir.isDirectory()) {
            return new ArrayList<DocFileVO>();
        }
        String[] files = dir.list();
        List<DocFileVO> dirFileTemp = new ArrayList<DocFileVO>();
        Long initId = 1L;
        for (int i = 0; i < files.length; i++) {
        	String tempFileName = files[i];
            File file = new File(dir, tempFileName);
            initId++;
            DocFileVO entity = new DocFileVO();
            entity.setId(initId);
            entity.setParentId(0L);
            String filePath = dirFilePath + File.separator + tempFileName;
            if (file.isDirectory()) {
            	entity.setIsDir(true);
            }
            else{
            	entity.setIsDir(false);
            }
            entity.setFilePath(filePath);
            entity.setFileName(tempFileName);
            dirFileTemp.add(entity);
        }
		return dirFileTemp;
	}
}
