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

package com.morpheus.sdk.provisioning

import com.morpheus.sdk.ArtifactBaseSpec
import com.morpheus.sdk.provisioning.*

/**
 * @author Bob Whiton
 */
class ListArtifactsRequestSpec extends ArtifactBaseSpec {
	def setup() {
	}

	def cleanup() {
	}
	
	void "it should successfully list artifacts"() {
		given:
		Artifact artifact = setupArtifact()
		def request = new ListArtifactsRequest()
		when:
		ListArtifactsResponse response = client.listArtifacts(request)
		then:
		response.artifacts?.size() > 0
		cleanup:
		destroyArtifact(artifact)
	}

	void "it should successfully list artifacts with a certain name"() {
		given:

		Artifact artifact = new Artifact(name: "Jenkins Example")
		CreateArtifactResponse createResponse = client.createArtifact(new CreateArtifactRequest().artifact(artifact))
		Artifact createdArtifact = createResponse.artifact
		def request = new ListArtifactsRequest().name("Jenkins Example")
		when:
		ListArtifactsResponse response = client.listArtifacts(request)
		then:
		response.artifacts?.size() == 1
		response.artifacts?.get(0).id == createdArtifact.id;
		cleanup:
		destroyArtifact(createdArtifact)
	}
}
