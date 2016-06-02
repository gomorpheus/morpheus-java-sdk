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

/**
 * @author Bob Whiton
 */
class CreateDeploymentRequestSpec extends BaseSpec {

	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully create an deployment"() {
		given:
		ListDeploymentsResponse listResponse = client.listDeployments(new ListDeploymentsRequest())
		def originalSize = listResponse.deployments.size()

		Deployment deployment = new Deployment(name: "Test Deployment ${System.currentTimeMillis()}", description: "Deployment Description", externalId: "${System.currentTimeMillis()}")
		CreateDeploymentRequest request = new CreateDeploymentRequest().deployment(deployment)
		when:
		CreateDeploymentResponse response = client.createDeployment(request)
		then:
		response.deployment != null
		client.listDeployments(new ListDeploymentsRequest()).deployments.size() == originalSize + 1
		cleanup:
		DeleteDeploymentRequest cleanupRequest = new DeleteDeploymentRequest()
		cleanupRequest.deploymentId(response.deployment.id)
		DeleteDeploymentResponse cleanupResponse = client.deleteDeployment(cleanupRequest)
	}
}
