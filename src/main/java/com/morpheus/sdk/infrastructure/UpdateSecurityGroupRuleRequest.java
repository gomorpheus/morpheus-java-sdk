package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPut;

import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request for updating a specific security group rule within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to update a specific {@link SecurityGroupRule} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	UpdateSecurityGroupRuleRequest request = new UpdateSecurityGroupRuleRequest().securityGroupId(1).securityGroupRuleId(1).securityGroupRule(updatedSecurityGroupRule)
 *     	UpdateSecurityGroupRuleResponse response = client.updateSecurityGroupRule(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class UpdateSecurityGroupRuleRequest extends AbstractApiRequest<UpdateSecurityGroupRuleResponse> {
	private Long securityGroupId;
	private Long securityGroupRuleId;
	private SecurityGroupRule securityGroupRule;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public UpdateSecurityGroupRuleResponse executeRequest() throws MorpheusApiRequestException {
		return (UpdateSecurityGroupRuleResponse)RequestHelper.executeRequest(UpdateSecurityGroupRuleResponse.class, this, "/api/security-groups/" + securityGroupId + "/rules/" + securityGroupRuleId,  HttpPut.METHOD_NAME);
	}

	@Override
	protected String generateRequestBody() {
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		final Gson gson = builder.create();
		Map<String,SecurityGroupRule> deployMap = new HashMap<String,SecurityGroupRule>();
		deployMap.put("rule", securityGroupRule);
		return gson.toJson(deployMap);
	}

	public Long getSecurityGroupId() {
		return securityGroupId;
	}

	public void setSecurityGroupId(Long securityGroupId) {
		this.securityGroupId = securityGroupId;
	}

	public Long getSecurityGroupRuleId() {
		return securityGroupRuleId;
	}

	public void setSecurityGroupRuleId(Long securityGroupRuleId) {
		this.securityGroupRuleId = securityGroupRuleId;
	}

	public UpdateSecurityGroupRuleRequest securityGroupId(Long securityGroupId) {
		this.securityGroupId = securityGroupId;
		return this;
	}

	public UpdateSecurityGroupRuleRequest securityGroupRuleId(Long securityGroupRuleId) {
		this.securityGroupRuleId = securityGroupRuleId;
		return this;
	}

	public SecurityGroupRule getSecurityGroupRule() {
		return securityGroupRule;
	}

	public void setSecurityGroupRule(SecurityGroupRule securityGroupRule) {
		this.securityGroupRule = securityGroupRule;
	}

	public UpdateSecurityGroupRuleRequest securityGroupRule(SecurityGroupRule securityGroupRule) {
		this.securityGroupRule = securityGroupRule;
		return this;
	}
}
