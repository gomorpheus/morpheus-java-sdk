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
import com.morpheus.sdk.provisioning.ListInstancesRequest
import com.morpheus.sdk.infrastructure.ListServersRequest
import com.morpheus.sdk.infrastructure.ListServersResponse
import spock.lang.Shared
import spock.lang.Specification
/**
 * @author William Chu
 */
class ListServersRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully list servers"() {
		given:
			def request = new ListServersRequest()
		when:
			ListServersResponse response = client.listServers(request)
		then:
			response.serverCount != null;
			response.servers != null
	}

	/**
	 * NOTE: This test assumes the api being hit in question has at least 2 instances
	 */
	/*
	void "it should properly utilize the offset parameter to offset by 1"() {
		given:
			def firstRequest = new ListServersRequest()
			def request = new ListServersRequest().offset(1)
			def firstResponse = client.listServers(firstRequest)
		when:
		ListServersResponse response = client.listServers(request)
		then:
			response.servers != null
			response.servers[0].id == firstResponse.servers[1].id
	}

	void "it should adhere to the max property of 1 row result"() {
		given:
			def request = new ListServersRequest().max(1)
		when:
		ListServersResponse response = client.listServers(request)
		then:
			response.serverCount > 1;
			response.servers?.size() == 1
	}
	*/
}
