package com.morpheus.sdk.provisioning;

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
 *     	GetCertificateRequest request = new GetCertificateRequest();
 *     	request.setCertificateId(1);
 *     	GetCertificateResponse response = client.getCertificate(request);
 *     	return response.certificate;
 *     }
 * </pre>
 * @author William Chu
 */
public class GetCertificateRequest extends AbstractApiRequest<GetCertificateResponse> {
  private Long certificateId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetCertificateResponse executeRequest() throws MorpheusApiRequestException {
    CloseableHttpClient client = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(endpointUrl);
      uriBuilder.setPath("/api/certificates/" + this.getCertificateId());
      HttpGet request = new HttpGet(uriBuilder.build());
      this.applyHeaders(request);
      HttpClientBuilder clientBuilder = HttpClients.custom();
      clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
      client = clientBuilder.build();

      CloseableHttpResponse response = client.execute(request);
      return GetCertificateResponse.createFromStream(response.getEntity().getContent());
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

  public Long getCertificateId() {
    return certificateId;
  }

  public void setCertificateId(Long certificateId) {
    this.certificateId = certificateId;
  }

  public GetCertificateRequest certificateId(Long certificateId) {
    this.certificateId = certificateId;
    return this;
  }
}
