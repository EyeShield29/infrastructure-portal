package com.infrastructure.portal.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infrastructure.portal.service.common.SysconfigService;
import com.infrastructure.portal.utils.SpringContextHolder;

/**
 * 系统上下文配置
 * 
 * @author 
 * 
 */
public class AppContext {
	private static final Logger logger = LoggerFactory
			.getLogger(AppContext.class);
	public static boolean isstarted = false;
	public static boolean logswitch = false;// 请求和响应debug日志开关 （默认关闭）
	// public static JedisCache cache;
	private static SysconfigService sysconfigService;
	private static ScheduledExecutorService scheduler = null;
	private static String filedomain;
	private static String filepath = "F:/infrastructure-portal/upload";

	public static void init() throws Exception {
		AppContext.sysconfigService = SpringContextHolder
				.getBean(SysconfigService.class);
		// 初始化
		refreshConfig();
		// String redisServers = sysconfigService.getValue("REDIS_SERVERS");
		// Config_JedisCache jedisCacheConfig = new Config_JedisCache(
		// redisServers, null);
		// cache = new JedisCache(jedisCacheConfig);
		// 定时器
		scheduler = Executors.newScheduledThreadPool(2);//定义2个线程空间
		 // 从现在开始2分钟之后，每隔2分钟刷新配置信息
		scheduler.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					sysconfigService.updateConfig();
					AppContext.refreshConfig();
				} catch (Throwable e) {
					logger.error("AppContext.scheduler faild!", e);
				}
			}
		}, 2, 2, TimeUnit.MINUTES);
		isstarted = true;
	}

	public static void destroy() {
		isstarted = false;
	}

	public static void refreshConfig() {
		// 请求和响应debug日志开关
		if (sysconfigService.getInt("LOG_SWITCH") != null) {
			if (sysconfigService.getInt("LOG_SWITCH") == 1) {
				logswitch = true;
			} else {
				logswitch = false;
			}
		}

		if (StringUtils.isNotBlank(sysconfigService.getValue("FILE_DOMAIN"))) {
			filedomain = sysconfigService.getValue("FILE_DOMAIN");
		}

		if (StringUtils.isNotBlank(sysconfigService.getValue("FILE_PATH"))) {
			filepath = sysconfigService.getValue("FILE_PATH");
		}
	}

	public static String getValueFromSysConfig(String pkey) {
		return sysconfigService.getValue(pkey);
	}

	public static Integer getIntegerFromSysConfig(String pkey) {
		return sysconfigService.getInt(pkey);
	}

	public static String getFileUrl(String fileUri) {
		if (StringUtils.isBlank(fileUri)) {
			return "";
		} else if (!fileUri.startsWith("http")) {
			return AppContext.filedomain + fileUri;
		}
		return fileUri;
	}

	public static String getFiledomain() {
		return filedomain;
	}

	public static String getFilepath() {
		return filepath;
	}

}
