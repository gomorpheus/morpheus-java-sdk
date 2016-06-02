package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPost;

import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request to create a new {@link Deployment} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	Deployment deployment = new Deployment();
 *     	deployment.name = "New Deployment Name";
 *     	CreateDeploymentRequest request = new CreateDeploymentRequest();
 *     	CreateDeploymentResponse response = client.createDeployment(request);
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class CreateDeploymentRequest extends AbstractApiRequest<CreateDeploymentResponse> {
	private Deployment deployment;

	@Override
	public CreateDeploymentResponse executeRequest() throws MorpheusApiRequestException {
		return (CreateDeploymentResponse) RequestHelper.executeRequest(CreateDeploymentResponse.class, this, "/api/deployments", HttpPost.METHOD_NAME);
	}

	protected String generateRequestBody() {
		Gson gson = new Gson();
		Map<String,Deployment> deployMap = new HashMap<String,Deployment>();
		deployMap.put("deployment", deployment);
		return gson.toJson(deployMap);
	}

	public Deployment getDeployment() {
		return deployment;
	}

	public void setDeployment(Deployment deployment) {
		this.deployment = deployment;
	}

	public CreateDeploymentRequest deployment(Deployment deployment) {
		this.deployment = deployment;
		return this;
	}
}
