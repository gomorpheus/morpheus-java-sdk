package com.morpheus.sdk

import com.morpheus.sdk.provisioning.CreateDeploymentRequest
import com.morpheus.sdk.provisioning.CreateDeploymentVersionRequest
import com.morpheus.sdk.provisioning.CreateDeploymentVersionResponse
import com.morpheus.sdk.provisioning.CreateDeploymentResponse
import com.morpheus.sdk.provisioning.DeleteDeploymentRequest
import com.morpheus.sdk.provisioning.DeleteDeploymentVersionRequest
import com.morpheus.sdk.provisioning.Deployment
import com.morpheus.sdk.provisioning.DeploymentVersion

public abstract class DeploymentBaseSpec extends BaseSpec {

	Deployment setupDeployment() {
		Deployment deployment = new Deployment(name: "Test Deployment ${System.currentTimeMillis()}", description: "Deployment description")
		CreateDeploymentResponse createResponse = client.createDeployment(new CreateDeploymentRequest().deployment(deployment))
		return createResponse.deployment
	}

	void destroyDeployment(Deployment deployment) {
		def request = new DeleteDeploymentRequest().deploymentId(deployment.id)
		client.deleteDeployment(request)
	}

	DeploymentVersion setupDeploymentVersion(Deployment deployment) {
		DeploymentVersion version = new DeploymentVersion()
		version.userVersion = "1.0"

		def request = new CreateDeploymentVersionRequest().deploymentId(deployment.id).deploymentVersion(version)
		CreateDeploymentVersionResponse response = client.createDeploymentVersion(request)
		return response.deploymentVersion
	}

	void destroyDeploymentVersion(DeploymentVersion deploymentVersion) {
		def request = new DeleteDeploymentVersionRequest().deploymentId(deploymentVersion.deploymentId).deploymentVersionId(deploymentVersion.id)
		client.deleteDeploymentVersion(request)
	}
}