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

import com.morpheus.sdk.DeploymentBaseSpec

/**
 * @author Bob Whiton
 */
class UploadFileRequestSpec extends DeploymentBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully upload a file to an deployment version"() {
		given:
		Deployment deployment = setupDeployment()
		DeploymentVersion version = setupDeploymentVersion(deployment)

		File tempFile = File.createTempFile("mph-java-sdk-test", ".tmp");
		tempFile.write("A TEST!")

		FileInputStream fileInputStream = new FileInputStream(tempFile)

		UploadFileRequest fileUploadRequest = new UploadFileRequest().deploymentId(deployment.id).deploymentVersionId(version.id)
		fileUploadRequest.inputStream(fileInputStream).originalName(tempFile.getName()).destination("");

		when:

		UploadFileResponse response = client.uploadDeploymentVersionFile(fileUploadRequest);

		then:
		response.success == true

		cleanup:
		destroyDeploymentVersion(version)
		destroyDeployment(deployment)
	}
}
