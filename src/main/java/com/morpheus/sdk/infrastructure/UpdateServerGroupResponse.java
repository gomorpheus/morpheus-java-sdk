package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.deployment.AppDeploy;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link UpdateServerGroupRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class UpdateServerGroupResponse {
	public Boolean success;
	@SerializedName("group")
	public ServerGroup serverGroup;

	public static UpdateServerGroupResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,UpdateServerGroupResponse.class);
	}
}
