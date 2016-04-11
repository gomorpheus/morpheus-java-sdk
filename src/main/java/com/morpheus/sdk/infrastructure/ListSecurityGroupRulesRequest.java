package com.morpheus.sdk.infrastructure;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpGet;

/**
 * A request object for defining a request for fetching a list of security group rules within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a list of {@link SecurityGroupRule} objects.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *
 *     	ListSecurityGroupsRequest request = new ListSecurityGroupRulesRequest().securityGroupId(1);
 *     	ListSecurityGroupsResponse response = client.listSecurityGroupRules(request);
 *     	return response.rules;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class ListSecurityGroupRulesRequest extends AbstractApiRequest<ListSecurityGroupRulesResponse> {

  private Long securityGroupId;

  /**
   * Executes the request against the appliance API (Should not be called directly).
   */
  @Override
  public ListSecurityGroupRulesResponse executeRequest() throws MorpheusApiRequestException {
    return (ListSecurityGroupRulesResponse) RequestHelper.executeRequest(ListSecurityGroupRulesResponse.class, this, "/api/security-groups/" + securityGroupId + "/rules", HttpGet.METHOD_NAME);
  }

  public ListSecurityGroupRulesRequest securityGroupId(Long securityGroupId) {
    this.securityGroupId = securityGroupId;
    return this;
  }

  public Long getSecurityGroupId() {
    return this.securityGroupId;
  }

}
