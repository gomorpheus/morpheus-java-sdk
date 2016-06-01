package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.provisioning.Artifact;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Date;

/**
 * A request object for defining a request for fetching a list of artifacts within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a list of {@link Artifact} objects.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	ListArtifactsRequest request = new ListArtifactsRequest();
 *     	ListArtifactsResponse response = client.listArtifacts(request);
 *     	return response.artifacts;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class ListArtifactsRequest extends AbstractApiRequest<ListArtifactsResponse> {

  private Integer max = 50;
  private Integer offset = 0;
  private String phrase;
  private String name;

  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public ListArtifactsResponse executeRequest() throws MorpheusApiRequestException {
    CloseableHttpClient client = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(endpointUrl);
      uriBuilder.setPath("/api/artifacts");
      addQueryParameters(uriBuilder);
      HttpGet request = new HttpGet(uriBuilder.build());
      this.applyHeaders(request);
      HttpClientBuilder clientBuilder = HttpClients.custom();
      clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
      client = clientBuilder.build();

      CloseableHttpResponse response = client.execute(request);
      return ListArtifactsResponse.createFromStream(response.getEntity().getContent());
    } catch (Exception ex) {
      //Throw custom exception
      throw new MorpheusApiRequestException("Error Performing API Request for Listing Artifacts", ex);
    } finally {
      if (client != null) {
        try {
          client.close();
        } catch (IOException io) {
          //ignore
        }
      }
    }
  }

  private void addQueryParameters(URIBuilder uriBuilder) {
    if (this.max != null) {
      uriBuilder.setParameter("max", this.max.toString());
    }
    if (this.offset != null) {
      uriBuilder.setParameter("offset", this.offset.toString());
    }
    if (this.name != null) {
      uriBuilder.setParameter("name", this.name);
    }

    if (this.phrase != null) {
      uriBuilder.setParameter("phrase", this.phrase);
    }
  }

  /**
   * Gets the current max result set limit for the request
   *
   * @return max
   */
  public Integer getMax() {
    return max;
  }

  /**
   * Sets the current max result set limit for the request
   *
   * @param max the max number of instances to return
   */
  public void setMax(Integer max) {
    this.max = max;
  }

  /**
   * Chain-able method for setting the max result set for the request
   *
   * @param max the max number of artifacts to return
   * @return current instance of ListArtifactsRequest
   */
  public ListArtifactsRequest max(Integer max) {
    this.max = max;
    return this;
  }

  /**
   * Get the offset for the request (defaults to 0)
   *
   * @return offset
   */
  public Integer getOffset() {
    return offset;
  }

  /**
   * Sets the current offset for the result. useful for paging through instance data.
   *
   * @param offset the offset of artifacts
   */
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  /**
   * Chain-able method for setting the offset for the request
   *
   * @param offset the offset of artifacts
   * @return current instance of ListArtifactsRequest
   */
  public ListArtifactsRequest offset(Integer offset) {
    this.offset = offset;
    return this;
  }

  /**
   * Gets the phrase parameter being applied to the request
   *
   * @return phrase to be applied to the filter
   * @see ListArtifactsRequest#setPhrase(String)
   */
  public String getPhrase() {
    return phrase;
  }

  /**
   * Sets a search phrase for the request. If set all artifacts will be searched for this matching phrase pattern.
   *
   * @param phrase the phrase to search for
   */
  public void setPhrase(String phrase) {
    this.phrase = phrase;
  }

  /**
   * Chain-able method for setting the phrase or search phrase for a request.
   *
   * @param phrase the phrase to be searched for
   * @return current instance of ListArtifactsRequest
   * @see ListArtifactsRequest#setPhrase(String)
   */
  public ListArtifactsRequest phrase(String phrase) {
    this.setPhrase(phrase);
    return this;
  }

  /**
   * Gets the name filter being applied to the request
   *
   * @return the current name filter being applied
   * @see ListArtifactsRequest#setName(String)
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name filter to be applied to the request. This is an exact match filter. Only the instance matching this filter request will be returned.
   *
   * @param name the name of the instance to look for
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Chain-able method for applying the name filter to be applied to the request
   *
   * @param name the name of the artifact to look for
   * @return the current instance of ListArtifactsRequest
   * @see ListArtifactsRequest#setName(String)
   */
  public ListArtifactsRequest name(String name) {
    this.setName(name);
    return this;
  }

}
