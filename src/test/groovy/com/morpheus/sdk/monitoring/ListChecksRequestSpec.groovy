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

package com.morpheus.sdk.monitoring

import com.morpheus.sdk.BasicCredentialsProvider
import com.morpheus.sdk.MorpheusClient
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class ListChecksRequestSpec extends Specification {
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


	void "it should successfully list checks"() {
		given:
			def request = new ListChecksRequest()
		when:
			ListChecksResponse response = client.listChecks(request)
		then:
			response.checkCount != null;
			response.checks != null
	}

	/**
	 * NOTE: This test assumes the api being hit in question has at least 2 checks
	 */
	void "it should properly utilize the offset parameter to offset by 1"() {
		given:
			def firstRequest = new ListChecksRequest()
			def request = new ListChecksRequest().offset(1)
			def firstResponse = client.listChecks(firstRequest)
		when:
		ListChecksResponse response = client.listChecks(request)
		then:
			response.checks != null
			response.checks[0].id == firstResponse.checks[1].id
	}

	void "it should adhere to the max property of 1 row result"() {
		given:
			def request = new ListChecksRequest().max(1)
		when:
		ListChecksResponse response = client.listChecks(request)
		then:
			response.checkCount > 1;
			response.checks?.size() == 1
	}
}
