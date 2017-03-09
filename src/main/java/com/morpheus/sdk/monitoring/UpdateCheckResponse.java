package com.morpheus.sdk.monitoring;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.admin.KeyPair;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link UpdateCheckRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class UpdateCheckResponse {
	public Boolean success;
	public Check check;
	public HashMap<String, String> errors;

	public static UpdateCheckResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,UpdateCheckResponse.class);
	}
}
