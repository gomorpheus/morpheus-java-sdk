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
 * A request object for defining a request for fetching a specific zone type within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link ZoneType} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetZoneTypeRequest request = new GetZoneTypeRequest();
 *     	request.setZoneTypeId(1);
 *     	GetZoneTypeResponse response = client.getZoneType(request);
 *     	return response.zoneType;
 *     }
 * </pre>
 * @author William Chu
 */
public class GetZoneTypeRequest extends AbstractApiRequest<GetZoneTypeResponse> {
  private Long zoneTypeId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetZoneTypeResponse executeRequest() throws MorpheusApiRequestException {
    CloseableHttpClient client = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(endpointUrl);
      uriBuilder.setPath("/api/zone-types/" + this.getZoneTypeId());
      HttpGet request = new HttpGet(uriBuilder.build());
      this.applyHeaders(request);
      HttpClientBuilder clientBuilder = HttpClients.custom();
      clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
      client = clientBuilder.build();

      CloseableHttpResponse response = client.execute(request);
      return GetZoneTypeResponse.createFromStream(response.getEntity().getContent());
    } catch(Exception ex) {
      //Throw custom exception
      throw new MorpheusApiRequestException("Error Performing API Request for Zone Type lookup", ex);
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

  public Long getZoneTypeId() {
    return zoneTypeId;
  }

  public void setZoneTypeId(Long zoneTypeId) {
    this.zoneTypeId = zoneTypeId;
  }

  public GetZoneTypeRequest zoneTypeId(Long zoneTypeId) {
    this.zoneTypeId = zoneTypeId;
    return this;
  }
}
