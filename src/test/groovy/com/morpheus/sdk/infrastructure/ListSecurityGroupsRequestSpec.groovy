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
import com.morpheus.sdk.SecurityGroupBaseSpec
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Bob Whiton
 */
class ListSecurityGroupsRequestSpec extends SecurityGroupBaseSpec {
	def setup() {
	}

	def cleanup() {
	}
	
	void "it should successfully list security groups"() {
		given:
		SecurityGroup securityGroup = setupSecurityGroup()
		def request = new ListSecurityGroupsRequest()
		when:
		ListSecurityGroupsResponse response = client.listSecurityGroups(request)
		then:
		response.securityGroups?.size() > 0
		cleanup:
		destroySecurityGroup(securityGroup)
	}
}
