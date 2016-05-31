package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.infrastructure.SecurityGroup;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPost;

import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request to create a new {@link Artifact} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	Artifact artifact = new Artifact();
 *     	artifact.name = "New Artifact Name";
 *     	CreateArtifactRequest request = new CreateArtifactRequest();
 *     	CreateArtifactResponse response = client.createArtifact(request);
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class CreateArtifactRequest extends AbstractApiRequest<CreateArtifactResponse> {
	private Artifact artifact;

	@Override
	public CreateArtifactResponse executeRequest() throws MorpheusApiRequestException {
		return (CreateArtifactResponse) RequestHelper.executeRequest(CreateArtifactResponse.class, this, "/api/artifacts", HttpPost.METHOD_NAME);
	}

	protected String generateRequestBody() {
		Gson gson = new Gson();
		Map<String,Artifact> deployMap = new HashMap<String,Artifact>();
		deployMap.put("artifact", artifact);
		return gson.toJson(deployMap);
	}

	public Artifact getArtifact() {
		return artifact;
	}

	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}

	public CreateArtifactRequest artifact(Artifact artifact) {
		this.artifact = artifact;
		return this;
	}
}
