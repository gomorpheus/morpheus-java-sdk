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
import com.morpheus.sdk.infrastructure.GetServerGroupRequest
import com.morpheus.sdk.infrastructure.GetServerGroupResponse
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class CloneInstanceRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://qa.gomorpheus.com")
	static String TEST_INSTANCE_ID=System.getProperty('morpheus.api.testInstanceId',"238")
	static String TEST_SERVER_GROUP_ID=System.getProperty('morpheus.api.testServerGroupId',"17")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully clone an instance"() {
		given:
		def getInstanceRequest = new GetInstanceRequest()
		getInstanceRequest.setInstanceId(Integer.parseInt(TEST_INSTANCE_ID))
		GetInstanceResponse getInstanceResponse = client.getInstance(getInstanceRequest)

		def getGroupRequest = new GetServerGroupRequest()
		getGroupRequest.serverGroupId(Integer.parseInt(TEST_SERVER_GROUP_ID))
		GetServerGroupResponse getGroupResponse = client.getServerGroup(getGroupRequest)

		def request = new CloneInstanceRequest()
		request.instanceId(getInstanceResponse.instance.id).serverGroup(getGroupResponse.serverGroup)
		when:
		CloneInstanceResponse response = client.cloneInstance(request)
		then:
		response.success == true
		response.errors.size() == 0
/*
		cleanup:
		DeleteServerRequest deleteServerRequest = new DeleteServerRequest()
		deleteServerRequest.serverId(response.server.id)
		DeleteServerResponse deleteServerResponse = client.deleteServer(deleteServerRequest)
		assert deleteServerResponse.msg == null
		*/
	}
}
