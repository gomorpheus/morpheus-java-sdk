package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.provisioning.Artifact;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpGet;

/**
 * A request object for defining a request for fetching a list of artifacts within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a list of {@link Artifact} objects.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	ListArtifactsRequest request = new ListArtifactsRequest();
 *     	ListArtifactsResponse response = client.listArtifacts(request);
 *     	return response.artifacts;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class ListArtifactsRequest extends AbstractApiRequest<ListArtifactsResponse> {
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public ListArtifactsResponse executeRequest() throws MorpheusApiRequestException {
    return (ListArtifactsResponse) RequestHelper.executeRequest(ListArtifactsResponse.class, this, "/api/artifacts", HttpGet.METHOD_NAME);
  }

}
