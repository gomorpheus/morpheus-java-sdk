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
class DeleteCheckRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://qa.gomorpheus.com")
	static String TEST_CHECK_TYPE_ID=System.getProperty('morpheus.api.testCheckTypeId',"1")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully delete a check"() {
		given:
		def checkTypeRequest = new GetCheckTypeRequest()
		checkTypeRequest.setCheckTypeId(Integer.parseInt(TEST_CHECK_TYPE_ID))
		GetCheckTypeResponse checkTypeResponse = client.getCheckType(checkTypeRequest)

		def createCheckRequest = new CreateCheckRequest()
		Check check = new Check()
		def m1 = System.currentTimeMillis()
		check.name = "Test Check ${m1}"
		check.checkType = checkTypeResponse.checkType
		createCheckRequest.setCheck(check)
		CreateCheckResponse createCheckResponse = client.createCheck(createCheckRequest)
		assert createCheckResponse.check != null

		DeleteCheckRequest request = new DeleteCheckRequest()
		request.checkId(createCheckResponse.check.id)

		when:
		DeleteCheckResponse response = client.deleteCheck(request)
		then:
		response.msg == null
		response.success == true
	}
}