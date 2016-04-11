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

import com.morpheus.sdk.BaseSpec

/**
 * @author Bob Whiton
 */
class CreateSecurityGroupRequestSpec extends BaseSpec {

	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully create a security group"() {
		given:
		ListSecurityGroupsResponse listResponse = client.listSecurityGroups(new ListSecurityGroupsRequest())
		def originalSize = listResponse.securityGroups.size()

		SecurityGroup securityGroup = new SecurityGroup(name: "Test Security Group ${System.currentTimeMillis()}", description: "Security Group Description")
		CreateSecurityGroupRequest request = new CreateSecurityGroupRequest().securityGroup(securityGroup)
		when:
		CreateSecurityGroupResponse response = client.createSecurityGroup(request)
		then:
		response.securityGroup != null
		client.listSecurityGroups(new ListSecurityGroupsRequest()).securityGroups.size() == originalSize + 1
		cleanup:
		DeleteSecurityGroupRequest cleanupRequest = new DeleteSecurityGroupRequest()
		cleanupRequest.securityGroupId(response.securityGroup.id)
		DeleteSecurityGroupResponse cleanupResponse = client.deleteSecurityGroup(cleanupRequest)
	}
}
