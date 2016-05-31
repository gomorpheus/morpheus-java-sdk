package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.provisioning.ArtifactVersion;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpDelete;

/**
 * A request object for defining a request to deleting an existing {@link ArtifactVersion} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteArtifactVersionRequest request = new DeleteArtifactVersionRequest().artifactId(1).artifactVersionId(1);
 *     	DeleteArtifactVersionResponse response = client.deleteArtifactVersion(request);
 *     	return response.success;
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class DeleteArtifactVersionRequest extends AbstractApiRequest<DeleteArtifactVersionResponse> {
	private Long artifactId;
	private Long artifactVersionId;

	@Override
	public DeleteArtifactVersionResponse executeRequest() throws MorpheusApiRequestException {
		return (DeleteArtifactVersionResponse) RequestHelper.executeRequest(DeleteArtifactVersionResponse.class, this, "/api/artifacts/" + artifactId + "/versions/" + artifactVersionId, HttpDelete.METHOD_NAME);
	}

	public Long getArtifactId() {
		return artifactId;
	}

	public DeleteArtifactVersionRequest artifactId(Long artifactId) {
		this.artifactId = artifactId;
		return this;
	}

	public Long getArtifactVersionId() {
		return artifactVersionId;
	}

	public DeleteArtifactVersionRequest artifactVersionId(Long artifactVersionId) {
		this.artifactVersionId = artifactVersionId;
		return this;
	}
}
