package com.infrastructure.portal.web.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MVCUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MVCUtil.class);
	private static final String userSessionKey = "admin";
	private static final ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> currentResponse = new ThreadLocal<HttpServletResponse>();

	public static void setCurrentSession(HttpServletRequest request,
			HttpServletResponse response) {
		currentRequest.set(request);
		currentResponse.set(response);
	}

	public static void removeCurrentSession() {
		currentRequest.remove();
		currentResponse.remove();
	}

	public static String getParam(String name) {
		Map<String, String[]> params = currentRequest.get().getParameterMap();
		if (params != null && params.get(name) != null) {
			return params.get(name)[0];
		}
		return null;
	}

	public static String[] getParamArray(String name) {
		Map<String, String[]> params = currentRequest.get().getParameterMap();
		if (params != null && params.get(name) != null) {
			System.out.println(name + " param : "+params.get(name));
		    return params.get(name);
		}
		return null;
	}

	public static int[] getIntParamArray(String name) {
		int[] intparams = new int[0];
		String[] params = getParamArray(name);
		if (params != null && params.length > 0) {
			intparams = new int[params.length];
			for (int i = 0; i < params.length; i++) {
				intparams[i] = Integer.parseInt(params[i]);
			}
		}
		return intparams;
	}

	public static int getIntParam(String name) {
		Map<String, String[]> params = currentRequest.get().getParameterMap();
		if (params != null && params.get(name) != null) {
			if (StringUtils.isNumeric(params.get(name)[0])) {
				return Integer.parseInt(params.get(name)[0]);
			}
		}
		return 0;
	}

	public static void setSessionAttribute(String key, Object value) {
		currentRequest.get().getSession().setAttribute(key, value);
	}

	public static void removeSessionAttribute(String key) {
		currentRequest.get().getSession().removeAttribute(key);
	}

	public static Object getSessionAttribute(String key) {
		return currentRequest.get().getSession().getAttribute(key);
	}

	public static void setUserSession(Object value) {
		setSessionAttribute(userSessionKey, value);
	}

	public static void removeUserSession() {
		removeSessionAttribute(userSessionKey);
	}

	public static Object getUserSession() {
		return getSessionAttribute(userSessionKey);
	}

	public static void ajaxJson(AjaxData ajaxdata) {
		PrintWriter printWriter = null;
		try {
			HttpServletResponse response = currentResponse.get();
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			printWriter = response.getWriter();
			printWriter.write(ajaxdata.getData().toString());
		} catch (Exception e) {
			LOGGER.error("MVCUtil.ajaxJson faild!", e);
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	public static void ajaxJson(String data) {
		PrintWriter printWriter = null;
		try {
			HttpServletResponse response = currentResponse.get();
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			printWriter = response.getWriter();
			printWriter.write(data);
		} catch (Exception e) {
			LOGGER.error("MVCUtil.ajaxJson faild!", e);
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	public static void writeString(String responsedata, int debug) {
		PrintWriter printWriter = null;
		try {
			HttpServletResponse response = currentResponse.get();
			response.setContentType("text/html;charset=UTF-8");
			if (debug == 1) {
				response.setHeader("Pragma", "No-cache");
				response.setDateHeader("Expires", 0);
			} else {
				response.setHeader("Cache-Control", "max-age=" + 60 * 5);
			}
			// response.setHeader("Access-Control-Allow-Origin", "*");
			printWriter = response.getWriter();
			printWriter.write(responsedata);
		} catch (Exception e) {
			LOGGER.error("MVCUtil.writeString faild!", e);
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	public static void returnFile(String fileData, String fileName)
			throws IOException {
		ServletOutputStream out = null;
		try {
			HttpServletResponse response = currentResponse.get();
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/msword");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			out = response.getOutputStream();
			out.write(fileData.getBytes("UTF-8"));
		} catch (Exception e) {
			LOGGER.error("MVCUtil.ajaxJson faild!", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static void returndata(String reponsedata) {
		PrintWriter printWriter = null;
		try {
			HttpServletResponse response = currentResponse.get();
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);
			printWriter = response.getWriter();
			printWriter.write(reponsedata);
		} catch (Exception e) {
			LOGGER.error("MVCUtil.returndata faild!", e);
		}
	}
}
