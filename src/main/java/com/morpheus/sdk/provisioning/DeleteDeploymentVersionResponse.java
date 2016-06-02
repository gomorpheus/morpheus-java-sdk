package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link DeleteDeploymentVersionRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class DeleteDeploymentVersionResponse implements ApiResponse{
	public int status;
	public String msg;
	public Boolean success;

	public static DeleteDeploymentVersionResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,DeleteDeploymentVersionResponse.class);
	}
}
