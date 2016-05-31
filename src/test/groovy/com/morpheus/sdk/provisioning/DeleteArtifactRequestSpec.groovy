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
class DeleteArtifactRequestSpec extends BaseSpec{

	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully delete an artifact"() {
		given:
		Artifact artifact = new Artifact(name: "Test Artifact ${System.currentTimeMillis()}", description: "Artifact Description")
		CreateArtifactResponse createResponse = client.createArtifact(new CreateArtifactRequest().artifact(artifact))
		def request = new DeleteArtifactRequest().artifactId(createResponse.artifact.id)
		when:
		DeleteArtifactResponse response = client.deleteArtifact(request)
		then:
		response.msg == null
		response.success == true
	}
}