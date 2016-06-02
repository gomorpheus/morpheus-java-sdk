package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpDelete;

/**
 * A request object for defining a request to deleting an existing {@link DeploymentVersion} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteDeploymentVersionRequest request = new DeleteDeploymentVersionRequest().deploymentId(1).deploymentVersionId(1);
 *     	DeleteDeploymentVersionResponse response = client.deleteDeploymentVersion(request);
 *     	return response.success;
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class DeleteDeploymentVersionRequest extends AbstractApiRequest<DeleteDeploymentVersionResponse> {
	private Long deploymentId;
	private Long deploymentVersionId;

	@Override
	public DeleteDeploymentVersionResponse executeRequest() throws MorpheusApiRequestException {
		return (DeleteDeploymentVersionResponse) RequestHelper.executeRequest(DeleteDeploymentVersionResponse.class, this, "/api/deployments/" + deploymentId + "/versions/" + deploymentVersionId, HttpDelete.METHOD_NAME);
	}

	public Long getDeploymentId() {
		return deploymentId;
	}

	public DeleteDeploymentVersionRequest deploymentId(Long deploymentId) {
		this.deploymentId = deploymentId;
		return this;
	}

	public Long getDeploymentVersionId() {
		return deploymentVersionId;
	}

	public DeleteDeploymentVersionRequest deploymentVersionId(Long deploymentVersionId) {
		this.deploymentVersionId = deploymentVersionId;
		return this;
	}
}
