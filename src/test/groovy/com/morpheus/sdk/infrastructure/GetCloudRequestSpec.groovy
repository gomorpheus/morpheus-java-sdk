package com.morpheus.sdk.infrastructure

import com.morpheus.sdk.BasicCredentialsProvider
import com.morpheus.sdk.MorpheusClient
import com.morpheus.sdk.infrastructure.GetCloudRequest
import com.morpheus.sdk.infrastructure.GetCloudResponse
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class GetCloudRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")
	static String TEST_CLOUD_ID=System.getProperty('morpheus.api.testCloudId',"23")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}

	void "it should successfully retrieve a cloud by id"() {
		given:
		def request = new GetCloudRequest()
		request.setCloudId(Integer.parseInt(TEST_CLOUD_ID))
		when:
		GetCloudResponse response = client.getCloud(request)
		then:
		response.cloud != null
	}
}
