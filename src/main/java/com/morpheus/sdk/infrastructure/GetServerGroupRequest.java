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
 * is used to fetch a specific {@link ServerGroup} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetServerGroupRequest request = new GetServerGroupRequest();
 *     	request.setServerGroupId(1);
 *     	GetServerGroupResponse response = client.getServerGroup(request);
 *     	return response.serverGroup;
 *     }
 * </pre>
 * @author William Chu
 */
public class GetServerGroupRequest extends AbstractApiRequest<GetServerGroupResponse> {
  private Long serverGroupId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetServerGroupResponse executeRequest() throws MorpheusApiRequestException {
    CloseableHttpClient client = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(endpointUrl);
      uriBuilder.setPath("/api/groups/" + this.getServerGroupId());
      HttpGet request = new HttpGet(uriBuilder.build());
      this.applyHeaders(request);
      HttpClientBuilder clientBuilder = HttpClients.custom();
      clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
      client = clientBuilder.build();

      CloseableHttpResponse response = client.execute(request);
      return GetServerGroupResponse.createFromStream(response.getEntity().getContent());
    } catch(Exception ex) {
      //Throw custom exception
      throw new MorpheusApiRequestException("Error Performing API Request for Server Group lookup", ex);
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

  public Long getServerGroupId() {
    return serverGroupId;
  }

  public void setServerGroupId(Long serverGroupId) {
    this.serverGroupId = serverGroupId;
  }

  public GetServerGroupRequest serverGroupId(Long serverGroupId) {
    this.serverGroupId = serverGroupId;
    return this;
  }
}