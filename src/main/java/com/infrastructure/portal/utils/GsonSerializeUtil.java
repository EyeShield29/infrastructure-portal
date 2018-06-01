package com.infrastructure.portal.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class GsonSerializeUtil {
	private final static Gson GSON = new Gson();

	public static JsonObject toJsonObject(Object obj) {
		if (obj != null) {
			return GSON.toJsonTree(obj).getAsJsonObject();
		} else {
			return new JsonObject();
		}
	}

	public static <T> JsonArray toJsonArray(List<T> objs, Class<T> clazz) {
		if (objs != null) {
			return GSON.toJsonTree(objs, new TypeToken<List<T>>() {
			}.getType()).getAsJsonArray();
		} else {
			return new JsonArray();
		}
	}

	public static <T> T fromJson(JsonObject jsonObject, Class<T> clazz) {
		if (jsonObject != null) {
			return GSON.fromJson(jsonObject, clazz);
		} else {
			return null;
		}
	}

	public static <T> List<T> fromJsonArray(JsonArray jsonArray, Class<T> clazz) {
		if (jsonArray != null && jsonArray.size() > 0) {
			return GSON.fromJson(jsonArray, new TypeToken<List<T>>() {
			}.getType());
		} else {
			return new ArrayList<T>();
		}
	}
}
