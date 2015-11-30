package com.morpheus.sdk.deployment;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link CreateDeployRequest} providing the de-serialized objects from the API.
 * @author David Estes
 */
public class CreateDeployResponse {
	public Boolean success;
	public AppDeploy appDeploy;

	public static CreateDeployResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,CreateDeployResponse.class);
	}
}
