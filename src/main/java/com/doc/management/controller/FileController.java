package com.doc.management.controller;

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.grapecity.documents.excel.IWorksheet;
import com.grapecity.documents.excel.Workbook;

@RestController
public class FileController {

	@Value("${filedir.path}")
	private String dirPath;
	
	@CrossOrigin
	@RequestMapping(value = "/api/loadExcel/{id}", method = RequestMethod.GET)
	public String loadExcel(@PathVariable("id") Integer id) {
		try {
//			String filePath = "E:\\vue3\\新建 Microsoft Excel 工作表.xlsx";
			String workbookJSON;
			Workbook workbook = new Workbook();
			workbook.open(dirPath + File.separator + "新建 Microsoft Excel 工作表.xlsx");

			IWorksheet sheet = workbook.getWorksheets().get("Sheet1");
			sheet.getRange("C4:AA6").setLocked(false);
			sheet.setProtection(true);

			workbookJSON = workbook.toJson();
			return workbookJSON;
//			return "workbookJSON workbookJSON";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/loadExcels/{filePath}", method = RequestMethod.GET)
	public ResponseEntity getFile(HttpServletResponse response,@PathVariable(value = "filePath", required = true) String filePath) {
		if("2".equals(filePath)){
			filePath = "gitcodeDocmanagement\\documentmanagement\\public\\11.docx";
		}
		else{
			filePath = dirPath + File.separator + "新建 Microsoft Excel 工作表.xlsx";
		}
		File file = new File(filePath);
		if(file.exists() && !file.isDirectory()) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        headers.add("Pragma", "no-cache");
	        headers.add("Expires", "0");
	        headers.add("Last-Modified", new Date().toString());
	        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
	        try {
				headers.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
			
	        URL url = new URL("file:///" + file);
	        headers.add("Content-Type",url.openConnection().getContentType());
            // 下载成功返回二进制流
	        return  ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentLength(file.length())
//	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//	                .contentType(MediaType.parseMediaType("application/octet-stream"))
	                .contentType(MediaType.parseMediaType("application/vnd.oasis.opendocument.spreadsheet;charset=utf-8"))
	                .body(FileUtils.readFileToByteArray(file));
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<String>("not content 204", HttpStatus.NO_CONTENT);
			}
		}else {
			return new ResponseEntity<String>("not content 204", HttpStatus.NO_CONTENT);
		}
	}
}
