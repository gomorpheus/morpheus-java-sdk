package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.infrastructure.SecurityGroupRule;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link UpdateArtifactVersionRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class UpdateArtifactVersionResponse implements ApiResponse {
	public Boolean success;
	@SerializedName("version")
	public ArtifactVersion artifactVersion;

	public static UpdateArtifactVersionResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		UpdateArtifactVersionResponse response =  gson.fromJson(reader,UpdateArtifactVersionResponse.class);
		return response;
	}
}
