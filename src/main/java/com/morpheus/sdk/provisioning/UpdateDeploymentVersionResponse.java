package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link UpdateDeploymentVersionRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class UpdateDeploymentVersionResponse implements ApiResponse {
	public Boolean success;
	@SerializedName("version")
	public DeploymentVersion deploymentVersion;

	public static UpdateDeploymentVersionResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		UpdateDeploymentVersionResponse response =  gson.fromJson(reader,UpdateDeploymentVersionResponse.class);
		return response;
	}
}
