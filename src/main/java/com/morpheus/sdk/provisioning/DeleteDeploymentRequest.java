package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpDelete;

/**
 * A request object for defining a request to deleting an existing {@link Deployment} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteDeploymentRequest request = new DeleteDeploymentRequest();
 *     	request.deploymentId(1);
 *     	DeleteDeploymentResponse response = client.deleteDeployment(request);
 *     	return response.success;
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class DeleteDeploymentRequest extends AbstractApiRequest<DeleteDeploymentResponse> {
	private Long deploymentId;

	@Override
	public DeleteDeploymentResponse executeRequest() throws MorpheusApiRequestException {
		return (DeleteDeploymentResponse) RequestHelper.executeRequest(DeleteDeploymentResponse.class, this, "/api/deployments/" + deploymentId, HttpDelete.METHOD_NAME);
	}

	public Long getDeploymentId() {
		return deploymentId;
	}

	public DeleteDeploymentRequest deploymentId(Long deploymentId) {
		this.deploymentId = deploymentId;
		return this;
	}
}
