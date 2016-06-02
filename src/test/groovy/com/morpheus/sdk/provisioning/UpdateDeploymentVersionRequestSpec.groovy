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
class UpdateDeploymentVersionRequestSpec extends DeploymentBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully update a deployment version"() {
		given:
		Deployment deployment = setupDeployment()
		DeploymentVersion version = setupDeploymentVersion(deployment)

		// Update new values
		version.userVersion = "11"
		
		def updateRequest = new UpdateDeploymentVersionRequest().deploymentId(version.deploymentId).deploymentVersionId(version.id).deploymentVersion(version)
		when:
		UpdateDeploymentVersionResponse updateDeploymentVersionResponse = client.updateDeploymentVersion(updateRequest)
		then:
		updateDeploymentVersionResponse.success == true
		updateDeploymentVersionResponse.deploymentVersion.userVersion == "11"
		cleanup:
		destroyDeploymentVersion(version)
		destroyDeployment(deployment)
	}
}
