package com.morpheus.sdk.provisioning

import com.morpheus.sdk.DeploymentBaseSpec

/**
 * @author Bob Whiton
 */
class GetDeploymentRequestSpec extends DeploymentBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully retrieve an deployment by id"() {
		given:
		Deployment deployment = setupDeployment()
		def request = new GetDeploymentRequest().deploymentId(deployment.id)
		when:
		GetDeploymentResponse response = client.getDeployment(request)
		then:
		response.deployment != null
		response.deployment.accountId == deployment.accountId
		response.deployment.name == deployment.name
		response.deployment.description == deployment.description
		cleanup:
		destroyDeployment(deployment)
	}
}
