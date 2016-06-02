package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPut;

/**
 * A request object for defining a request for fetching a specific Deployment within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link Deployment} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetDeploymentRequest request = new GetDeploymentRequest();
 *     	request.setDeploymentId(1);
 *     	GetDeploymentResponse response = client.getDeployment(request);
 *     	return response.deployment;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class GetDeploymentRequest extends AbstractApiRequest<GetDeploymentResponse> {
  private Long deploymentId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetDeploymentResponse executeRequest() throws MorpheusApiRequestException {
    return (GetDeploymentResponse) RequestHelper.executeRequest(GetDeploymentResponse.class, this, "/api/deployments/" + deploymentId, HttpPut.METHOD_NAME);
  }

  public Long getDeploymentId() {
    return deploymentId;
  }

  public void setDeploymentId(Long deploymentId) {
    this.deploymentId = deploymentId;
  }

  public GetDeploymentRequest deploymentId(Long deploymentId) {
    this.deploymentId = deploymentId;
    return this;
  }
}