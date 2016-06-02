package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpGet;

/**
 * A request object for defining a request for fetching a specific Deployment Version within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link DeploymentVersion} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetDeploymentVersionRequest request = new GetDeploymentVersionRequest().deploymentId(1).deploymentVersionId(1);
 *     	GetDeploymentVersionResponse response = client.getDeploymentVersion(request);
 *     	return response.deploymentVersion;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class GetDeploymentVersionRequest extends AbstractApiRequest<GetDeploymentVersionResponse> {

  private Long deploymentId;
  private Long deploymentVersionId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetDeploymentVersionResponse executeRequest() throws MorpheusApiRequestException {
    return (GetDeploymentVersionResponse) RequestHelper.executeRequest(GetDeploymentVersionResponse.class, this, "/api/deployments/" + deploymentId + "/versions/" + deploymentVersionId, HttpGet.METHOD_NAME);
  }

  public GetDeploymentVersionRequest deploymentId(Long deploymentId) {
    this.deploymentId = deploymentId;
    return this;
  }

  public Long getDeploymentId() {
    return this.deploymentId;
  }

  public GetDeploymentVersionRequest deploymentVersionId(Long deploymentVersionId) {
    this.deploymentVersionId = deploymentVersionId;
    return this;
  }

  public Long getDeploymentVersionId() {
    return deploymentVersionId;
  }
}