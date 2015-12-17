package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.infrastructure.Server;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link UpgradeInstanceRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class UpgradeInstanceResponse {
	public Boolean success;
	public String name;
	public String message;
	public Server server;
	public HashMap<String, String> errors;

	public static UpgradeInstanceResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,UpgradeInstanceResponse.class);
	}
}
