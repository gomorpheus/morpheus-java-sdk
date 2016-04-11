package com.morpheus.sdk

import spock.lang.Shared
import spock.lang.Specification

public abstract class BaseSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://morpheus.bertramlabs.com")

	@Shared
	MorpheusClient client

	def setup() {
		def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
		MorpheusClient client = new MorpheusClient(creds)
		client.setEndpointUrl(API_URL)
		this.client = client
	}

	def cleanup() {
	}
}