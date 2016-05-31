package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.provisioning.Artifact;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPut;

/**
 * A request object for defining a request for fetching a specific Artifact within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link Artifact} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetArtifactRequest request = new GetArtifactRequest();
 *     	request.setArtifactId(1);
 *     	GetArtifactResponse response = client.getArtifact(request);
 *     	return response.artifact;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class GetArtifactRequest extends AbstractApiRequest<GetArtifactResponse> {
  private Long artifactId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetArtifactResponse executeRequest() throws MorpheusApiRequestException {
    return (GetArtifactResponse) RequestHelper.executeRequest(GetArtifactResponse.class, this, "/api/artifacts/" + artifactId, HttpPut.METHOD_NAME);
  }

  public Long getArtifactId() {
    return artifactId;
  }

  public void setArtifactId(Long artifactId) {
    this.artifactId = artifactId;
  }

  public GetArtifactRequest artifactId(Long artifactId) {
    this.artifactId = artifactId;
    return this;
  }
}