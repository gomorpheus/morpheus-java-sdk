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
class CreateDeploymentVersionRequestSpec extends DeploymentBaseSpec  {
	def setup() {
	}

	def cleanup() {
}

	void "it should successfully create a deployment version"() {
		given:
		Deployment deployment = setupDeployment()
		ListDeploymentVersionsResponse listResponse = client.listDeploymentVersions(new ListDeploymentVersionsRequest().deploymentId(deployment.id))
		def originalSize = listResponse.deploymentVersions.size()

		DeploymentVersion version = new DeploymentVersion()
		version.userVersion = "1.1"
		
		def request = new CreateDeploymentVersionRequest().deploymentId(deployment.id).deploymentVersion(version)
		when:
		CreateDeploymentVersionResponse response = client.createDeploymentVersion(request)
		then:
		response.deploymentVersion != null
		response.deploymentVersion.userVersion == "1.1"
		response.deploymentVersion.deploymentId == deployment.id

		client.listDeploymentVersions(new ListDeploymentVersionsRequest().deploymentId(deployment.id)).deploymentVersions.size() == originalSize + 1

		cleanup:
		destroyDeploymentVersion(response.deploymentVersion)
		destroyDeployment(deployment)
	}
}
