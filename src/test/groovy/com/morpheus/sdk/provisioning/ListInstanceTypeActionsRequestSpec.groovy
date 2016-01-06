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
class ListInstanceTypeActionsRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")
	static String TEST_INSTANCE_TYPE_ID=System.getProperty('morpheus.api.testInstanceTypeId',"20")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully list actions for a given instance layout"() {
		given:
		def instanceTypeRequest = new GetInstanceTypeRequest()
		instanceTypeRequest.setInstanceTypeId(Integer.parseInt(TEST_INSTANCE_TYPE_ID))
		GetInstanceTypeResponse instanceTypeResponse = client.getInstanceType(instanceTypeRequest)
		assert instanceTypeResponse.instanceType.instanceTypeLayouts.size() > 0, "The instance type must have at least one instance type layout before we can proceed with testing"

		def request = new ListInstanceTypeActionsRequest()
		request.instanceTypeId(instanceTypeResponse.instanceType.instanceTypeLayouts.get(0).id)
		when:
		ListInstanceTypeActionsResponse response = client.listInstanceTypeActions(request)
		then:
		response.actions?.size() > 0
	}
}
