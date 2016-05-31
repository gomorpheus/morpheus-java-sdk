package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.provisioning.Artifact;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpDelete;

/**
 * A request object for defining a request to deleting an existing {@link Artifact} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteArtifactRequest request = new DeleteArtifactRequest();
 *     	request.artifactId(1);
 *     	DeleteArtifactResponse response = client.deleteArtifact(request);
 *     	return response.success;
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class DeleteArtifactRequest extends AbstractApiRequest<DeleteArtifactResponse> {
	private Long artifactId;

	@Override
	public DeleteArtifactResponse executeRequest() throws MorpheusApiRequestException {
		return (DeleteArtifactResponse) RequestHelper.executeRequest(DeleteArtifactResponse.class, this, "/api/artifacts/" + artifactId, HttpDelete.METHOD_NAME);
	}

	public Long getArtifactId() {
		return artifactId;
	}

	public DeleteArtifactRequest artifactId(Long artifactId) {
		this.artifactId = artifactId;
		return this;
	}
}
