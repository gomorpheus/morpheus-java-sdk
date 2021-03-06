package com.morpheus.sdk.infrastructure

import com.morpheus.sdk.BasicCredentialsProvider
import com.morpheus.sdk.MorpheusClient
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author William Chu
 */
class GetServerGroupRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://qa.gomorpheus.com")
	static String TEST_SERVER_GROUP_ID=System.getProperty('morpheus.api.testServerGroupId',"17")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {

	}


	void "it should successfully retrieve a server group by id"() {
		given:
		def request = new GetServerGroupRequest()
		request.setServerGroupId(Integer.parseInt(TEST_SERVER_GROUP_ID))
		when:
		GetServerGroupResponse response = client.getServerGroup(request)
		then:
		response.serverGroup != null
	}
}
