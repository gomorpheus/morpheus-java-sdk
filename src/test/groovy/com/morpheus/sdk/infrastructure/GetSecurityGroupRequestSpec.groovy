package com.morpheus.sdk.infrastructure

import com.morpheus.sdk.SecurityGroupBaseSpec

/**
 * @author Bob Whiton
 */
class GetSecurityGroupRequestSpec extends SecurityGroupBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully retrieve a security group by id"() {
		given:
		SecurityGroup securityGroup = setupSecurityGroup()
		def request = new GetSecurityGroupRequest().securityGroupId(securityGroup.id)
		when:
		GetSecurityGroupResponse response = client.getSecurityGroup(request)
		then:
		response.securityGroup != null
		response.securityGroup.accountId == securityGroup.accountId
		response.securityGroup.name == securityGroup.name
		response.securityGroup.description == securityGroup.description
		cleanup:
		destroySecurityGroup(securityGroup)
	}
}
