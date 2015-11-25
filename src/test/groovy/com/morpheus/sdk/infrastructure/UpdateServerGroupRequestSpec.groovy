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
 * @author William Chu
 */
class UpdateServerGroupRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://v2.gomorpheus.com")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}


	void "it should successfully update a server group"() {
		given:
			def testServerId = 1
			def testServerGroupName = "Booyah!"
			def request = new GetServerGroupRequest()
			request.setServerGroupId(testServerId)
			GetServerGroupResponse response = client.getServerGroup(request)
			ServerGroup serverGroup = response.serverGroup
			def previousName = serverGroup.name
			serverGroup.name = testServerGroupName
			def updateRequest = new UpdateServerGroupRequest().serverGroupId(testServerId).serverGroup(serverGroup)
		when:
			UpdateServerGroupResponse updateServerResponse = client.updateServerGroup(updateRequest)
		then:
			updateServerResponse.success == true
			updateServerResponse.serverGroup?.name == testServerGroupName
		cleanup:
			serverGroup.name = previousName
			def restoreUpdateRequest = new UpdateServerGroupRequest().serverGroupId(testServerId).serverGroup(serverGroup)
			UpdateServerGroupResponse restoreUpdateServerResponse = client.updateServerGroup(restoreUpdateRequest)
			restoreUpdateServerResponse.success == true

	}
}
