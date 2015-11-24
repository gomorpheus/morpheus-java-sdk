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
 * A request object for defining a request for fetching a specific cloud type within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link CloudType} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetCloudTypeRequest request = new GetCloudTypeRequest();
 *     	request.setCloudTypeId(1);
 *     	GetCloudTypeResponse response = client.getCloudType(request);
 *     	return response.cloudType;
 *     }
 * </pre>
 * @author William Chu
 */
public class GetCloudTypeRequest extends AbstractApiRequest<GetCloudTypeResponse> {
  private Long cloudTypeId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetCloudTypeResponse executeRequest() throws MorpheusApiRequestException {
    CloseableHttpClient client = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(endpointUrl);
      uriBuilder.setPath("/api/zone-types/" + this.getCloudTypeId());
      HttpGet request = new HttpGet(uriBuilder.build());
      this.applyHeaders(request);
      HttpClientBuilder clientBuilder = HttpClients.custom();
      clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
      client = clientBuilder.build();

      CloseableHttpResponse response = client.execute(request);
      return GetCloudTypeResponse.createFromStream(response.getEntity().getContent());
    } catch(Exception ex) {
      //Throw custom exception
      throw new MorpheusApiRequestException("Error Performing API Request for Cloud Type lookup", ex);
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

  public Long getCloudTypeId() {
    return cloudTypeId;
  }

  public void setCloudTypeId(Long cloudTypeId) {
    this.cloudTypeId = cloudTypeId;
  }

  public GetCloudTypeRequest cloudTypeId(Long cloudTypeId) {
    this.cloudTypeId = cloudTypeId;
    return this;
  }
}
