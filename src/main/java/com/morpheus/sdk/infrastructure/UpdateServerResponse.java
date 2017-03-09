package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link UpdateServerRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class UpdateServerResponse {
	public Boolean success;
	public Server server;

	public static UpdateServerResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,UpdateServerResponse.class);
	}
}
