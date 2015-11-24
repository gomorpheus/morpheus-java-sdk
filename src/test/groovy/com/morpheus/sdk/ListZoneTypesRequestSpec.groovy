package com.morpheus.sdk

import com.morpheus.sdk.infrastructure.ListZoneTypesRequest
import com.morpheus.sdk.infrastructure.ListZoneTypesResponse
import spock.lang.Shared
import spock.lang.Specification
/**
 * @author William Chu
 */
class ListZoneTypesRequestSpec extends Specification {
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
	
	void "it should successfully list zone types"() {
		given:
		def request = new ListZoneTypesRequest()
		when:
		ListZoneTypesResponse response = client.listZoneTypes(request)
		then:
		response.zoneTypes?.size() > 0
	}
}
