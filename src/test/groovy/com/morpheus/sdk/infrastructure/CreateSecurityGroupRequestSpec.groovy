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
class CreateSecurityGroupRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully create a security group"() {
		given:
		ListSecurityGroupsResponse listResponse = client.listSecurityGroups(new ListSecurityGroupsRequest())
		def originalSize = listResponse.securityGroups.size()

		def request = new CreateSecurityGroupRequest()
		SecurityGroup securityGroup = new SecurityGroup()
		def m1 = System.currentTimeMillis()
		securityGroup.name = "Test Security Group ${m1}"
		securityGroup.description = "my description"
		request.setSecurityGroup(securityGroup)
		when:
		CreateSecurityGroupResponse response = client.createSecurityGroup(request)
		then:
		response.securityGroup != null
		client.listSecurityGroups(new ListSecurityGroupsRequest()).securityGroups.size() == originalSize + 1

		DeleteSecurityGroupRequest cleanupRequest = new DeleteSecurityGroupRequest()
		cleanupRequest.securityGroupId(response.securityGroup.id)
		DeleteSecurityGroupResponse cleanupResponse = client.deleteSecurityGroup(cleanupRequest)
	}
}
