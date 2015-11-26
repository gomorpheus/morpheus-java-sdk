package com.morpheus.sdk.monitoring;

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
 * A request object for defining a request for fetching a specific check within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link Check} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetCheckRequest request = new GetCheckRequest();
 *     	request.setCheckId(1);
 *     	GetCheckResponse response = client.getCheck(request);
 *     	return response.check;
 *     }
 * </pre>
 * @author William Chu
 */
public class GetCheckRequest extends AbstractApiRequest<GetCheckResponse> {
  private Long checkId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetCheckResponse executeRequest() throws MorpheusApiRequestException {
    CloseableHttpClient client = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(endpointUrl);
      uriBuilder.setPath("/api/checks/" + this.getCheckId());
      HttpGet request = new HttpGet(uriBuilder.build());
      this.applyHeaders(request);
      HttpClientBuilder clientBuilder = HttpClients.custom();
      clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
      client = clientBuilder.build();

      CloseableHttpResponse response = client.execute(request);
      return GetCheckResponse.createFromStream(response.getEntity().getContent());
    } catch(Exception ex) {
      //Throw custom exception
      throw new MorpheusApiRequestException("Error Performing API Request for Check lookup", ex);
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

  public Long getCheckId() {
    return checkId;
  }

  public void setCheckId(Long checkId) {
    this.checkId = checkId;
  }

  public GetCheckRequest checkId(Long checkId) {
    this.checkId = checkId;
    return this;
  }
}
