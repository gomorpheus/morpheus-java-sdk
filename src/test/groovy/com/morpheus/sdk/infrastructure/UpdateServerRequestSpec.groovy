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
class UpdateServerRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://qa.gomorpheus.com")
	static String TEST_SERVER_ID=System.getProperty('morpheus.api.testUpdateServerId',"43")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully update a server"() {
		given:
		def testServerId = Integer.parseInt(TEST_SERVER_ID)
		def request = new GetServerRequest()
		request.setServerId(testServerId)
		GetServerResponse response = client.getServer(request)
		Server server = response.server
		def previousVisibility = server.visibility
		def testServerVisibility = "private"
		if(previousVisibility && previousVisibility == "private") {
			testServerVisibility = "public"
		}
		server.visibility = testServerVisibility
		def updateRequest = new UpdateServerRequest().serverId(testServerId).server(server)
		when:
		UpdateServerResponse updateServerResponse = client.updateServer(updateRequest)
		then:
		updateServerResponse.success == true
		updateServerResponse.server?.visibility == testServerVisibility
		cleanup:
		server.visibility = previousVisibility
		def restoreUpdateRequest = new UpdateServerRequest().serverId(testServerId).server(server)
		UpdateServerResponse restoreUpdateServerResponse = client.updateServer(restoreUpdateRequest)
		restoreUpdateServerResponse.success == true

	}
}