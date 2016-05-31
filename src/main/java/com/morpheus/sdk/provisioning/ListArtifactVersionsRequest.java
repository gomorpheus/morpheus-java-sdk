package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpGet;

/**
 * A request object for defining a request for fetching a list of artifact versions within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a list of {@link ArtifactVersion} objects.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *
 *     	ListArtifactVersionsRequest request = new ListArtifactVersionsRequest().artifactId(1);
 *     	ListArtifactVersionsResponse response = client.listArtifactVersions(request);
 *     	return response.rules;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class ListArtifactVersionsRequest extends AbstractApiRequest<ListArtifactVersionsResponse> {

  private Long artifactId;

  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public ListArtifactVersionsResponse executeRequest() throws MorpheusApiRequestException {
    return (ListArtifactVersionsResponse) RequestHelper.executeRequest(ListArtifactVersionsResponse.class, this, "/api/artifacts/" + artifactId + "/versions", HttpGet.METHOD_NAME);
  }

  public ListArtifactVersionsRequest artifactId(Long artifactId) {
    this.artifactId = artifactId;
    return this;
  }

  public Long getArtifactId() {
    return this.artifactId;
  }

}
