package com.infrastructure.portal.service.common;

import java.io.File;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.JsonObject;
import com.infrastructure.portal.common.AppContext;
import com.infrastructure.portal.utils.DateUtils;


public class FileService {

    /**
     * 保存照片(.jpg)
     */
	public static JsonObject saveImageFile(byte[] fileBytes, String directory)
			throws Exception {
		JsonObject reponsedata = new JsonObject();
		try {
			String typedir = directory + "/";
			String date = DateUtils.parseDateToStr(new Date(), "yyyyMMdd") + "/";
			String newfilename = UUID.randomUUID().toString().replace("-", "");
			String parenturi = typedir + date
					+ newfilename.substring(newfilename.length() - 2) + "/";
			String suffix = ".jpg";
			newfilename = newfilename + suffix;
			// uri
			String fileuri = parenturi + newfilename;
			// 父目录
			String parentpath = AppContext.getFilepath() + parenturi;
			File parent = new File(parentpath);
			if (!parent.exists()) {
				parent.mkdirs();
			}
			// 文件地址
			String filepath = AppContext.getFilepath() + fileuri;
			// 保存文件
			FileUtils.writeByteArrayToFile(new File(filepath), fileBytes);
			reponsedata = new JsonObject();
			reponsedata.addProperty("image_uri", fileuri);
			
		} catch (Exception e) {
			//throw new Exception(ErrorCode.CODE_JAVA, "upload faild!", e);
		}
		return reponsedata;
	}
	/**
	 * 保存文件
	 */
	public static String saveFile(MultipartFile mf, String filename, String parentPath)
			throws Exception {
		String fileuri = "";
		try {
			String typedir = "\\file\\";
			String date = DateUtils.parseDateToStr(new Date(), "yyyyMMdd") + "\\";
			String newfilename = UUID.randomUUID().toString().replace("-", "");
			String parenturi = typedir + date
					+ newfilename.substring(newfilename.length() - 2) + "\\";
			String suffix = "";
			if (filename.contains(".")) {
				suffix = filename.substring(filename.lastIndexOf("."));
			}
			newfilename = newfilename + suffix;

			// uri
			fileuri = parenturi + newfilename;

			// 父目录
			//String parentpath = AppContext.getFilepath() + parenturi;
			String parentpath = parentPath + parenturi;
			File parent = new File(parentpath);
			if (!parent.exists()) {
				parent.mkdirs();
			}
			// 文件地址
			//String filepath = AppContext.getFilepath() + fileuri;
			
			//System.out.println(filepath);
			
			// 保存文件
			//mf.transferTo(new File(filepath));
			String filepath = parentPath + fileuri;
			System.out.println(filepath);
			mf.transferTo(new File(filepath));
		} catch (Exception e) {
			//throw new ServiceException(ErrorCode.CODE_JAVA, "upload faild!", e);
		}
		return fileuri;
	}
	
	/**
	 * 保存服务地区的照片
	 */
	public static String saveServiceAreaImageFile(byte[] fileBytes,String filename)
            throws Exception {
		String fileuri = "";
		try {
            String typedir = "image/service_area_image/Destination_list_";
            String parenturi = typedir;
            String suffix = "";
            String newfilename = filename + suffix;
            // uri
            fileuri = parenturi + newfilename;
            // 父目录
            String parentpath = AppContext.getFilepath() + parenturi;
            File parent = new File(parentpath);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            // 文件地址
            String filepath = AppContext.getFilepath() + fileuri;
            // 保存文件
            FileUtils.writeByteArrayToFile(new File(filepath), fileBytes);
            
        } catch (Exception e) {
            //throw new ServiceException(Errorcode.CODE_JAVA, "upload faild!", e);
        }
        return fileuri;
    }
}
