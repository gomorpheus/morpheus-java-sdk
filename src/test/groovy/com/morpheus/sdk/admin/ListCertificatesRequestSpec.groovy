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
import com.morpheus.sdk.admin.ListCertificatesRequest
import com.morpheus.sdk.admin.ListCertificatesResponse
import spock.lang.Shared
import spock.lang.Specification
/**
 * @author William Chu
 */
class ListCertificatesRequestSpec extends Specification {
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


	void "it should successfully list certificates"() {
		given:
			def request = new ListCertificatesRequest()
		when:
			ListCertificatesResponse response = client.listCertificates(request)
		then:
			response.certificateCount != null;
			response.certificates != null
	}

	/**
	 * NOTE: This test assumes the api being hit in question has at least 2 instances
	 */
	void "it should properly utilize the offset parameter to offset by 1"() {
		given:
			def firstRequest = new ListCertificatesRequest()
			def request = new ListCertificatesRequest().offset(1)
			def firstResponse = client.listCertificates(firstRequest)
		when:
		ListCertificatesResponse response = client.listCertificates(request)
		then:
			response.certificates != null
			response.certificates[0].id == firstResponse.certificates[1].id
	}

	void "it should adhere to the max property of 1 row result"() {
		given:
			def request = new ListCertificatesRequest().max(1)
		when:
		ListCertificatesResponse response = client.listCertificates(request)
		then:
			response.certificateCount > 1;
			response.certificates?.size() == 1
	}
}
