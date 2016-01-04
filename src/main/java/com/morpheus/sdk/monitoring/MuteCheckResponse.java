package com.morpheus.sdk.monitoring;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link MuteCheckRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class MuteCheckResponse {
	public String muteState;
	public HashMap<String, String> errors;

	public static MuteCheckResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,MuteCheckResponse.class);
	}
}
