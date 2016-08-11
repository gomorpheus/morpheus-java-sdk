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
	static String API_URL=System.getProperty('morpheus.api.host',"https://qa.gomorpheus.com")

	@Shared
	MorpheusClient client

	private cert1Response
	private cert2Response

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)

		//Setup 2 certificates
		def request = new CreateCertificateRequest()
		SslCertificate certificate = new SslCertificate()
		certificate.name = "Cert 1 ListCert Spec Request"
		certificate.certFile = "cert file"
		certificate.keyFile = "key file"
		request.setCertificate(certificate)
		cert1Response = client.createCertificate(request)
		request = new CreateCertificateRequest()
		certificate = new SslCertificate()
		certificate.name = "Cert 2 ListCert Spec Request"
		certificate.certFile = "cert file"
		certificate.keyFile = "key file"
		request.setCertificate(certificate)
		cert2Response = client.createCertificate(request)
	}

	def cleanup() {
		def request = new DeleteCertificateRequest()
		request.certificateId(cert1Response.certificate.id)
		client.deleteCertificate(request)
		request.certificateId(cert2Response.certificate.id)
		client.deleteCertificate(request)
	}


	void "it should successfully list certificates"() {
		given:
			def request = new ListCertificatesRequest()
		when:
			ListCertificatesResponse response = client.listCertificates(request)
		then:
			response.meta.size != null;
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
			response.meta.total > 1;
			response.meta.size == 1
			response.certificates?.size() == 1
	}
}
