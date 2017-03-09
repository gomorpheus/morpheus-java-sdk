package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPost;

import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request to create a new {@link DeploymentVersion} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeploymentVersion deploymentVersion = new DeploymentVersion();
 *     	deploymentVersion.userVersion = "1.0";
 *     	CreateDeploymentVersionRequest request = new CreateDeploymentVersionRequest().deploymentId(1).deploymentVersion(deploymentVersion);
 *     	CreateDeploymentVersionResponse response = client.createDeploymentVersion(request);
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class CreateDeploymentVersionRequest extends AbstractApiRequest<CreateDeploymentVersionResponse> {
	private Long deploymentId;
	private DeploymentVersion deploymentVersion;

	@Override
	public CreateDeploymentVersionResponse executeRequest() throws MorpheusApiRequestException {
		return (CreateDeploymentVersionResponse) RequestHelper.executeRequest(CreateDeploymentVersionResponse.class, this, "/api/deployments/" + deploymentId + "/versions", HttpPost.METHOD_NAME);
	}

	protected String generateRequestBody() {
		Gson gson = MorpheusGsonBuilder.build();
		Map<String,DeploymentVersion> deployMap = new HashMap<String,DeploymentVersion>();
		deployMap.put("version", deploymentVersion);
		return gson.toJson(deployMap);
	}

	public CreateDeploymentVersionRequest deploymentId(Long deploymentId) {
		this.deploymentId = deploymentId;
		return this;
	}

	public DeploymentVersion getDeploymentVersion() {
		return deploymentVersion;
	}

	public CreateDeploymentVersionRequest deploymentVersion(DeploymentVersion deploymentVersion) {
		this.deploymentVersion = deploymentVersion;
		return this;
	}
}
