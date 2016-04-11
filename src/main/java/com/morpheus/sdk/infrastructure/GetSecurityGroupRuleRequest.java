package com.morpheus.sdk.infrastructure;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;

/**
 * A request object for defining a request for fetching a specific Security Group within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link SecurityGroupRule} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetSecurityGroupRuleRequest request = new GetSecurityGroupRuleRequest().securityGroupId(1).securityGroupRuleId(1);
 *     	GetSecurityGroupRuleResponse response = client.getSecurityGroupRule(request);
 *     	return response.securityGroupRule;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class GetSecurityGroupRuleRequest extends AbstractApiRequest<GetSecurityGroupRuleResponse> {

  private Long securityGroupId;
  private Long securityGroupRuleId;
  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public GetSecurityGroupRuleResponse executeRequest() throws MorpheusApiRequestException {
    return (GetSecurityGroupRuleResponse) RequestHelper.executeRequest(GetSecurityGroupRuleResponse.class, this, "/api/security-groups/" + securityGroupId + "/rules/" + securityGroupRuleId, HttpGet.METHOD_NAME);
  }

  public GetSecurityGroupRuleRequest securityGroupId(Long securityGroupId) {
    this.securityGroupId = securityGroupId;
    return this;
  }

  public Long getSecurityGroupId() {
    return this.securityGroupId;
  }

  public GetSecurityGroupRuleRequest securityGroupRuleId(Long securityGroupRuleId) {
    this.securityGroupRuleId = securityGroupRuleId;
    return this;
  }

  public Long getSecurityGroupRuleId() {
    return securityGroupRuleId;
  }
}