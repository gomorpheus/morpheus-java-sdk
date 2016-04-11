package com.morpheus.sdk.infrastructure

import com.morpheus.sdk.BasicCredentialsProvider
import com.morpheus.sdk.MorpheusClient
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Bob Whiton
 */
class GetSecurityGroupRuleRequestSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")
	static String TEST_SECURITY_GROUP_ID=System.getProperty('morpheus.api.testSecurityGroupId',"19")
	static String TEST_SECURITY_GROUP_RULE_ID=System.getProperty('morpheus.api.testSecurityGroupId',"30")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
	}

	def cleanup() {
	}

	void "it should successfully retrieve a security group rule by id"() {
		given:
		def request = new GetSecurityGroupRuleRequest().securityGroupId(Integer.parseInt(TEST_SECURITY_GROUP_ID)).securityGroupRuleId(Integer.parseInt(TEST_SECURITY_GROUP_RULE_ID))
		when:
		GetSecurityGroupRuleResponse response = client.getSecurityGroupRule(request)
		then:
		response.securityGroupRule != null
		response.securityGroupRule.id == Integer.parseInt(TEST_SECURITY_GROUP_RULE_ID)
		response.securityGroupRule.securityGroupId == Integer.parseInt(TEST_SECURITY_GROUP_ID)
		response.securityGroupRule.source == "10.100.54.9/32"
		response.securityGroupRule.portRange == "99"
		response.securityGroupRule.protocol == "tcp"
		response.securityGroupRule.customRule == true
		response.securityGroupRule.instanceTypeId == null
	}
}
