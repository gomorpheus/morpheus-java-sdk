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

import com.morpheus.sdk.BaseSpec
import com.morpheus.sdk.provisioning.*

/**
 * @author Bob Whiton
 */
class CreateArtifactRequestSpec extends BaseSpec {

	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully create an artifact"() {
		given:
		ListArtifactsResponse listResponse = client.listArtifacts(new ListArtifactsRequest())
		def originalSize = listResponse.artifacts.size()

		Artifact artifact = new Artifact(name: "Test Artifact ${System.currentTimeMillis()}", description: "Artifact Description", externalId: "${System.currentTimeMillis()}")
		CreateArtifactRequest request = new CreateArtifactRequest().artifact(artifact)
		when:
		CreateArtifactResponse response = client.createArtifact(request)
		then:
		response.artifact != null
		client.listArtifacts(new ListArtifactsRequest()).artifacts.size() == originalSize + 1
		cleanup:
		DeleteArtifactRequest cleanupRequest = new DeleteArtifactRequest()
		cleanupRequest.artifactId(response.artifact.id)
		DeleteArtifactResponse cleanupResponse = client.deleteArtifact(cleanupRequest)
	}
}