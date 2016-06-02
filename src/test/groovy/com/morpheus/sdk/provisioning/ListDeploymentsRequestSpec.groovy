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
class ListDeploymentsRequestSpec extends DeploymentBaseSpec {
	def setup() {
	}

	def cleanup() {
	}
	
	void "it should successfully list deployments"() {
		given:
		Deployment deployment = setupDeployment()
		def request = new ListDeploymentsRequest()
		when:
		ListDeploymentsResponse response = client.listDeployments(request)
		then:
		response.deployments?.size() > 0
		cleanup:
		destroyDeployment(deployment)
	}

	void "it should successfully list deployments with a certain name"() {
		given:

		Deployment deployment = new Deployment(name: "Jenkins Example")
		CreateDeploymentResponse createResponse = client.createDeployment(new CreateDeploymentRequest().deployment(deployment))
		Deployment createdDeployment = createResponse.deployment
		def request = new ListDeploymentsRequest().name("Jenkins Example")
		when:
		ListDeploymentsResponse response = client.listDeployments(request)
		then:
		response.deployments?.size() == 1
		response.deployments?.get(0).id == createdDeployment.id;
		cleanup:
		destroyDeployment(createdDeployment)
	}
}
