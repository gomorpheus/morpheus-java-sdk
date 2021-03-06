package com.morpheus.sdk.infrastructure;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * A request object for defining a request for fetching a specific cloud within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link Cloud} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetCloudRequest request = new GetCloudRequest();
 *     	request.setCloudId(1);
 *     	GetCloudResponse response = client.getCloud(request);
 *     	return response.cloud;
 *     }
 * </pre>
 * @author William Chu
 */
public class GetCloudRequest extends AbstractApiRequest<GetCloudResponse> {
  private Long cloudId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetCloudResponse executeRequest() throws MorpheusApiRequestException {
    CloseableHttpClient client = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(endpointUrl);
      uriBuilder.setPath("/api/zones/" + this.getCloudId());
      HttpGet request = new HttpGet(uriBuilder.build());
      this.applyHeaders(request);
      HttpClientBuilder clientBuilder = HttpClients.custom();
      clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
      client = clientBuilder.build();

      CloseableHttpResponse response = client.execute(request);
      return GetCloudResponse.createFromStream(response.getEntity().getContent());
    } catch(Exception ex) {
      //Throw custom exception
      throw new MorpheusApiRequestException("Error Performing API Request for Cloud lookup", ex);
    } finally {
      if(client != null) {
        try {
          client.close();
        } catch(IOException io) {
          //ignore
        }
      }
    }
  }

  public Long getCloudId() {
    return cloudId;
  }

  public void setCloudId(Long cloudId) {
    this.cloudId = cloudId;
  }

  public GetCloudRequest cloudId(Long cloudId) {
    this.cloudId = cloudId;
    return this;
  }
}
