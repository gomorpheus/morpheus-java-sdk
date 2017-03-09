package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link ResizeInstanceRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class ResizeInstanceResponse {
	public Boolean success;
	public String msg;
	public Instance instance;
	public HashMap<String, String> errors;

	public static ResizeInstanceResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,ResizeInstanceResponse.class);
	}
}
