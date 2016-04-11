/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morpheus.sdk.infrastructure

import com.morpheus.sdk.SecurityGroupBaseSpec

/**
 * @author Bob Whiton
 */
class UpdateSecurityGroupRuleRequestSpec extends SecurityGroupBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully update a security group rule"() {
		given:
		SecurityGroup securityGroup = setupSecurityGroup()
		SecurityGroupRule rule = setupSecurityGroupRule(securityGroup)

		// Store original values
		def previousSource = rule.source
		def previousProtocol = rule.protocol
		def previousPortRange = rule.portRange

		// New values
		def testSource = "10.100.10.6/32"
		def testProtocol = "udp"
		def testPortRange = "47"

		// Update new values
		rule.source = testSource
		rule.protocol = testProtocol
		rule.portRange = testPortRange

		def updateRequest = new UpdateSecurityGroupRuleRequest().securityGroupId(rule.securityGroupId).securityGroupRuleId(rule.id).securityGroupRule(rule)
		when:
		UpdateSecurityGroupRuleResponse updateSecurityGroupRuleResponse = client.updateSecurityGroupRule(updateRequest)
		then:
		updateSecurityGroupRuleResponse.success == true
		updateSecurityGroupRuleResponse.securityGroupRule.source == testSource
		updateSecurityGroupRuleResponse.securityGroupRule.protocol == testProtocol
		updateSecurityGroupRuleResponse.securityGroupRule.portRange == testPortRange
		cleanup:
		destroySecurityGroupRule(rule)
		destroySecurityGroup(securityGroup)
	}
}
