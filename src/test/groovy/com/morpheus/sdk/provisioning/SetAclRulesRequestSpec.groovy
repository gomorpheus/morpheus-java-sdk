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

package com.morpheus.sdk.provisioning

import com.morpheus.sdk.BasicCredentialsProvider
import com.morpheus.sdk.MorpheusClient
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class SetAclRulesRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")
	static String TEST_INSTANCE_ID=System.getProperty('morpheus.api.testInstanceId',"238")
	static String TEST_ACL_RULE_IP=System.getProperty('morpheus.api.testAclRuleIp',"192.168.1.1/32")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}


	void "it should successfully set acl rules for an instance"() {
		given:
		def request = new SetAclRulesRequest()
		request.instanceId(Integer.parseInt(TEST_INSTANCE_ID))
		AclRule rule = new AclRule()
		rule.ip = TEST_ACL_RULE_IP
		rule.description = "Test ACL rule for ${TEST_ACL_RULE_IP}"
		rule.isEnabled =  true
		rule.isReadOnly = false
		rule.jump = 'ACCEPT'
		request.getRules().add(rule)
		when:
		SetAclRulesResponse response = client.setAclRules(request)
		then:
		response.success == true
		response.rules != null
		response.rules.size() > 0
	}
}
