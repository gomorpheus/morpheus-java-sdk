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
class CreateSecurityGroupRuleRequestSpec extends SecurityGroupBaseSpec  {
	def setup() {
	}

	def cleanup() {
}

	void "it should successfully create a security group rule"() {
		given:
		SecurityGroup securityGroup = setupSecurityGroup()
		ListSecurityGroupRulesResponse listResponse = client.listSecurityGroupRules(new ListSecurityGroupRulesRequest().securityGroupId(securityGroup.id))
		def originalSize = listResponse.securityGroupRules.size()

		SecurityGroupRule rule = new SecurityGroupRule()
		rule.source = "10.100.54.1/32"
		rule.protocol = "tcp"
		rule.portRange = "44"
		rule.customRule = true

		def request = new CreateSecurityGroupRuleRequest().securityGroupId(securityGroup.id).securityGroupRule(rule)
		when:
		CreateSecurityGroupRuleResponse response = client.createSecurityGroupRule(request)
		then:
		response.securityGroupRule != null
		response.securityGroupRule.source == "10.100.54.1/32"

		client.listSecurityGroupRules(new ListSecurityGroupRulesRequest().securityGroupId(securityGroup.id)).securityGroupRules.size() == originalSize + 1

		cleanup:
		destroySecurityGroup(securityGroup)
		destroySecurityGroupRule(response.securityGroupRule)
	}
}
