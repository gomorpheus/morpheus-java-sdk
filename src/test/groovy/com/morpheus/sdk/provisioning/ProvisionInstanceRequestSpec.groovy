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
import com.morpheus.sdk.infrastructure.GetCloudRequest
import com.morpheus.sdk.infrastructure.GetCloudResponse
import com.morpheus.sdk.infrastructure.Server
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class ProvisionInstanceRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://v2.gomorpheus.com")
	static String TEST_CLOUD_ID=System.getProperty('morpheus.api.testCloudId',"9")
	static String TEST_SSH_USERNAME=System.getProperty('morpheus.api.testServerSshUsername',"admin")
	static String TEST_SSH_PASSWORD=System.getProperty('morpheus.api.testServerSshPassword',"password")
	static String TEST_SSH_HOST=System.getProperty('morpheus.api.testServerSshHost',"172.0.0.1")
	static String TEST_SSH_PORT=System.getProperty('morpheus.api.testServerSshPort',"22")
	static String TEST_SERVER_NAME=System.getProperty('morpheus.api.testServerName',"test-server-name")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully provision a server"() {
		given:
		def cloudRequest = new GetCloudRequest()
		cloudRequest.setCloudId(Integer.parseInt(TEST_CLOUD_ID))
		GetCloudResponse cloudResponse = client.getCloud(cloudRequest)

		Server server = new Server()
		server.name = TEST_SERVER_NAME
		server.cloud = cloudResponse.cloud
		server.sshUsername = TEST_SSH_USERNAME
		server.sshPassword = TEST_SSH_PASSWORD
		server.sshHost = TEST_SSH_HOST
		server.sshPort = Integer.parseInt(TEST_SSH_PORT)
		server.dataDevice = "/dev/sdb"

		def request = new ProvisionServerRequest()
		request.setServer(server)
		request.getNetwork().put("name", "eth0")
		when:
		ProvisionServerResponse response = client.provisionServer(request)
		then:
		response.errors.size() == 0
		response.success == true
		response.server != null
		response.server.name == server.name

		cleanup:
		DeleteServerRequest deleteServerRequest = new DeleteServerRequest()
		deleteServerRequest.serverId(response.server.id)
		DeleteServerResponse deleteServerResponse = client.deleteServer(deleteServerRequest)
		assert deleteServerResponse.msg == null
	}
}
