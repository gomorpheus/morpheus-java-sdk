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
	static String API_URL=System.getProperty('morpheus.api.host',"https://qa.gomorpheus.com")

	@Shared
	MorpheusClient client

	@Shared
	private cert1Response

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)

		//setup a cert to use
		def request = new CreateCertificateRequest()
		SslCertificate certificate = new SslCertificate()
		certificate.name = "Cert 1 GetCert Spec Request"
		certificate.certFile = "cert file"
		certificate.keyFile = "key file"
		request.setCertificate(certificate)
		cert1Response = client.createCertificate(request)
	}

	def cleanup() {
		def request = new DeleteCertificateRequest()
		request.certificateId(cert1Response.certificate.id)
		client.deleteCertificate(request)
	}


	void "it should successfully retrieve an certificate by id"() {
		given:
		def request = new GetCertificateRequest()
		def testCertificateId = cert1Response.certificate.id
		request.setCertificateId(testCertificateId)
		when:
		GetCertificateResponse response = client.getCertificate(request)
		then:
		response.certificate != null
	}
}
