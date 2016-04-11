package com.morpheus.sdk.infrastructure

import com.morpheus.sdk.SecurityGroupBaseSpec

/**
 * @author Bob Whiton
 */
class GetSecurityGroupRuleRequestSpec extends SecurityGroupBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully retrieve a security group rule by id"() {
		given:
		SecurityGroup securityGroup = setupSecurityGroup()
		SecurityGroupRule securityGroupRule = setupSecurityGroupRule(securityGroup)
		def request = new GetSecurityGroupRuleRequest().securityGroupId(securityGroup.id).securityGroupRuleId(securityGroupRule.id)
		when:
		GetSecurityGroupRuleResponse response = client.getSecurityGroupRule(request)
		then:
		response.securityGroupRule != null
		response.securityGroupRule.id == securityGroupRule.id
		response.securityGroupRule.securityGroupId == securityGroup.id
		response.securityGroupRule.source == securityGroupRule.source
		response.securityGroupRule.portRange == securityGroupRule.portRange
		response.securityGroupRule.protocol == securityGroupRule.protocol
		response.securityGroupRule.customRule == true
		response.securityGroupRule.instanceTypeId == null
		cleanup:
		destroySecurityGroupRule(securityGroupRule)
		destroySecurityGroup(securityGroup)
	}
}
