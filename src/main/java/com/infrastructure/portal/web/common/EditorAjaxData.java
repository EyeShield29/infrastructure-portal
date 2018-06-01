package com.infrastructure.portal.web.common;

import com.google.gson.JsonObject;

public class EditorAjaxData {
	private int error;
	private String url;
	private String message;
	private String width;

	public EditorAjaxData(boolean status, String url, String message,
			String width) {
		if (status) {
			this.error = 0;
		} else {
			this.error = 1;
		}
		this.url = url;
		this.message = message;
		this.width = width;
	}

	public JsonObject getData() {
		JsonObject result = new JsonObject();
		result.addProperty("error", error);
		if (error == 0) {
			result.addProperty("url", url);
			if (width != null) {
				result.addProperty("width", width);
			}
		} else {
			result.addProperty("message", message);
		}
		return result;
	}
}
