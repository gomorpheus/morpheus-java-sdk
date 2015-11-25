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

import com.morpheus.sdk.admin.GetCertificateRequest
import com.morpheus.sdk.admin.GetCertificateResponse
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class GetCertificateRequestSpec extends Specification {
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


	void "it should successfully retrieve an certificate by id"() {
		given:
		def request = new GetCertificateRequest()
		request.setCertificateId(1)
		when:
		GetCertificateResponse response = client.getCertificate(request)
		then:
		response.certificate != null
	}
}
