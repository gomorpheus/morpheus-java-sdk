package com.morpheus.sdk.infrastructure

import com.morpheus.sdk.BasicCredentialsProvider
import com.morpheus.sdk.MorpheusClient
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Bob Whiton
 */
class GetSecurityGroupRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")
	static String TEST_SECURITY_GROUP_ID=System.getProperty('morpheus.api.testSecurityGroupId',"19")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {
	}

	void "it should successfully retrieve a security group by id"() {
		given:
		def request = new GetSecurityGroupRequest()
		request.setSecurityGroupId(Integer.parseInt(TEST_SECURITY_GROUP_ID))
		when:
		GetSecurityGroupResponse response = client.getSecurityGroup(request)
		then:
		response.securityGroup != null
		response.securityGroup.accountId == 1
		response.securityGroup.name == "My Security Group"
		response.securityGroup.description == "Security Group Description"
	}
}
