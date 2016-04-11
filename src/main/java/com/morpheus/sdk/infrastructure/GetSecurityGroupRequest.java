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
 * A request object for defining a request for fetching a specific Security Group within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link SecurityGroup} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetSecurityGroupRequest request = new GetSecurityGroupRequest();
 *     	request.setSecurityGroupId(1);
 *     	GetSecurityGroupResponse response = client.getSecurityGroup(request);
 *     	return response.securityGroup;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class GetSecurityGroupRequest extends AbstractApiRequest<GetSecurityGroupResponse> {
  private Long securityGroupId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetSecurityGroupResponse executeRequest() throws MorpheusApiRequestException {
    return (GetSecurityGroupResponse) RequestHelper.executeRequest(GetSecurityGroupResponse.class, this, "/api/security-groups/" + securityGroupId, HttpPut.METHOD_NAME);
  }

  public Long getSecurityGroupId() {
    return securityGroupId;
  }

  public void setSecurityGroupId(Long securityGroupId) {
    this.securityGroupId = securityGroupId;
  }

  public GetSecurityGroupRequest securityGroupId(Long securityGroupId) {
    this.securityGroupId = securityGroupId;
    return this;
  }
}