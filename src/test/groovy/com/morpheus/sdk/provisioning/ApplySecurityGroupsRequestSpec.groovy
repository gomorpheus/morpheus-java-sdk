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

import com.morpheus.sdk.BaseSpec
import com.morpheus.sdk.SecurityGroupBaseSpec
import com.morpheus.sdk.infrastructure.SecurityGroup

/**
 * @author Bob Whiton
 */
class ApplySecurityGroupsRequestSpec extends SecurityGroupBaseSpec {
	static String TEST_INSTANCE_ID=System.getProperty('morpheus.api.testInstanceId',"123")

	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully apply the security groups to a given instance"() {
		given:
		SecurityGroup securityGroup1 = setupSecurityGroup()
		SecurityGroup securityGroup2 = setupSecurityGroup()
		SecurityGroup securityGroup3 = setupSecurityGroup()

		def request = new ApplySecurityGroupsRequest().instanceId(Integer.parseInt(TEST_INSTANCE_ID))
		def securityGroupIds = new Long[3]
		securityGroupIds[0] = securityGroup1.id
		securityGroupIds[1] = securityGroup2.id
		securityGroupIds[2] = securityGroup3.id
		request.setSecurityGroupIds(securityGroupIds)
		when:
		ApplySecurityGroupsResponse response = client.applySecurityGroups(request)
		then:
		response.success == true
		response.securityGroups.size() == 3
		response.securityGroups[0].id == securityGroup1.id
		response.securityGroups[1].id == securityGroup2.id
		response.securityGroups[2].id == securityGroup3.id
		cleanup:
		destroySecurityGroup(securityGroup3)
		destroySecurityGroup(securityGroup2)
		destroySecurityGroup(securityGroup1)
	}

	void "it should successfully clear out security groups when passing none"() {
		given:
		SecurityGroup securityGroup1 = setupSecurityGroup()
		SecurityGroup securityGroup2 = setupSecurityGroup()
		SecurityGroup securityGroup3 = setupSecurityGroup()

		def request = new ApplySecurityGroupsRequest().instanceId(Integer.parseInt(TEST_INSTANCE_ID))
		def securityGroupIds = new Long[3]
		securityGroupIds[0] = securityGroup1.id
		securityGroupIds[1] = securityGroup2.id
		securityGroupIds[2] = securityGroup3.id
		request.setSecurityGroupIds(securityGroupIds)
		ApplySecurityGroupsResponse response = client.applySecurityGroups(request)
		assert response.securityGroups.size() == 3
		when:
		ApplySecurityGroupsResponse clearresponse = client.applySecurityGroups(new ApplySecurityGroupsRequest().instanceId(Integer.parseInt(TEST_INSTANCE_ID)))
		then:
		println "response: ${clearresponse}"
		clearresponse.success == true
		clearresponse.securityGroups.size() == 0
		cleanup:
		destroySecurityGroup(securityGroup3)
		destroySecurityGroup(securityGroup2)
		destroySecurityGroup(securityGroup1)
	}
}
