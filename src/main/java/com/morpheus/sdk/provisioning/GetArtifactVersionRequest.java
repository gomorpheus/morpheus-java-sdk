package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.provisioning.ArtifactVersion;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpGet;

/**
 * A request object for defining a request for fetching a specific Artifact Version within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link ArtifactVersion} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetArtifactVersionRequest request = new GetArtifactVersionRequest().artifactId(1).artifactVersionId(1);
 *     	GetArtifactVersionResponse response = client.getArtifactVersion(request);
 *     	return response.artifactVersion;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class GetArtifactVersionRequest extends AbstractApiRequest<GetArtifactVersionResponse> {

  private Long artifactId;
  private Long artifactVersionId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetArtifactVersionResponse executeRequest() throws MorpheusApiRequestException {
    return (GetArtifactVersionResponse) RequestHelper.executeRequest(GetArtifactVersionResponse.class, this, "/api/artifacts/" + artifactId + "/versions/" + artifactVersionId, HttpGet.METHOD_NAME);
  }

  public GetArtifactVersionRequest artifactId(Long artifactId) {
    this.artifactId = artifactId;
    return this;
  }

  public Long getArtifactId() {
    return this.artifactId;
  }

  public GetArtifactVersionRequest artifactVersionId(Long artifactVersionId) {
    this.artifactVersionId = artifactVersionId;
    return this;
  }

  public Long getArtifactVersionId() {
    return artifactVersionId;
  }
}