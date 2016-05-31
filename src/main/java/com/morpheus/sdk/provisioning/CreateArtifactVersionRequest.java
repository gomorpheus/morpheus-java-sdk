package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.provisioning.ArtifactVersion;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPost;

import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request to create a new {@link ArtifactVersion} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	ArtifactVersion artifactVersion = new ArtifactVersion();
 *     	artifactVersion.userVersion = "1.0";
 *     	CreateArtifactVersionRequest request = new CreateArtifactVersionRequest().artifactId(1).artifactVersion(artifactVersion);
 *     	CreateArtifactVersionResponse response = client.createArtifactVersion(request);
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class CreateArtifactVersionRequest extends AbstractApiRequest<CreateArtifactVersionResponse> {
	private Long artifactId;
	private ArtifactVersion artifactVersion;

	@Override
	public CreateArtifactVersionResponse executeRequest() throws MorpheusApiRequestException {
		return (CreateArtifactVersionResponse) RequestHelper.executeRequest(CreateArtifactVersionResponse.class, this, "/api/artifacts/" + artifactId + "/versions", HttpPost.METHOD_NAME);
	}

	protected String generateRequestBody() {
		Gson gson = new Gson();
		Map<String,ArtifactVersion> deployMap = new HashMap<String,ArtifactVersion>();
		deployMap.put("version", artifactVersion);
		return gson.toJson(deployMap);
	}

	public CreateArtifactVersionRequest artifactId(Long artifactId) {
		this.artifactId = artifactId;
		return this;
	}

	public ArtifactVersion getArtifactVersion() {
		return artifactVersion;
	}

	public CreateArtifactVersionRequest artifactVersion(ArtifactVersion artifactVersion) {
		this.artifactVersion = artifactVersion;
		return this;
	}
}
