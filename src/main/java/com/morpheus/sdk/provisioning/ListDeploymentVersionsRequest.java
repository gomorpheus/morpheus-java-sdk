package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpGet;

/**
 * A request object for defining a request for fetching a list of deployment versions within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a list of {@link DeploymentVersion} objects.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *
 *     	ListDeploymentVersionsRequest request = new ListDeploymentVersionsRequest().deploymentId(1);
 *     	ListDeploymentVersionsResponse response = client.listDeploymentVersions(request);
 *     	return response.rules;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class ListDeploymentVersionsRequest extends AbstractApiRequest<ListDeploymentVersionsResponse> {

  private Long deploymentId;

  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public ListDeploymentVersionsResponse executeRequest() throws MorpheusApiRequestException {
    return (ListDeploymentVersionsResponse) RequestHelper.executeRequest(ListDeploymentVersionsResponse.class, this, "/api/deployments/" + deploymentId + "/versions", HttpGet.METHOD_NAME);
  }

  public ListDeploymentVersionsRequest deploymentId(Long deploymentId) {
    this.deploymentId = deploymentId;
    return this;
  }

  public Long getDeploymentId() {
    return this.deploymentId;
  }

}
