package com.morpheus.sdk.monitoring;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link DeleteCheckRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class DeleteCheckResponse {
	public int status;
	public String msg;
	public Boolean success;

	public static DeleteCheckResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,DeleteCheckResponse.class);
	}
}
