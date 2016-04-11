package com.morpheus.sdk

import com.morpheus.sdk.infrastructure.CreateSecurityGroupRequest
import com.morpheus.sdk.infrastructure.CreateSecurityGroupResponse
import com.morpheus.sdk.infrastructure.CreateSecurityGroupRuleRequest
import com.morpheus.sdk.infrastructure.CreateSecurityGroupRuleResponse
import com.morpheus.sdk.infrastructure.DeleteSecurityGroupRequest
import com.morpheus.sdk.infrastructure.DeleteSecurityGroupRuleRequest
import com.morpheus.sdk.infrastructure.SecurityGroup
import com.morpheus.sdk.infrastructure.SecurityGroupRule

public abstract class SecurityGroupBaseSpec extends BaseSpec {

	SecurityGroup setupSecurityGroup() {
		SecurityGroup securityGroup = new SecurityGroup(name: "Test Security Group ${System.currentTimeMillis()}", description: "Security Group Description")
		CreateSecurityGroupResponse createResponse = client.createSecurityGroup(new CreateSecurityGroupRequest().securityGroup(securityGroup))
		return createResponse.securityGroup
	}

	void destroySecurityGroup(SecurityGroup securityGroup) {
		def request = new DeleteSecurityGroupRequest().securityGroupId(securityGroup.id)
		client.deleteSecurityGroup(request)
	}

	SecurityGroupRule setupSecurityGroupRule(SecurityGroup securityGroup) {
		SecurityGroupRule rule = new SecurityGroupRule()
		rule.source = "10.100.54.1/32"
		rule.protocol = "tcp"
		rule.portRange = "44"
		rule.customRule = true

		def request = new CreateSecurityGroupRuleRequest().securityGroupId(securityGroup.id).securityGroupRule(rule)
		CreateSecurityGroupRuleResponse response = client.createSecurityGroupRule(request)
		return response.securityGroupRule
	}

	void destroySecurityGroupRule(SecurityGroupRule securityGroupRule) {
		def request = new DeleteSecurityGroupRuleRequest().securityGroupId(securityGroupRule.securityGroupId).securityGroupRuleId(securityGroupRule.id)
		client.deleteSecurityGroupRule(request)
	}
}