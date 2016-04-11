package com.morpheus.sdk.infrastructure;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * A request object for defining a request for fetching a list of security groups within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a list of {@link SecurityGroup} objects.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	ListSecurityGroupsRequest request = new ListSecurityGroupsRequest();
 *     	ListSecurityGroupsResponse response = client.listSecurityGroups(request);
 *     	return response.securityGroups;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class ListSecurityGroupsRequest extends AbstractApiRequest<ListSecurityGroupsResponse> {
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public ListSecurityGroupsResponse executeRequest() throws MorpheusApiRequestException {
    return (ListSecurityGroupsResponse) RequestHelper.executeRequest(ListSecurityGroupsResponse.class, this, "/api/security-groups", HttpGet.METHOD_NAME);
  }

}
