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
class StopInstanceRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://v2.gomorpheus.com")
	static String TEST_INSTANCE_ID=System.getProperty('morpheus.api.testInstanceId',"23")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully stop an instance"() {
		given:
		def instanceLookupRequest = new GetInstanceRequest().instanceId(Integer.parseInt(TEST_INSTANCE_ID))
		GetInstanceResponse instanceLookupResponse = client.getInstance(instanceLookupRequest)
		assert instanceLookupResponse.instance.status == "running", "Instance must be running before you can stop it"
		def request = new StopInstanceRequest()
		request.instanceId(Integer.parseInt(TEST_INSTANCE_ID))
		when:
		StopInstanceResponse response = client.stopInstance(request)
		then:
		response.success == true
	}
}
