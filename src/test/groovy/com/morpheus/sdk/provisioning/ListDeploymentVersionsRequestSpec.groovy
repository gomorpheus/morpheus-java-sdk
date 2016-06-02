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
class ListDeploymentVersionsRequestSpec extends DeploymentBaseSpec  {
	def setup() {
	}

	def cleanup() {
	}
	
	void "it should successfully list deployment version rules"() {
		given:
		Deployment deployment = setupDeployment()
		DeploymentVersion version = setupDeploymentVersion(deployment)
		def request = new ListDeploymentVersionsRequest().deploymentId(deployment.id)
		when:
		ListDeploymentVersionsResponse response = client.listDeploymentVersions(request)
		then:
		response.deploymentVersions?.size() == 1
		cleanup:
		destroyDeploymentVersion(response.deploymentVersions[0])
		destroyDeployment(deployment)
	}
}
