package com.infrastructure.portal.web.interceptor;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.infrastructure.portal.entity.po.portal.PortalUser;

public class SessionInterceptor implements HandlerInterceptor {
	/**
	 * 可以忽略的请求集合
	 */
	private Set<String> ignorePaths = new HashSet<String>();

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		String currentPath = request.getRequestURI().substring(
				request.getRequestURI().lastIndexOf('/') + 1);
		if (request.getRequestURI().contains("/view/")) {
			return true;
		}
		PortalUser portalUser = (PortalUser) SecurityUtils.getSubject()
				.getPrincipal();
		if (portalUser != null) {
			MDCInterceptor.setUserKeyForMDC(portalUser.getId() + "");
		}
		if (!ignorePaths.contains(currentPath)) {
			//如果没有用户登陆，跳转回登陆界面
			if (portalUser == null) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<script>");
				out.println("window.open ('" + request.getContextPath()
						+ "/login','_top')");
				out.println("</script>");
				out.println("</html>");
				return false;
			}
		}
		//设置session超时失效为4h
		request.getSession().setMaxInactiveInterval(60 * 60 * 4);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	public void setIgnorePath(String ignorePath) {
		if (StringUtils.isNotBlank(ignorePath)) {
			String[] paths = ignorePath.split(",");
			for (String path : paths)
				ignorePaths.add(path);
		}
	}

}
