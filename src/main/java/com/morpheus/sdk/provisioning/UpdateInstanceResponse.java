package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.infrastructure.ServerGroup;
import com.morpheus.sdk.infrastructure.UpdateServerGroupRequest;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link UpdateInstanceRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class UpdateInstanceResponse {
	public Boolean success;
	public Instance instance;

	public static UpdateInstanceResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,UpdateInstanceResponse.class);
	}
}
