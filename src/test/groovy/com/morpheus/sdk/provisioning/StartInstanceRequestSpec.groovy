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
class StartInstanceRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")
	static String TEST_INSTANCE_ID=System.getProperty('morpheus.api.testInstanceId',"212")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully start an instance"() {
		given:
		def instanceLookupRequest = new GetInstanceRequest().instanceId(Integer.parseInt(TEST_INSTANCE_ID))
		GetInstanceResponse instanceLookupResponse = client.getInstance(instanceLookupRequest)
		if(instanceLookupResponse.instance.status == "running") {
			def stopRequest = new StopInstanceRequest()
			stopRequest.instanceId(Integer.parseInt(TEST_INSTANCE_ID))
			StopInstanceResponse stopResponse = client.stopInstance(stopRequest)
		}
		instanceLookupResponse = client.getInstance(instanceLookupRequest)
		assert instanceLookupResponse.instance.status == "stopped", "Instance must be stopped before you can start it"

		def request = new StartInstanceRequest()
		request.instanceId(Integer.parseInt(TEST_INSTANCE_ID))
		when:
		StartInstanceResponse response = client.startInstance(request)
		then:
		response.success == true
	}
}
