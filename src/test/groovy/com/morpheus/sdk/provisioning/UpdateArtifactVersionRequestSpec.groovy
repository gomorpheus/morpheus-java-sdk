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

/**
 * @author Bob Whiton
 */
class UpdateArtifactVersionRequestSpec extends ArtifactBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully update a artifact version"() {
		given:
		Artifact artifact = setupArtifact()
		ArtifactVersion version = setupArtifactVersion(artifact)

		// Update new values
		version.userVersion = "11"
		
		def updateRequest = new UpdateArtifactVersionRequest().artifactId(version.artifactId).artifactVersionId(version.id).artifactVersion(version)
		when:
		UpdateArtifactVersionResponse updateArtifactVersionResponse = client.updateArtifactVersion(updateRequest)
		then:
		updateArtifactVersionResponse.success == true
		updateArtifactVersionResponse.artifactVersion.userVersion == "11"
		cleanup:
		destroyArtifactVersion(version)
		destroyArtifact(artifact)
	}
}
