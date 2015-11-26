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
 * A request object for defining a request for fetching a specific check type within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link CheckType} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetCheckTypeRequest request = new GetCheckTypeRequest();
 *     	request.setCheckTypeId(1);
 *     	GetCheckTypeResponse response = client.getCheckType(request);
 *     	return response.checkType;
 *     }
 * </pre>
 * @author William Chu
 */
public class GetCheckTypeRequest extends AbstractApiRequest<GetCheckTypeResponse> {
  private Long checkTypeId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetCheckTypeResponse executeRequest() throws MorpheusApiRequestException {
    CloseableHttpClient client = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(endpointUrl);
      uriBuilder.setPath("/api/check-types/" + this.getCheckTypeId());
      HttpGet request = new HttpGet(uriBuilder.build());
      this.applyHeaders(request);
      HttpClientBuilder clientBuilder = HttpClients.custom();
      clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
      client = clientBuilder.build();

      CloseableHttpResponse response = client.execute(request);
      return GetCheckTypeResponse.createFromStream(response.getEntity().getContent());
    } catch(Exception ex) {
      //Throw custom exception
      throw new MorpheusApiRequestException("Error Performing API Request for Check Type lookup", ex);
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

  public Long getCheckTypeId() {
    return checkTypeId;
  }

  public void setCheckTypeId(Long checkTypeId) {
    this.checkTypeId = checkTypeId;
  }

  public GetCheckTypeRequest checkTypeId(Long checkTypeId) {
    this.checkTypeId = checkTypeId;
    return this;
  }
}
