package com.doc.management.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.doc.management.bean.DocFileEntity;
import com.doc.management.constant.Constant;

public class FileUtil {

    public static void findFileList(File dir, List<String> fileNames) {
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        String[] files = dir.list();
        for (int i = 0; i < files.length; i++) {
            File file = new File(dir, files[i]);
            if (file.isFile()) {
                fileNames.add(dir + File.separator + file.getName());
            } else {
                findFileList(file, fileNames);
            }
        }
    }
    
    public static void main(String[] args) {
		/*String dirPath = "E:/vue3/express";
		File dir = new File(dirPath);
		Long initId = 0L;
		List<DocFileEntity> dirFile = new ArrayList<DocFileEntity>();
		DocFileEntity entity = new DocFileEntity();
		entity.setId(initId);
		entity.setParentId(-1L);
		entity.setFilePath(dirPath);
		entity.setIsDir(1L);
		String fileName = dirPath.substring(dirPath.lastIndexOf("/") + 1);
		entity.setFileName(fileName);
		dirFile.add(entity);
		findFileList(dir, dirFile, entity, initId, fileName);
		System.out.println(dirFile);*/
    	String originalFilename = "dirFile/file2";
    	System.out.println(originalFilename.substring(originalFilename.indexOf("/")+1));
	}
    
    public static void findFileList(File dir, List<DocFileEntity> dirFile, DocFileEntity parentEntity, Long initId, String fileName) {
    	if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        String[] files = dir.list();
        List<DocFileEntity> dirFileTemp = new ArrayList<DocFileEntity>();
        for (int i = 0; i < files.length; i++) {
        	String tempFileName = files[i];
            File file = new File(dir, tempFileName);
            initId++;
            DocFileEntity entity = new DocFileEntity();
            entity.setId(initId);
            entity.setParentId(parentEntity.getId());
            String filePath = parentEntity.getFilePath() + File.separator + tempFileName;
            if (file.isDirectory()) {
            	entity.setIsDir(1L);
            }
            else{
            	entity.setIsDir(0L);
            }
            entity.setFilePath(filePath);
            entity.setFileName(tempFileName);
            dirFileTemp.add(entity);
            findFileList(file, dirFileTemp, entity, initId, tempFileName);
        }
        parentEntity.setChildren(dirFileTemp);
    }
    
    public static void uploadFile(byte[] file, String filePath, String fileName) throws IOException{
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + Constant.separator +  fileName);
        out.write(file);
        out.flush();
        out.close();
   }
}
