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
class DeleteCloudRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://qa.gomorpheus.com")
	static String TEST_SERVER_GROUP_ID=System.getProperty('morpheus.api.testServerGroupId',"17")

	@Shared
	MorpheusClient client

	@Shared
	CloudType morpheusCloudType

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
		ListCloudTypesRequest cloudTypesRequest = new ListCloudTypesRequest()
		ListCloudTypesResponse cloudTypeResponse = client.listCloudTypes(cloudTypesRequest)
		morpheusCloudType = cloudTypeResponse.cloudTypes.find { CloudType cloudType ->
			cloudType.name == 'Morpheus'
		}
	}

	def cleanup() {

	}

	void "it should successfully delete a cloud"() {
		given:
		def cloudTypeRequest = new GetCloudTypeRequest()
		cloudTypeRequest.setCloudTypeId(morpheusCloudType.id)
		GetCloudTypeResponse cloudTypeResponse = client.getCloudType(cloudTypeRequest)

		def createTestCloudRequest = new CreateCloudRequest()
		Cloud cloud = new Cloud()
		def m1 = System.currentTimeMillis()
		cloud.name = "Test DeleteCloudRequest ${m1}"
		cloud.visibility = "public"
		cloud.cloudType = cloudTypeResponse.cloudType
		cloud.groupId = Integer.parseInt(TEST_SERVER_GROUP_ID)
		createTestCloudRequest.setCloud(cloud)
		CreateCloudResponse createTestCloudResponse = client.createCloud(createTestCloudRequest)
		assert createTestCloudResponse.cloud != null

		def request = new DeleteCloudRequest()
		request.cloudId(createTestCloudResponse.cloud.id)

		when:
		DeleteCloudResponse response = client.deleteCloud(request)
		then:
		response.msg == null
		response.success == true
	}
}
