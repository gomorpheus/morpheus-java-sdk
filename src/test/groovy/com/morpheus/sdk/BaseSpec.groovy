package com.morpheus.sdk

import spock.lang.Shared
import spock.lang.Specification

public abstract class BaseSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://qa.gomorpheus.com")
	static String API_TOKEN=System.getProperty('morpheus.api.token')

	@Shared
	MorpheusClient client

	def setup() {
		if(API_USERNAME != null) {
			def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
			MorpheusClient client = new MorpheusClient(creds)
			client.setEndpointUrl(API_URL)
			this.client = client
		} else if(API_TOKEN != null) {
			def creds = new AccessTokenProvider(API_TOKEN)
			MorpheusClient client = new MorpheusClient(creds)
			client.setEndpointUrl(API_URL)
			this.client = client
		}
	}

	def cleanup() {
	}
}