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
class UpdateSecurityGroupRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")
	static String TEST_SECURITY_GROUP_ID=System.getProperty('morpheus.api.testSecurityGroupId',"19")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully update a security group"() {
		given:
			def testSecurityGroupId = Integer.parseInt(TEST_SECURITY_GROUP_ID)
			def testSecurityGroupName = "Booyah!"
			def request = new GetSecurityGroupRequest()
			request.setSecurityGroupId(testSecurityGroupId)
			GetSecurityGroupResponse response = client.getSecurityGroup(request)
			SecurityGroup securityGroup = response.securityGroup
			def previousName = securityGroup.name
			securityGroup.name = testSecurityGroupName
			def updateRequest = new UpdateSecurityGroupRequest().securityGroupId(testSecurityGroupId).securityGroup(securityGroup)
		when:
			UpdateSecurityGroupResponse updateSecurityGroupResponse = client.updateSecurityGroup(updateRequest)
		then:
			updateSecurityGroupResponse.success == true
			updateSecurityGroupResponse.securityGroup?.name == testSecurityGroupName
		cleanup:
			securityGroup.name = previousName
			def restoreUpdateRequest = new UpdateSecurityGroupRequest().securityGroupId(testSecurityGroupId).securityGroup(securityGroup)
			UpdateSecurityGroupResponse restoreUpdateSecurityGroupResponse = client.updateSecurityGroup(restoreUpdateRequest)
			restoreUpdateSecurityGroupResponse.success == true

	}
}
