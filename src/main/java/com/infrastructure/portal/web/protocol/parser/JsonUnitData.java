package com.infrastructure.portal.web.protocol.parser;

import com.google.gson.JsonObject;

public abstract class JsonUnitData {
	public String getResponseData() throws Exception {
		JsonObject responseData = parser();
		if (!responseData.has("code")) {
			responseData.addProperty("code", 1);
		}
		return responseData.toString();
	}

	public abstract JsonObject parser() throws Exception;
}
