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
class DeleteSecurityGroupRuleRequestSpec extends SecurityGroupBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully delete a security group rule"() {
		given:

		SecurityGroup securityGroup = setupSecurityGroup()
		SecurityGroupRule rule = setupSecurityGroupRule(securityGroup)
		def request = new DeleteSecurityGroupRuleRequest().securityGroupId(securityGroup.id).securityGroupRuleId(rule.id)
		when:
		DeleteSecurityGroupRuleResponse response = client.deleteSecurityGroupRule(request)
		then:
		response.msg == null
		response.success == true
		cleanup:
		destroySecurityGroupRule(rule)
	}
}
