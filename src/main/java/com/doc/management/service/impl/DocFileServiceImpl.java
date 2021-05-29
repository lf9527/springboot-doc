package com.doc.management.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.doc.management.bean.DocFileEntity;
import com.doc.management.constant.Constant;
import com.doc.management.dao.DocFileMapper;
import com.doc.management.service.DocFileService;
import com.doc.management.utils.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class DocFileServiceImpl implements DocFileService {

	@Autowired
	private DocFileMapper docFileMapper;
	
	@Value("${filedir.path}")
	private String dirPath;
	
	public String separator = Constant.separator;

	@Override
	public PageInfo<DocFileEntity> findDocFileList(Integer pageNum, Integer pageSize) {
		return PageHelper.startPage(pageNum, pageSize)
					.doSelectPageInfo(() -> docFileMapper.findDocFileList());
	}

	@Override
	public DocFileEntity findDocFileById(Long id) {
		DocFileEntity entity = docFileMapper.findDocFileById(id);
		this.resetFilePath(entity);
		return entity;
		
	}

	@Override
	public Integer save(DocFileEntity entity) {
		Integer existsCount = docFileMapper.existsDocFile(entity);
		if(existsCount != null && existsCount > 0){
			return 1;
		}
		return docFileMapper.insert(entity);
	}

	@Override
	public Integer delete(Long id) {
		DocFileEntity entity = docFileMapper.findDocFileById(id);
		this.resetFilePath(entity);
		String filePath = dirPath.substring(0, dirPath.lastIndexOf(Constant.separator)) + Constant.separator + entity.getFilePath() + Constant.separator + entity.getFileName();
		FileUtil.deleteFile(filePath);
		return docFileMapper.delete(id);
	}

	/*@Override
	public List<DocFileVO> findDirFileList() {
		File dir = new File(dirPath);
		Long initId = 1L;
		List<DocFileVO> dirFileList = new ArrayList<DocFileVO>();
		DocFileVO entity = new DocFileVO();
		entity.setId(initId);
		entity.setParentId(-1L);
		entity.setIsDir(true);
		String fileName = dirPath.substring(dirPath.lastIndexOf("/") + 1);
		entity.setFilePath(fileName);
		entity.setFileName(fileName);
		dirFileList.add(entity);
		System.out.println("date : " + new Date());
		this.findFileList(dir, dirFileList, entity, initId, fileName);
		System.out.println("end date : " + new Date());
		return dirFileList;
	}*/
	
	/*private void findFileList(File dir, List<DocFileVO> dirFileList, DocFileVO parentEntity, Long initId, String fileName) {
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
            String filePath = parentEntity.getFilePath() + separator + tempFileName;
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
    }*/

	/*@Override
	public List<DocFileVO> findDirFileListByDirPath(String dirFilePath) {
		
		return findLocalDirFileListByDirPath(dirFilePath);
	}*/
	
	/*private List<DocFileVO> findLocalDirFileListByDirPath(String dirFilePath) {
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
            String filePath = dirFilePath + separator + tempFileName;
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
	}*/

	@Override
	public List<DocFileEntity> findAllDirFileList() {
		
		return docFileMapper.findAllDirFileList(0L);
	}
	
	/**
	 * 查询所有子级结构记录
	 * @param id
	 * @return
	 */
	@Override
	public List<DocFileEntity> findAllDirTreeList() {
		List<DocFileEntity> dirFileList = docFileMapper.findAllDirFileList(0L);
		
		return filterFile(dirFileList);
	}
	
	/**
	 * 查询所有子级结构记录
	 * @param id
	 * @return
	 */
	@Override
	public List<DocFileEntity> findAllDirTreeList(Long parentId) {
		List<DocFileEntity> dirFileList = docFileMapper.findAllDirFileList(parentId);
		
		return filterFile(dirFileList);
	}
	
	/**
	 * 查询所有父级结构记录
	 * @param id
	 * @return
	 */
	@Override
	public List<DocFileEntity> findAllParentDirFileList(Long id){
		List<DocFileEntity> dirFileList = docFileMapper.findAllParentDirFileList(id);
		
		return dirFileList;
	}	

	@Override
	public Integer update(DocFileEntity entity) {
		
		return docFileMapper.update(entity);
	}

	/**
	 * 过滤不是目录的数据
	 * @param dirFileList
	 * @return List<DocFileEntity>
	 */
	private List<DocFileEntity> filterFile(List<DocFileEntity> dirFileList) {
		List<DocFileEntity> dirFileTemp = new ArrayList<DocFileEntity>();
		for(DocFileEntity dir : dirFileList){
			if(dir.getIsDir() == 1){
				if(!dir.getChildren().isEmpty() && dir.getChildren().size() > 0){
					dir.setChildren(this.filterFile(dir.getChildren()));
				}
				dirFileTemp.add(dir);
			}
		}
		return dirFileTemp;
	}
	
	/**
	 * 拼接目录/文件路径file path
	 */
	private void resetFilePath(DocFileEntity entity){
		if(entity == null || entity.getId() == null){
			return;
		}
		List<DocFileEntity> entityList = this.findAllParentDirFileList(entity.getId());
		if(entityList == null || entityList.isEmpty()){
			return;
		}
		String filePath = this.getFullFilePath(entityList.get(0));
		filePath = filePath.substring(0, filePath.lastIndexOf(separator));//删除本身目录
		entity.setFilePath(filePath);
	}
	
	
	private String getFullFilePath(DocFileEntity entity) {
		String filePath = entity.getFilePath();
		
		if(!entity.getChildren().isEmpty() && entity.getChildren().size() > 0){
			DocFileEntity entityTemp = entity.getChildren().get(0);
			filePath = this.getFullFilePath(entityTemp) + separator + filePath;
		}
		return filePath;
	}

}
