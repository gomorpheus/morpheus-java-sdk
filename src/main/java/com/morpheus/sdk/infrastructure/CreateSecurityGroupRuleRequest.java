package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPost;

import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request to create a new {@link SecurityGroupRule} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	SecurityGroupRule securityGroupRule = new SecurityGroupRule();
 *     	securityGroupRule.source = "10.100.54.4";
 *     	securityGroupRule.protocol = "tcp";
 *     	securityGroupRule.portRange = "44";
 *     	securityGroupRule.customRule = true;
 *     	CreateSecurityGroupRuleRequest request = new CreateSecurityGroupRuleRequest().securityGroupId(1).securityGroupRule(securityGroupRule);
 *     	CreateSecurityGroupRuleResponse response = client.createSecurityGroupRule(request);
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class CreateSecurityGroupRuleRequest extends AbstractApiRequest<CreateSecurityGroupRuleResponse> {
	private Long securityGroupId;
	private SecurityGroupRule securityGroupRule;

	@Override
	public CreateSecurityGroupRuleResponse executeRequest() throws MorpheusApiRequestException {
		return (CreateSecurityGroupRuleResponse) RequestHelper.executeRequest(CreateSecurityGroupRuleResponse.class, this, "/api/security-groups/" + securityGroupId + "/rules", HttpPost.METHOD_NAME);
	}

	protected String generateRequestBody() {
		Gson gson = MorpheusGsonBuilder.build();
		Map<String,SecurityGroupRule> deployMap = new HashMap<String,SecurityGroupRule>();
		deployMap.put("rule", securityGroupRule);
		return gson.toJson(deployMap);
	}

	public CreateSecurityGroupRuleRequest securityGroupId(Long securityGroupId) {
		this.securityGroupId = securityGroupId;
		return this;
	}

	public SecurityGroupRule getSecurityGroupRule() {
		return securityGroupRule;
	}

	public CreateSecurityGroupRuleRequest securityGroupRule(SecurityGroupRule securityGroupRule) {
		this.securityGroupRule = securityGroupRule;
		return this;
	}
}
