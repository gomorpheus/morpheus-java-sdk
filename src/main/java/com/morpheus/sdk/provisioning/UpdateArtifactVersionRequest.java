package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.provisioning.ArtifactVersion;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPut;

import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request for updating a specific artifact version within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to update a specific {@link ArtifactVersion} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	UpdateArtifactVersionRequest request = new UpdateArtifactVersionRequest().artifactId(1).artifactVersionId(1).artifactVersion(updatedArtifactVersion)
 *     	UpdateArtifactVersionResponse response = client.updateArtifactVersion(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class UpdateArtifactVersionRequest extends AbstractApiRequest<UpdateArtifactVersionResponse> {
	private Long artifactId;
	private Long artifactVersionId;
	private ArtifactVersion artifactVersion;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public UpdateArtifactVersionResponse executeRequest() throws MorpheusApiRequestException {
		return (UpdateArtifactVersionResponse)RequestHelper.executeRequest(UpdateArtifactVersionResponse.class, this, "/api/artifacts/" + artifactId + "/versions/" + artifactVersionId,  HttpPut.METHOD_NAME);
	}

	@Override
	protected String generateRequestBody() {
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		final Gson gson = builder.create();
		Map<String,ArtifactVersion> deployMap = new HashMap<String,ArtifactVersion>();
		deployMap.put("version", artifactVersion);
		return gson.toJson(deployMap);
	}

	public Long getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(Long artifactId) {
		this.artifactId = artifactId;
	}

	public Long getArtifactVersionId() {
		return artifactVersionId;
	}

	public void setArtifactVersionId(Long artifactVersionId) {
		this.artifactVersionId = artifactVersionId;
	}

	public UpdateArtifactVersionRequest artifactId(Long artifactId) {
		this.artifactId = artifactId;
		return this;
	}

	public UpdateArtifactVersionRequest artifactVersionId(Long artifactVersionId) {
		this.artifactVersionId = artifactVersionId;
		return this;
	}

	public ArtifactVersion getArtifactVersion() {
		return artifactVersion;
	}

	public void setArtifactVersion(ArtifactVersion artifactVersion) {
		this.artifactVersion = artifactVersion;
	}

	public UpdateArtifactVersionRequest artifactVersion(ArtifactVersion artifactVersion) {
		this.artifactVersion = artifactVersion;
		return this;
	}
}
