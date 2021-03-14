package com.doc.management.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DocFileEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long parentId;
	
	private String fileName;
	
	private String newFileName;
	
	private String filePath;
	
	private Long isDir;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	
	private List<DocFileEntity> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getIsDir() {
		return isDir;
	}

	public void setIsDir(Long isDir) {
		this.isDir = isDir;
	}

	public List<DocFileEntity> getChildren() {
		return children;
	}

	public void setChildren(List<DocFileEntity> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "DocFileEntity [id=" + id + ", parentId=" + parentId + ", fileName=" + fileName + ", newFileName="
				+ newFileName + ", filePath=" + filePath + ", isDir=" + isDir + ", createDate=" + createDate
				+ ", children=" + children + "]";
	}
	
}
