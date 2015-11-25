package com.morpheus.sdk.admin;

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
 * A request object for defining a request for fetching a specific certificate within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch an {@link SslCertificate} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetKeyPairRequest request = new GetKeyPairRequest();
 *     	request.setKeyPairId(1);
 *     	GetKeyPairResponse response = client.getKeyPair(request);
 *     	return response.keyPair;
 *     }
 * </pre>
 * @author William Chu
 */
public class GetKeyPairRequest extends AbstractApiRequest<GetKeyPairResponse> {
  private Long keyPairId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetKeyPairResponse executeRequest() throws MorpheusApiRequestException {
    CloseableHttpClient client = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(endpointUrl);
      uriBuilder.setPath("/api/key-pairs/" + this.getKeyPairId());
      HttpGet request = new HttpGet(uriBuilder.build());
      this.applyHeaders(request);
      HttpClientBuilder clientBuilder = HttpClients.custom();
      clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
      client = clientBuilder.build();

      CloseableHttpResponse response = client.execute(request);
      return GetKeyPairResponse.createFromStream(response.getEntity().getContent());
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

  public Long getKeyPairId() {
    return keyPairId;
  }

  public void setKeyPairId(Long keyPairId) {
    this.keyPairId = keyPairId;
  }

  public GetKeyPairRequest keyPairId(Long keyPairId) {
    this.keyPairId = keyPairId;
    return this;
  }
}
