package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.infrastructure.SecurityGroup;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link CreateArtifactRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class CreateArtifactResponse implements ApiResponse {
	public HashMap<String,String> errors;
	public Boolean success;
	@SerializedName("artifact")
	public Artifact artifact;

	public static CreateArtifactResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,CreateArtifactResponse.class);
	}
}
