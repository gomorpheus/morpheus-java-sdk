package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPut;

import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request for updating a specific deployment version within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to update a specific {@link DeploymentVersion} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	UpdateDeploymentVersionRequest request = new UpdateDeploymentVersionRequest().deploymentId(1).deploymentVersionId(1).deploymentVersion(updatedDeploymentVersion)
 *     	UpdateDeploymentVersionResponse response = client.updateDeploymentVersion(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class UpdateDeploymentVersionRequest extends AbstractApiRequest<UpdateDeploymentVersionResponse> {
	private Long deploymentId;
	private Long deploymentVersionId;
	private DeploymentVersion deploymentVersion;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public UpdateDeploymentVersionResponse executeRequest() throws MorpheusApiRequestException {
		return (UpdateDeploymentVersionResponse)RequestHelper.executeRequest(UpdateDeploymentVersionResponse.class, this, "/api/deployments/" + deploymentId + "/versions/" + deploymentVersionId,  HttpPut.METHOD_NAME);
	}

	@Override
	protected String generateRequestBody() {
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		final Gson gson = builder.create();
		Map<String,DeploymentVersion> deployMap = new HashMap<String,DeploymentVersion>();
		deployMap.put("version", deploymentVersion);
		return gson.toJson(deployMap);
	}

	public Long getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(Long deploymentId) {
		this.deploymentId = deploymentId;
	}

	public Long getDeploymentVersionId() {
		return deploymentVersionId;
	}

	public void setDeploymentVersionId(Long deploymentVersionId) {
		this.deploymentVersionId = deploymentVersionId;
	}

	public UpdateDeploymentVersionRequest deploymentId(Long deploymentId) {
		this.deploymentId = deploymentId;
		return this;
	}

	public UpdateDeploymentVersionRequest deploymentVersionId(Long deploymentVersionId) {
		this.deploymentVersionId = deploymentVersionId;
		return this;
	}

	public DeploymentVersion getDeploymentVersion() {
		return deploymentVersion;
	}

	public void setDeploymentVersion(DeploymentVersion deploymentVersion) {
		this.deploymentVersion = deploymentVersion;
	}

	public UpdateDeploymentVersionRequest deploymentVersion(DeploymentVersion deploymentVersion) {
		this.deploymentVersion = deploymentVersion;
		return this;
	}
}
