package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.infrastructure.ServerGroup;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Response of a {@link CloneInstanceRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class CloneInstanceResponse {
	public Boolean success;
	public String msg;
	public Instance instance;
	@SerializedName("sites")
	public ArrayList<ServerGroup> serverGroups;
	public HashMap<String, String> errors;

	public static CloneInstanceResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,CloneInstanceResponse.class);
	}
}
