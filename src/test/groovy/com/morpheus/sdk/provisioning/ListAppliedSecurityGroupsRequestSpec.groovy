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

/**
 * @author Bob Whiton
 */
class ListAppliedSecurityGroupsRequestSpec extends BaseSpec {
	static String TEST_INSTANCE_ID=System.getProperty('morpheus.api.testInstanceId',"123")

	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully list the security groups for a given instance"() {
		given:
		def request = new ListAppliedSecurityGroupsRequest().instanceId(Integer.parseInt(TEST_INSTANCE_ID))
		when:
		ListAppliedSecurityGroupsResponse response = client.listAppliedSecurityGroups(request)
		then:
		response.success == true
		response.securityGroups.size() > 0
	}
}
