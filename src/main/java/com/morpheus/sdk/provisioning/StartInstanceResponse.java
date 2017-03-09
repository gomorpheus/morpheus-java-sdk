package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.infrastructure.Server;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link ProvisionServerRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class StartInstanceResponse {
	public Boolean success;
	public String name;
	public String message;
	public Server server;
	public HashMap<String, String> errors;

	public static StartInstanceResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,StartInstanceResponse.class);
	}
}
