package com.morpheus.sdk.infrastructure;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpDelete;

/**
 * A request object for defining a request to deleting an existing {@link SecurityGroupRule} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteSecurityGroupRuleRequest request = new DeleteSecurityGroupRuleRequest().securityGroupId(1).securityGroupRuleId(1);
 *     	DeleteSecurityGroupRuleResponse response = client.deleteSecurityGroupRule(request);
 *     	return response.success;
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class DeleteSecurityGroupRuleRequest extends AbstractApiRequest<DeleteSecurityGroupRuleResponse> {
	private Long securityGroupId;
	private Long securityGroupRuleId;

	@Override
	public DeleteSecurityGroupRuleResponse executeRequest() throws MorpheusApiRequestException {
		return (DeleteSecurityGroupRuleResponse) RequestHelper.executeRequest(DeleteSecurityGroupRuleResponse.class, this, "/api/security-groups/" + securityGroupId + "/rules/" + securityGroupRuleId, HttpDelete.METHOD_NAME);
	}

	public Long getSecurityGroupId() {
		return securityGroupId;
	}

	public DeleteSecurityGroupRuleRequest securityGroupId(Long securityGroupId) {
		this.securityGroupId = securityGroupId;
		return this;
	}

	public Long getSecurityGroupRuleId() {
		return securityGroupRuleId;
	}

	public DeleteSecurityGroupRuleRequest securityGroupRuleId(Long securityGroupRuleId) {
		this.securityGroupRuleId = securityGroupRuleId;
		return this;
	}
}
