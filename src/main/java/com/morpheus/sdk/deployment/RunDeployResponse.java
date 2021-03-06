package com.morpheus.sdk.deployment;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link RunDeployRequest} providing the de-serialized objects from the API.
 * @author David Estes
 */
public class RunDeployResponse implements ApiResponse {

	@SerializedName("appDeploy")
	public AppDeploy appDeploy;

	public static RunDeployResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,RunDeployResponse.class);
	}
}
