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

import com.google.gson.Gson
import com.morpheus.sdk.BasicCredentialsProvider
import com.morpheus.sdk.MorpheusClient
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class CreateCheckRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://v2.gomorpheus.com")
	static String TEST_CHECK_TYPE_ID=System.getProperty('morpheus.api.testCheckTypeId',"1")
	static String TEST_CHECK_WEB_URL=System.getProperty('morpheus.api.testCheckWebUrl',"http://www.bing.com")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}
/*
	void "it should fail creating a cloud"() {
		given:
		def cloudTypeRequest = new GetCloudTypeRequest()
		cloudTypeRequest.setCloudTypeId(Integer.parseInt(TEST_CLOUD_TYPE_ID))
		GetCloudTypeResponse cloudTypeResponse = client.getCloudType(cloudTypeRequest)

		def request = new CreateCloudRequest()
		Cloud cloud = new Cloud()
		def m1 = System.currentTimeMillis()
		cloud.name = "Test Cloud ${m1}"
		cloud.visibility = "public"
		cloud.cloudType = cloudTypeResponse.cloudType
		request.setCloud(cloud)
		when:
		CreateCloudResponse response = client.createCloud(request)
		then:
		response.errors['groupId'] != null
		response.cloud == null
	}
*/
	void "it should successfully create a check"() {
		given:
		def checkTypeRequest = new GetCheckTypeRequest()
		checkTypeRequest.setCheckTypeId(Integer.parseInt(TEST_CHECK_TYPE_ID))
		GetCheckTypeResponse checkTypeResponse = client.getCheckType(checkTypeRequest)

		def request = new CreateCheckRequest()
		Check check = new Check()
		def m1 = System.currentTimeMillis()
		check.name = "Test Check ${m1}"
		check.checkType = checkTypeResponse.checkType
		check.checkInterval = 60
		def configMap = [:]
		configMap.webUrl = TEST_CHECK_WEB_URL
		configMap.webMethod = "GET"
		def gsonBuilder = new Gson()
		String json = gsonBuilder.toJson(configMap)
		check.config = json
		request.setCheck(check)
		when:
		CreateCheckResponse response = client.createCheck(request)
		then:
		response.check != null

		DeleteCheckRequest cleanupRequest = new DeleteCheckRequest()
		cleanupRequest.checkId(response.check.id)
		DeleteCheckResponse cleanupResponse = client.deleteCheck(cleanupRequest)
		assert cleanupResponse.success == true
	}
}
