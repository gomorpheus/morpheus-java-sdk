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
import com.morpheus.sdk.infrastructure.*
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class UpdateInstanceRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://qa.gomorpheus.com")
	static String TEST_INSTANCE_ID=System.getProperty('morpheus.api.testInstanceId',"4096")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully update an instance"() {
		given:
		def testInstanceId = Integer.parseInt(TEST_INSTANCE_ID)
		def testInstanceDescription = "Booyah!"
		def request = new GetInstanceRequest()
		request.setInstanceId(testInstanceId)
		GetInstanceResponse response = client.getInstance(request)
		Instance instance = response.instance
		def previousDescription = instance.description
		instance.description = testInstanceDescription
		def updateRequest = new UpdateInstanceRequest().instanceId(testInstanceId).instance(instance)
		when:
		UpdateInstanceResponse updateInstanceResponse = client.updateInstance(updateRequest)
		then:
		updateInstanceResponse.success == true
		updateInstanceResponse.instance?.description == testInstanceDescription
		cleanup:
		instance.description = previousDescription
		def restoreUpdateRequest = new UpdateInstanceRequest().instanceId(testInstanceId).instance(instance)
		UpdateInstanceResponse restoreUpdateInstanceResponse = client.updateInstance(restoreUpdateRequest)
		restoreUpdateInstanceResponse.success == true

	}
}
