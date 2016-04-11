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

import com.morpheus.sdk.BasicCredentialsProvider
import com.morpheus.sdk.MorpheusClient
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Bob Whiton
 */
class UpdateSecurityGroupRuleRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")
	static String TEST_SECURITY_GROUP_ID=System.getProperty('morpheus.api.testSecurityGroupId',"19")
	static String TEST_SECURITY_GROUP_RULE_ID=System.getProperty('morpheus.api.testSecurityGroupRuleId',"30")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully update a security group rule"() {
		given:
			// Create a test security group rule
			def testSource = "10.100.10.6/32"
			def testProtocol = "udp"
			def testPortRange = "47"

			def request = new GetSecurityGroupRuleRequest().securityGroupId(Integer.parseInt(TEST_SECURITY_GROUP_ID)).securityGroupRuleId(Integer.parseInt(TEST_SECURITY_GROUP_RULE_ID))

			GetSecurityGroupRuleResponse response = client.getSecurityGroupRule(request)
			SecurityGroupRule rule = response.securityGroupRule
			def previousSource = rule.source
			def previousProtocol = rule.protocol
			def previousPortRange = rule.portRange

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
			rule.source == testSource
			rule.protocol == testProtocol
			rule.portRange == testPortRange
			def restoreUpdateRequest = new UpdateSecurityGroupRuleRequest().securityGroupId(rule.securityGroupId).securityGroupRuleId(rule.id).securityGroupRule(rule)
			UpdateSecurityGroupRuleResponse restoreUpdateSecurityGroupRuleResponse = client.updateSecurityGroupRule(restoreUpdateRequest)
			restoreUpdateSecurityGroupRuleResponse.success == true

	}
}
