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

package com.morpheus.sdk

import com.morpheus.sdk.provisioning.ListKeyPairsRequest
import com.morpheus.sdk.provisioning.ListKeyPairsResponse
import spock.lang.Shared
import spock.lang.Specification
/**
 * @author William Chu
 */
class ListKeyPairsRequestSpec extends Specification {
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


	void "it should successfully list key pairs"() {
		given:
			def request = new ListKeyPairsRequest()
		when:
		ListKeyPairsResponse response = client.listKeyPairs(request)
		then:
			response.keyPairCount != null;
			response.keyPairs != null
	}

	/**
	 * NOTE: This test assumes the api being hit in question has at least 2 instances
	 */
	void "it should properly utilize the offset parameter to offset by 1"() {
		given:
			def firstRequest = new ListKeyPairsRequest()
			def request = new ListKeyPairsRequest().offset(1)
			def firstResponse = client.listKeyPairs(firstRequest)
		when:
		ListKeyPairsResponse response = client.listKeyPairs(request)
		then:
			response.keyPairs != null
			response.keyPairs[0].id == firstResponse.keyPairs[1].id
	}

	void "it should adhere to the max property of 1 row result"() {
		given:
			def request = new ListKeyPairsRequest().max(1)
		when:
		ListKeyPairsResponse response = client.listKeyPairs(request)
		then:
			response.keyPairCount > 1;
			response.keyPairs?.size() == 1
	}
}
