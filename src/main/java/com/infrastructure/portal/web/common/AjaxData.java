package com.infrastructure.portal.web.common;

import com.google.gson.JsonObject;

public class AjaxData {
	private int status;
	private String errormsg;
	private JsonObject data;

	/**
	 * @param status
	 *            :true标示执行成功,false执行失败
	 * @param jsondata
	 *            :返回的json数据
	 * @param errormsg
	 *            :错误信息，仅当status=false才有
	 */
	public AjaxData(boolean status, JsonObject data, String errormsg) {
		if (status) {
			this.status = 1;
		} else {
			this.status = 0;
		}
		this.data = data;
		this.errormsg = errormsg;
	}

	public JsonObject getData() {
		JsonObject result = new JsonObject();
		result.addProperty("status", status);
		if (errormsg != null) {
			result.addProperty("errormsg", errormsg);
		}
		if (data != null) {
			result.add("data", data);
		}
		return result;
	}
}
