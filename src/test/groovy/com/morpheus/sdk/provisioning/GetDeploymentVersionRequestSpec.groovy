package com.morpheus.sdk.provisioning

import com.morpheus.sdk.DeploymentBaseSpec
/**
 * @author Bob Whiton
 */
class GetDeploymentVersionRequestSpec extends DeploymentBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully retrieve an deployment version by id"() {
		given:
		Deployment deployment = setupDeployment()
		DeploymentVersion version = setupDeploymentVersion(deployment)
		def request = new GetDeploymentVersionRequest().deploymentId(deployment.id).deploymentVersionId(version.id)
		when:
		GetDeploymentVersionResponse response = client.getDeploymentVersion(request)
		then:
		response.deploymentVersion != null
		response.deploymentVersion.id == version.id
		response.deploymentVersion.deploymentId == deployment.id
		cleanup:
		destroyDeploymentVersion(version)
		destroyDeployment(deployment)
	}
}
