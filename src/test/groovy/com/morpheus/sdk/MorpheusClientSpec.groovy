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

package com.morpheus.sdk

import spock.lang.Specification
/**
 * @author David Estes
 */
class MorpheusClientSpec extends Specification {
	static String API_USERNAME=System.getProperty('morpheus.api.username')
	static String API_PASSWORD=System.getProperty('morpheus.api.password')
	static String API_URL=System.getProperty('morpheus.api.host',"https://v2.gomorpheus.com")

	def setup() {

	}

	def cleanup() {

	}


	void "it should successfully authenticate against v2"() {
		given:
			def creds = new BasicCredentialsProvider(API_USERNAME,API_PASSWORD)
			def morpheusClient = new MorpheusClient(creds)
		expect:
			morpheusClient.isAuthenticated() == true
	}
}
