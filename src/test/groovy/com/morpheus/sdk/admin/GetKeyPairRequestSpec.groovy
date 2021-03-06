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

package com.morpheus.sdk.admin

import com.morpheus.sdk.BasicCredentialsProvider
import com.morpheus.sdk.MorpheusClient
import com.morpheus.sdk.admin.GetKeyPairRequest
import com.morpheus.sdk.admin.GetKeyPairResponse
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class GetKeyPairRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://qa.gomorpheus.com")

	@Shared
	MorpheusClient client

	@Shared
	private resp1

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)

		//create test keypair first
		def request = new CreateKeyPairRequest()
		KeyPair keyPair = new KeyPair()
		def m1 = System.currentTimeMillis()
		keyPair.name = "Test GetKeyPairSpec ${m1}"
		keyPair.publicKey = "fake public key"
		keyPair.privateKey = "fake private key"
		request.setKeyPair(keyPair)
		resp1 = client.createKeyPair(request)

	}

	def cleanup() {
		DeleteKeyPairRequest request = new DeleteKeyPairRequest()
		request.keyPairId = resp1.keyPair.id
		client.deleteKeyPair(request)
	}


	void "it should successfully retrieve an key pair by id"() {
		given:
		def request = new GetKeyPairRequest()
		request.setKeyPairId(resp1.keyPair.id)
		when:
		GetKeyPairResponse response = client.getKeyPair(request)
		then:
		response.keyPair != null
	}
}
