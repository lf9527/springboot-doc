package com.doc.management.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doc.management.VO.DocFileVO;
import com.doc.management.bean.DocFileEntity;
import com.doc.management.constant.Constant;
import com.doc.management.service.DocFileService;
import com.doc.management.utils.FileUtil;
import com.github.pagehelper.util.StringUtil;
import com.grapecity.documents.excel.IWorksheet;
import com.grapecity.documents.excel.Workbook;

@RestController
public class FileController {

	@Value("${filedir.path}")
	private String dirPath;
	
	@Autowired
	private DocFileService docFileService;

	private String separator = Constant.separator;
	
	@CrossOrigin
	@RequestMapping(value = "/api/loadExcel/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String loadExcel(@PathVariable("id") Integer id) {
		try {
			// String filePath = "E:\\vue3\\新建 Microsoft Excel 工作表.xlsx";
			String workbookJSON;
			Workbook workbook = new Workbook();
			workbook.open(dirPath + separator + "新建 Microsoft Excel 工作表.xlsx");

			IWorksheet sheet = workbook.getWorksheets().get("Sheet1");
			sheet.getRange("C4:AA6").setLocked(false);
			sheet.setProtection(true);

			workbookJSON = workbook.toJson();
			return workbookJSON;
			// return "workbookJSON workbookJSON";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

	@CrossOrigin
	@RequestMapping(value = "/api/loadFile", method = RequestMethod.GET)
	public ResponseEntity getFile(HttpServletResponse response,
			@RequestParam(value = "filePath", required = true) String filePath) {
		File file = new File(filePath);
		if (file.exists() && !file.isDirectory()) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add("Last-Modified", new Date().toString());
			headers.add("ETag", String.valueOf(System.currentTimeMillis()));
			try {
				headers.add("Content-Disposition",
						"attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));

				URL url = new URL("file:///" + file);
				headers.add("Content-Type", url.openConnection().getContentType());
				// 下载成功返回二进制流
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						// .contentType(MediaType.APPLICATION_OCTET_STREAM)
						.contentType(MediaType.parseMediaType("application/octet-stream"))
						// .contentType(MediaType.parseMediaType("application/vnd.oasis.opendocument.spreadsheet;charset=utf-8"))
						.body(FileUtils.readFileToByteArray(file));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<String>("not content 204", HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<String>("not content 204", HttpStatus.NO_CONTENT);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/api/download/{id}", method = RequestMethod.GET)
	public void download(HttpServletResponse response,
			@PathVariable(value = "id", required = true) Long id) {
		DocFileEntity entity = docFileService.findDocFileById(id);
		if(entity == null){
			return ;
		}
		String filePath = dirPath.substring(0, dirPath.lastIndexOf(Constant.separator)) + Constant.separator + entity.getFilePath() + Constant.separator + entity.getFileName();
		File file = new File(filePath);
		OutputStream toClient = null;
		try {
			InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
//			toClient.close();
//			return response;
		} catch (Exception e) {
			e.printStackTrace();
//			return null;
		}finally{
			if(toClient != null){
				try {
					toClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/api/preview/{id}", method = RequestMethod.GET)
	public void preview(HttpServletResponse response,
			@PathVariable(value = "id", required = true) Long id) {
		DocFileEntity entity = docFileService.findDocFileById(id);
		if(entity == null){
			return ;
		}
		String filePath = dirPath.substring(0, dirPath.lastIndexOf(Constant.separator)) + Constant.separator + entity.getFilePath() + Constant.separator + entity.getFileName();
		File file = new File(filePath);
		if (file.exists()) {
			byte[] data = null;
			FileInputStream input = null;
			try {
				input = new FileInputStream(file);
				data = new byte[input.available()];
				input.read(data);
				response.getOutputStream().write(data);
			} catch (Exception e) {
				System.out.println("文件处理异常：" + e);
			} finally {
				try {
					if (input != null) {
						input.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/uploadMulti", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(MultipartFile[] files, @RequestBody DocFileVO docFile) throws IOException{
		
		if(files.length == 0){
			return new ResponseEntity<String>("请选择要上传的文件", HttpStatus.FORBIDDEN);
		}
		for (MultipartFile multipartFile : files) {
			if(multipartFile.isEmpty()){
				return new ResponseEntity<String>("文件上传失败", HttpStatus.NOT_FOUND);
			}
			byte[] fileBytes = multipartFile.getBytes();
			String filePath = dirPath + separator + docFile.getFilePath();
			//取得当前上传文件的文件名称
			String originalFilename = multipartFile.getOriginalFilename();
			//生成文件名
			String fileName = UUID.randomUUID() +"&"+ originalFilename;
			FileUtil.uploadFile(fileBytes, filePath, fileName);
		}

		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	@CrossOrigin
	@RequestMapping(value = "/api/uploadSingle", method = RequestMethod.POST)
	public ResponseEntity<String> uploadSingleFile(@RequestParam(value = "file",required = false)MultipartFile file, DocFileEntity docFile) throws IOException{
		
		if(file == null || file.isEmpty()){
			return new ResponseEntity<String>("请选择要上传的文件", HttpStatus.FORBIDDEN);
		}
		Long parentId = docFile.getParentId();
		if(null == parentId) {
			parentId = 0L;
		}
		byte[] fileBytes = file.getBytes();
		String parentFilePath = docFile.getFilePath();
		String baseDirPath = dirPath;
		if(StringUtil.isNotEmpty(parentFilePath)){
			if(parentFilePath.indexOf(Constant.separator) > -1){
				baseDirPath = dirPath.substring(0, dirPath.lastIndexOf(Constant.separator));
				DocFileEntity docPathFile = this.loopDirPath(baseDirPath, parentFilePath, parentId);
				if(docPathFile != null){
					parentId = docPathFile.getId();
				}
				baseDirPath += Constant.separator + parentFilePath;
			}
		}
		//取得当前上传文件的文件名称
		String originalFilename = file.getOriginalFilename();
		//生成文件名
//		String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.indexOf("."));
		String fileName = originalFilename;
		FileUtil.uploadFile(fileBytes, baseDirPath, fileName);
		docFile.setNewFileName(fileName);
		docFile.setFileName(originalFilename);
		docFile.setFilePath(parentFilePath);
		docFile.setParentId(parentId);
		docFile.setIsDir(0L);
		docFileService.save(docFile);

		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	private DocFileEntity loopDirPath(String baseDirPath, String dirFilePath, Long parentId){
		if(StringUtil.isEmpty(dirFilePath)){
			return null;
		}
		String[] dirPathArr = dirFilePath.trim().split(Constant.separator);
		String dirPathTemp = null;
		String filePath = null;
		DocFileEntity docFile = null;
		for(String path : dirPathArr){
			if(null == dirPathTemp) {
				dirPathTemp = path;
				filePath = path;
			}
			else {
				dirPathTemp += Constant.separator + path;
				filePath = dirPathTemp.substring(0, dirPathTemp.lastIndexOf(Constant.separator));
			}
			if(FileUtil.mkDir(baseDirPath, dirPathTemp) == 1){//new dir
				docFile = new DocFileEntity();
	    		docFile.setIsDir(1L);
	    		docFile.setFileName(path);
	    		docFile.setParentId(parentId);
	    		docFile.setNewFileName(path);
	    		docFile.setFilePath(filePath);
	    		docFile.setCreateDate(new Date());
	    		docFileService.save(docFile);
	    		parentId = docFile.getId();
			}
		}
		
		return docFile;
	}
}
