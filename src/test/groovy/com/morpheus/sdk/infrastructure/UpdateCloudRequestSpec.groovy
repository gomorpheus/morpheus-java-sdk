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
class UpdateCloudRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")
	static String TEST_CLOUD_ID=System.getProperty('morpheus.api.testUpdateCloudId',"26")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully update a cloud"() {
		given:
			def testCloudId = Integer.parseInt(TEST_CLOUD_ID)
			def testCloudName = "Booyah!"
			def request = new GetCloudRequest()
			request.setCloudId(testCloudId)
			GetCloudResponse response = client.getCloud(request)
			Cloud cloud = response.cloud
			def previousName = cloud.name
			cloud.name = testCloudName
			def updateRequest = new UpdateCloudRequest().cloudId(testCloudId).cloud(cloud)
		when:
			UpdateCloudResponse updateCloudResponse = client.updateCloud(updateRequest)
		then:
			updateCloudResponse.success == true
			updateCloudResponse.cloud?.name == testCloudName
		cleanup:
			cloud.name = previousName
			def restoreUpdateRequest = new UpdateCloudRequest().cloudId(testCloudId).cloud(cloud)
			UpdateCloudResponse restoreUpdateCloudResponse = client.updateCloud(restoreUpdateRequest)
			restoreUpdateCloudResponse.success == true

	}
}
