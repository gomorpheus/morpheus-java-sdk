package com.morpheus.sdk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;

public class MorpheusGsonBuilder {

	public static Gson build() {
		return new GsonBuilder().registerTypeAdapter(Date.class, new CustomDateJsonSerializer()).create();
	}

}
