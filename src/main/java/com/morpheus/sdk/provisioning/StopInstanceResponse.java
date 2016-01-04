package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link StopInstanceRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class StopInstanceResponse {
	public Boolean success;

	public static StopInstanceResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,StopInstanceResponse.class);
	}
}
