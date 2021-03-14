package com.doc.management.VO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DocFileVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long parentId;
	
	private String fileName;
	
	private String newFileName;
	
	private String label;
	
	private String filePath;
	
	private Boolean isDir;
	
	private List<DocFileVO> children;
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		this.label = this.fileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = this.fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Boolean getIsDir() {
		return isDir;
	}

	public void setIsDir(Boolean isDir) {
		this.isDir = isDir;
	}

	public List<DocFileVO> getChildren() {
		return children;
	}

	public void setChildren(List<DocFileVO> children) {
		this.children = children;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "DocFileVO [id=" + id + ", parentId=" + parentId + ", fileName=" + fileName + ", newFileName="
				+ newFileName + ", label=" + label + ", filePath=" + filePath + ", isDir=" + isDir + ", children="
				+ children + ", createDate=" + createDate + "]";
	}

}
