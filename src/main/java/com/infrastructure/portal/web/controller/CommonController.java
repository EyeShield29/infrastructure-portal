package com.infrastructure.portal.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;
import com.infrastructure.portal.service.common.FileService;
import com.infrastructure.portal.service.document.DocumentInfoService;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
@RequestMapping(value = "/common")
public class CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
    
    @Autowired
    private DocumentInfoService documentInfoService;
    
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	@ResponseBody
	public String fileupload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		AjaxData ajaxdata = null;
		try {
			MultipartFile mf = multipartRequest.getFile("file");			
			String path = multipartRequest.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
			String fileuri = FileService.saveFile(mf, mf.getOriginalFilename(), path);
			JsonObject data = new JsonObject();
			data.addProperty("fileuri", fileuri);
			data.addProperty("fileurl", path+fileuri);
			data.addProperty("size", (int)(mf.getSize()/1024));
			
			ajaxdata = new AjaxData(true, data, null);
		} catch (Exception e) {
			ajaxdata = new AjaxData(false, null, "文件上传失败！" + e.getMessage());
			LOGGER.error(e.getMessage());
		}

		MVCUtil.ajaxJson(ajaxdata);
		return null;
	}
	
    @RequestMapping(value = "/fileDownloadTwo", method = RequestMethod.GET)  
	public void fileDownLoad(@RequestParam(value = "fileUrl") String fileUrl, HttpServletRequest request, HttpServletResponse response){  
	    FileInputStream fos = null;  
	    ServletOutputStream sos = null;  
	    try{  
	        //String fileUrl = request.getParameter("fileUrl");  
	        response.setHeader("Content-Disposition", "attachment;");  
	        response.setContentType("application/octet-stream");               
	        response.setContentType("application/OCTET-STREAM;charset=UTF-8");  
	        byte b[] = new byte[1024*1024*1];//1M  
	        int read = 0;  
	        fos = new FileInputStream(new File(fileUrl));   
	        sos = response.getOutputStream();  
	        while((read=fos.read(b))!=-1){  
	            sos.write(b,0,read);//每次写1M  
	        }  
	        //OutputUtil.jsonOutPut(response, null);  
	    }catch (Exception e) {  
	        throw new RuntimeException("");  
	    }finally{  
	        try {  
	            if(sos!=null){  
	                sos.close();  
	            }  
	            if(fos!=null){  
	                fos.close();  
	            }  
	        } catch (IOException e) {  
	            throw new RuntimeException("");  
	        }  
	    }  
	}
    
    @RequestMapping(value = "/fileDownload", method = RequestMethod.GET)  
    public ResponseEntity<byte[]> fileDownLoad(@RequestParam(value = "fileUri") String fileUri, @RequestParam(value = "fileName") String fileName, @RequestParam(value = "documentId") int id, HttpServletRequest request) throws IOException {
        String dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
        String fileUrl = path+fileUri;
        String subfix=fileUri.substring(fileUri.lastIndexOf("."));
        ResponseEntity<byte[]> res = null;
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", dfileName+subfix);
            File file = new File(fileUrl);
            res = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers,
                    HttpStatus.CREATED);            
            documentInfoService.addDownloadTimes(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return res;
    }
    
}
