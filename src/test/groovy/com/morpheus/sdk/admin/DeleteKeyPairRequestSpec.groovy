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
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class DeleteKeyPairRequestSpec extends Specification {
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

	void "it should successfully delete a key pair"() {
		given:
		def createTestKeyPairRequest = new CreateKeyPairRequest()
		KeyPair keyPair = new KeyPair()
		def m1 = System.currentTimeMillis()
		keyPair.name = "Test Key Pair ${m1}"
		keyPair.publicKey = "fake public key"
		keyPair.privateKey = "fake private key"
		createTestKeyPairRequest.setKeyPair(keyPair)
		CreateKeyPairResponse createTestKeyPairResponse = client.createKeyPair(createTestKeyPairRequest)
		assert createTestKeyPairResponse.keyPair != null

		def request = new DeleteKeyPairRequest()
		request.keyPairId(createTestKeyPairResponse.keyPair.id)

		when:
		DeleteKeyPairResponse response = client.deleteKeyPair(request)
		then:
		response.msg == null
		response.success == true
	}
}
