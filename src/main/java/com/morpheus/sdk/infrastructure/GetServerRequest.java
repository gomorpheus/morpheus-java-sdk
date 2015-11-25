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
 * A request object for defining a request for fetching a specific instance within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch an {@link Server} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetServerRequest request = new GetServerRequest();
 *     	request.setServerId(1);
 *     	GetServerResponse response = client.getServer(request);
 *     	return response.server;
 *     }
 * </pre>
 * @author William Chu
 */
public class GetServerRequest extends AbstractApiRequest<GetServerResponse> {
  private Long serverId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetServerResponse executeRequest() throws MorpheusApiRequestException {
    CloseableHttpClient client = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(endpointUrl);
      uriBuilder.setPath("/api/servers/" + this.getServerId());
      HttpGet request = new HttpGet(uriBuilder.build());
      this.applyHeaders(request);
      HttpClientBuilder clientBuilder = HttpClients.custom();
      clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
      client = clientBuilder.build();

      CloseableHttpResponse response = client.execute(request);
      return GetServerResponse.createFromStream(response.getEntity().getContent());
    } catch(Exception ex) {
      //Throw custom exception
      throw new MorpheusApiRequestException("Error Performing API Request for Listing Instances", ex);
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

  public Long getServerId() {
    return serverId;
  }

  public void setServerId(Long serverId) {
    this.serverId = serverId;
  }

  public GetServerRequest serverId(Long serverId) {
    this.serverId = serverId;
    return this;
  }
}
