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
import com.morpheus.sdk.infrastructure.*

/**
 * @author Bob Whiton
 */
class CreateArtifactVersionRequestSpec extends ArtifactBaseSpec  {
	def setup() {
	}

	def cleanup() {
}

	void "it should successfully create a artifact version"() {
		given:
		Artifact artifact = setupArtifact()
		ListArtifactVersionsResponse listResponse = client.listArtifactVersions(new ListArtifactVersionsRequest().artifactId(artifact.id))
		def originalSize = listResponse.artifactVersions.size()

		ArtifactVersion version = new ArtifactVersion()
		version.userVersion = "1.1"
		
		def request = new CreateArtifactVersionRequest().artifactId(artifact.id).artifactVersion(version)
		when:
		CreateArtifactVersionResponse response = client.createArtifactVersion(request)
		then:
		response.artifactVersion != null
		response.artifactVersion.userVersion == "1.1"
		response.artifactVersion.artifactId == artifact.id

		client.listArtifactVersions(new ListArtifactVersionsRequest().artifactId(artifact.id)).artifactVersions.size() == originalSize + 1

		cleanup:
		destroyArtifactVersion(response.artifactVersion)
		destroyArtifact(artifact)
	}
}
