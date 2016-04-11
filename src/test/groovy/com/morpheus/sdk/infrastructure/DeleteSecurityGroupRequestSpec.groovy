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
class DeleteSecurityGroupRequestSpec extends BaseSpec{

	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully delete a security group"() {
		given:
		SecurityGroup securityGroup = new SecurityGroup(name: "Test Security Group ${System.currentTimeMillis()}", description: "Security Group Description")
		CreateSecurityGroupResponse createResponse = client.createSecurityGroup(new CreateSecurityGroupRequest().securityGroup(securityGroup))
		def request = new DeleteSecurityGroupRequest().securityGroupId(createResponse.securityGroup.id)
		when:
		DeleteSecurityGroupResponse response = client.deleteSecurityGroup(request)
		then:
		response.msg == null
		response.success == true
	}
}