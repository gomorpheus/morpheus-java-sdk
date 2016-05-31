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
class UploadFileRequestSpec extends ArtifactBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully upload a file to an artifact version"() {
		given:
		Artifact artifact = setupArtifact()
		ArtifactVersion version = setupArtifactVersion(artifact)

		File tempFile = File.createTempFile("mph-java-sdk-test", ".tmp");
		tempFile.write("A TEST!")

		FileInputStream fileInputStream = new FileInputStream(tempFile)

		UploadFileRequest fileUploadRequest = new UploadFileRequest().artifactId(artifact.id).artifactVersionId(version.id)
		fileUploadRequest.inputStream(fileInputStream).originalName(tempFile.getName()).destination("");

		when:

		UploadFileResponse response = client.uploadArtifactVersionFile(fileUploadRequest);

		then:
		response.success == true

		cleanup:
		destroyArtifactVersion(version)
		destroyArtifact(artifact)
	}
}
