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
class ListSecurityGroupRulesRequestSpec extends SecurityGroupBaseSpec  {
	def setup() {
	}

	def cleanup() {
	}
	
	void "it should successfully list security group rules"() {
		given:
		SecurityGroup securityGroup = setupSecurityGroup()
		SecurityGroupRule securityGroupRule = setupSecurityGroupRule(securityGroup)
		def request = new ListSecurityGroupRulesRequest().securityGroupId(securityGroup.id)
		when:
		ListSecurityGroupRulesResponse response = client.listSecurityGroupRules(request)
		then:
		response.securityGroupRules?.size() == 1
		cleanup:
		destroySecurityGroupRule(securityGroupRule)
		destroySecurityGroup(securityGroup)
	}
}
